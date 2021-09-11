
package com.mysql.cj.log;

import com.mysql.cj.util.Util;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.Session;

public class ProfilerEventHandlerFactory
{
    public static synchronized ProfilerEventHandler getInstance(final Session sess) {
        ProfilerEventHandler handler = sess.getProfilerEventHandler();
        if (handler == null) {
            handler = (ProfilerEventHandler)Util.getInstance(sess.getPropertySet().getStringProperty(PropertyKey.profilerEventHandler).getStringValue(), new Class[0], new Object[0], sess.getExceptionInterceptor());
            handler.init(sess.getLog());
            sess.setProfilerEventHandler(handler);
        }
        return handler;
    }
    
    public static synchronized void removeInstance(final Session sess) {
        final ProfilerEventHandler handler = sess.getProfilerEventHandler();
        if (handler != null) {
            handler.destroy();
        }
    }
}
