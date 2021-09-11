
package com.mysql.cj.xdevapi;

public interface Client
{
    Session getSession();
    
    void close();
    
    public enum ClientProperty
    {
        POOLING_ENABLED("pooling.enabled"), 
        POOLING_MAX_SIZE("pooling.maxSize"), 
        POOLING_MAX_IDLE_TIME("pooling.maxIdleTime"), 
        POOLING_QUEUE_TIMEOUT("pooling.queueTimeout");
        
        private String keyName;
        
        private ClientProperty(final String keyName) {
            this.keyName = "";
            this.keyName = keyName;
        }
        
        public String getKeyName() {
            return this.keyName;
        }
    }
}
