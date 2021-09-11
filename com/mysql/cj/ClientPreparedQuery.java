
package com.mysql.cj;

import com.mysql.cj.util.StringUtils;

public class ClientPreparedQuery extends AbstractPreparedQuery<ClientPreparedQueryBindings>
{
    public ClientPreparedQuery(final NativeSession sess) {
        super(sess);
    }
    
    @Override
    protected long[] computeMaxParameterSetSizeAndBatchSize(final int numBatchedArgs) {
        long sizeOfEntireBatch = 0L;
        long maxSizeOfParameterSet = 0L;
        for (int i = 0; i < numBatchedArgs; ++i) {
            final ClientPreparedQueryBindings qBindings = this.batchedArgs.get(i);
            final BindValue[] bindValues = ((AbstractQueryBindings<BindValue>)qBindings).getBindValues();
            long sizeOfParameterSet = 0L;
            for (int j = 0; j < bindValues.length; ++j) {
                if (!bindValues[j].isNull()) {
                    if (bindValues[j].isStream()) {
                        final long streamLength = bindValues[j].getStreamLength();
                        if (streamLength != -1L) {
                            sizeOfParameterSet += streamLength * 2L;
                        }
                        else {
                            final int paramLength = qBindings.getBindValues()[j].getByteValue().length;
                            sizeOfParameterSet += paramLength;
                        }
                    }
                    else {
                        sizeOfParameterSet += qBindings.getBindValues()[j].getByteValue().length;
                    }
                }
                else {
                    sizeOfParameterSet += 4L;
                }
            }
            if (this.parseInfo.getValuesClause() != null) {
                sizeOfParameterSet += this.parseInfo.getValuesClause().length() + 1;
            }
            else {
                sizeOfParameterSet += this.originalSql.length() + 1;
            }
            sizeOfEntireBatch += sizeOfParameterSet;
            if (sizeOfParameterSet > maxSizeOfParameterSet) {
                maxSizeOfParameterSet = sizeOfParameterSet;
            }
        }
        return new long[] { maxSizeOfParameterSet, sizeOfEntireBatch };
    }
    
    public byte[] getBytesRepresentation(final int parameterIndex) {
        final BindValue bv = ((ClientPreparedQueryBindings)this.queryBindings).getBindValues()[parameterIndex];
        if (bv.isStream()) {
            return this.streamToBytes(bv.getStreamValue(), false, bv.getStreamLength(), this.useStreamLengthsInPrepStmts.getValue());
        }
        final byte[] parameterVal = bv.getByteValue();
        if (parameterVal == null) {
            return null;
        }
        if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
            final byte[] valNoQuotes = new byte[parameterVal.length - 2];
            System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
            return valNoQuotes;
        }
        return parameterVal;
    }
    
    public byte[] getBytesRepresentationForBatch(final int parameterIndex, final int commandIndex) {
        final Object batchedArg = this.batchedArgs.get(commandIndex);
        if (batchedArg instanceof String) {
            return StringUtils.getBytes((String)batchedArg, this.charEncoding);
        }
        final BindValue bv = ((ClientPreparedQueryBindings)batchedArg).getBindValues()[parameterIndex];
        if (bv.isStream()) {
            return this.streamToBytes(bv.getStreamValue(), false, bv.getStreamLength(), this.useStreamLengthsInPrepStmts.getValue());
        }
        final byte[] parameterVal = bv.getByteValue();
        if (parameterVal == null) {
            return null;
        }
        if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
            final byte[] valNoQuotes = new byte[parameterVal.length - 2];
            System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
            return valNoQuotes;
        }
        return parameterVal;
    }
}
