
package com.mysql.cj.protocol.a.authentication;

import com.mysql.cj.protocol.Message;
import java.io.UnsupportedEncodingException;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.protocol.a.NativeConstants;
import java.util.List;
import com.mysql.cj.protocol.Protocol;
import com.mysql.cj.protocol.a.NativePacketPayload;
import com.mysql.cj.protocol.AuthenticationPlugin;

public class MysqlOldPasswordPlugin implements AuthenticationPlugin<NativePacketPayload>
{
    private Protocol<NativePacketPayload> protocol;
    private String password;
    
    public MysqlOldPasswordPlugin() {
        this.password = null;
    }
    
    @Override
    public void init(final Protocol<NativePacketPayload> prot) {
        this.protocol = prot;
    }
    
    @Override
    public void destroy() {
        this.password = null;
    }
    
    @Override
    public String getProtocolPluginName() {
        return "mysql_old_password";
    }
    
    @Override
    public boolean requiresConfidentiality() {
        return false;
    }
    
    @Override
    public boolean isReusable() {
        return true;
    }
    
    @Override
    public void setAuthenticationParameters(final String user, final String password) {
        this.password = password;
    }
    
    @Override
    public boolean nextAuthenticationStep(final NativePacketPayload fromServer, final List<NativePacketPayload> toServer) {
        toServer.clear();
        NativePacketPayload bresp = null;
        final String pwd = this.password;
        if (fromServer == null || pwd == null || pwd.length() == 0) {
            bresp = new NativePacketPayload(new byte[0]);
        }
        else {
            bresp = new NativePacketPayload(StringUtils.getBytes(newCrypt(pwd, fromServer.readString(NativeConstants.StringSelfDataType.STRING_TERM, null).substring(0, 8), this.protocol.getPasswordCharacterEncoding())));
            bresp.setPosition(bresp.getPayloadLength());
            bresp.writeInteger(NativeConstants.IntegerDataType.INT1, 0L);
            bresp.setPosition(0);
        }
        toServer.add(bresp);
        return true;
    }
    
    private static String newCrypt(final String password, final String seed, final String encoding) {
        if (password == null || password.length() == 0) {
            return password;
        }
        final long[] pw = newHash(seed.getBytes());
        final long[] msg = hashPre41Password(password, encoding);
        final long max = 1073741823L;
        long seed2 = (pw[0] ^ msg[0]) % max;
        long seed3 = (pw[1] ^ msg[1]) % max;
        final char[] chars = new char[seed.length()];
        for (int i = 0; i < seed.length(); ++i) {
            seed2 = (seed2 * 3L + seed3) % max;
            seed3 = (seed2 + seed3 + 33L) % max;
            final double d = seed2 / (double)max;
            final byte b = (byte)Math.floor(d * 31.0 + 64.0);
            chars[i] = (char)b;
        }
        seed2 = (seed2 * 3L + seed3) % max;
        seed3 = (seed2 + seed3 + 33L) % max;
        final double d = seed2 / (double)max;
        final byte b = (byte)Math.floor(d * 31.0);
        for (int i = 0; i < seed.length(); ++i) {
            final char[] array = chars;
            final int n = i;
            array[n] ^= (char)b;
        }
        return new String(chars);
    }
    
    private static long[] hashPre41Password(final String password, final String encoding) {
        try {
            return newHash(password.replaceAll("\\s", "").getBytes(encoding));
        }
        catch (UnsupportedEncodingException e) {
            return new long[0];
        }
    }
    
    private static long[] newHash(final byte[] password) {
        long nr = 1345345333L;
        long add = 7L;
        long nr2 = 305419889L;
        for (final byte b : password) {
            final long tmp = 0xFF & b;
            nr ^= ((nr & 0x3FL) + add) * tmp + (nr << 8);
            nr2 += (nr2 << 8 ^ nr);
            add += tmp;
        }
        final long[] result = { nr & 0x7FFFFFFFL, nr2 & 0x7FFFFFFFL };
        return result;
    }
}
