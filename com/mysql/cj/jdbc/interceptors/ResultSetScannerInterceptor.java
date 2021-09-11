
package com.mysql.cj.jdbc.interceptors;

import java.lang.reflect.Proxy;
import java.util.regex.Matcher;
import java.sql.SQLException;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import com.mysql.cj.protocol.Resultset;
import com.mysql.cj.protocol.ServerSession;
import com.mysql.cj.Query;
import java.util.function.Supplier;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.log.Log;
import java.util.Properties;
import com.mysql.cj.MysqlConnection;
import java.util.regex.Pattern;
import com.mysql.cj.interceptors.QueryInterceptor;

public class ResultSetScannerInterceptor implements QueryInterceptor
{
    public static final String PNAME_resultSetScannerRegex = "resultSetScannerRegex";
    protected Pattern regexP;
    
    @Override
    public QueryInterceptor init(final MysqlConnection conn, final Properties props, final Log log) {
        final String regexFromUser = props.getProperty("resultSetScannerRegex");
        if (regexFromUser == null || regexFromUser.length() == 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ResultSetScannerInterceptor.0"));
        }
        try {
            this.regexP = Pattern.compile(regexFromUser);
        }
        catch (Throwable t) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ResultSetScannerInterceptor.1"), t);
        }
        return this;
    }
    
    @Override
    public <T extends Resultset> T postProcess(final Supplier<String> sql, final Query interceptedQuery, final T originalResultSet, final ServerSession serverSession) {
        final T finalResultSet = originalResultSet;
        return (T)Proxy.newProxyInstance(originalResultSet.getClass().getClassLoader(), new Class[] { Resultset.class, ResultSetInternalMethods.class }, new InvocationHandler() {
            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                if ("equals".equals(method.getName())) {
                    return args[0].equals(this);
                }
                final Object invocationResult = method.invoke(finalResultSet, args);
                final String methodName = method.getName();
                if ((invocationResult != null && invocationResult instanceof String) || "getString".equals(methodName) || "getObject".equals(methodName) || "getObjectStoredProc".equals(methodName)) {
                    final Matcher matcher = ResultSetScannerInterceptor.this.regexP.matcher(invocationResult.toString());
                    if (matcher.matches()) {
                        throw new SQLException(Messages.getString("ResultSetScannerInterceptor.2"));
                    }
                }
                return invocationResult;
            }
        });
    }
    
    @Override
    public <T extends Resultset> T preProcess(final Supplier<String> sql, final Query interceptedQuery) {
        return null;
    }
    
    @Override
    public boolean executeTopLevelOnly() {
        return false;
    }
    
    @Override
    public void destroy() {
    }
}
