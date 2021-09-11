
package com.mysql.cj.result;

import com.mysql.cj.exceptions.DataConversionException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.protocol.a.MysqlTextValueDecoder;
import com.mysql.cj.log.ProfilerEvent;
import com.mysql.cj.log.ProfilerEventImpl;
import com.mysql.cj.util.LogUtils;
import com.mysql.cj.Constants;
import com.mysql.cj.Messages;
import com.mysql.cj.log.ProfilerEventHandler;

public class StringConverter<T> extends BaseDecoratingValueFactory<T>
{
    private String encoding;
    private boolean emptyStringsConvertToZero;
    private ProfilerEventHandler eventSink;
    
    public StringConverter(final String encoding, final ValueFactory<T> targetVf) {
        super(targetVf);
        this.emptyStringsConvertToZero = false;
        this.encoding = encoding;
    }
    
    public void setEmptyStringsConvertToZero(final boolean val) {
        this.emptyStringsConvertToZero = val;
    }
    
    public void setEventSink(final ProfilerEventHandler eventSink) {
        this.eventSink = eventSink;
    }
    
    private void issueConversionViaParsingWarning() {
        if (this.eventSink == null) {
            return;
        }
        final String message = Messages.getString("ResultSet.CostlyConversion", new Object[] { this.targetVf.getTargetTypeName(), -1, "<unknown>", "<unknown>", "<unknown>", "<unknown>", "<unknown>", "<unknown>" });
        this.eventSink.consumeEvent(new ProfilerEventImpl((byte)0, "", "<unknown>", -1L, -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, LogUtils.findCallingClassAndMethod(new Throwable()), message));
    }
    
    @Override
    public T createFromBytes(final byte[] origBytes, final int offset, final int length) {
        final MysqlTextValueDecoder stringInterpreter = new MysqlTextValueDecoder();
        final String s = StringUtils.toString(origBytes, offset, length, this.encoding);
        final byte[] bytes = s.getBytes();
        final ValueFactory<T> vf = this.targetVf;
        this.issueConversionViaParsingWarning();
        if (s.length() == 0) {
            if (this.emptyStringsConvertToZero) {
                return this.targetVf.createFromLong(0L);
            }
        }
        else {
            if (s.equalsIgnoreCase("Y") || s.equalsIgnoreCase("true")) {
                return vf.createFromLong(1L);
            }
            if (s.equalsIgnoreCase("N") || s.equalsIgnoreCase("false")) {
                return vf.createFromLong(0L);
            }
            if (s.contains("e") || s.contains("E") || s.matches("-?(\\d+)?\\.\\d+")) {
                return stringInterpreter.decodeDouble(bytes, 0, bytes.length, vf);
            }
            if (s.matches("-?\\d+")) {
                if (s.charAt(0) == '-') {
                    return stringInterpreter.decodeInt8(bytes, 0, bytes.length, vf);
                }
                return stringInterpreter.decodeUInt8(bytes, 0, bytes.length, vf);
            }
            else {
                if (s.length() == 10 && s.charAt(4) == '-' && s.charAt(7) == '-') {
                    return stringInterpreter.decodeDate(bytes, 0, bytes.length, vf);
                }
                if (s.length() >= 8 && s.length() <= 17 && s.charAt(2) == ':' && s.charAt(5) == ':') {
                    return stringInterpreter.decodeTime(bytes, 0, bytes.length, vf);
                }
                if (s.length() >= 19 && (s.length() <= 26 || s.length() == 29) && s.charAt(4) == '-' && s.charAt(7) == '-' && s.charAt(10) == ' ' && s.charAt(13) == ':' && s.charAt(16) == ':') {
                    return stringInterpreter.decodeTimestamp(bytes, 0, bytes.length, vf);
                }
            }
        }
        throw new DataConversionException(Messages.getString("ResultSet.UnableToInterpretString", new Object[] { s }));
    }
    
    @Override
    public T createFromBit(final byte[] bytes, final int offset, final int length) {
        final MysqlTextValueDecoder stringInterpreter = new MysqlTextValueDecoder();
        final ValueFactory<T> vf = this.targetVf;
        return stringInterpreter.decodeBit(bytes, offset, length, vf);
    }
}
