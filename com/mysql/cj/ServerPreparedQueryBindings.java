
package com.mysql.cj;

import com.mysql.cj.util.TimeUtil;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.NClob;
import com.mysql.cj.conf.PropertyKey;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Clob;
import java.io.Reader;
import java.sql.Blob;
import java.math.BigInteger;
import com.mysql.cj.util.StringUtils;
import java.math.BigDecimal;
import java.io.InputStream;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerPreparedQueryBindings extends AbstractQueryBindings<ServerPreparedQueryBindValue>
{
    private AtomicBoolean sendTypesToServer;
    private boolean longParameterSwitchDetected;
    
    public ServerPreparedQueryBindings(final int parameterCount, final Session sess) {
        super(parameterCount, sess);
        this.sendTypesToServer = new AtomicBoolean(false);
        this.longParameterSwitchDetected = false;
    }
    
    @Override
    protected void initBindValues(final int parameterCount) {
        this.bindValues = (T[])new ServerPreparedQueryBindValue[parameterCount];
        for (int i = 0; i < parameterCount; ++i) {
            ((ServerPreparedQueryBindValue[])this.bindValues)[i] = new ServerPreparedQueryBindValue(this.session.getServerSession().getDefaultTimeZone());
        }
    }
    
    @Override
    public ServerPreparedQueryBindings clone() {
        final ServerPreparedQueryBindings newBindings = new ServerPreparedQueryBindings(((ServerPreparedQueryBindValue[])this.bindValues).length, this.session);
        final ServerPreparedQueryBindValue[] bvs = new ServerPreparedQueryBindValue[((ServerPreparedQueryBindValue[])this.bindValues).length];
        for (int i = 0; i < ((ServerPreparedQueryBindValue[])this.bindValues).length; ++i) {
            bvs[i] = ((ServerPreparedQueryBindValue[])this.bindValues)[i].clone();
        }
        newBindings.bindValues = (T[])bvs;
        newBindings.sendTypesToServer = this.sendTypesToServer;
        newBindings.longParameterSwitchDetected = this.longParameterSwitchDetected;
        newBindings.isLoadDataQuery = this.isLoadDataQuery;
        return newBindings;
    }
    
    public ServerPreparedQueryBindValue getBinding(final int parameterIndex, final boolean forLongData) {
        if (((ServerPreparedQueryBindValue[])this.bindValues)[parameterIndex] != null) {
            if (((ServerPreparedQueryBindValue[])this.bindValues)[parameterIndex].isStream && !forLongData) {
                this.longParameterSwitchDetected = true;
            }
        }
        return ((ServerPreparedQueryBindValue[])this.bindValues)[parameterIndex];
    }
    
    @Override
    public void checkParameterSet(final int columnIndex) {
        if (!((ServerPreparedQueryBindValue[])this.bindValues)[columnIndex].isSet()) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("ServerPreparedStatement.13") + (columnIndex + 1) + Messages.getString("ServerPreparedStatement.14"));
        }
    }
    
    public AtomicBoolean getSendTypesToServer() {
        return this.sendTypesToServer;
    }
    
    public boolean isLongParameterSwitchDetected() {
        return this.longParameterSwitchDetected;
    }
    
    public void setLongParameterSwitchDetected(final boolean longParameterSwitchDetected) {
        this.longParameterSwitchDetected = longParameterSwitchDetected;
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x) {
        this.setAsciiStream(parameterIndex, x, -1);
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
            binding.value = x;
            binding.isStream = true;
            binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? length : -1L);
        }
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) {
        this.setAsciiStream(parameterIndex, x, (int)length);
        ((ServerPreparedQueryBindValue[])this.bindValues)[parameterIndex].setMysqlType(MysqlType.TEXT);
    }
    
    @Override
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(246, this.numberOfExecutions));
            binding.value = StringUtils.fixDecimalExponent(x.toPlainString());
        }
    }
    
    @Override
    public void setBigInteger(final int parameterIndex, final BigInteger x) {
        this.setValue(parameterIndex, x.toString(), MysqlType.BIGINT_UNSIGNED);
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x) {
        this.setBinaryStream(parameterIndex, x, -1);
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
            binding.value = x;
            binding.isStream = true;
            binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? length : -1L);
        }
    }
    
    @Override
    public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) {
        this.setBinaryStream(parameterIndex, x, (int)length);
    }
    
    @Override
    public void setBlob(final int parameterIndex, final InputStream inputStream) {
        this.setBinaryStream(parameterIndex, inputStream);
    }
    
    @Override
    public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) {
        this.setBinaryStream(parameterIndex, inputStream, (int)length);
    }
    
    @Override
    public void setBlob(final int parameterIndex, final Blob x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            try {
                final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
                this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
                binding.value = x;
                binding.isStream = true;
                binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? x.length() : -1L);
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(t.getMessage(), t);
            }
        }
    }
    
    @Override
    public void setBoolean(final int parameterIndex, final boolean x) {
        this.setByte(parameterIndex, (byte)(x ? 1 : 0));
    }
    
    @Override
    public void setByte(final int parameterIndex, final byte x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(1, this.numberOfExecutions));
        binding.value = x;
    }
    
    @Override
    public void setBytes(final int parameterIndex, final byte[] x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(253, this.numberOfExecutions));
            binding.value = x;
        }
    }
    
    @Override
    public void setBytes(final int parameterIndex, final byte[] x, final boolean checkForIntroducer, final boolean escapeForMBChars) {
        this.setBytes(parameterIndex, x);
    }
    
    @Override
    public void setBytesNoEscape(final int parameterIndex, final byte[] parameterAsBytes) {
        this.setBytes(parameterIndex, parameterAsBytes);
    }
    
    @Override
    public void setBytesNoEscapeNoQuotes(final int parameterIndex, final byte[] parameterAsBytes) {
        this.setBytes(parameterIndex, parameterAsBytes);
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader) {
        this.setCharacterStream(parameterIndex, reader, -1);
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) {
        if (reader == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
            binding.value = reader;
            binding.isStream = true;
            binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? length : -1L);
        }
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) {
        this.setCharacterStream(parameterIndex, reader, (int)length);
    }
    
    @Override
    public void setClob(final int parameterIndex, final Reader reader) {
        this.setCharacterStream(parameterIndex, reader);
    }
    
    @Override
    public void setClob(final int parameterIndex, final Reader reader, final long length) {
        this.setCharacterStream(parameterIndex, reader, length);
    }
    
    @Override
    public void setClob(final int parameterIndex, final Clob x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            try {
                final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
                this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
                binding.value = x.getCharacterStream();
                binding.isStream = true;
                binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? x.length() : -1L);
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(t.getMessage(), t);
            }
        }
    }
    
    @Override
    public void setDate(final int parameterIndex, final Date x) {
        this.setDate(parameterIndex, x, null);
    }
    
    @Override
    public void setDate(final int parameterIndex, final Date x, final Calendar cal) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(10, this.numberOfExecutions));
            binding.value = x;
            binding.calendar = ((cal == null) ? null : ((Calendar)cal.clone()));
        }
    }
    
    @Override
    public void setDouble(final int parameterIndex, final double x) {
        if (!this.session.getPropertySet().getBooleanProperty(PropertyKey.allowNanAndInf).getValue() && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x))) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.64", new Object[] { x }), this.session.getExceptionInterceptor());
        }
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(5, this.numberOfExecutions));
        binding.value = x;
    }
    
    @Override
    public void setFloat(final int parameterIndex, final float x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(4, this.numberOfExecutions));
        binding.value = x;
    }
    
    @Override
    public void setInt(final int parameterIndex, final int x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(3, this.numberOfExecutions));
        binding.value = x;
    }
    
    @Override
    public void setLong(final int parameterIndex, final long x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(8, this.numberOfExecutions));
        binding.value = x;
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader value) {
        this.setNCharacterStream(parameterIndex, value, -1L);
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader reader, final long length) {
        if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.28"), this.session.getExceptionInterceptor());
        }
        if (reader == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, true);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(252, this.numberOfExecutions));
            binding.value = reader;
            binding.isStream = true;
            binding.streamLength = (this.useStreamLengthsInPrepStmts.getValue() ? length : -1L);
        }
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader) {
        this.setNCharacterStream(parameterIndex, reader);
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader, final long length) {
        if (!this.charEncoding.equalsIgnoreCase("UTF-8") && !this.charEncoding.equalsIgnoreCase("utf8")) {
            throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.29"), this.session.getExceptionInterceptor());
        }
        this.setNCharacterStream(parameterIndex, reader, length);
    }
    
    @Override
    public void setNClob(final int parameterIndex, final NClob value) {
        try {
            this.setNClob(parameterIndex, value.getCharacterStream(), ((boolean)this.useStreamLengthsInPrepStmts.getValue()) ? value.length() : -1L);
        }
        catch (Throwable t) {
            throw ExceptionFactory.createException(t.getMessage(), t, this.session.getExceptionInterceptor());
        }
    }
    
    @Override
    public void setNString(final int parameterIndex, final String x) {
        if (this.charEncoding.equalsIgnoreCase("UTF-8") || this.charEncoding.equalsIgnoreCase("utf8")) {
            this.setString(parameterIndex, x);
            return;
        }
        throw ExceptionFactory.createException(Messages.getString("ServerPreparedStatement.30"), this.session.getExceptionInterceptor());
    }
    
    @Override
    public void setNull(final int parameterIndex) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(6, this.numberOfExecutions));
        binding.setNull(true);
    }
    
    @Override
    public void setShort(final int parameterIndex, final short x) {
        final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
        this.sendTypesToServer.compareAndSet(false, binding.resetToType(2, this.numberOfExecutions));
        binding.value = x;
    }
    
    @Override
    public void setString(final int parameterIndex, final String x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(253, this.numberOfExecutions));
            binding.value = x;
        }
    }
    
    @Override
    public void setTime(final int parameterIndex, final Time x, final Calendar cal) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(11, this.numberOfExecutions));
            binding.value = x;
            binding.calendar = ((cal == null) ? null : ((Calendar)cal.clone()));
        }
    }
    
    @Override
    public void setTime(final int parameterIndex, final Time x) {
        this.setTime(parameterIndex, x, null);
    }
    
    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x) {
        int fractLen = -1;
        if (!this.sendFractionalSeconds.getValue() || !this.session.getServerSession().getCapabilities().serverSupportsFracSecs()) {
            fractLen = 0;
        }
        else if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0) {
            fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
        }
        this.setTimestamp(parameterIndex, x, null, fractLen);
    }
    
    @Override
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) {
        int fractLen = -1;
        if (!this.sendFractionalSeconds.getValue() || !this.session.getServerSession().getCapabilities().serverSupportsFracSecs()) {
            fractLen = 0;
        }
        else if (this.columnDefinition != null && parameterIndex <= this.columnDefinition.getFields().length && parameterIndex >= 0 && this.columnDefinition.getFields()[parameterIndex].getDecimals() > 0) {
            fractLen = this.columnDefinition.getFields()[parameterIndex].getDecimals();
        }
        this.setTimestamp(parameterIndex, x, cal, fractLen);
    }
    
    @Override
    public void setTimestamp(final int parameterIndex, Timestamp x, final Calendar targetCalendar, int fractionalLength) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final ServerPreparedQueryBindValue binding = this.getBinding(parameterIndex, false);
            this.sendTypesToServer.compareAndSet(false, binding.resetToType(12, this.numberOfExecutions));
            x = (Timestamp)x.clone();
            if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || (!this.sendFractionalSeconds.getValue() && fractionalLength == 0)) {
                x = TimeUtil.truncateFractionalSeconds(x);
            }
            if (fractionalLength < 0) {
                fractionalLength = 6;
            }
            x = TimeUtil.adjustTimestampNanosPrecision(x, fractionalLength, !this.session.getServerSession().isServerTruncatesFracSecs());
            binding.value = x;
            binding.calendar = ((targetCalendar == null) ? null : ((Calendar)targetCalendar.clone()));
        }
    }
}
