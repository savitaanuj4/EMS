
package com.mysql.cj.xdevapi;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import java.util.function.Supplier;
import java.util.Collection;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.Collections;
import java.util.LinkedList;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import com.mysql.cj.exceptions.WrongArgumentException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

public class ExprParser
{
    String string;
    List<Token> tokens;
    int tokenPos;
    Map<String, Integer> placeholderNameToPosition;
    int positionalPlaceholderCount;
    private boolean allowRelationalColumns;
    static Map<String, TokenType> reservedWords;
    
    public ExprParser(final String s) {
        this(s, true);
    }
    
    public ExprParser(final String s, final boolean allowRelationalColumns) {
        this.tokens = new ArrayList<Token>();
        this.tokenPos = 0;
        this.placeholderNameToPosition = new HashMap<String, Integer>();
        this.positionalPlaceholderCount = 0;
        this.string = s;
        this.lex();
        this.allowRelationalColumns = allowRelationalColumns;
    }
    
    boolean nextCharEquals(final int i, final char c) {
        return i + 1 < this.string.length() && this.string.charAt(i + 1) == c;
    }
    
    private int lexNumber(int i) {
        boolean isInt = true;
        final int start = i;
        while (i < this.string.length()) {
            final char c = this.string.charAt(i);
            if (c == '.') {
                isInt = false;
            }
            else if (c == 'e' || c == 'E') {
                isInt = false;
                if (this.nextCharEquals(i, '-') || this.nextCharEquals(i, '+')) {
                    ++i;
                }
            }
            else if (!Character.isDigit(c)) {
                break;
            }
            ++i;
        }
        if (isInt) {
            this.tokens.add(new Token(TokenType.LNUM_INT, this.string.substring(start, i)));
        }
        else {
            this.tokens.add(new Token(TokenType.LNUM_DOUBLE, this.string.substring(start, i)));
        }
        return --i;
    }
    
