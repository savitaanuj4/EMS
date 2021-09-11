
package com.mysql.cj.exceptions;

public class DataTruncationException extends CJException
{
    private static final long serialVersionUID = -5209088385943506720L;
    private int index;
    private boolean parameter;
    private boolean read;
    private int dataSize;
    private int transferSize;
    
    public DataTruncationException() {
    }
    
    public DataTruncationException(final String message) {
        super(message);
    }
    
    public DataTruncationException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    public DataTruncationException(final Throwable cause) {
        super(cause);
    }
    
    protected DataTruncationException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public DataTruncationException(final String message, final int index, final boolean parameter, final boolean read, final int dataSize, final int transferSize, final int vendorErrorCode) {
        super(message);
        this.setIndex(index);
        this.setParameter(parameter);
        this.setRead(read);
        this.setDataSize(dataSize);
        this.setTransferSize(transferSize);
        this.setVendorCode(vendorErrorCode);
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public void setIndex(final int index) {
        this.index = index;
    }
    
    public boolean isParameter() {
        return this.parameter;
    }
    
    public void setParameter(final boolean parameter) {
        this.parameter = parameter;
    }
    
    public boolean isRead() {
        return this.read;
    }
    
    public void setRead(final boolean read) {
        this.read = read;
    }
    
    public int getDataSize() {
        return this.dataSize;
    }
    
    public void setDataSize(final int dataSize) {
        this.dataSize = dataSize;
    }
    
    public int getTransferSize() {
        return this.transferSize;
    }
    
    public void setTransferSize(final int transferSize) {
        this.transferSize = transferSize;
    }
}
