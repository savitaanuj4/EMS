
package com.mysql.cj.log;

public class BaseMetricsHolder
{
    private static final int HISTOGRAM_BUCKETS = 20;
    private long longestQueryTimeMs;
    private long maximumNumberTablesAccessed;
    private long minimumNumberTablesAccessed;
    private long numberOfPreparedExecutes;
    private long numberOfPrepares;
    private long numberOfQueriesIssued;
    private long numberOfResultSetsCreated;
    private long[] numTablesMetricsHistBreakpoints;
    private int[] numTablesMetricsHistCounts;
    private long[] oldHistBreakpoints;
    private int[] oldHistCounts;
    private long shortestQueryTimeMs;
    private double totalQueryTimeMs;
    private long[] perfMetricsHistBreakpoints;
    private int[] perfMetricsHistCounts;
    private long queryTimeCount;
    private double queryTimeSum;
    private double queryTimeSumSquares;
    private double queryTimeMean;
    
    public BaseMetricsHolder() {
        this.longestQueryTimeMs = 0L;
        this.maximumNumberTablesAccessed = 0L;
        this.minimumNumberTablesAccessed = Long.MAX_VALUE;
        this.numberOfPreparedExecutes = 0L;
        this.numberOfPrepares = 0L;
        this.numberOfQueriesIssued = 0L;
        this.numberOfResultSetsCreated = 0L;
        this.oldHistBreakpoints = null;
        this.oldHistCounts = null;
        this.shortestQueryTimeMs = Long.MAX_VALUE;
        this.totalQueryTimeMs = 0.0;
    }
    
    private void createInitialHistogram(final long[] breakpoints, long lowerBound, final long upperBound) {
        double bucketSize = (upperBound - (double)lowerBound) / 20.0 * 1.25;
        if (bucketSize < 1.0) {
            bucketSize = 1.0;
        }
        for (int i = 0; i < 20; ++i) {
            breakpoints[i] = lowerBound;
            lowerBound += (long)bucketSize;
        }
    }
    
    private void addToHistogram(final int[] histogramCounts, final long[] histogramBreakpoints, final long value, final int numberOfTimes, final long currentLowerBound, final long currentUpperBound) {
        if (histogramCounts == null) {
            this.createInitialHistogram(histogramBreakpoints, currentLowerBound, currentUpperBound);
        }
        else {
            for (int i = 0; i < 20; ++i) {
                if (histogramBreakpoints[i] >= value) {
                    final int n = i;
                    histogramCounts[n] += numberOfTimes;
                    break;
                }
            }
        }
    }
    
    private void addToPerformanceHistogram(final long value, final int numberOfTimes) {
        this.checkAndCreatePerformanceHistogram();
        this.addToHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, value, numberOfTimes, (this.shortestQueryTimeMs == Long.MAX_VALUE) ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
    }
    
    private void addToTablesAccessedHistogram(final long value, final int numberOfTimes) {
        this.checkAndCreateTablesAccessedHistogram();
        this.addToHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, value, numberOfTimes, (this.minimumNumberTablesAccessed == Long.MAX_VALUE) ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
    }
    
    private void checkAndCreatePerformanceHistogram() {
        if (this.perfMetricsHistCounts == null) {
            this.perfMetricsHistCounts = new int[20];
        }
        if (this.perfMetricsHistBreakpoints == null) {
            this.perfMetricsHistBreakpoints = new long[20];
        }
    }
    
    private void checkAndCreateTablesAccessedHistogram() {
        if (this.numTablesMetricsHistCounts == null) {
            this.numTablesMetricsHistCounts = new int[20];
        }
        if (this.numTablesMetricsHistBreakpoints == null) {
            this.numTablesMetricsHistBreakpoints = new long[20];
        }
    }
    
