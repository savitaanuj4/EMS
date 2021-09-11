
package com.mysql.cj.exceptions;

public class CJException extends RuntimeException
{
    private static final long serialVersionUID = -8618536991444733607L;
    protected String exceptionMessage;
    private String SQLState;
    private int vendorCode;
    private boolean isTransient;
    
    public CJException() {
        this.SQLState = "S1000";
        this.vendorCode = 0;
        this.isTransient = false;
    }
    
    public CJException(final String message) {
        super(message);
        this.SQLState = "S1000";
        this.vendorCode = 0;
        this.isTransient = false;
    }
    
    public CJException(final Throwable cause) {
        super(cause);
        this.SQLState = "S1000";
        this.vendorCode = 0;
        this.isTransient = false;
    }
    
    public CJException(final String message, final Throwable cause) {
        super(message, cause);
        this.SQLState = "S1000";
        this.vendorCode = 0;
        this.isTransient = false;
    }
    
    protected CJException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.SQLState = "S1000";
        this.vendorCode = 0;
        this.isTransient = false;
    }
    
    public String getSQLState() {
        return this.SQLState;
    }
    
    public void setSQLState(final String sQLState) {
        this.SQLState = sQLState;
    }
    
    public int getVendorCode() {
        return this.vendorCode;
    }
    
    public void setVendorCode(final int vendorCode) {
        this.vendorCode = vendorCode;
    }
    
    public boolean isTransient() {
        return this.isTransient;
    }
    
    public void setTransient(final boolean isTransient) {
        this.isTransient = isTransient;
    }
    
    @Override
    public String getMessage() {
        return (this.exceptionMessage != null) ? this.exceptionMessage : super.getMessage();
    }
    
    public void appendMessage(final String messageToAppend) {
        this.exceptionMessage = this.getMessage() + messageToAppend;
    }
}
