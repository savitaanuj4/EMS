
package com.mysql.cj.util;

import java.util.concurrent.ConcurrentHashMap;
import java.io.IOException;
import java.io.Reader;
import com.mysql.cj.Constants;
import java.util.Set;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import com.mysql.cj.exceptions.CJException;
import java.lang.reflect.Constructor;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import com.mysql.cj.Messages;
import java.util.concurrent.ConcurrentMap;

public class Util
{
    private static int jvmVersion;
    private static int jvmUpdateNumber;
    private static final ConcurrentMap<Class<?>, Boolean> isJdbcInterfaceCache;
    private static final ConcurrentMap<Class<?>, Class<?>[]> implementedInterfacesCache;
    
    public static int getJVMVersion() {
        return Util.jvmVersion;
    }
    
    public static boolean jvmMeetsMinimum(final int version, final int updateNumber) {
        return getJVMVersion() > version || (getJVMVersion() == version && getJVMUpdateNumber() >= updateNumber);
    }
    
    public static int getJVMUpdateNumber() {
        return Util.jvmUpdateNumber;
    }
    
    public static boolean isCommunityEdition(final String serverVersion) {
        return !isEnterpriseEdition(serverVersion);
    }
    
    public static boolean isEnterpriseEdition(final String serverVersion) {
        return serverVersion.contains("enterprise") || serverVersion.contains("commercial") || serverVersion.contains("advanced");
    }
    
    public static String stackTraceToString(final Throwable ex) {
        final StringBuilder traceBuf = new StringBuilder();
        traceBuf.append(Messages.getString("Util.1"));
        if (ex != null) {
            traceBuf.append(ex.getClass().getName());
            final String message = ex.getMessage();
            if (message != null) {
                traceBuf.append(Messages.getString("Util.2"));
                traceBuf.append(message);
            }
            final StringWriter out = new StringWriter();
            final PrintWriter printOut = new PrintWriter(out);
            ex.printStackTrace(printOut);
            traceBuf.append(Messages.getString("Util.3"));
            traceBuf.append(out.toString());
        }
        traceBuf.append(Messages.getString("Util.4"));
        return traceBuf.toString();
    }
    
    public static Object getInstance(final String className, final Class<?>[] argTypes, final Object[] args, final ExceptionInterceptor exceptionInterceptor, final String errorMessage) {
        try {
            return handleNewInstance(Class.forName(className).getConstructor(argTypes), args, exceptionInterceptor);
        }
        catch (SecurityException | NoSuchMethodException | ClassNotFoundException ex2) {
            final Exception ex;
            final Exception e = ex;
            throw ExceptionFactory.createException(WrongArgumentException.class, errorMessage, e, exceptionInterceptor);
        }
    }
    
    public static Object getInstance(final String className, final Class<?>[] argTypes, final Object[] args, final ExceptionInterceptor exceptionInterceptor) {
        return getInstance(className, argTypes, args, exceptionInterceptor, "Can't instantiate required class");
    }
    
    public static Object handleNewInstance(final Constructor<?> ctor, final Object[] args, final ExceptionInterceptor exceptionInterceptor) {
        try {
            return ctor.newInstance(args);
        }
        catch (IllegalArgumentException | InstantiationException | IllegalAccessException ex2) {
            final Exception ex;
            final Exception e = ex;
            throw ExceptionFactory.createException(WrongArgumentException.class, "Can't instantiate required class", e, exceptionInterceptor);
        }
        catch (InvocationTargetException e2) {
            Throwable target = e2.getTargetException();
            if (target instanceof ExceptionInInitializerError) {
                target = ((ExceptionInInitializerError)target).getException();
            }
            else if (target instanceof CJException) {
                throw (CJException)target;
            }
            throw ExceptionFactory.createException(WrongArgumentException.class, target.getMessage(), target, exceptionInterceptor);
        }
    }
    
