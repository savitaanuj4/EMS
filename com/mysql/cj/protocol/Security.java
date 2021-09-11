
package com.mysql.cj.protocol;

import java.security.DigestException;
import java.security.NoSuchAlgorithmException;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.security.MessageDigest;
import com.mysql.cj.util.StringUtils;

public class Security
{
    private static int CACHING_SHA2_DIGEST_LENGTH;
    
    public static void xorString(final byte[] from, final byte[] to, final byte[] scramble, final int length) {
        int pos = 0;
        final int scrambleLength = scramble.length;
        while (pos < length) {
            to[pos] = (byte)(from[pos] ^ scramble[pos % scrambleLength]);
            ++pos;
        }
    }
    
    public static byte[] scramble411(final String password, final byte[] seed, final String passwordEncoding) {
        final byte[] passwordBytes = (passwordEncoding == null || passwordEncoding.length() == 0) ? StringUtils.getBytes(password) : StringUtils.getBytes(password, passwordEncoding);
        return scramble411(passwordBytes, seed);
    }
    
    public static byte[] scramble411(final byte[] password, final byte[] seed) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-1");
        }
        catch (NoSuchAlgorithmException ex) {
            throw new AssertionFailedException(ex);
        }
        final byte[] passwordHashStage1 = md.digest(password);
        md.reset();
        final byte[] passwordHashStage2 = md.digest(passwordHashStage1);
        md.reset();
        md.update(seed);
        md.update(passwordHashStage2);
        final byte[] toBeXord = md.digest();
        for (int numToXor = toBeXord.length, i = 0; i < numToXor; ++i) {
            toBeXord[i] ^= passwordHashStage1[i];
        }
        return toBeXord;
    }
    
    public static byte[] scrambleCachingSha2(final byte[] password, final byte[] seed) throws DigestException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException ex) {
            throw new AssertionFailedException(ex);
        }
        final byte[] dig1 = new byte[Security.CACHING_SHA2_DIGEST_LENGTH];
        final byte[] dig2 = new byte[Security.CACHING_SHA2_DIGEST_LENGTH];
        final byte[] scramble1 = new byte[Security.CACHING_SHA2_DIGEST_LENGTH];
        md.update(password, 0, password.length);
        md.digest(dig1, 0, Security.CACHING_SHA2_DIGEST_LENGTH);
        md.reset();
        md.update(dig1, 0, dig1.length);
        md.digest(dig2, 0, Security.CACHING_SHA2_DIGEST_LENGTH);
        md.reset();
        md.update(dig2, 0, dig1.length);
        md.update(seed, 0, seed.length);
        md.digest(scramble1, 0, Security.CACHING_SHA2_DIGEST_LENGTH);
        final byte[] mysqlScrambleBuff = new byte[Security.CACHING_SHA2_DIGEST_LENGTH];
        xorString(dig1, mysqlScrambleBuff, scramble1, Security.CACHING_SHA2_DIGEST_LENGTH);
        return mysqlScrambleBuff;
    }
    
    private Security() {
    }
    
    static {
        Security.CACHING_SHA2_DIGEST_LENGTH = 32;
    }
}
