
package com.mysql.cj.xdevapi;

import java.util.HashSet;
import com.mysql.cj.exceptions.ExceptionFactory;
import com.mysql.cj.Messages;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.io.IOException;
import com.mysql.cj.exceptions.AssertionFailedException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Set;

public class JsonParser
{
    static Set<Character> whitespaceChars;
    static HashMap<Character, Character> unescapeChars;
    
    private static boolean isValidEndOfValue(final char ch) {
        return StructuralToken.COMMA.CHAR == ch || StructuralToken.RCRBRACKET.CHAR == ch || StructuralToken.RSQBRACKET.CHAR == ch;
    }
    
    public static DbDoc parseDoc(final String jsonString) {
        try {
            return parseDoc(new StringReader(jsonString));
        }
        catch (IOException ex) {
            throw AssertionFailedException.shouldNotHappen(ex);
        }
    }
    
    public static DbDoc parseDoc(final StringReader reader) throws IOException {
        final DbDoc doc = new DbDocImpl();
        int leftBrackets = 0;
        int rightBrackets = 0;
        int intch;
        while ((intch = reader.read()) != -1) {
            String key = null;
            final char ch = (char)intch;
            if (ch == StructuralToken.LCRBRACKET.CHAR || ch == StructuralToken.COMMA.CHAR) {
                if (ch == StructuralToken.LCRBRACKET.CHAR) {
                    ++leftBrackets;
                }
                if ((key = nextKey(reader)) != null) {
                    try {
                        final JsonValue val;
                        if ((val = nextValue(reader)) != null) {
                            doc.put(key, val);
                        }
                        else {
                            reader.reset();
                        }
                        continue;
                    }
                    catch (WrongArgumentException ex) {
                        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.0", new String[] { key }), ex);
                    }
                }
                reader.reset();
            }
            else {
                if (ch == StructuralToken.RCRBRACKET.CHAR) {
                    ++rightBrackets;
                    break;
                }
                if (!JsonParser.whitespaceChars.contains(ch)) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.1", new Character[] { ch }));
                }
                continue;
            }
        }
        if (leftBrackets == 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.2"));
        }
        if (leftBrackets > rightBrackets) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.3", new Character[] { StructuralToken.RCRBRACKET.CHAR }));
        }
        return doc;
    }
    
    public static JsonArray parseArray(final StringReader reader) throws IOException {
        final JsonArray arr = new JsonArray();
        int openings = 0;
        int intch;
        while ((intch = reader.read()) != -1) {
            final char ch = (char)intch;
            if (ch == StructuralToken.LSQBRACKET.CHAR || ch == StructuralToken.COMMA.CHAR) {
                if (ch == StructuralToken.LSQBRACKET.CHAR) {
                    ++openings;
                }
                final JsonValue val;
                if ((val = nextValue(reader)) != null) {
                    arr.add(val);
                }
                else {
                    reader.reset();
                }
            }
            else {
                if (ch == StructuralToken.RSQBRACKET.CHAR) {
                    --openings;
                    break;
                }
                if (!JsonParser.whitespaceChars.contains(ch)) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.1", new Character[] { ch }));
                }
                continue;
            }
        }
        if (openings > 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.3", new Character[] { StructuralToken.RSQBRACKET.CHAR }));
        }
        return arr;
    }
    
    private static String nextKey(final StringReader reader) throws IOException {
        reader.mark(1);
        final JsonString val = parseString(reader);
        if (val == null) {
            reader.reset();
        }
        char ch = ' ';
        int intch;
        while ((intch = reader.read()) != -1) {
            ch = (char)intch;
            if (ch == StructuralToken.COLON.CHAR) {
                break;
            }
            if (ch == StructuralToken.RCRBRACKET.CHAR) {
                break;
            }
            if (!JsonParser.whitespaceChars.contains(ch)) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.1", new Character[] { ch }));
            }
        }
        if (ch != StructuralToken.COLON.CHAR && val != null && val.getString().length() > 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.4", new String[] { val.getString() }));
        }
        return (val != null) ? val.getString() : null;
    }
    
    private static JsonValue nextValue(final StringReader reader) throws IOException {
        reader.mark(1);
        int intch;
        while ((intch = reader.read()) != -1) {
            final char ch = (char)intch;
            if (ch == EscapeChar.QUOTE.CHAR) {
                reader.reset();
                return parseString(reader);
            }
            if (ch == StructuralToken.LSQBRACKET.CHAR) {
                reader.reset();
                return parseArray(reader);
            }
            if (ch == StructuralToken.LCRBRACKET.CHAR) {
                reader.reset();
                return parseDoc(reader);
            }
            if (ch == '-' || (ch >= '0' && ch <= '9')) {
                reader.reset();
                return parseNumber(reader);
            }
            if (ch == JsonLiteral.TRUE.value.charAt(0)) {
                reader.reset();
                return parseLiteral(reader);
            }
            if (ch == JsonLiteral.FALSE.value.charAt(0)) {
                reader.reset();
                return parseLiteral(reader);
            }
            if (ch == JsonLiteral.NULL.value.charAt(0)) {
                reader.reset();
                return parseLiteral(reader);
            }
            if (ch == StructuralToken.RSQBRACKET.CHAR) {
                return null;
            }
            if (!JsonParser.whitespaceChars.contains(ch)) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.1", new Character[] { ch }));
            }
            reader.mark(1);
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.5"));
    }
    
    private static void appendChar(final StringBuilder sb, final char ch) {
        if (sb == null) {
            if (!JsonParser.whitespaceChars.contains(ch)) {
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.6", new Character[] { ch }));
            }
        }
        else {
            sb.append(ch);
        }
    }
    
    static JsonString parseString(final StringReader reader) throws IOException {
        int quotes = 0;
        boolean escapeNextChar = false;
        StringBuilder sb = null;
        int intch;
        while ((intch = reader.read()) != -1) {
            final char ch = (char)intch;
            if (escapeNextChar) {
                if (!JsonParser.unescapeChars.containsKey(ch)) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.7", new Character[] { ch }));
                }
                appendChar(sb, JsonParser.unescapeChars.get(ch));
                escapeNextChar = false;
            }
            else if (ch == EscapeChar.QUOTE.CHAR) {
                if (sb != null) {
                    --quotes;
                    break;
                }
                sb = new StringBuilder();
                ++quotes;
            }
            else {
                if (quotes == 0 && ch == StructuralToken.RCRBRACKET.CHAR) {
                    break;
                }
                if (ch == EscapeChar.RSOLIDUS.CHAR) {
                    escapeNextChar = true;
                }
                else {
                    appendChar(sb, ch);
                }
            }
        }
        if (quotes > 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.3", new Character[] { EscapeChar.QUOTE.CHAR }));
        }
        return (sb == null) ? null : new JsonString().setValue(sb.toString());
    }
    
    static JsonNumber parseNumber(final StringReader reader) throws IOException {
        StringBuilder sb = null;
        char lastChar = ' ';
        boolean hasFractionalPart = false;
        boolean hasExponent = false;
        int intch;
        while ((intch = reader.read()) != -1) {
            final char ch = (char)intch;
            if (sb == null) {
                if (ch == '-') {
                    sb = new StringBuilder();
                    sb.append(ch);
                }
                else if (ch >= '0' && ch <= '9') {
                    sb = new StringBuilder();
                    sb.append(ch);
                }
                else if (!JsonParser.whitespaceChars.contains(ch)) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.1", new Character[] { ch }));
                }
            }
            else if (ch == '-') {
                if (lastChar != 'E' && lastChar != 'e') {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.8", new Object[] { ch, sb.toString() }));
                }
                sb.append(ch);
            }
            else if (ch >= '0' && ch <= '9') {
                sb.append(ch);
            }
            else if (ch == 'E' || ch == 'e') {
                if (lastChar < '0' || lastChar > '9') {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.8", new Object[] { ch, sb.toString() }));
                }
                hasExponent = true;
                sb.append(ch);
            }
            else if (ch == '.') {
                if (hasFractionalPart) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.10", new Object[] { ch, sb.toString() }));
                }
                if (hasExponent) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.11"));
                }
                if (lastChar < '0' || lastChar > '9') {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.8", new Object[] { ch, sb.toString() }));
                }
                hasFractionalPart = true;
                sb.append(ch);
            }
            else if (ch == '+') {
                if (lastChar != 'E' && lastChar != 'e') {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.8", new Object[] { ch, sb.toString() }));
                }
                sb.append(ch);
            }
            else {
                if (JsonParser.whitespaceChars.contains(ch) || isValidEndOfValue(ch)) {
                    reader.reset();
                    break;
                }
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.1", new Character[] { ch }));
            }
            lastChar = ch;
            reader.mark(1);
        }
        if (sb == null || sb.length() == 0) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.5"));
        }
        return new JsonNumber().setValue(sb.toString());
    }
    
    static JsonLiteral parseLiteral(final StringReader reader) throws IOException {
        StringBuilder sb = null;
        JsonLiteral res = null;
        int literalIndex = 0;
        int intch;
        while ((intch = reader.read()) != -1) {
            final char ch = (char)intch;
            if (sb == null) {
                if (ch == JsonLiteral.TRUE.value.charAt(0)) {
                    res = JsonLiteral.TRUE;
                    sb = new StringBuilder();
                    sb.append(ch);
                    ++literalIndex;
                }
                else if (ch == JsonLiteral.FALSE.value.charAt(0)) {
                    res = JsonLiteral.FALSE;
                    sb = new StringBuilder();
                    sb.append(ch);
                    ++literalIndex;
                }
                else if (ch == JsonLiteral.NULL.value.charAt(0)) {
                    res = JsonLiteral.NULL;
                    sb = new StringBuilder();
                    sb.append(ch);
                    ++literalIndex;
                }
                else if (!JsonParser.whitespaceChars.contains(ch)) {
                    throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.1", new Character[] { ch }));
                }
            }
            else if (literalIndex < res.value.length() && ch == res.value.charAt(literalIndex)) {
                sb.append(ch);
                ++literalIndex;
            }
            else {
                if (JsonParser.whitespaceChars.contains(ch) || isValidEndOfValue(ch)) {
                    reader.reset();
                    break;
                }
                throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.1", new Character[] { ch }));
            }
            reader.mark(1);
        }
        if (sb == null) {
            throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.5"));
        }
        if (literalIndex == res.value.length()) {
            return res;
        }
        throw ExceptionFactory.createException(WrongArgumentException.class, Messages.getString("JsonParser.12", new String[] { sb.toString() }));
    }
    
    static {
        JsonParser.whitespaceChars = new HashSet<Character>();
        JsonParser.unescapeChars = new HashMap<Character, Character>();
        for (final EscapeChar ec : EscapeChar.values()) {
            JsonParser.unescapeChars.put(ec.ESCAPED.charAt(1), ec.CHAR);
        }
        for (final Whitespace ws : Whitespace.values()) {
            JsonParser.whitespaceChars.add(ws.CHAR);
        }
    }
    
    enum Whitespace
    {
        TAB('\t'), 
        LF('\n'), 
        CR('\r'), 
        SPACE(' ');
        
        public final char CHAR;
        
        private Whitespace(final char character) {
            this.CHAR = character;
        }
    }
    
    enum StructuralToken
    {
        LSQBRACKET('['), 
        RSQBRACKET(']'), 
        LCRBRACKET('{'), 
        RCRBRACKET('}'), 
        COLON(':'), 
        COMMA(',');
        
        public final char CHAR;
        
        private StructuralToken(final char character) {
            this.CHAR = character;
        }
    }
    
    enum EscapeChar
    {
        QUOTE('\"', "\\\""), 
        RSOLIDUS('\\', "\\\\"), 
        SOLIDUS('/', "\\/"), 
        BACKSPACE('\b', "\\b"), 
        FF('\f', "\\f"), 
        LF('\n', "\\n"), 
        CR('\r', "\\r"), 
        TAB('\t', "\\t");
        
        public final char CHAR;
        public final String ESCAPED;
        
        private EscapeChar(final char character, final String escaped) {
            this.CHAR = character;
            this.ESCAPED = escaped;
        }
    }
}