    void lex() {
        for (int i = 0; i < this.string.length(); ++i) {
            final int start = i;
            char c = this.string.charAt(i);
            if (!Character.isWhitespace(c)) {
                if (Character.isDigit(c)) {
                    i = this.lexNumber(i);
                }
                else if (c != '_' && !Character.isUnicodeIdentifierStart(c)) {
                    switch (c) {
                        case ':': {
                            this.tokens.add(new Token(TokenType.COLON, c));
                            break;
                        }
                        case '+': {
                            this.tokens.add(new Token(TokenType.PLUS, c));
                            break;
                        }
                        case '-': {
                            if (this.nextCharEquals(i, '>')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.COLDOCPATH, "->"));
                                break;
                            }
                            this.tokens.add(new Token(TokenType.MINUS, c));
                            break;
                        }
                        case '*': {
                            if (this.nextCharEquals(i, '*')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.DOUBLESTAR, "**"));
                                break;
                            }
                            this.tokens.add(new Token(TokenType.STAR, c));
                            break;
                        }
                        case '/': {
                            this.tokens.add(new Token(TokenType.SLASH, c));
                            break;
                        }
                        case '$': {
                            this.tokens.add(new Token(TokenType.DOLLAR, c));
                            break;
                        }
                        case '%': {
                            this.tokens.add(new Token(TokenType.MOD, c));
                            break;
                        }
                        case '=': {
                            if (this.nextCharEquals(i, '=')) {
                                ++i;
                            }
                            this.tokens.add(new Token(TokenType.EQ, "=="));
                            break;
                        }
                        case '&': {
                            if (this.nextCharEquals(i, '&')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.ANDAND, "&&"));
                                break;
                            }
                            this.tokens.add(new Token(TokenType.BITAND, c));
                            break;
                        }
                        case '|': {
                            if (this.nextCharEquals(i, '|')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.OROR, "||"));
                                break;
                            }
                            this.tokens.add(new Token(TokenType.BITOR, c));
                            break;
                        }
                        case '^': {
                            this.tokens.add(new Token(TokenType.BITXOR, c));
                            break;
                        }
                        case '(': {
                            this.tokens.add(new Token(TokenType.LPAREN, c));
                            break;
                        }
                        case ')': {
                            this.tokens.add(new Token(TokenType.RPAREN, c));
                            break;
                        }
                        case '[': {
                            this.tokens.add(new Token(TokenType.LSQBRACKET, c));
                            break;
                        }
                        case ']': {
                            this.tokens.add(new Token(TokenType.RSQBRACKET, c));
                            break;
                        }
                        case '{': {
                            this.tokens.add(new Token(TokenType.LCURLY, c));
                            break;
                        }
                        case '}': {
                            this.tokens.add(new Token(TokenType.RCURLY, c));
                            break;
                        }
                        case '~': {
                            this.tokens.add(new Token(TokenType.NEG, c));
                            break;
                        }
                        case ',': {
                            this.tokens.add(new Token(TokenType.COMMA, c));
                            break;
                        }
                        case '!': {
                            if (this.nextCharEquals(i, '=')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.NE, "!="));
                                break;
                            }
                            this.tokens.add(new Token(TokenType.BANG, c));
                            break;
                        }
                        case '?': {
                            this.tokens.add(new Token(TokenType.EROTEME, c));
                            break;
                        }
                        case '<': {
                            if (this.nextCharEquals(i, '<')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.LSHIFT, "<<"));
                                break;
                            }
                            if (this.nextCharEquals(i, '=')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.LE, "<="));
                                break;
                            }
                            this.tokens.add(new Token(TokenType.LT, c));
                            break;
                        }
                        case '>': {
                            if (this.nextCharEquals(i, '>')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.RSHIFT, ">>"));
                                break;
                            }
                            if (this.nextCharEquals(i, '=')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.GE, ">="));
                                break;
                            }
                            this.tokens.add(new Token(TokenType.GT, c));
                            break;
                        }
                        case '.': {
                            if (this.nextCharEquals(i, '*')) {
                                ++i;
                                this.tokens.add(new Token(TokenType.DOTSTAR, ".*"));
                                break;
                            }
                            if (i + 1 < this.string.length() && Character.isDigit(this.string.charAt(i + 1))) {
                                i = this.lexNumber(i);
                                break;
                            }
                            this.tokens.add(new Token(TokenType.DOT, c));
                            break;
                        }
                        case '\"':
                        case '\'':
                        case '`': {
                            final char quoteChar = c;
                            final StringBuilder val = new StringBuilder();
                            try {
                                for (c = this.string.charAt(++i); c != quoteChar || (i + 1 < this.string.length() && this.string.charAt(i + 1) == quoteChar); c = this.string.charAt(++i)) {
                                    if (c == '\\' || c == quoteChar) {
                                        ++i;
                                    }
                                    val.append(this.string.charAt(i));
                                }
                            }
                            catch (StringIndexOutOfBoundsException ex) {
                                throw new WrongArgumentException("Unterminated string starting at " + start);
                            }
                            this.tokens.add(new Token((quoteChar == '`') ? TokenType.IDENT : TokenType.LSTRING, val.toString()));
                            break;
                        }
                        default: {
                            throw new WrongArgumentException("Can't parse at pos: " + i);
                        }
                    }
                }
                else {
                    while (i < this.string.length() && Character.isUnicodeIdentifierPart(this.string.charAt(i))) {
                        ++i;
                    }
                    final String val2 = this.string.substring(start, i);
                    final String valLower = val2.toLowerCase();
                    if (i < this.string.length()) {
                        --i;
                    }
                    if (ExprParser.reservedWords.containsKey(valLower)) {
                        if ("and".equals(valLower)) {
                            this.tokens.add(new Token(ExprParser.reservedWords.get(valLower), "&&"));
                        }
                        else if ("or".equals(valLower)) {
                            this.tokens.add(new Token(ExprParser.reservedWords.get(valLower), "||"));
                        }
                        else {
                            this.tokens.add(new Token(ExprParser.reservedWords.get(valLower), valLower));
                        }
                    }
                    else {
                        this.tokens.add(new Token(TokenType.IDENT, val2));
                    }
                }
            }
        }
    }
    
    void assertTokenAt(final int pos, final TokenType type) {
        if (this.tokens.size() <= pos) {
            throw new WrongArgumentException("No more tokens when expecting " + type + " at token pos " + pos);
        }
        if (this.tokens.get(pos).type != type) {
            throw new WrongArgumentException("Expected token type " + type + " at token pos " + pos);
        }
    }
    
    boolean currentTokenTypeEquals(final TokenType t) {
        return this.posTokenTypeEquals(this.tokenPos, t);
    }
    
    boolean nextTokenTypeEquals(final TokenType t) {
        return this.posTokenTypeEquals(this.tokenPos + 1, t);
    }
    
    boolean posTokenTypeEquals(final int pos, final TokenType t) {
        return this.tokens.size() > pos && this.tokens.get(pos).type == t;
    }
    
    String consumeToken(final TokenType t) {
        this.assertTokenAt(this.tokenPos, t);
        final String value = this.tokens.get(this.tokenPos).value;
        ++this.tokenPos;
        return value;
    }
    
    List<MysqlxExpr.Expr> parenExprList() {
        final List<MysqlxExpr.Expr> exprs = new ArrayList<MysqlxExpr.Expr>();
        this.consumeToken(TokenType.LPAREN);
        if (!this.currentTokenTypeEquals(TokenType.RPAREN)) {
            exprs.add(this.expr());
            while (this.currentTokenTypeEquals(TokenType.COMMA)) {
                this.consumeToken(TokenType.COMMA);
                exprs.add(this.expr());
            }
        }
        this.consumeToken(TokenType.RPAREN);
        return exprs;
    }
    
    MysqlxExpr.Expr functionCall() {
        final MysqlxExpr.Identifier id = this.identifier();
        final MysqlxExpr.FunctionCall.Builder b = MysqlxExpr.FunctionCall.newBuilder();
        b.setName(id);
        b.addAllParam(this.parenExprList());
        return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.FUNC_CALL).setFunctionCall(b.build()).build();
    }
    
    MysqlxExpr.Expr starOperator() {
        final MysqlxExpr.Operator op = MysqlxExpr.Operator.newBuilder().setName("*").build();
        return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.OPERATOR).setOperator(op).build();
    }
    
    MysqlxExpr.Identifier identifier() {
        final MysqlxExpr.Identifier.Builder builder = MysqlxExpr.Identifier.newBuilder();
        this.assertTokenAt(this.tokenPos, TokenType.IDENT);
        if (this.nextTokenTypeEquals(TokenType.DOT)) {
            builder.setSchemaName(this.tokens.get(this.tokenPos).value);
            this.consumeToken(TokenType.IDENT);
            this.consumeToken(TokenType.DOT);
            this.assertTokenAt(this.tokenPos, TokenType.IDENT);
        }
        builder.setName(this.tokens.get(this.tokenPos).value);
        this.consumeToken(TokenType.IDENT);
        return builder.build();
    }
    
    MysqlxExpr.DocumentPathItem docPathMember() {
        this.consumeToken(TokenType.DOT);
        final Token t = this.tokens.get(this.tokenPos);
        String memberName;
        if (this.currentTokenTypeEquals(TokenType.IDENT)) {
            if (!t.value.equals(ExprUnparser.quoteIdentifier(t.value))) {
                throw new WrongArgumentException("'" + t.value + "' is not a valid JSON/ECMAScript identifier");
            }
            this.consumeToken(TokenType.IDENT);
            memberName = t.value;
        }
        else {
            if (!this.currentTokenTypeEquals(TokenType.LSTRING)) {
                throw new WrongArgumentException("Expected token type IDENT or LSTRING in JSON path at token pos " + this.tokenPos);
            }
            this.consumeToken(TokenType.LSTRING);
            memberName = t.value;
        }
        final MysqlxExpr.DocumentPathItem.Builder item = MysqlxExpr.DocumentPathItem.newBuilder();
        item.setType(MysqlxExpr.DocumentPathItem.Type.MEMBER);
        item.setValue(memberName);
        return item.build();
    }
    
    MysqlxExpr.DocumentPathItem docPathArrayLoc() {
        final MysqlxExpr.DocumentPathItem.Builder builder = MysqlxExpr.DocumentPathItem.newBuilder();
        this.consumeToken(TokenType.LSQBRACKET);
        if (this.currentTokenTypeEquals(TokenType.STAR)) {
            this.consumeToken(TokenType.STAR);
            this.consumeToken(TokenType.RSQBRACKET);
            return builder.setType(MysqlxExpr.DocumentPathItem.Type.ARRAY_INDEX_ASTERISK).build();
        }
        if (!this.currentTokenTypeEquals(TokenType.LNUM_INT)) {
            throw new WrongArgumentException("Expected token type STAR or LNUM_INT in JSON path array index at token pos " + this.tokenPos);
        }
        final Integer v = Integer.valueOf(this.tokens.get(this.tokenPos).value);
        if (v < 0) {
            throw new WrongArgumentException("Array index cannot be negative at " + this.tokenPos);
        }
        this.consumeToken(TokenType.LNUM_INT);
        this.consumeToken(TokenType.RSQBRACKET);
        return builder.setType(MysqlxExpr.DocumentPathItem.Type.ARRAY_INDEX).setIndex(v).build();
    }
    
    public List<MysqlxExpr.DocumentPathItem> documentPath() {
        final List<MysqlxExpr.DocumentPathItem> items = new ArrayList<MysqlxExpr.DocumentPathItem>();
        while (true) {
            if (this.currentTokenTypeEquals(TokenType.DOT)) {
                items.add(this.docPathMember());
            }
            else if (this.currentTokenTypeEquals(TokenType.DOTSTAR)) {
                this.consumeToken(TokenType.DOTSTAR);
                items.add(MysqlxExpr.DocumentPathItem.newBuilder().setType(MysqlxExpr.DocumentPathItem.Type.MEMBER_ASTERISK).build());
            }
            else if (this.currentTokenTypeEquals(TokenType.LSQBRACKET)) {
                items.add(this.docPathArrayLoc());
            }
            else {
                if (!this.currentTokenTypeEquals(TokenType.DOUBLESTAR)) {
                    break;
                }
                this.consumeToken(TokenType.DOUBLESTAR);
                items.add(MysqlxExpr.DocumentPathItem.newBuilder().setType(MysqlxExpr.DocumentPathItem.Type.DOUBLE_ASTERISK).build());
            }
        }
        if (items.size() > 0 && items.get(items.size() - 1).getType() == MysqlxExpr.DocumentPathItem.Type.DOUBLE_ASTERISK) {
            throw new WrongArgumentException("JSON path may not end in '**' at " + this.tokenPos);
        }
        return items;
    }
    
    public MysqlxExpr.Expr documentField() {
        final MysqlxExpr.ColumnIdentifier.Builder builder = MysqlxExpr.ColumnIdentifier.newBuilder();
        if (this.currentTokenTypeEquals(TokenType.IDENT)) {
            builder.addDocumentPath(MysqlxExpr.DocumentPathItem.newBuilder().setType(MysqlxExpr.DocumentPathItem.Type.MEMBER).setValue(this.consumeToken(TokenType.IDENT)).build());
        }
        builder.addAllDocumentPath(this.documentPath());
        return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.IDENT).setIdentifier(builder.build()).build();
    }
    
    MysqlxExpr.Expr columnIdentifier() {
        final List<String> parts = new LinkedList<String>();
        parts.add(this.consumeToken(TokenType.IDENT));
        while (this.currentTokenTypeEquals(TokenType.DOT)) {
            this.consumeToken(TokenType.DOT);
            parts.add(this.consumeToken(TokenType.IDENT));
            if (parts.size() == 3) {
                break;
            }
        }
        Collections.reverse(parts);
        final MysqlxExpr.ColumnIdentifier.Builder id = MysqlxExpr.ColumnIdentifier.newBuilder();
        for (int i = 0; i < parts.size(); ++i) {
            switch (i) {
                case 0: {
                    id.setName(parts.get(0));
                    break;
                }
                case 1: {
                    id.setTableName(parts.get(1));
                    break;
                }
                case 2: {
                    id.setSchemaName(parts.get(2));
                    break;
                }
            }
        }
        if (this.currentTokenTypeEquals(TokenType.COLDOCPATH)) {
            this.consumeToken(TokenType.COLDOCPATH);
            if (this.currentTokenTypeEquals(TokenType.DOLLAR)) {
                this.consumeToken(TokenType.DOLLAR);
                id.addAllDocumentPath(this.documentPath());
            }
            else if (this.currentTokenTypeEquals(TokenType.LSTRING)) {
                final String path = this.consumeToken(TokenType.LSTRING);
                if (path.charAt(0) != '$') {
                    throw new WrongArgumentException("Invalid document path at " + this.tokenPos);
                }
                id.addAllDocumentPath(new ExprParser(path.substring(1, path.length())).documentPath());
            }
            if (id.getDocumentPathCount() == 0) {
                throw new WrongArgumentException("Invalid document path at " + this.tokenPos);
            }
        }
        return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.IDENT).setIdentifier(id.build()).build();
    }
    
    MysqlxExpr.Expr buildUnaryOp(final String name, final MysqlxExpr.Expr param) {
        final MysqlxExpr.Operator op = MysqlxExpr.Operator.newBuilder().setName(name).addParam(param).build();
        return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.OPERATOR).setOperator(op).build();
    }
    
    MysqlxExpr.Expr atomicExpr() {
        if (this.tokenPos >= this.tokens.size()) {
            throw new WrongArgumentException("No more tokens when expecting one at token pos " + this.tokenPos);
        }
        final Token t = this.tokens.get(this.tokenPos);
        ++this.tokenPos;
        switch (t.type) {
            case EROTEME:
            case COLON: {
                String placeholderName;
                if (this.currentTokenTypeEquals(TokenType.LNUM_INT)) {
                    placeholderName = this.consumeToken(TokenType.LNUM_INT);
                }
                else if (this.currentTokenTypeEquals(TokenType.IDENT)) {
                    placeholderName = this.consumeToken(TokenType.IDENT);
                }
                else {
                    if (t.type != TokenType.EROTEME) {
                        throw new WrongArgumentException("Invalid placeholder name at token pos " + this.tokenPos);
                    }
                    placeholderName = String.valueOf(this.positionalPlaceholderCount);
                }
                final MysqlxExpr.Expr.Builder placeholder = MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.PLACEHOLDER);
                if (this.placeholderNameToPosition.containsKey(placeholderName)) {
                    placeholder.setPosition(this.placeholderNameToPosition.get(placeholderName));
                }
                else {
                    placeholder.setPosition(this.positionalPlaceholderCount);
                    this.placeholderNameToPosition.put(placeholderName, this.positionalPlaceholderCount);
                    ++this.positionalPlaceholderCount;
                }
                return placeholder.build();
            }
            case LPAREN: {
                final MysqlxExpr.Expr e2 = this.expr();
                this.consumeToken(TokenType.RPAREN);
                return e2;
            }
            case LCURLY: {
                final MysqlxExpr.Object.Builder builder = MysqlxExpr.Object.newBuilder();
                if (this.currentTokenTypeEquals(TokenType.LSTRING)) {
                    final String key;
                    final MysqlxExpr.Expr value;
                    this.parseCommaSeparatedList(() -> {
                        key = this.consumeToken(TokenType.LSTRING);
                        this.consumeToken(TokenType.COLON);
                        value = this.expr();
                        return Collections.singletonMap(key, value);
                    }).stream().map(pair -> pair.entrySet().iterator().next()).map(e -> MysqlxExpr.Object.ObjectField.newBuilder().setKey(e.getKey()).setValue((MysqlxExpr.Expr)e.getValue())).forEach((Consumer<? super Object>)builder::addFld);
                }
                this.consumeToken(TokenType.RCURLY);
                return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.OBJECT).setObject(builder.build()).build();
            }
            case LSQBRACKET: {
                final MysqlxExpr.Array.Builder builder2 = MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.ARRAY).getArrayBuilder();
                this.parseCommaSeparatedList(() -> this.expr()).stream().forEach((Consumer<? super Object>)builder2::addValue);
                this.consumeToken(TokenType.RSQBRACKET);
                return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.ARRAY).setArray(builder2).build();
            }
            case CAST: {
                this.consumeToken(TokenType.LPAREN);
                final MysqlxExpr.Operator.Builder builder3 = MysqlxExpr.Operator.newBuilder().setName(TokenType.CAST.toString().toLowerCase());
                builder3.addParam(this.expr());
                this.consumeToken(TokenType.AS);
                final StringBuilder typeStr = new StringBuilder(this.tokens.get(this.tokenPos).value.toUpperCase());
                if (this.currentTokenTypeEquals(TokenType.DECIMAL)) {
                    ++this.tokenPos;
                    if (this.currentTokenTypeEquals(TokenType.LPAREN)) {
                        typeStr.append(this.consumeToken(TokenType.LPAREN));
                        typeStr.append(this.consumeToken(TokenType.LNUM_INT));
                        if (this.currentTokenTypeEquals(TokenType.COMMA)) {
                            typeStr.append(this.consumeToken(TokenType.COMMA));
                            typeStr.append(this.consumeToken(TokenType.LNUM_INT));
                        }
                        typeStr.append(this.consumeToken(TokenType.RPAREN));
                    }
                }
                else if (this.currentTokenTypeEquals(TokenType.CHAR) || this.currentTokenTypeEquals(TokenType.BINARY)) {
                    ++this.tokenPos;
                    if (this.currentTokenTypeEquals(TokenType.LPAREN)) {
                        typeStr.append(this.consumeToken(TokenType.LPAREN));
                        typeStr.append(this.consumeToken(TokenType.LNUM_INT));
                        typeStr.append(this.consumeToken(TokenType.RPAREN));
                    }
                }
                else if (this.currentTokenTypeEquals(TokenType.UNSIGNED) || this.currentTokenTypeEquals(TokenType.SIGNED)) {
                    ++this.tokenPos;
                    if (this.currentTokenTypeEquals(TokenType.INTEGER)) {
                        this.consumeToken(TokenType.INTEGER);
                    }
                }
                else {
                    if (!this.currentTokenTypeEquals(TokenType.JSON) && !this.currentTokenTypeEquals(TokenType.DATE) && !this.currentTokenTypeEquals(TokenType.DATETIME) && !this.currentTokenTypeEquals(TokenType.TIME)) {
                        throw new WrongArgumentException("Expected valid CAST type argument at " + this.tokenPos);
                    }
                    ++this.tokenPos;
                }
                this.consumeToken(TokenType.RPAREN);
                builder3.addParam(ExprUtil.buildLiteralScalar(typeStr.toString().getBytes()));
                return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.OPERATOR).setOperator(builder3.build()).build();
            }
            case PLUS:
            case MINUS: {
                if (this.currentTokenTypeEquals(TokenType.LNUM_INT) || this.currentTokenTypeEquals(TokenType.LNUM_DOUBLE)) {
                    this.tokens.get(this.tokenPos).value = t.value + this.tokens.get(this.tokenPos).value;
                    return this.atomicExpr();
                }
                return this.buildUnaryOp(t.value, this.atomicExpr());
            }
            case NOT:
            case NEG:
            case BANG: {
                return this.buildUnaryOp(t.value, this.atomicExpr());
            }
            case LSTRING: {
                return ExprUtil.buildLiteralScalar(t.value);
            }
            case NULL: {
                return ExprUtil.buildLiteralNullScalar();
            }
            case LNUM_INT: {
                return ExprUtil.buildLiteralScalar(Long.valueOf(t.value));
            }
            case LNUM_DOUBLE: {
                return ExprUtil.buildLiteralScalar(Double.valueOf(t.value));
            }
            case TRUE:
            case FALSE: {
                return ExprUtil.buildLiteralScalar(t.type == TokenType.TRUE);
            }
            case DOLLAR: {
                return this.documentField();
            }
            case STAR: {
                return this.starOperator();
            }
            case IDENT: {
                --this.tokenPos;
                if (this.nextTokenTypeEquals(TokenType.LPAREN) || (this.posTokenTypeEquals(this.tokenPos + 1, TokenType.DOT) && this.posTokenTypeEquals(this.tokenPos + 2, TokenType.IDENT) && this.posTokenTypeEquals(this.tokenPos + 3, TokenType.LPAREN))) {
                    return this.functionCall();
                }
                if (this.allowRelationalColumns) {
                    return this.columnIdentifier();
                }
                return this.documentField();
            }
            default: {
                throw new WrongArgumentException("Cannot find atomic expression at token pos: " + (this.tokenPos - 1));
            }
        }
    }
    
    MysqlxExpr.Expr parseLeftAssocBinaryOpExpr(final TokenType[] types, final ParseExpr innerParser) {
        MysqlxExpr.Expr lhs = innerParser.parseExpr();
        while (this.tokenPos < this.tokens.size() && Arrays.asList(types).contains(this.tokens.get(this.tokenPos).type)) {
            final MysqlxExpr.Operator.Builder builder = MysqlxExpr.Operator.newBuilder().setName(this.tokens.get(this.tokenPos).value).addParam(lhs);
            ++this.tokenPos;
            builder.addParam(innerParser.parseExpr());
            lhs = MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.OPERATOR).setOperator(builder.build()).build();
        }
        return lhs;
    }
    
    MysqlxExpr.Expr addSubIntervalExpr() {
        MysqlxExpr.Expr lhs = this.atomicExpr();
        while ((this.currentTokenTypeEquals(TokenType.PLUS) || this.currentTokenTypeEquals(TokenType.MINUS)) && this.nextTokenTypeEquals(TokenType.INTERVAL)) {
            final Token op = this.tokens.get(this.tokenPos);
            ++this.tokenPos;
            final MysqlxExpr.Operator.Builder builder = MysqlxExpr.Operator.newBuilder().addParam(this.unquoteWorkaround(lhs));
            this.consumeToken(TokenType.INTERVAL);
            if (op.type == TokenType.PLUS) {
                builder.setName("date_add");
            }
            else {
                builder.setName("date_sub");
            }
            builder.addParam(this.unquoteWorkaround(this.bitExpr()));
            if (!this.currentTokenTypeEquals(TokenType.MICROSECOND) && !this.currentTokenTypeEquals(TokenType.SECOND) && !this.currentTokenTypeEquals(TokenType.MINUTE) && !this.currentTokenTypeEquals(TokenType.HOUR) && !this.currentTokenTypeEquals(TokenType.DAY) && !this.currentTokenTypeEquals(TokenType.WEEK) && !this.currentTokenTypeEquals(TokenType.MONTH) && !this.currentTokenTypeEquals(TokenType.QUARTER) && !this.currentTokenTypeEquals(TokenType.YEAR) && !this.currentTokenTypeEquals(TokenType.SECOND_MICROSECOND) && !this.currentTokenTypeEquals(TokenType.MINUTE_MICROSECOND) && !this.currentTokenTypeEquals(TokenType.MINUTE_SECOND) && !this.currentTokenTypeEquals(TokenType.HOUR_MICROSECOND) && !this.currentTokenTypeEquals(TokenType.HOUR_SECOND) && !this.currentTokenTypeEquals(TokenType.HOUR_MINUTE) && !this.currentTokenTypeEquals(TokenType.DAY_MICROSECOND) && !this.currentTokenTypeEquals(TokenType.DAY_SECOND) && !this.currentTokenTypeEquals(TokenType.DAY_MINUTE) && !this.currentTokenTypeEquals(TokenType.DAY_HOUR) && !this.currentTokenTypeEquals(TokenType.YEAR_MONTH)) {
                throw new WrongArgumentException("Expected interval units at " + this.tokenPos);
            }
            builder.addParam(ExprUtil.buildLiteralScalar(this.tokens.get(this.tokenPos).value.toUpperCase().getBytes()));
            ++this.tokenPos;
            lhs = MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.OPERATOR).setOperator(builder.build()).build();
        }
        return lhs;
    }
    
    MysqlxExpr.Expr mulDivExpr() {
        return this.parseLeftAssocBinaryOpExpr(new TokenType[] { TokenType.STAR, TokenType.SLASH, TokenType.MOD }, this::addSubIntervalExpr);
    }
    
    MysqlxExpr.Expr addSubExpr() {
        return this.parseLeftAssocBinaryOpExpr(new TokenType[] { TokenType.PLUS, TokenType.MINUS }, this::mulDivExpr);
    }
    
    MysqlxExpr.Expr shiftExpr() {
        return this.parseLeftAssocBinaryOpExpr(new TokenType[] { TokenType.LSHIFT, TokenType.RSHIFT }, this::addSubExpr);
    }
    
    MysqlxExpr.Expr bitExpr() {
        return this.parseLeftAssocBinaryOpExpr(new TokenType[] { TokenType.BITAND, TokenType.BITOR, TokenType.BITXOR }, this::shiftExpr);
    }
    
    MysqlxExpr.Expr compExpr() {
        return this.parseLeftAssocBinaryOpExpr(new TokenType[] { TokenType.GE, TokenType.GT, TokenType.LE, TokenType.LT, TokenType.EQ, TokenType.NE }, this::bitExpr);
    }
    
    private MysqlxExpr.Expr unquoteWorkaround(final MysqlxExpr.Expr e) {
        if (e.getType() == MysqlxExpr.Expr.Type.IDENT && e.getIdentifier().getDocumentPathList().size() > 0) {
            final MysqlxExpr.Identifier id = MysqlxExpr.Identifier.newBuilder().setName("JSON_UNQUOTE").build();
            final MysqlxExpr.FunctionCall.Builder b = MysqlxExpr.FunctionCall.newBuilder().setName(id).addParam(e);
            return MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.FUNC_CALL).setFunctionCall(b.build()).build();
        }
        return e;
    }
    
    MysqlxExpr.Expr ilriExpr() {
        MysqlxExpr.Expr lhs = this.compExpr();
        final List<TokenType> expected = Arrays.asList(TokenType.IS, TokenType.IN, TokenType.LIKE, TokenType.BETWEEN, TokenType.REGEXP, TokenType.NOT);
        while (this.tokenPos < this.tokens.size() && expected.contains(this.tokens.get(this.tokenPos).type)) {
            boolean isNot = false;
            if (this.currentTokenTypeEquals(TokenType.NOT)) {
                this.consumeToken(TokenType.NOT);
                isNot = true;
            }
            if (this.tokenPos < this.tokens.size()) {
                final List<MysqlxExpr.Expr> params = new ArrayList<MysqlxExpr.Expr>();
                params.add(this.unquoteWorkaround(lhs));
                String opName = this.tokens.get(this.tokenPos).value.toLowerCase();
                switch (this.tokens.get(this.tokenPos).type) {
                    case IS: {
                        this.consumeToken(TokenType.IS);
                        if (this.currentTokenTypeEquals(TokenType.NOT)) {
                            this.consumeToken(TokenType.NOT);
                            opName = "is_not";
                        }
                        params.add(this.compExpr());
                        break;
                    }
                    case IN: {
                        this.consumeToken(TokenType.IN);
                        if (this.currentTokenTypeEquals(TokenType.LPAREN)) {
                            params.addAll(this.parenExprList());
                            break;
                        }
                        if (!this.currentTokenTypeEquals(TokenType.LSQBRACKET)) {
                            opName = "cont_in";
                        }
                        params.add(this.compExpr());
                        break;
                    }
                    case LIKE: {
                        this.consumeToken(TokenType.LIKE);
                        params.add(this.unquoteWorkaround(this.compExpr()));
                        if (this.currentTokenTypeEquals(TokenType.ESCAPE)) {
                            this.consumeToken(TokenType.ESCAPE);
                            params.add(this.unquoteWorkaround(this.compExpr()));
                            break;
                        }
                        break;
                    }
                    case BETWEEN: {
                        this.consumeToken(TokenType.BETWEEN);
                        params.add(this.compExpr());
                        this.assertTokenAt(this.tokenPos, TokenType.AND);
                        this.consumeToken(TokenType.AND);
                        params.add(this.compExpr());
                        break;
                    }
                    case REGEXP: {
                        this.consumeToken(TokenType.REGEXP);
                        params.add(this.compExpr());
                        break;
                    }
                    default: {
                        throw new WrongArgumentException("Unknown token after NOT at pos: " + this.tokenPos);
                    }
                }
                if (isNot) {
                    opName = "not_" + opName;
                }
                final MysqlxExpr.Operator.Builder builder = MysqlxExpr.Operator.newBuilder().setName(opName).addAllParam(params);
                lhs = MysqlxExpr.Expr.newBuilder().setType(MysqlxExpr.Expr.Type.OPERATOR).setOperator(builder.build()).build();
            }
        }
        return lhs;
    }
    
    MysqlxExpr.Expr andExpr() {
        return this.parseLeftAssocBinaryOpExpr(new TokenType[] { TokenType.AND, TokenType.ANDAND }, this::ilriExpr);
    }
    
    MysqlxExpr.Expr orExpr() {
        return this.parseLeftAssocBinaryOpExpr(new TokenType[] { TokenType.OR, TokenType.OROR }, this::andExpr);
    }
    
    MysqlxExpr.Expr expr() {
        final MysqlxExpr.Expr e = this.orExpr();
        return e;
    }
    
    public MysqlxExpr.Expr parse() {
        try {
            final MysqlxExpr.Expr e = this.expr();
            if (this.tokenPos != this.tokens.size()) {
                throw new WrongArgumentException("Only " + this.tokenPos + " tokens consumed, out of " + this.tokens.size());
            }
            return e;
        }
        catch (IllegalArgumentException ex) {
            throw new WrongArgumentException("Unable to parse query '" + this.string + "'", ex);
        }
    }
    
    private <T> List<T> parseCommaSeparatedList(final Supplier<T> elementParser) {
        final List<T> elements = new ArrayList<T>();
        boolean first = true;
        while (first || this.currentTokenTypeEquals(TokenType.COMMA)) {
            if (!first) {
                this.consumeToken(TokenType.COMMA);
            }
            else {
                first = false;
            }
            elements.add(elementParser.get());
        }
        return elements;
    }
    
    public List<MysqlxCrud.Order> parseOrderSpec() {
        final MysqlxCrud.Order.Builder builder;
        return this.parseCommaSeparatedList(() -> {
            builder = MysqlxCrud.Order.newBuilder();
            builder.setExpr(this.expr());
            if (this.currentTokenTypeEquals(TokenType.ORDERBY_ASC)) {
                this.consumeToken(TokenType.ORDERBY_ASC);
                builder.setDirection(MysqlxCrud.Order.Direction.ASC);
            }
            else if (this.currentTokenTypeEquals(TokenType.ORDERBY_DESC)) {
                this.consumeToken(TokenType.ORDERBY_DESC);
                builder.setDirection(MysqlxCrud.Order.Direction.DESC);
            }
            return builder.build();
        });
    }
    
    public List<MysqlxCrud.Projection> parseTableSelectProjection() {
        final MysqlxCrud.Projection.Builder builder;
        return this.parseCommaSeparatedList(() -> {
            builder = MysqlxCrud.Projection.newBuilder();
            builder.setSource(this.expr());
            if (this.currentTokenTypeEquals(TokenType.AS)) {
                this.consumeToken(TokenType.AS);
                builder.setAlias(this.consumeToken(TokenType.IDENT));
            }
            return builder.build();
        });
    }
    
    public MysqlxCrud.Column parseTableInsertField() {
        return MysqlxCrud.Column.newBuilder().setName(this.consumeToken(TokenType.IDENT)).build();
    }
    
    public MysqlxExpr.ColumnIdentifier parseTableUpdateField() {
        return this.columnIdentifier().getIdentifier();
    }
    
    public List<MysqlxCrud.Projection> parseDocumentProjection() {
        this.allowRelationalColumns = false;
        final MysqlxCrud.Projection.Builder builder;
        return this.parseCommaSeparatedList(() -> {
            builder = MysqlxCrud.Projection.newBuilder();
            builder.setSource(this.expr());
            this.consumeToken(TokenType.AS);
            builder.setAlias(this.consumeToken(TokenType.IDENT));
            return builder.build();
        });
    }
    
    public List<MysqlxExpr.Expr> parseExprList() {
        return this.parseCommaSeparatedList(this::expr);
    }
    
    public int getPositionalPlaceholderCount() {
        return this.positionalPlaceholderCount;
    }
    
    public Map<String, Integer> getPlaceholderNameToPositionMap() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends Integer>)this.placeholderNameToPosition);
    }
    
    static {
        (ExprParser.reservedWords = new HashMap<String, TokenType>()).put("and", TokenType.AND);
        ExprParser.reservedWords.put("or", TokenType.OR);
        ExprParser.reservedWords.put("xor", TokenType.XOR);
        ExprParser.reservedWords.put("is", TokenType.IS);
        ExprParser.reservedWords.put("not", TokenType.NOT);
        ExprParser.reservedWords.put("like", TokenType.LIKE);
        ExprParser.reservedWords.put("in", TokenType.IN);
        ExprParser.reservedWords.put("regexp", TokenType.REGEXP);
        ExprParser.reservedWords.put("between", TokenType.BETWEEN);
        ExprParser.reservedWords.put("interval", TokenType.INTERVAL);
        ExprParser.reservedWords.put("escape", TokenType.ESCAPE);
        ExprParser.reservedWords.put("div", TokenType.SLASH);
        ExprParser.reservedWords.put("hex", TokenType.HEX);
        ExprParser.reservedWords.put("bin", TokenType.BIN);
        ExprParser.reservedWords.put("true", TokenType.TRUE);
        ExprParser.reservedWords.put("false", TokenType.FALSE);
        ExprParser.reservedWords.put("null", TokenType.NULL);
        ExprParser.reservedWords.put("microsecond", TokenType.MICROSECOND);
        ExprParser.reservedWords.put("second", TokenType.SECOND);
        ExprParser.reservedWords.put("minute", TokenType.MINUTE);
        ExprParser.reservedWords.put("hour", TokenType.HOUR);
        ExprParser.reservedWords.put("day", TokenType.DAY);
        ExprParser.reservedWords.put("week", TokenType.WEEK);
        ExprParser.reservedWords.put("month", TokenType.MONTH);
        ExprParser.reservedWords.put("quarter", TokenType.QUARTER);
        ExprParser.reservedWords.put("year", TokenType.YEAR);
        ExprParser.reservedWords.put("second_microsecond", TokenType.SECOND_MICROSECOND);
        ExprParser.reservedWords.put("minute_microsecond", TokenType.MINUTE_MICROSECOND);
        ExprParser.reservedWords.put("minute_second", TokenType.MINUTE_SECOND);
        ExprParser.reservedWords.put("hour_microsecond", TokenType.HOUR_MICROSECOND);
        ExprParser.reservedWords.put("hour_second", TokenType.HOUR_SECOND);
        ExprParser.reservedWords.put("hour_minute", TokenType.HOUR_MINUTE);
        ExprParser.reservedWords.put("day_microsecond", TokenType.DAY_MICROSECOND);
        ExprParser.reservedWords.put("day_second", TokenType.DAY_SECOND);
        ExprParser.reservedWords.put("day_minute", TokenType.DAY_MINUTE);
        ExprParser.reservedWords.put("day_hour", TokenType.DAY_HOUR);
        ExprParser.reservedWords.put("year_month", TokenType.YEAR_MONTH);
        ExprParser.reservedWords.put("asc", TokenType.ORDERBY_ASC);
        ExprParser.reservedWords.put("desc", TokenType.ORDERBY_DESC);
        ExprParser.reservedWords.put("as", TokenType.AS);
        ExprParser.reservedWords.put("cast", TokenType.CAST);
        ExprParser.reservedWords.put("decimal", TokenType.DECIMAL);
        ExprParser.reservedWords.put("unsigned", TokenType.UNSIGNED);
        ExprParser.reservedWords.put("signed", TokenType.SIGNED);
        ExprParser.reservedWords.put("integer", TokenType.INTEGER);
        ExprParser.reservedWords.put("date", TokenType.DATE);
        ExprParser.reservedWords.put("time", TokenType.TIME);
        ExprParser.reservedWords.put("datetime", TokenType.DATETIME);
        ExprParser.reservedWords.put("char", TokenType.CHAR);
        ExprParser.reservedWords.put("binary", TokenType.BINARY);
        ExprParser.reservedWords.put("json", TokenType.BINARY);
    }
    
    private enum TokenType
    {
        NOT, 
        AND, 
        ANDAND, 
        OR, 
        OROR, 
        XOR, 
        IS, 
        LPAREN, 
        RPAREN, 
        LSQBRACKET, 
        RSQBRACKET, 
        BETWEEN, 
        TRUE, 
        NULL, 
        FALSE, 
        IN, 
        LIKE, 
        INTERVAL, 
        REGEXP, 
        ESCAPE, 
        IDENT, 
        LSTRING, 
        LNUM_INT, 
        LNUM_DOUBLE, 
        DOT, 
        DOLLAR, 
        COMMA, 
        EQ, 
        NE, 
        GT, 
        GE, 
        LT, 
        LE, 
        BITAND, 
        BITOR, 
        BITXOR, 
        LSHIFT, 
        RSHIFT, 
        PLUS, 
        MINUS, 
        STAR, 
        SLASH, 
        HEX, 
        BIN, 
        NEG, 
        BANG, 
        EROTEME, 
        MICROSECOND, 
        SECOND, 
        MINUTE, 
        HOUR, 
        DAY, 
        WEEK, 
        MONTH, 
        QUARTER, 
        YEAR, 
        SECOND_MICROSECOND, 
        MINUTE_MICROSECOND, 
        MINUTE_SECOND, 
        HOUR_MICROSECOND, 
        HOUR_SECOND, 
        HOUR_MINUTE, 
        DAY_MICROSECOND, 
        DAY_SECOND, 
        DAY_MINUTE, 
        DAY_HOUR, 
        YEAR_MONTH, 
        DOUBLESTAR, 
        MOD, 
        COLON, 
        ORDERBY_ASC, 
        ORDERBY_DESC, 
        AS, 
        LCURLY, 
        RCURLY, 
        DOTSTAR, 
        CAST, 
        DECIMAL, 
        UNSIGNED, 
        SIGNED, 
        INTEGER, 
        DATE, 
        TIME, 
        DATETIME, 
        CHAR, 
        BINARY, 
        JSON, 
        COLDOCPATH;
    }
    
    static class Token
    {
        TokenType type;
        String value;
        
        public Token(final TokenType x, final char c) {
            this.type = x;
            this.value = new String(new char[] { c });
        }
        
        public Token(final TokenType t, final String v) {
            this.type = t;
            this.value = v;
        }
        
        @Override
        public String toString() {
            if (this.type == TokenType.IDENT || this.type == TokenType.LNUM_INT || this.type == TokenType.LNUM_DOUBLE || this.type == TokenType.LSTRING) {
                return this.type.toString() + "(" + this.value + ")";
            }
            return this.type.toString();
        }
    }
    
    @FunctionalInterface
    interface ParseExpr
    {
        MysqlxExpr.Expr parseExpr();
    }
}
