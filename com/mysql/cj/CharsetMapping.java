
package com.mysql.cj;

import java.util.TreeMap;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.List;
import java.util.Map;

public class CharsetMapping
{
    public static final int MAP_SIZE = 2048;
    public static final String[] COLLATION_INDEX_TO_COLLATION_NAME;
    public static final MysqlCharset[] COLLATION_INDEX_TO_CHARSET;
    public static final Map<String, MysqlCharset> CHARSET_NAME_TO_CHARSET;
    public static final Map<String, Integer> CHARSET_NAME_TO_COLLATION_INDEX;
    private static final Map<String, List<MysqlCharset>> JAVA_ENCODING_UC_TO_MYSQL_CHARSET;
    private static final Set<String> MULTIBYTE_ENCODINGS;
    public static final Set<Integer> UTF8MB4_INDEXES;
    private static final String MYSQL_CHARSET_NAME_armscii8 = "armscii8";
    private static final String MYSQL_CHARSET_NAME_ascii = "ascii";
    private static final String MYSQL_CHARSET_NAME_big5 = "big5";
    private static final String MYSQL_CHARSET_NAME_binary = "binary";
    private static final String MYSQL_CHARSET_NAME_cp1250 = "cp1250";
    private static final String MYSQL_CHARSET_NAME_cp1251 = "cp1251";
    private static final String MYSQL_CHARSET_NAME_cp1256 = "cp1256";
    private static final String MYSQL_CHARSET_NAME_cp1257 = "cp1257";
    private static final String MYSQL_CHARSET_NAME_cp850 = "cp850";
    private static final String MYSQL_CHARSET_NAME_cp852 = "cp852";
    private static final String MYSQL_CHARSET_NAME_cp866 = "cp866";
    private static final String MYSQL_CHARSET_NAME_cp932 = "cp932";
    private static final String MYSQL_CHARSET_NAME_dec8 = "dec8";
    private static final String MYSQL_CHARSET_NAME_eucjpms = "eucjpms";
    private static final String MYSQL_CHARSET_NAME_euckr = "euckr";
    private static final String MYSQL_CHARSET_NAME_gb18030 = "gb18030";
    private static final String MYSQL_CHARSET_NAME_gb2312 = "gb2312";
    private static final String MYSQL_CHARSET_NAME_gbk = "gbk";
    private static final String MYSQL_CHARSET_NAME_geostd8 = "geostd8";
    private static final String MYSQL_CHARSET_NAME_greek = "greek";
    private static final String MYSQL_CHARSET_NAME_hebrew = "hebrew";
    private static final String MYSQL_CHARSET_NAME_hp8 = "hp8";
    private static final String MYSQL_CHARSET_NAME_keybcs2 = "keybcs2";
    private static final String MYSQL_CHARSET_NAME_koi8r = "koi8r";
    private static final String MYSQL_CHARSET_NAME_koi8u = "koi8u";
    private static final String MYSQL_CHARSET_NAME_latin1 = "latin1";
    private static final String MYSQL_CHARSET_NAME_latin2 = "latin2";
    private static final String MYSQL_CHARSET_NAME_latin5 = "latin5";
    private static final String MYSQL_CHARSET_NAME_latin7 = "latin7";
    private static final String MYSQL_CHARSET_NAME_macce = "macce";
    private static final String MYSQL_CHARSET_NAME_macroman = "macroman";
    private static final String MYSQL_CHARSET_NAME_sjis = "sjis";
    private static final String MYSQL_CHARSET_NAME_swe7 = "swe7";
    private static final String MYSQL_CHARSET_NAME_tis620 = "tis620";
    private static final String MYSQL_CHARSET_NAME_ucs2 = "ucs2";
    private static final String MYSQL_CHARSET_NAME_ujis = "ujis";
    private static final String MYSQL_CHARSET_NAME_utf16 = "utf16";
    private static final String MYSQL_CHARSET_NAME_utf16le = "utf16le";
    private static final String MYSQL_CHARSET_NAME_utf32 = "utf32";
    private static final String MYSQL_CHARSET_NAME_utf8 = "utf8";
    private static final String MYSQL_CHARSET_NAME_utf8mb4 = "utf8mb4";
    public static final String NOT_USED = "latin1";
    public static final String COLLATION_NOT_DEFINED = "none";
    public static final int MYSQL_COLLATION_INDEX_utf8 = 33;
    public static final int MYSQL_COLLATION_INDEX_binary = 63;
    private static int numberOfEncodingsConfigured;
    