    public static boolean interfaceExists(final String hostname) {
        try {
            final Class<?> networkInterfaceClass = Class.forName("java.net.NetworkInterface");
            return networkInterfaceClass.getMethod("getByName", (Class<?>[])null).invoke(networkInterfaceClass, hostname) != null;
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    public static Map<Object, Object> calculateDifferences(final Map<?, ?> map1, final Map<?, ?> map2) {
        final Map<Object, Object> diffMap = new HashMap<Object, Object>();
        for (final Map.Entry<?, ?> entry : map1.entrySet()) {
            final Object key = entry.getKey();
            Number value1 = null;
            Number value2 = null;
            if (entry.getValue() instanceof Number) {
                value1 = (Number)entry.getValue();
                value2 = (Number)map2.get(key);
            }
            else {
                try {
                    value1 = new Double(entry.getValue().toString());
                    value2 = new Double(map2.get(key).toString());
                }
                catch (NumberFormatException nfe) {
                    continue;
                }
            }
            if (value1.equals(value2)) {
                continue;
            }
            if (value1 instanceof Byte) {
                diffMap.put(key, (byte)((byte)value2 - (byte)value1));
            }
            else if (value1 instanceof Short) {
                diffMap.put(key, (short)((short)value2 - (short)value1));
            }
            else if (value1 instanceof Integer) {
                diffMap.put(key, (int)value2 - (int)value1);
            }
            else if (value1 instanceof Long) {
                diffMap.put(key, (long)value2 - (long)value1);
            }
            else if (value1 instanceof Float) {
                diffMap.put(key, (float)value2 - (float)value1);
            }
            else if (value1 instanceof Double) {
                diffMap.put(key, (double)(((Double)value2).shortValue() - ((Double)value1).shortValue()));
            }
            else if (value1 instanceof BigDecimal) {
                diffMap.put(key, ((BigDecimal)value2).subtract((BigDecimal)value1));
            }
            else {
                if (!(value1 instanceof BigInteger)) {
                    continue;
                }
                diffMap.put(key, ((BigInteger)value2).subtract((BigInteger)value1));
            }
        }
        return diffMap;
    }
    
    public static <T> List<T> loadClasses(final String extensionClassNames, final String errorMessageKey, final ExceptionInterceptor exceptionInterceptor) {
        final List<T> instances = new LinkedList<T>();
        final List<String> interceptorsToCreate = StringUtils.split(extensionClassNames, ",", true);
        String className = null;
        try {
            for (int i = 0, s = interceptorsToCreate.size(); i < s; ++i) {
                className = interceptorsToCreate.get(i);
                final T instance = (T)Class.forName(className).newInstance();
                instances.add(instance);
            }
        }
        catch (Throwable t) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString(errorMessageKey, new Object[] { className }), t, exceptionInterceptor);
        }
        return instances;
    }
    
    public static boolean isJdbcInterface(final Class<?> clazz) {
        if (Util.isJdbcInterfaceCache.containsKey(clazz)) {
            return Util.isJdbcInterfaceCache.get(clazz);
        }
        if (clazz.isInterface()) {
            try {
                if (isJdbcPackage(clazz.getPackage().getName())) {
                    Util.isJdbcInterfaceCache.putIfAbsent(clazz, true);
                    return true;
                }
            }
            catch (Exception ex) {}
        }
        for (final Class<?> iface : clazz.getInterfaces()) {
            if (isJdbcInterface(iface)) {
                Util.isJdbcInterfaceCache.putIfAbsent(clazz, true);
                return true;
            }
        }
        if (clazz.getSuperclass() != null && isJdbcInterface(clazz.getSuperclass())) {
            Util.isJdbcInterfaceCache.putIfAbsent(clazz, true);
            return true;
        }
        Util.isJdbcInterfaceCache.putIfAbsent(clazz, false);
        return false;
    }
    