    public void registerQueryExecutionTime(final long queryTimeMs) {
        if (queryTimeMs > this.longestQueryTimeMs) {
            this.longestQueryTimeMs = queryTimeMs;
            this.repartitionPerformanceHistogram();
        }
        this.addToPerformanceHistogram(queryTimeMs, 1);
        if (queryTimeMs < this.shortestQueryTimeMs) {
            this.shortestQueryTimeMs = ((queryTimeMs == 0L) ? 1L : queryTimeMs);
        }
        ++this.numberOfQueriesIssued;
        this.totalQueryTimeMs += queryTimeMs;
    }
    
    private void repartitionHistogram(final int[] histCounts, final long[] histBreakpoints, final long currentLowerBound, final long currentUpperBound) {
        if (this.oldHistCounts == null) {
            this.oldHistCounts = new int[histCounts.length];
            this.oldHistBreakpoints = new long[histBreakpoints.length];
        }
        System.arraycopy(histCounts, 0, this.oldHistCounts, 0, histCounts.length);
        System.arraycopy(histBreakpoints, 0, this.oldHistBreakpoints, 0, histBreakpoints.length);
        this.createInitialHistogram(histBreakpoints, currentLowerBound, currentUpperBound);
        for (int i = 0; i < 20; ++i) {
            this.addToHistogram(histCounts, histBreakpoints, this.oldHistBreakpoints[i], this.oldHistCounts[i], currentLowerBound, currentUpperBound);
        }
    }
    
    private void repartitionPerformanceHistogram() {
        this.checkAndCreatePerformanceHistogram();
        this.repartitionHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, (this.shortestQueryTimeMs == Long.MAX_VALUE) ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
    }
    
    private void repartitionTablesAccessedHistogram() {
        this.checkAndCreateTablesAccessedHistogram();
        this.repartitionHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, (this.minimumNumberTablesAccessed == Long.MAX_VALUE) ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
    }
    