    public static final String getMysqlCharsetForJavaEncoding(final String javaEncoding, final ServerVersion version) {
        final List<MysqlCharset> mysqlCharsets = CharsetMapping.JAVA_ENCODING_UC_TO_MYSQL_CHARSET.get(javaEncoding.toUpperCase(Locale.ENGLISH));
        if (mysqlCharsets != null) {
            final Iterator<MysqlCharset> iter = mysqlCharsets.iterator();
            MysqlCharset currentChoice = null;
            while (iter.hasNext()) {
                final MysqlCharset charset = iter.next();
                if (version == null) {
                    return charset.charsetName;
                }
                if ((currentChoice != null && currentChoice.minimumVersion.compareTo(charset.minimumVersion) >= 0 && (currentChoice.priority >= charset.priority || currentChoice.minimumVersion.compareTo(charset.minimumVersion) != 0)) || !charset.isOkayForVersion(version)) {
                    continue;
                }
                currentChoice = charset;
            }
            if (currentChoice != null) {
                return currentChoice.charsetName;
            }
        }
        return null;
    }
    
    public static int getCollationIndexForJavaEncoding(final String javaEncoding, final ServerVersion version) {
        final String charsetName = getMysqlCharsetForJavaEncoding(javaEncoding, version);
        if (charsetName != null) {
            final Integer ci = CharsetMapping.CHARSET_NAME_TO_COLLATION_INDEX.get(charsetName);
            if (ci != null) {
                return ci;
            }
        }
        return 0;
    }
    
    public static String getMysqlCharsetNameForCollationIndex(final Integer collationIndex) {
        if (collationIndex != null && collationIndex > 0 && collationIndex < 2048) {
            return CharsetMapping.COLLATION_INDEX_TO_CHARSET[collationIndex].charsetName;
        }
        return null;
    }
    
    public static String getJavaEncodingForMysqlCharset(final String mysqlCharsetName, final String javaEncoding) {
        String res = javaEncoding;
        final MysqlCharset cs = CharsetMapping.CHARSET_NAME_TO_CHARSET.get(mysqlCharsetName);
        if (cs != null) {
            res = cs.getMatchingJavaEncoding(javaEncoding);
        }
        return res;
    }
    
    public static String getJavaEncodingForMysqlCharset(final String mysqlCharsetName) {
        return getJavaEncodingForMysqlCharset(mysqlCharsetName, null);
    }
    
    public static String getJavaEncodingForCollationIndex(final Integer collationIndex, final String javaEncoding) {
        if (collationIndex != null && collationIndex > 0 && collationIndex < 2048) {
            final MysqlCharset cs = CharsetMapping.COLLATION_INDEX_TO_CHARSET[collationIndex];
            return cs.getMatchingJavaEncoding(javaEncoding);
        }
        return null;
    }
    
    public static String getJavaEncodingForCollationIndex(final Integer collationIndex) {
        return getJavaEncodingForCollationIndex(collationIndex, null);
    }
    
    public static final int getNumberOfCharsetsConfigured() {
        return CharsetMapping.numberOfEncodingsConfigured;
    }
    
    public static final boolean isMultibyteCharset(final String javaEncodingName) {
        return CharsetMapping.MULTIBYTE_ENCODINGS.contains(javaEncodingName.toUpperCase(Locale.ENGLISH));
    }
    
    public static int getMblen(final String charsetName) {
        if (charsetName != null) {
            final MysqlCharset cs = CharsetMapping.CHARSET_NAME_TO_CHARSET.get(charsetName);
            if (cs != null) {
                return cs.mblen;
            }
        }
        return 0;
    }
    
