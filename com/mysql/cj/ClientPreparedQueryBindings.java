
package com.mysql.cj;

import java.sql.Timestamp;
import java.sql.Time;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.sql.NClob;
import com.mysql.cj.util.TimeUtil;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Clob;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import com.mysql.cj.exceptions.WrongArgumentException;
import com.mysql.cj.util.Util;
import com.mysql.cj.conf.PropertyKey;
import java.io.Reader;
import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.math.BigInteger;
import com.mysql.cj.util.StringUtils;
import java.math.BigDecimal;
import java.io.InputStream;
import com.mysql.cj.exceptions.ExceptionFactory;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.nio.charset.CharsetEncoder;

public class ClientPreparedQueryBindings extends AbstractQueryBindings<ClientPreparedQueryBindValue>
{
    private CharsetEncoder charsetEncoder;
    private SimpleDateFormat ddf;
    private SimpleDateFormat tdf;
    private SimpleDateFormat tsdf;
    
    public ClientPreparedQueryBindings(final int parameterCount, final Session sess) {
        super(parameterCount, sess);
        this.tsdf = null;
        if (((NativeSession)this.session).getRequiresEscapingEncoder()) {
            this.charsetEncoder = Charset.forName(this.charEncoding).newEncoder();
        }
    }
    
    @Override
    protected void initBindValues(final int parameterCount) {
        this.bindValues = (T[])new ClientPreparedQueryBindValue[parameterCount];
        for (int i = 0; i < parameterCount; ++i) {
            ((ClientPreparedQueryBindValue[])this.bindValues)[i] = new ClientPreparedQueryBindValue();
        }
    }
    
    @Override
    public ClientPreparedQueryBindings clone() {
        final ClientPreparedQueryBindings newBindings = new ClientPreparedQueryBindings(((ClientPreparedQueryBindValue[])this.bindValues).length, this.session);
        final ClientPreparedQueryBindValue[] bvs = new ClientPreparedQueryBindValue[((ClientPreparedQueryBindValue[])this.bindValues).length];
        for (int i = 0; i < ((ClientPreparedQueryBindValue[])this.bindValues).length; ++i) {
            bvs[i] = ((ClientPreparedQueryBindValue[])this.bindValues)[i].clone();
        }
        newBindings.setBindValues(bvs);
        newBindings.isLoadDataQuery = this.isLoadDataQuery;
        return newBindings;
    }
    