    public static boolean isJdbcPackage(final String packageName) {
        return packageName != null && (packageName.startsWith("java.sql") || packageName.startsWith("javax.sql") || packageName.startsWith("com.mysql.cj.jdbc"));
    }
    
    public static Class<?>[] getImplementedInterfaces(final Class<?> clazz) {
        Class<?>[] implementedInterfaces = (Class<?>[])Util.implementedInterfacesCache.get(clazz);
        if (implementedInterfaces != null) {
            return implementedInterfaces;
        }
        final Set<Class<?>> interfaces = new LinkedHashSet<Class<?>>();
        Class<?> superClass = clazz;
        do {
            Collections.addAll(interfaces, superClass.getInterfaces());
        } while ((superClass = superClass.getSuperclass()) != null);
        implementedInterfaces = interfaces.toArray(new Class[interfaces.size()]);
        final Class<?>[] oldValue = Util.implementedInterfacesCache.putIfAbsent(clazz, implementedInterfaces);
        if (oldValue != null) {
            implementedInterfaces = oldValue;
        }
        return implementedInterfaces;
    }
    
    public static long secondsSinceMillis(final long timeInMillis) {
        return (System.currentTimeMillis() - timeInMillis) / 1000L;
    }
    
    public static int truncateAndConvertToInt(final long longValue) {
        return (longValue > 2147483647L) ? Integer.MAX_VALUE : ((longValue < -2147483648L) ? Integer.MIN_VALUE : ((int)longValue));
    }
    
    public static int[] truncateAndConvertToInt(final long[] longArray) {
        final int[] intArray = new int[longArray.length];
        for (int i = 0; i < longArray.length; ++i) {
            intArray[i] = ((longArray[i] > 2147483647L) ? Integer.MAX_VALUE : ((longArray[i] < -2147483648L) ? Integer.MIN_VALUE : ((int)longArray[i])));
        }
        return intArray;
    }
    
    public static String getPackageName(final Class<?> clazz) {
        final String fqcn = clazz.getName();
        final int classNameStartsAt = fqcn.lastIndexOf(46);
        if (classNameStartsAt > 0) {
            return fqcn.substring(0, classNameStartsAt);
        }
        return "";
    }
    
    public static boolean isRunningOnWindows() {
        return StringUtils.indexOfIgnoreCase(Constants.OS_NAME, "WINDOWS") != -1;
    }
    
    public static int readFully(final Reader reader, final char[] buf, final int length) throws IOException {
        int numCharsRead;
        int count;
        for (numCharsRead = 0; numCharsRead < length; numCharsRead += count) {
            count = reader.read(buf, numCharsRead, length - numCharsRead);
            if (count < 0) {
                break;
            }
        }
        return numCharsRead;
    }
    
    static {
        Util.jvmVersion = 8;
        Util.jvmUpdateNumber = -1;
        int startPos = Constants.JVM_VERSION.indexOf(46);
        int endPos = startPos + 1;
        if (startPos != -1) {
            while (Character.isDigit(Constants.JVM_VERSION.charAt(endPos)) && ++endPos < Constants.JVM_VERSION.length()) {}
        }
        ++startPos;
        if (endPos > startPos) {
            Util.jvmVersion = Integer.parseInt(Constants.JVM_VERSION.substring(startPos, endPos));
        }
        startPos = Constants.JVM_VERSION.indexOf("_");
        endPos = startPos + 1;
        if (startPos != -1) {
            while (Character.isDigit(Constants.JVM_VERSION.charAt(endPos)) && ++endPos < Constants.JVM_VERSION.length()) {}
        }
        ++startPos;
        if (endPos > startPos) {
            Util.jvmUpdateNumber = Integer.parseInt(Constants.JVM_VERSION.substring(startPos, endPos));
        }
        isJdbcInterfaceCache = new ConcurrentHashMap<Class<?>, Boolean>();
        implementedInterfacesCache = new ConcurrentHashMap<Class<?>, Class<?>[]>();
    }
}