    static {
        CharsetMapping.numberOfEncodingsConfigured = 0;
        final MysqlCharset[] charset = { new MysqlCharset("ascii", 1, 0, new String[] { "US-ASCII", "ASCII" }), new MysqlCharset("big5", 2, 0, new String[] { "Big5" }), new MysqlCharset("gbk", 2, 0, new String[] { "GBK" }), new MysqlCharset("sjis", 2, 0, new String[] { "SHIFT_JIS", "Cp943", "WINDOWS-31J" }), new MysqlCharset("cp932", 2, 1, new String[] { "WINDOWS-31J" }), new MysqlCharset("gb2312", 2, 0, new String[] { "GB2312" }), new MysqlCharset("ujis", 3, 0, new String[] { "EUC_JP" }), new MysqlCharset("eucjpms", 3, 0, new String[] { "EUC_JP_Solaris" }, new ServerVersion(5, 0, 3)), new MysqlCharset("gb18030", 4, 0, new String[] { "GB18030" }, new ServerVersion(5, 7, 4)), new MysqlCharset("euckr", 2, 0, new String[] { "EUC-KR" }), new MysqlCharset("latin1", 1, 1, new String[] { "Cp1252", "ISO8859_1" }), new MysqlCharset("swe7", 1, 0, new String[] { "Cp1252" }), new MysqlCharset("hp8", 1, 0, new String[] { "Cp1252" }), new MysqlCharset("dec8", 1, 0, new String[] { "Cp1252" }), new MysqlCharset("armscii8", 1, 0, new String[] { "Cp1252" }), new MysqlCharset("geostd8", 1, 0, new String[] { "Cp1252" }), new MysqlCharset("latin2", 1, 0, new String[] { "ISO8859_2" }), new MysqlCharset("greek", 1, 0, new String[] { "ISO8859_7", "greek" }), new MysqlCharset("latin7", 1, 0, new String[] { "ISO-8859-13" }), new MysqlCharset("hebrew", 1, 0, new String[] { "ISO8859_8" }), new MysqlCharset("latin5", 1, 0, new String[] { "ISO8859_9" }), new MysqlCharset("cp850", 1, 0, new String[] { "Cp850", "Cp437" }), new MysqlCharset("cp852", 1, 0, new String[] { "Cp852" }), new MysqlCharset("keybcs2", 1, 0, new String[] { "Cp852" }), new MysqlCharset("cp866", 1, 0, new String[] { "Cp866" }), new MysqlCharset("koi8r", 1, 1, new String[] { "KOI8_R" }), new MysqlCharset("koi8u", 1, 0, new String[] { "KOI8_R" }), new MysqlCharset("tis620", 1, 0, new String[] { "TIS620" }), new MysqlCharset("cp1250", 1, 0, new String[] { "Cp1250" }), new MysqlCharset("cp1251", 1, 1, new String[] { "Cp1251" }), new MysqlCharset("cp1256", 1, 0, new String[] { "Cp1256" }), new MysqlCharset("cp1257", 1, 0, new String[] { "Cp1257" }), new MysqlCharset("macroman", 1, 0, new String[] { "MacRoman" }), new MysqlCharset("macce", 1, 0, new String[] { "MacCentralEurope" }), new MysqlCharset("utf8", 3, 1, new String[] { "UTF-8" }), new MysqlCharset("utf8mb4", 4, 0, new String[] { "UTF-8" }), new MysqlCharset("ucs2", 2, 0, new String[] { "UnicodeBig" }), new MysqlCharset("binary", 1, 1, new String[] { "ISO8859_1" }), new MysqlCharset("utf16", 4, 0, new String[] { "UTF-16" }), new MysqlCharset("utf16le", 4, 0, new String[] { "UTF-16LE" }), new MysqlCharset("utf32", 4, 0, new String[] { "UTF-32" }) };
        final HashMap<String, MysqlCharset> charsetNameToMysqlCharsetMap = new HashMap<String, MysqlCharset>();
        final HashMap<String, List<MysqlCharset>> javaUcToMysqlCharsetMap = new HashMap<String, List<MysqlCharset>>();
        final Set<String> tempMultibyteEncodings = new HashSet<String>();
        for (int i = 0; i < charset.length; ++i) {
            final String charsetName = charset[i].charsetName;
            charsetNameToMysqlCharsetMap.put(charsetName, charset[i]);
            CharsetMapping.numberOfEncodingsConfigured += charset[i].javaEncodingsUc.size();
            for (final String encUC : charset[i].javaEncodingsUc) {
                List<MysqlCharset> charsets = javaUcToMysqlCharsetMap.get(encUC);
                if (charsets == null) {
                    charsets = new ArrayList<MysqlCharset>();
                    javaUcToMysqlCharsetMap.put(encUC, charsets);
                }
                charsets.add(charset[i]);
                if (charset[i].mblen > 1) {
                    tempMultibyteEncodings.add(encUC);
                }
            }
        }
        CHARSET_NAME_TO_CHARSET = Collections.unmodifiableMap((Map<? extends String, ? extends MysqlCharset>)charsetNameToMysqlCharsetMap);
        JAVA_ENCODING_UC_TO_MYSQL_CHARSET = Collections.unmodifiableMap((Map<? extends String, ? extends List<MysqlCharset>>)javaUcToMysqlCharsetMap);
        MULTIBYTE_ENCODINGS = Collections.unmodifiableSet((Set<? extends String>)tempMultibyteEncodings);
        Collation[] collation = { null, new Collation(1, "big5_chinese_ci", 1, "big5"), new Collation(2, "latin2_czech_cs", 0, "latin2"), new Collation(3, "dec8_swedish_ci", 0, "dec8"), new Collation(4, "cp850_general_ci", 1, "cp850"), new Collation(5, "latin1_german1_ci", 0, "latin1"), new Collation(6, "hp8_english_ci", 0, "hp8"), new Collation(7, "koi8r_general_ci", 0, "koi8r"), new Collation(8, "latin1_swedish_ci", 1, "latin1"), new Collation(9, "latin2_general_ci", 1, "latin2"), new Collation(10, "swe7_swedish_ci", 0, "swe7"), new Collation(11, "ascii_general_ci", 0, "ascii"), new Collation(12, "ujis_japanese_ci", 0, "ujis"), new Collation(13, "sjis_japanese_ci", 0, "sjis"), new Collation(14, "cp1251_bulgarian_ci", 0, "cp1251"), new Collation(15, "latin1_danish_ci", 0, "latin1"), new Collation(16, "hebrew_general_ci", 0, "hebrew"), null, new Collation(18, "tis620_thai_ci", 0, "tis620"), new Collation(19, "euckr_korean_ci", 0, "euckr"), new Collation(20, "latin7_estonian_cs", 0, "latin7"), new Collation(21, "latin2_hungarian_ci", 0, "latin2"), new Collation(22, "koi8u_general_ci", 0, "koi8u"), new Collation(23, "cp1251_ukrainian_ci", 0, "cp1251"), new Collation(24, "gb2312_chinese_ci", 0, "gb2312"), new Collation(25, "greek_general_ci", 0, "greek"), new Collation(26, "cp1250_general_ci", 1, "cp1250"), new Collation(27, "latin2_croatian_ci", 0, "latin2"), new Collation(28, "gbk_chinese_ci", 1, "gbk"), new Collation(29, "cp1257_lithuanian_ci", 0, "cp1257"), new Collation(30, "latin5_turkish_ci", 1, "latin5"), new Collation(31, "latin1_german2_ci", 0, "latin1"), new Collation(32, "armscii8_general_ci", 0, "armscii8"), new Collation(33, "utf8_general_ci", 1, "utf8"), new Collation(34, "cp1250_czech_cs", 0, "cp1250"), new Collation(35, "ucs2_general_ci", 1, "ucs2"), new Collation(36, "cp866_general_ci", 1, "cp866"), new Collation(37, "keybcs2_general_ci", 1, "keybcs2"), new Collation(38, "macce_general_ci", 1, "macce"), new Collation(39, "macroman_general_ci", 1, "macroman"), new Collation(40, "cp852_general_ci", 1, "cp852"), new Collation(41, "latin7_general_ci", 1, "latin7"), new Collation(42, "latin7_general_cs", 0, "latin7"), new Collation(43, "macce_bin", 0, "macce"), new Collation(44, "cp1250_croatian_ci", 0, "cp1250"), new Collation(45, "utf8mb4_general_ci", 0, "utf8mb4"), new Collation(46, "utf8mb4_bin", 0, "utf8mb4"), new Collation(47, "latin1_bin", 0, "latin1"), new Collation(48, "latin1_general_ci", 0, "latin1"), new Collation(49, "latin1_general_cs", 0, "latin1"), new Collation(50, "cp1251_bin", 0, "cp1251"), new Collation(51, "cp1251_general_ci", 1, "cp1251"), new Collation(52, "cp1251_general_cs", 0, "cp1251"), new Collation(53, "macroman_bin", 0, "macroman"), new Collation(54, "utf16_general_ci", 1, "utf16"), new Collation(55, "utf16_bin", 0, "utf16"), new Collation(56, "utf16le_general_ci", 1, "utf16le"), new Collation(57, "cp1256_general_ci", 1, "cp1256"), new Collation(58, "cp1257_bin", 0, "cp1257"), new Collation(59, "cp1257_general_ci", 1, "cp1257"), new Collation(60, "utf32_general_ci", 1, "utf32"), new Collation(61, "utf32_bin", 0, "utf32"), new Collation(62, "utf16le_bin", 0, "utf16le"), new Collation(63, "binary", 1, "binary"), new Collation(64, "armscii8_bin", 0, "armscii8"), new Collation(65, "ascii_bin", 0, "ascii"), new Collation(66, "cp1250_bin", 0, "cp1250"), new Collation(67, "cp1256_bin", 0, "cp1256"), new Collation(68, "cp866_bin", 0, "cp866"), new Collation(69, "dec8_bin", 0, "dec8"), new Collation(70, "greek_bin", 0, "greek"), new Collation(71, "hebrew_bin", 0, "hebrew"), new Collation(72, "hp8_bin", 0, "hp8"), new Collation(73, "keybcs2_bin", 0, "keybcs2"), new Collation(74, "koi8r_bin", 0, "koi8r"), new Collation(75, "koi8u_bin", 0, "koi8u"), new Collation(76, "utf8_tolower_ci", 0, "utf8"), new Collation(77, "latin2_bin", 0, "latin2"), new Collation(78, "latin5_bin", 0, "latin5"), new Collation(79, "latin7_bin", 0, "latin7"), new Collation(80, "cp850_bin", 0, "cp850"), new Collation(81, "cp852_bin", 0, "cp852"), new Collation(82, "swe7_bin", 0, "swe7"), new Collation(83, "utf8_bin", 0, "utf8"), new Collation(84, "big5_bin", 0, "big5"), new Collation(85, "euckr_bin", 0, "euckr"), new Collation(86, "gb2312_bin", 0, "gb2312"), new Collation(87, "gbk_bin", 0, "gbk"), new Collation(88, "sjis_bin", 0, "sjis"), new Collation(89, "tis620_bin", 0, "tis620"), new Collation(90, "ucs2_bin", 0, "ucs2"), new Collation(91, "ujis_bin", 0, "ujis"), new Collation(92, "geostd8_general_ci", 0, "geostd8"), new Collation(93, "geostd8_bin", 0, "geostd8"), new Collation(94, "latin1_spanish_ci", 0, "latin1"), new Collation(95, "cp932_japanese_ci", 1, "cp932"), new Collation(96, "cp932_bin", 0, "cp932"), new Collation(97, "eucjpms_japanese_ci", 1, "eucjpms"), new Collation(98, "eucjpms_bin", 0, "eucjpms"), new Collation(99, "cp1250_polish_ci", 0, "cp1250"), null, new Collation(101, "utf16_unicode_ci", 0, "utf16"), new Collation(102, "utf16_icelandic_ci", 0, "utf16"), new Collation(103, "utf16_latvian_ci", 0, "utf16"), new Collation(104, "utf16_romanian_ci", 0, "utf16"), new Collation(105, "utf16_slovenian_ci", 0, "utf16"), new Collation(106, "utf16_polish_ci", 0, "utf16"), new Collation(107, "utf16_estonian_ci", 0, "utf16"), new Collation(108, "utf16_spanish_ci", 0, "utf16"), new Collation(109, "utf16_swedish_ci", 0, "utf16"), new Collation(110, "utf16_turkish_ci", 0, "utf16"), new Collation(111, "utf16_czech_ci", 0, "utf16"), new Collation(112, "utf16_danish_ci", 0, "utf16"), new Collation(113, "utf16_lithuanian_ci", 0, "utf16"), new Collation(114, "utf16_slovak_ci", 0, "utf16"), new Collation(115, "utf16_spanish2_ci", 0, "utf16"), new Collation(116, "utf16_roman_ci", 0, "utf16"), new Collation(117, "utf16_persian_ci", 0, "utf16"), new Collation(118, "utf16_esperanto_ci", 0, "utf16"), new Collation(119, "utf16_hungarian_ci", 0, "utf16"), new Collation(120, "utf16_sinhala_ci", 0, "utf16"), new Collation(121, "utf16_german2_ci", 0, "utf16"), new Collation(122, "utf16_croatian_ci", 0, "utf16"), new Collation(123, "utf16_unicode_520_ci", 0, "utf16"), new Collation(124, "utf16_vietnamese_ci", 0, "utf16"), null, null, null, new Collation(128, "ucs2_unicode_ci", 0, "ucs2"), new Collation(129, "ucs2_icelandic_ci", 0, "ucs2"), new Collation(130, "ucs2_latvian_ci", 0, "ucs2"), new Collation(131, "ucs2_romanian_ci", 0, "ucs2"), new Collation(132, "ucs2_slovenian_ci", 0, "ucs2"), new Collation(133, "ucs2_polish_ci", 0, "ucs2"), new Collation(134, "ucs2_estonian_ci", 0, "ucs2"), new Collation(135, "ucs2_spanish_ci", 0, "ucs2"), new Collation(136, "ucs2_swedish_ci", 0, "ucs2"), new Collation(137, "ucs2_turkish_ci", 0, "ucs2"), new Collation(138, "ucs2_czech_ci", 0, "ucs2"), new Collation(139, "ucs2_danish_ci", 0, "ucs2"), new Collation(140, "ucs2_lithuanian_ci", 0, "ucs2"), new Collation(141, "ucs2_slovak_ci", 0, "ucs2"), new Collation(142, "ucs2_spanish2_ci", 0, "ucs2"), new Collation(143, "ucs2_roman_ci", 0, "ucs2"), new Collation(144, "ucs2_persian_ci", 0, "ucs2"), new Collation(145, "ucs2_esperanto_ci", 0, "ucs2"), new Collation(146, "ucs2_hungarian_ci", 0, "ucs2"), new Collation(147, "ucs2_sinhala_ci", 0, "ucs2"), new Collation(148, "ucs2_german2_ci", 0, "ucs2"), new Collation(149, "ucs2_croatian_ci", 0, "ucs2"), new Collation(150, "ucs2_unicode_520_ci", 0, "ucs2"), new Collation(151, "ucs2_vietnamese_ci", 0, "ucs2"), null, null, null, null, null, null, null, new Collation(159, "ucs2_general_mysql500_ci", 0, "ucs2"), new Collation(160, "utf32_unicode_ci", 0, "utf32"), new Collation(161, "utf32_icelandic_ci", 0, "utf32"), new Collation(162, "utf32_latvian_ci", 0, "utf32"), new Collation(163, "utf32_romanian_ci", 0, "utf32"), new Collation(164, "utf32_slovenian_ci", 0, "utf32"), new Collation(165, "utf32_polish_ci", 0, "utf32"), new Collation(166, "utf32_estonian_ci", 0, "utf32"), new Collation(167, "utf32_spanish_ci", 0, "utf32"), new Collation(168, "utf32_swedish_ci", 0, "utf32"), new Collation(169, "utf32_turkish_ci", 0, "utf32"), new Collation(170, "utf32_czech_ci", 0, "utf32"), new Collation(171, "utf32_danish_ci", 0, "utf32"), new Collation(172, "utf32_lithuanian_ci", 0, "utf32"), new Collation(173, "utf32_slovak_ci", 0, "utf32"), new Collation(174, "utf32_spanish2_ci", 0, "utf32"), new Collation(175, "utf32_roman_ci", 0, "utf32"), new Collation(176, "utf32_persian_ci", 0, "utf32"), new Collation(177, "utf32_esperanto_ci", 0, "utf32"), new Collation(178, "utf32_hungarian_ci", 0, "utf32"), new Collation(179, "utf32_sinhala_ci", 0, "utf32"), new Collation(180, "utf32_german2_ci", 0, "utf32"), new Collation(181, "utf32_croatian_ci", 0, "utf32"), new Collation(182, "utf32_unicode_520_ci", 0, "utf32"), new Collation(183, "utf32_vietnamese_ci", 0, "utf32"), null, null, null, null, null, null, null, null, new Collation(192, "utf8_unicode_ci", 0, "utf8"), new Collation(193, "utf8_icelandic_ci", 0, "utf8"), new Collation(194, "utf8_latvian_ci", 0, "utf8"), new Collation(195, "utf8_romanian_ci", 0, "utf8"), new Collation(196, "utf8_slovenian_ci", 0, "utf8"), new Collation(197, "utf8_polish_ci", 0, "utf8"), new Collation(198, "utf8_estonian_ci", 0, "utf8"), new Collation(199, "utf8_spanish_ci", 0, "utf8"), new Collation(200, "utf8_swedish_ci", 0, "utf8"), new Collation(201, "utf8_turkish_ci", 0, "utf8"), new Collation(202, "utf8_czech_ci", 0, "utf8"), new Collation(203, "utf8_danish_ci", 0, "utf8"), new Collation(204, "utf8_lithuanian_ci", 0, "utf8"), new Collation(205, "utf8_slovak_ci", 0, "utf8"), new Collation(206, "utf8_spanish2_ci", 0, "utf8"), new Collation(207, "utf8_roman_ci", 0, "utf8"), new Collation(208, "utf8_persian_ci", 0, "utf8"), new Collation(209, "utf8_esperanto_ci", 0, "utf8"), new Collation(210, "utf8_hungarian_ci", 0, "utf8"), new Collation(211, "utf8_sinhala_ci", 0, "utf8"), new Collation(212, "utf8_german2_ci", 0, "utf8"), new Collation(213, "utf8_croatian_ci", 0, "utf8"), new Collation(214, "utf8_unicode_520_ci", 0, "utf8"), new Collation(215, "utf8_vietnamese_ci", 0, "utf8"), null, null, null, null, null, null, null, new Collation(223, "utf8_general_mysql500_ci", 0, "utf8"), new Collation(224, "utf8mb4_unicode_ci", 0, "utf8mb4"), new Collation(225, "utf8mb4_icelandic_ci", 0, "utf8mb4"), new Collation(226, "utf8mb4_latvian_ci", 0, "utf8mb4"), new Collation(227, "utf8mb4_romanian_ci", 0, "utf8mb4"), new Collation(228, "utf8mb4_slovenian_ci", 0, "utf8mb4"), new Collation(229, "utf8mb4_polish_ci", 0, "utf8mb4"), new Collation(230, "utf8mb4_estonian_ci", 0, "utf8mb4"), new Collation(231, "utf8mb4_spanish_ci", 0, "utf8mb4"), new Collation(232, "utf8mb4_swedish_ci", 0, "utf8mb4"), new Collation(233, "utf8mb4_turkish_ci", 0, "utf8mb4"), new Collation(234, "utf8mb4_czech_ci", 0, "utf8mb4"), new Collation(235, "utf8mb4_danish_ci", 0, "utf8mb4"), new Collation(236, "utf8mb4_lithuanian_ci", 0, "utf8mb4"), new Collation(237, "utf8mb4_slovak_ci", 0, "utf8mb4"), new Collation(238, "utf8mb4_spanish2_ci", 0, "utf8mb4"), new Collation(239, "utf8mb4_roman_ci", 0, "utf8mb4"), new Collation(240, "utf8mb4_persian_ci", 0, "utf8mb4"), new Collation(241, "utf8mb4_esperanto_ci", 0, "utf8mb4"), new Collation(242, "utf8mb4_hungarian_ci", 0, "utf8mb4"), new Collation(243, "utf8mb4_sinhala_ci", 0, "utf8mb4"), new Collation(244, "utf8mb4_german2_ci", 0, "utf8mb4"), new Collation(245, "utf8mb4_croatian_ci", 0, "utf8mb4"), new Collation(246, "utf8mb4_unicode_520_ci", 0, "utf8mb4"), new Collation(247, "utf8mb4_vietnamese_ci", 0, "utf8mb4"), new Collation(248, "gb18030_chinese_ci", 1, "gb18030"), new Collation(249, "gb18030_bin", 0, "gb18030"), new Collation(250, "gb18030_unicode_520_ci", 0, "gb18030"), null, null, null, null, new Collation(255, "utf8mb4_0900_ai_ci", 1, "utf8mb4"), new Collation(256, "utf8mb4_de_pb_0900_ai_ci", 0, "utf8mb4"), new Collation(257, "utf8mb4_is_0900_ai_ci", 0, "utf8mb4"), new Collation(258, "utf8mb4_lv_0900_ai_ci", 0, "utf8mb4"), new Collation(259, "utf8mb4_ro_0900_ai_ci", 0, "utf8mb4"), new Collation(260, "utf8mb4_sl_0900_ai_ci", 0, "utf8mb4"), new Collation(261, "utf8mb4_pl_0900_ai_ci", 0, "utf8mb4"), new Collation(262, "utf8mb4_et_0900_ai_ci", 0, "utf8mb4"), new Collation(263, "utf8mb4_es_0900_ai_ci", 0, "utf8mb4"), new Collation(264, "utf8mb4_sv_0900_ai_ci", 0, "utf8mb4"), new Collation(265, "utf8mb4_tr_0900_ai_ci", 0, "utf8mb4"), new Collation(266, "utf8mb4_cs_0900_ai_ci", 0, "utf8mb4"), new Collation(267, "utf8mb4_da_0900_ai_ci", 0, "utf8mb4"), new Collation(268, "utf8mb4_lt_0900_ai_ci", 0, "utf8mb4"), new Collation(269, "utf8mb4_sk_0900_ai_ci", 0, "utf8mb4"), new Collation(270, "utf8mb4_es_trad_0900_ai_ci", 0, "utf8mb4"), new Collation(271, "utf8mb4_la_0900_ai_ci", 0, "utf8mb4"), null, new Collation(273, "utf8mb4_eo_0900_ai_ci", 0, "utf8mb4"), new Collation(274, "utf8mb4_hu_0900_ai_ci", 0, "utf8mb4"), new Collation(275, "utf8mb4_hr_0900_ai_ci", 0, "utf8mb4"), null, new Collation(277, "utf8mb4_vi_0900_ai_ci", 0, "utf8mb4"), new Collation(278, "utf8mb4_0900_as_cs", 0, "utf8mb4"), new Collation(279, "utf8mb4_de_pb_0900_as_cs", 0, "utf8mb4"), new Collation(280, "utf8mb4_is_0900_as_cs", 0, "utf8mb4"), new Collation(281, "utf8mb4_lv_0900_as_cs", 0, "utf8mb4"), new Collation(282, "utf8mb4_ro_0900_as_cs", 0, "utf8mb4"), new Collation(283, "utf8mb4_sl_0900_as_cs", 0, "utf8mb4"), new Collation(284, "utf8mb4_pl_0900_as_cs", 0, "utf8mb4"), new Collation(285, "utf8mb4_et_0900_as_cs", 0, "utf8mb4"), new Collation(286, "utf8mb4_es_0900_as_cs", 0, "utf8mb4"), new Collation(287, "utf8mb4_sv_0900_as_cs", 0, "utf8mb4"), new Collation(288, "utf8mb4_tr_0900_as_cs", 0, "utf8mb4"), new Collation(289, "utf8mb4_cs_0900_as_cs", 0, "utf8mb4"), new Collation(290, "utf8mb4_da_0900_as_cs", 0, "utf8mb4"), new Collation(291, "utf8mb4_lt_0900_as_cs", 0, "utf8mb4"), new Collation(292, "utf8mb4_sk_0900_as_cs", 0, "utf8mb4"), new Collation(293, "utf8mb4_es_trad_0900_as_cs", 0, "utf8mb4"), new Collation(294, "utf8mb4_la_0900_as_cs", 0, "utf8mb4"), null, new Collation(296, "utf8mb4_eo_0900_as_cs", 0, "utf8mb4"), new Collation(297, "utf8mb4_hu_0900_as_cs", 0, "utf8mb4"), new Collation(298, "utf8mb4_hr_0900_as_cs", 0, "utf8mb4"), null, new Collation(300, "utf8mb4_vi_0900_as_cs", 0, "utf8mb4"), null, null, new Collation(303, "utf8mb4_ja_0900_as_cs", 0, "utf8mb4"), new Collation(304, "utf8mb4_ja_0900_as_cs_ks", 0, "utf8mb4"), new Collation(305, "utf8mb4_0900_as_ci", 0, "utf8mb4"), new Collation(306, "utf8mb4_ru_0900_ai_ci", 0, "utf8mb4"), new Collation(307, "utf8mb4_ru_0900_as_cs", 0, "utf8mb4"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Collation(326, "utf8mb4_test_ci", 0, "utf8mb4"), new Collation(327, "utf16_test_ci", 0, "utf16"), new Collation(328, "utf8mb4_test_400_ci", 0, "utf8mb4"), null, null, null, null, null, null, null, new Collation(336, "utf8_bengali_standard_ci", 0, "utf8"), new Collation(337, "utf8_bengali_traditional_ci", 0, "utf8"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Collation(352, "utf8_phone_ci", 0, "utf8"), new Collation(353, "utf8_test_ci", 0, "utf8"), new Collation(354, "utf8_5624_1", 0, "utf8"), new Collation(355, "utf8_5624_2", 0, "utf8"), new Collation(356, "utf8_5624_3", 0, "utf8"), new Collation(357, "utf8_5624_4", 0, "utf8"), new Collation(358, "ucs2_test_ci", 0, "ucs2"), new Collation(359, "ucs2_vn_ci", 0, "ucs2"), new Collation(360, "ucs2_5624_1", 0, "ucs2"), null, null, null, null, null, null, null, new Collation(368, "utf8_5624_5", 0, "utf8"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Collation(391, "utf32_test_ci", 0, "utf32"), null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, new Collation(2047, "utf8_maxuserid_ci", 0, "utf8") };
        COLLATION_INDEX_TO_COLLATION_NAME = new String[2048];
        COLLATION_INDEX_TO_CHARSET = new MysqlCharset[2048];
        final Map<String, Integer> charsetNameToCollationIndexMap = new TreeMap<String, Integer>();
        final Map<String, Integer> charsetNameToCollationPriorityMap = new TreeMap<String, Integer>();
        final Set<Integer> tempUTF8MB4Indexes = new HashSet<Integer>();
        final Collation notUsedCollation = new Collation(0, "none", 0, "latin1");
        for (int j = 1; j < 2048; ++j) {
            final Collation coll = (collation[j] != null) ? collation[j] : notUsedCollation;
            CharsetMapping.COLLATION_INDEX_TO_COLLATION_NAME[j] = coll.collationName;
            CharsetMapping.COLLATION_INDEX_TO_CHARSET[j] = coll.mysqlCharset;
            final String charsetName2 = coll.mysqlCharset.charsetName;
            if (!charsetNameToCollationIndexMap.containsKey(charsetName2) || charsetNameToCollationPriorityMap.get(charsetName2) < coll.priority) {
                charsetNameToCollationIndexMap.put(charsetName2, j);
                charsetNameToCollationPriorityMap.put(charsetName2, coll.priority);
            }
            if (charsetName2.equals("utf8mb4")) {
                tempUTF8MB4Indexes.add(j);
            }
        }
        CHARSET_NAME_TO_COLLATION_INDEX = Collections.unmodifiableMap((Map<? extends String, ? extends Integer>)charsetNameToCollationIndexMap);
        UTF8MB4_INDEXES = Collections.unmodifiableSet((Set<? extends Integer>)tempUTF8MB4Indexes);
        collation = null;
    }
}