    @Override
    public void checkParameterSet(final int columnIndex) {
        if (!((ClientPreparedQueryBindValue[])this.bindValues)[columnIndex].isSet()) {
            throw ExceptionFactory.createException(Messages.getString("PreparedStatement.40") + (columnIndex + 1), "07001", 0, true, null, this.session.getExceptionInterceptor());
        }
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
            this.setBinaryStream(parameterIndex, x, length);
        }
    }
    
    @Override
    public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) {
        this.setAsciiStream(parameterIndex, x, (int)length);
        ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setMysqlType(MysqlType.TEXT);
    }
    
    @Override
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            this.setValue(parameterIndex, StringUtils.fixDecimalExponent(x.toPlainString()), MysqlType.DECIMAL);
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
            ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setNull(false);
            ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setIsStream(true);
            ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setMysqlType(MysqlType.BLOB);
            ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setStreamValue(x, length);
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
                final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
                bytesOut.write(39);
                StringUtils.escapeblockFast(x.getBytes(1L, (int)x.length()), bytesOut, (int)x.length(), this.session.getServerSession().useAnsiQuotedIdentifiers());
                bytesOut.write(39);
                this.setValue(parameterIndex, bytesOut.toByteArray(), MysqlType.BLOB);
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(t.getMessage(), t);
            }
        }
    }
    
    @Override
    public void setBoolean(final int parameterIndex, final boolean x) {
        this.setValue(parameterIndex, x ? "1" : "0");
    }
    
    @Override
    public void setByte(final int parameterIndex, final byte x) {
        this.setValue(parameterIndex, String.valueOf(x), MysqlType.TINYINT);
    }
    
    @Override
    public void setBytes(final int parameterIndex, final byte[] x) {
        this.setBytes(parameterIndex, x, true, true);
        if (x != null) {
            ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setMysqlType(MysqlType.BINARY);
        }
    }
    
    @Override
    public synchronized void setBytes(final int parameterIndex, final byte[] x, final boolean checkForIntroducer, final boolean escapeForMBChars) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            if (this.session.getServerSession().isNoBackslashEscapesSet() || (escapeForMBChars && CharsetMapping.isMultibyteCharset(this.charEncoding))) {
                final ByteArrayOutputStream bOut = new ByteArrayOutputStream(x.length * 2 + 3);
                bOut.write(120);
                bOut.write(39);
                for (int i = 0; i < x.length; ++i) {
                    final int lowBits = (x[i] & 0xFF) / 16;
                    final int highBits = (x[i] & 0xFF) % 16;
                    bOut.write(ClientPreparedQueryBindings.HEX_DIGITS[lowBits]);
                    bOut.write(ClientPreparedQueryBindings.HEX_DIGITS[highBits]);
                }
                bOut.write(39);
                this.setValue(parameterIndex, bOut.toByteArray());
                return;
            }
            final int numBytes = x.length;
            int pad = 2;
            if (checkForIntroducer) {
                pad += 7;
            }
            final ByteArrayOutputStream bOut2 = new ByteArrayOutputStream(numBytes + pad);
            if (checkForIntroducer) {
                bOut2.write(95);
                bOut2.write(98);
                bOut2.write(105);
                bOut2.write(110);
                bOut2.write(97);
                bOut2.write(114);
                bOut2.write(121);
            }
            bOut2.write(39);
            for (final byte b : x) {
                switch (b) {
                    case 0: {
                        bOut2.write(92);
                        bOut2.write(48);
                        break;
                    }
                    case 10: {
                        bOut2.write(92);
                        bOut2.write(110);
                        break;
                    }
                    case 13: {
                        bOut2.write(92);
                        bOut2.write(114);
                        break;
                    }
                    case 92: {
                        bOut2.write(92);
                        bOut2.write(92);
                        break;
                    }
                    case 39: {
                        bOut2.write(92);
                        bOut2.write(39);
                        break;
                    }
                    case 34: {
                        bOut2.write(92);
                        bOut2.write(34);
                        break;
                    }
                    case 26: {
                        bOut2.write(92);
                        bOut2.write(90);
                        break;
                    }
                    default: {
                        bOut2.write(b);
                        break;
                    }
                }
            }
            bOut2.write(39);
            this.setValue(parameterIndex, bOut2.toByteArray());
        }
    }
    
    @Override
    public void setBytesNoEscape(final int parameterIndex, final byte[] parameterAsBytes) {
        final byte[] parameterWithQuotes = new byte[parameterAsBytes.length + 2];
        parameterWithQuotes[0] = 39;
        System.arraycopy(parameterAsBytes, 0, parameterWithQuotes, 1, parameterAsBytes.length);
        parameterWithQuotes[parameterAsBytes.length + 1] = 39;
        this.setValue(parameterIndex, parameterWithQuotes);
    }
    
    @Override
    public void setBytesNoEscapeNoQuotes(final int parameterIndex, final byte[] parameterAsBytes) {
        this.setValue(parameterIndex, parameterAsBytes);
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader) {
        this.setCharacterStream(parameterIndex, reader, -1);
    }
    
    @Override
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) {
        try {
            if (reader == null) {
                this.setNull(parameterIndex);
            }
            else {
                char[] c = null;
                int len = 0;
                final boolean useLength = this.useStreamLengthsInPrepStmts.getValue();
                final String forcedEncoding = this.session.getPropertySet().getStringProperty(PropertyKey.clobCharacterEncoding).getStringValue();
                if (useLength && length != -1) {
                    c = new char[length];
                    final int numCharsRead = Util.readFully(reader, c, length);
                    if (forcedEncoding == null) {
                        this.setString(parameterIndex, new String(c, 0, numCharsRead));
                    }
                    else {
                        this.setBytes(parameterIndex, StringUtils.getBytes(new String(c, 0, numCharsRead), forcedEncoding));
                    }
                }
                else {
                    c = new char[4096];
                    final StringBuilder buf = new StringBuilder();
                    while ((len = reader.read(c)) != -1) {
                        buf.append(c, 0, len);
                    }
                    if (forcedEncoding == null) {
                        this.setString(parameterIndex, buf.toString());
                    }
                    else {
                        this.setBytes(parameterIndex, StringUtils.getBytes(buf.toString(), forcedEncoding));
                    }
                }
                ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setMysqlType(MysqlType.TEXT);
            }
        }
        catch (UnsupportedEncodingException uec) {
            throw ExceptionFactory.createException(WrongArgumentException.class, uec.toString(), uec, this.session.getExceptionInterceptor());
        }
        catch (IOException ioEx) {
            throw ExceptionFactory.createException(ioEx.toString(), ioEx, this.session.getExceptionInterceptor());
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
    public void setClob(final int i, final Clob x) {
        if (x == null) {
            this.setNull(i);
        }
        else {
            try {
                final String forcedEncoding = this.session.getPropertySet().getStringProperty(PropertyKey.clobCharacterEncoding).getStringValue();
                if (forcedEncoding == null) {
                    this.setString(i, x.getSubString(1L, (int)x.length()));
                }
                else {
                    this.setBytes(i, StringUtils.getBytes(x.getSubString(1L, (int)x.length()), forcedEncoding));
                }
                ((ClientPreparedQueryBindValue[])this.bindValues)[i].setMysqlType(MysqlType.TEXT);
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
            this.ddf = TimeUtil.getSimpleDateFormat(this.ddf, "''yyyy-MM-dd''", cal, (cal != null) ? null : this.session.getServerSession().getDefaultTimeZone());
            this.setValue(parameterIndex, this.ddf.format(x));
        }
    }
    
    @Override
    public void setDouble(final int parameterIndex, final double x) {
        if (!this.session.getPropertySet().getBooleanProperty(PropertyKey.allowNanAndInf).getValue() && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x))) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("PreparedStatement.64", new Object[] { x }), this.session.getExceptionInterceptor());
        }
        this.setValue(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)), MysqlType.DOUBLE);
    }
    
    @Override
    public void setFloat(final int parameterIndex, final float x) {
        this.setValue(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)), MysqlType.FLOAT);
    }
    
    @Override
    public void setInt(final int parameterIndex, final int x) {
        this.setValue(parameterIndex, String.valueOf(x), MysqlType.INT);
    }
    
    @Override
    public void setLong(final int parameterIndex, final long x) {
        this.setValue(parameterIndex, String.valueOf(x), MysqlType.BIGINT);
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader value) {
        this.setNCharacterStream(parameterIndex, value, -1L);
    }
    
    @Override
    public void setNCharacterStream(final int parameterIndex, final Reader reader, final long length) {
        if (reader == null) {
            this.setNull(parameterIndex);
        }
        else {
            try {
                char[] c = null;
                int len = 0;
                final boolean useLength = this.useStreamLengthsInPrepStmts.getValue();
                if (useLength && length != -1L) {
                    c = new char[(int)length];
                    final int numCharsRead = Util.readFully(reader, c, (int)length);
                    this.setNString(parameterIndex, new String(c, 0, numCharsRead));
                }
                else {
                    c = new char[4096];
                    final StringBuilder buf = new StringBuilder();
                    while ((len = reader.read(c)) != -1) {
                        buf.append(c, 0, len);
                    }
                    this.setNString(parameterIndex, buf.toString());
                }
                ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setMysqlType(MysqlType.TEXT);
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(t.getMessage(), t, this.session.getExceptionInterceptor());
            }
        }
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader) {
        this.setNCharacterStream(parameterIndex, reader);
    }
    
    @Override
    public void setNClob(final int parameterIndex, final Reader reader, final long length) {
        if (reader == null) {
            this.setNull(parameterIndex);
        }
        else {
            this.setNCharacterStream(parameterIndex, reader, length);
        }
    }
    
    @Override
    public void setNClob(final int parameterIndex, final NClob value) {
        if (value == null) {
            this.setNull(parameterIndex);
        }
        else {
            try {
                this.setNCharacterStream(parameterIndex, value.getCharacterStream(), value.length());
            }
            catch (Throwable t) {
                throw ExceptionFactory.createException(t.getMessage(), t, this.session.getExceptionInterceptor());
            }
        }
    }
    
    @Override
    public void setNString(final int parameterIndex, final String x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            if (this.charEncoding.equalsIgnoreCase("UTF-8") || this.charEncoding.equalsIgnoreCase("utf8")) {
                this.setString(parameterIndex, x);
                return;
            }
            final int stringLength = x.length();
            final StringBuilder buf = new StringBuilder((int)(x.length() * 1.1 + 4.0));
            buf.append("_utf8");
            buf.append('\'');
            for (int i = 0; i < stringLength; ++i) {
                final char c = x.charAt(i);
                switch (c) {
                    case '\0': {
                        buf.append('\\');
                        buf.append('0');
                        break;
                    }
                    case '\n': {
                        buf.append('\\');
                        buf.append('n');
                        break;
                    }
                    case '\r': {
                        buf.append('\\');
                        buf.append('r');
                        break;
                    }
                    case '\\': {
                        buf.append('\\');
                        buf.append('\\');
                        break;
                    }
                    case '\'': {
                        buf.append('\\');
                        buf.append('\'');
                        break;
                    }
                    case '\"': {
                        if (this.session.getServerSession().useAnsiQuotedIdentifiers()) {
                            buf.append('\\');
                        }
                        buf.append('\"');
                        break;
                    }
                    case '\u001a': {
                        buf.append('\\');
                        buf.append('Z');
                        break;
                    }
                    default: {
                        buf.append(c);
                        break;
                    }
                }
            }
            buf.append('\'');
            final byte[] parameterAsBytes = this.isLoadDataQuery ? StringUtils.getBytes(buf.toString()) : StringUtils.getBytes(buf.toString(), "UTF-8");
            this.setValue(parameterIndex, parameterAsBytes, MysqlType.VARCHAR);
        }
    }
    
    @Override
    public synchronized void setNull(final int parameterIndex) {
        this.setValue(parameterIndex, "null");
        ((ClientPreparedQueryBindValue[])this.bindValues)[parameterIndex].setNull(true);
    }
    
    @Override
    public void setShort(final int parameterIndex, final short x) {
        this.setValue(parameterIndex, String.valueOf(x), MysqlType.SMALLINT);
    }
    
    @Override
    public void setString(final int parameterIndex, final String x) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            final int stringLength = x.length();
            if (this.session.getServerSession().isNoBackslashEscapesSet()) {
                final boolean needsHexEscape = this.isEscapeNeededForString(x, stringLength);
                if (!needsHexEscape) {
                    final StringBuilder quotedString = new StringBuilder(x.length() + 2);
                    quotedString.append('\'');
                    quotedString.append(x);
                    quotedString.append('\'');
                    final byte[] parameterAsBytes = this.isLoadDataQuery ? StringUtils.getBytes(quotedString.toString()) : StringUtils.getBytes(quotedString.toString(), this.charEncoding);
                    this.setValue(parameterIndex, parameterAsBytes);
                }
                else {
                    final byte[] parameterAsBytes2 = this.isLoadDataQuery ? StringUtils.getBytes(x) : StringUtils.getBytes(x, this.charEncoding);
                    this.setBytes(parameterIndex, parameterAsBytes2);
                }
                return;
            }
            String parameterAsString = x;
            boolean needsQuoted = true;
            if (this.isLoadDataQuery || this.isEscapeNeededForString(x, stringLength)) {
                needsQuoted = false;
                final StringBuilder buf = new StringBuilder((int)(x.length() * 1.1));
                buf.append('\'');
                for (int i = 0; i < stringLength; ++i) {
                    final char c = x.charAt(i);
                    switch (c) {
                        case '\0': {
                            buf.append('\\');
                            buf.append('0');
                            break;
                        }
                        case '\n': {
                            buf.append('\\');
                            buf.append('n');
                            break;
                        }
                        case '\r': {
                            buf.append('\\');
                            buf.append('r');
                            break;
                        }
                        case '\\': {
                            buf.append('\\');
                            buf.append('\\');
                            break;
                        }
                        case '\'': {
                            buf.append('\\');
                            buf.append('\'');
                            break;
                        }
                        case '\"': {
                            if (this.session.getServerSession().useAnsiQuotedIdentifiers()) {
                                buf.append('\\');
                            }
                            buf.append('\"');
                            break;
                        }
                        case '\u001a': {
                            buf.append('\\');
                            buf.append('Z');
                            break;
                        }
                        case '¥':
                        case '\u20a9': {
                            if (this.charsetEncoder != null) {
                                final CharBuffer cbuf = CharBuffer.allocate(1);
                                final ByteBuffer bbuf = ByteBuffer.allocate(1);
                                cbuf.put(c);
                                cbuf.position(0);
                                this.charsetEncoder.encode(cbuf, bbuf, true);
                                if (bbuf.get(0) == 92) {
                                    buf.append('\\');
                                }
                            }
                            buf.append(c);
                            break;
                        }
                        default: {
                            buf.append(c);
                            break;
                        }
                    }
                }
                buf.append('\'');
                parameterAsString = buf.toString();
            }
            final byte[] parameterAsBytes = this.isLoadDataQuery ? StringUtils.getBytes(parameterAsString) : (needsQuoted ? StringUtils.getBytesWrapped(parameterAsString, '\'', '\'', this.charEncoding) : StringUtils.getBytes(parameterAsString, this.charEncoding));
            this.setValue(parameterIndex, parameterAsBytes, MysqlType.VARCHAR);
        }
    }
    
    private boolean isEscapeNeededForString(final String x, final int stringLength) {
        boolean needsHexEscape = false;
        for (int i = 0; i < stringLength; ++i) {
            final char c = x.charAt(i);
            switch (c) {
                case '\0':
                case '\n':
                case '\r':
                case '\u001a':
                case '\"':
                case '\'':
                case '\\': {
                    needsHexEscape = true;
                    break;
                }
            }
            if (needsHexEscape) {
                break;
            }
        }
        return needsHexEscape;
    }
    
    @Override
    public void setTime(final int parameterIndex, final Time x, final Calendar cal) {
        if (x == null) {
            this.setNull(parameterIndex);
        }
        else {
            this.tdf = TimeUtil.getSimpleDateFormat(this.tdf, "''HH:mm:ss''", cal, (cal != null) ? null : this.session.getServerSession().getDefaultTimeZone());
            this.setValue(parameterIndex, this.tdf.format(x), MysqlType.TIME);
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
            x = (Timestamp)x.clone();
            if (!this.session.getServerSession().getCapabilities().serverSupportsFracSecs() || (!this.sendFractionalSeconds.getValue() && fractionalLength == 0)) {
                x = TimeUtil.truncateFractionalSeconds(x);
            }
            if (fractionalLength < 0) {
                fractionalLength = 6;
            }
            x = TimeUtil.adjustTimestampNanosPrecision(x, fractionalLength, !this.session.getServerSession().isServerTruncatesFracSecs());
            this.tsdf = TimeUtil.getSimpleDateFormat(this.tsdf, "''yyyy-MM-dd HH:mm:ss", targetCalendar, (targetCalendar != null) ? null : this.session.getServerSession().getDefaultTimeZone());
            final StringBuffer buf = new StringBuffer();
            buf.append(this.tsdf.format(x));
            if (this.session.getServerSession().getCapabilities().serverSupportsFracSecs()) {
                buf.append('.');
                buf.append(TimeUtil.formatNanos(x.getNanos(), 6));
            }
            buf.append('\'');
            this.setValue(parameterIndex, buf.toString(), MysqlType.TIMESTAMP);
        }
    }
}