    public void reportMetrics(final Log log) {
        final StringBuilder logMessage = new StringBuilder(256);
        logMessage.append("** Performance Metrics Report **\n");
        logMessage.append("\nLongest reported query: " + this.longestQueryTimeMs + " ms");
        logMessage.append("\nShortest reported query: " + this.shortestQueryTimeMs + " ms");
        logMessage.append("\nAverage query execution time: " + this.totalQueryTimeMs / this.numberOfQueriesIssued + " ms");
        logMessage.append("\nNumber of statements executed: " + this.numberOfQueriesIssued);
        logMessage.append("\nNumber of result sets created: " + this.numberOfResultSetsCreated);
        logMessage.append("\nNumber of statements prepared: " + this.numberOfPrepares);
        logMessage.append("\nNumber of prepared statement executions: " + this.numberOfPreparedExecutes);
        if (this.perfMetricsHistBreakpoints != null) {
            logMessage.append("\n\n\tTiming Histogram:\n");
            final int maxNumPoints = 20;
            int highestCount = Integer.MIN_VALUE;
            for (int i = 0; i < 20; ++i) {
                if (this.perfMetricsHistCounts[i] > highestCount) {
                    highestCount = this.perfMetricsHistCounts[i];
                }
            }
            if (highestCount == 0) {
                highestCount = 1;
            }
            for (int i = 0; i < 19; ++i) {
                if (i == 0) {
                    logMessage.append("\n\tless than " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
                }
                else {
                    logMessage.append("\n\tbetween " + this.perfMetricsHistBreakpoints[i] + " and " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
                }
                logMessage.append("\t");
                for (int numPointsToGraph = (int)(maxNumPoints * (this.perfMetricsHistCounts[i] / (double)highestCount)), j = 0; j < numPointsToGraph; ++j) {
                    logMessage.append("*");
                }
                if (this.longestQueryTimeMs < this.perfMetricsHistCounts[i + 1]) {
                    break;
                }
            }
            if (this.perfMetricsHistBreakpoints[18] < this.longestQueryTimeMs) {
                logMessage.append("\n\tbetween ");
                logMessage.append(this.perfMetricsHistBreakpoints[18]);
                logMessage.append(" and ");
                logMessage.append(this.perfMetricsHistBreakpoints[19]);
                logMessage.append(" ms: \t");
                logMessage.append(this.perfMetricsHistCounts[19]);
            }
        }
        if (this.numTablesMetricsHistBreakpoints != null) {
            logMessage.append("\n\n\tTable Join Histogram:\n");
            final int maxNumPoints = 20;
            int highestCount = Integer.MIN_VALUE;
            for (int i = 0; i < 20; ++i) {
                if (this.numTablesMetricsHistCounts[i] > highestCount) {
                    highestCount = this.numTablesMetricsHistCounts[i];
                }
            }
            if (highestCount == 0) {
                highestCount = 1;
            }
            for (int i = 0; i < 19; ++i) {
                if (i == 0) {
                    logMessage.append("\n\t" + this.numTablesMetricsHistBreakpoints[i + 1] + " tables or less: \t\t" + this.numTablesMetricsHistCounts[i]);
                }
                else {
                    logMessage.append("\n\tbetween " + this.numTablesMetricsHistBreakpoints[i] + " and " + this.numTablesMetricsHistBreakpoints[i + 1] + " tables: \t" + this.numTablesMetricsHistCounts[i]);
                }
                logMessage.append("\t");
                for (int numPointsToGraph = (int)(maxNumPoints * (this.numTablesMetricsHistCounts[i] / (double)highestCount)), j = 0; j < numPointsToGraph; ++j) {
                    logMessage.append("*");
                }
                if (this.maximumNumberTablesAccessed < this.numTablesMetricsHistBreakpoints[i + 1]) {
                    break;
                }
            }
            if (this.numTablesMetricsHistBreakpoints[18] < this.maximumNumberTablesAccessed) {
                logMessage.append("\n\tbetween ");
                logMessage.append(this.numTablesMetricsHistBreakpoints[18]);
                logMessage.append(" and ");
                logMessage.append(this.numTablesMetricsHistBreakpoints[19]);
                logMessage.append(" tables: ");
                logMessage.append(this.numTablesMetricsHistCounts[19]);
            }
        }
        log.logInfo(logMessage);
    }
    
    public void reportNumberOfTablesAccessed(final int numTablesAccessed) {
        if (numTablesAccessed < this.minimumNumberTablesAccessed) {
            this.minimumNumberTablesAccessed = numTablesAccessed;
        }
        if (numTablesAccessed > this.maximumNumberTablesAccessed) {
            this.maximumNumberTablesAccessed = numTablesAccessed;
            this.repartitionTablesAccessedHistogram();
        }
        this.addToTablesAccessedHistogram(numTablesAccessed, 1);
    }
    
    public void incrementNumberOfPreparedExecutes() {
        ++this.numberOfPreparedExecutes;
        ++this.numberOfQueriesIssued;
    }
    
    public void incrementNumberOfPrepares() {
        ++this.numberOfPrepares;
    }
    
    public void incrementNumberOfResultSetsCreated() {
        ++this.numberOfResultSetsCreated;
    }
    
    public void reportQueryTime(final long millisOrNanos) {
        ++this.queryTimeCount;
        this.queryTimeSum += millisOrNanos;
        this.queryTimeSumSquares += millisOrNanos * millisOrNanos;
        this.queryTimeMean = (this.queryTimeMean * (this.queryTimeCount - 1L) + millisOrNanos) / this.queryTimeCount;
    }
    
    public boolean isAbonormallyLongQuery(final long millisOrNanos) {
        if (this.queryTimeCount < 15L) {
            return false;
        }
        final double stddev = Math.sqrt((this.queryTimeSumSquares - this.queryTimeSum * this.queryTimeSum / this.queryTimeCount) / (this.queryTimeCount - 1L));
        return millisOrNanos > this.queryTimeMean + 5.0 * stddev;
    }
}
