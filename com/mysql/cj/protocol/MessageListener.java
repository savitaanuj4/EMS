
package com.mysql.cj.protocol;

public interface MessageListener<M extends Message> extends ProtocolEntityFactory<Boolean, M>
{
    default void error(final Throwable ex) {
        ex.printStackTrace();
    }
}
