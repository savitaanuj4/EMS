
package com.mysql.cj.jdbc;

import java.lang.ref.PhantomReference;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.ConcurrentHashMap;
import com.mysql.cj.protocol.NetworkResources;
import java.lang.ref.Reference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.ExecutorService;
import com.mysql.cj.MysqlConnection;
import java.lang.ref.ReferenceQueue;
import java.util.Set;

public class AbandonedConnectionCleanupThread implements Runnable
{
    private static final Set<ConnectionFinalizerPhantomReference> connectionFinalizerPhantomRefs;
    private static final ReferenceQueue<MysqlConnection> referenceQueue;
    private static final ExecutorService cleanupThreadExcecutorService;
    private static Thread threadRef;
    private static Lock threadRefLock;
    
    private AbandonedConnectionCleanupThread() {
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    this.checkThreadContextClassLoader();
                    final Reference<? extends MysqlConnection> reference = AbandonedConnectionCleanupThread.referenceQueue.remove(5000L);
                    if (reference != null) {
                        finalizeResource((ConnectionFinalizerPhantomReference)reference);
                    }
                }
            }
            catch (InterruptedException e) {
                AbandonedConnectionCleanupThread.threadRefLock.lock();
                try {
                    AbandonedConnectionCleanupThread.threadRef = null;
                    Reference<? extends MysqlConnection> reference2;
                    while ((reference2 = AbandonedConnectionCleanupThread.referenceQueue.poll()) != null) {
                        finalizeResource((ConnectionFinalizerPhantomReference)reference2);
                    }
                    AbandonedConnectionCleanupThread.connectionFinalizerPhantomRefs.clear();
                }
                finally {
                    AbandonedConnectionCleanupThread.threadRefLock.unlock();
                }
            }
            catch (Exception ex) {
                continue;
            }
            break;
        }
    }
    
    private void checkThreadContextClassLoader() {
        try {
            AbandonedConnectionCleanupThread.threadRef.getContextClassLoader().getResource("");
        }
        catch (Throwable e) {
            uncheckedShutdown();
        }
    }
    
    private static boolean consistentClassLoaders() {
        AbandonedConnectionCleanupThread.threadRefLock.lock();
        try {
            if (AbandonedConnectionCleanupThread.threadRef == null) {
                return false;
            }
            final ClassLoader callerCtxClassLoader = Thread.currentThread().getContextClassLoader();
            final ClassLoader threadCtxClassLoader = AbandonedConnectionCleanupThread.threadRef.getContextClassLoader();
            return callerCtxClassLoader != null && threadCtxClassLoader != null && callerCtxClassLoader == threadCtxClassLoader;
        }
        finally {
            AbandonedConnectionCleanupThread.threadRefLock.unlock();
        }
    }
    
    private static void shutdown(final boolean checked) {
        if (checked && !consistentClassLoaders()) {
            return;
        }
        AbandonedConnectionCleanupThread.cleanupThreadExcecutorService.shutdownNow();
    }
    
    public static void checkedShutdown() {
        shutdown(true);
    }
    
    public static void uncheckedShutdown() {
        shutdown(false);
    }
    
    public static boolean isAlive() {
        AbandonedConnectionCleanupThread.threadRefLock.lock();
        try {
            return AbandonedConnectionCleanupThread.threadRef != null && AbandonedConnectionCleanupThread.threadRef.isAlive();
        }
        finally {
            AbandonedConnectionCleanupThread.threadRefLock.unlock();
        }
    }
    
    protected static void trackConnection(final MysqlConnection conn, final NetworkResources io) {
        AbandonedConnectionCleanupThread.threadRefLock.lock();
        try {
            if (isAlive()) {
                final ConnectionFinalizerPhantomReference reference = new ConnectionFinalizerPhantomReference(conn, io, AbandonedConnectionCleanupThread.referenceQueue);
                AbandonedConnectionCleanupThread.connectionFinalizerPhantomRefs.add(reference);
            }
        }
        finally {
            AbandonedConnectionCleanupThread.threadRefLock.unlock();
        }
    }
    
    private static void finalizeResource(final ConnectionFinalizerPhantomReference reference) {
        try {
            reference.finalizeResources();
            reference.clear();
        }
        finally {
            AbandonedConnectionCleanupThread.connectionFinalizerPhantomRefs.remove(reference);
        }
    }
    
    static {
        connectionFinalizerPhantomRefs = ConcurrentHashMap.newKeySet();
        referenceQueue = new ReferenceQueue<MysqlConnection>();
        AbandonedConnectionCleanupThread.threadRef = null;
        AbandonedConnectionCleanupThread.threadRefLock = new ReentrantLock();
        final Thread t;
        ClassLoader classLoader;
        final Thread threadRef;
        (cleanupThreadExcecutorService = Executors.newSingleThreadExecutor(r -> {
            t = new Thread(r, "mysql-cj-abandoned-connection-cleanup");
            t.setDaemon(true);
            classLoader = AbandonedConnectionCleanupThread.class.getClassLoader();
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }
            t.setContextClassLoader(classLoader);
            return AbandonedConnectionCleanupThread.threadRef = threadRef;
        })).execute(new AbandonedConnectionCleanupThread());
    }
    
    private static class ConnectionFinalizerPhantomReference extends PhantomReference<MysqlConnection>
    {
        private NetworkResources networkResources;
        
        ConnectionFinalizerPhantomReference(final MysqlConnection conn, final NetworkResources networkResources, final ReferenceQueue<? super MysqlConnection> refQueue) {
            super(conn, refQueue);
            this.networkResources = networkResources;
        }
        
        void finalizeResources() {
            if (this.networkResources != null) {
                try {
                    this.networkResources.forceClose();
                }
                finally {
                    this.networkResources = null;
                }
            }
        }
    }
}
