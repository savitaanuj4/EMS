
package com.mysql.cj.jdbc.ha;

import java.util.ArrayList;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.conf.PropertyKey;
import java.util.Properties;
import java.util.Iterator;
import com.mysql.cj.exceptions.CJCommunicationsException;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.SQLException;
import java.util.List;

public class StandardLoadBalanceExceptionChecker implements LoadBalanceExceptionChecker
{
    private List<String> sqlStateList;
    private List<Class<?>> sqlExClassList;
    
    @Override
    public boolean shouldExceptionTriggerFailover(final Throwable ex) {
        final String sqlState = (ex instanceof SQLException) ? ((SQLException)ex).getSQLState() : null;
        if (sqlState != null) {
            if (sqlState.startsWith("08")) {
                return true;
            }
            if (this.sqlStateList != null) {
                final Iterator<String> i = this.sqlStateList.iterator();
                while (i.hasNext()) {
                    if (sqlState.startsWith(i.next().toString())) {
                        return true;
                    }
                }
            }
        }
        if (ex instanceof CommunicationsException || ex instanceof CJCommunicationsException) {
            return true;
        }
        if (this.sqlExClassList != null) {
            final Iterator<Class<?>> j = this.sqlExClassList.iterator();
            while (j.hasNext()) {
                if (j.next().isInstance(ex)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public void destroy() {
    }
    
    @Override
    public void init(final Properties props) {
        this.configureSQLStateList(props.getProperty(PropertyKey.loadBalanceSQLStateFailover.getKeyName(), null));
        this.configureSQLExceptionSubclassList(props.getProperty(PropertyKey.loadBalanceSQLExceptionSubclassFailover.getKeyName(), null));
    }
    
    private void configureSQLStateList(final String sqlStates) {
        if (sqlStates == null || "".equals(sqlStates)) {
            return;
        }
        final List<String> states = StringUtils.split(sqlStates, ",", true);
        final List<String> newStates = new ArrayList<String>();
        for (final String state : states) {
            if (state.length() > 0) {
                newStates.add(state);
            }
        }
        if (newStates.size() > 0) {
            this.sqlStateList = newStates;
        }
    }
    
    private void configureSQLExceptionSubclassList(final String sqlExClasses) {
        if (sqlExClasses == null || "".equals(sqlExClasses)) {
            return;
        }
        final List<String> classes = StringUtils.split(sqlExClasses, ",", true);
        final List<Class<?>> newClasses = new ArrayList<Class<?>>();
        for (final String exClass : classes) {
            try {
                final Class<?> c = Class.forName(exClass);
                newClasses.add(c);
            }
            catch (Exception ex) {}
        }
        if (newClasses.size() > 0) {
            this.sqlExClassList = newClasses;
        }
    }
}
