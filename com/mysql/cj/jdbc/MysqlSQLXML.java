
package com.mysql.cj.jdbc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.io.IOException;
import javax.xml.stream.XMLStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayInputStream;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.ContentHandler;
import javax.xml.transform.Result;
import java.io.Writer;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Node;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.StringReader;
import org.xml.sax.InputSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.Source;
import java.io.Reader;
import java.io.InputStream;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.Messages;
import java.sql.SQLException;
import com.mysql.cj.exceptions.CJException;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.dom.DOMResult;
import com.mysql.cj.jdbc.result.ResultSetInternalMethods;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLInputFactory;
import java.sql.SQLXML;

public class MysqlSQLXML implements SQLXML
{
    private XMLInputFactory inputFactory;
    private XMLOutputFactory outputFactory;
    private String stringRep;
    private ResultSetInternalMethods owningResultSet;
    private int columnIndexOfXml;
    private boolean fromResultSet;
    private boolean isClosed;
    private boolean workingWithResult;
    private DOMResult asDOMResult;
    private SAXResult asSAXResult;
    private SimpleSaxToReader saxToReaderConverter;
    private StringWriter asStringWriter;
    private ByteArrayOutputStream asByteArrayOutputStream;
    private ExceptionInterceptor exceptionInterceptor;
    
    public MysqlSQLXML(final ResultSetInternalMethods owner, final int index, final ExceptionInterceptor exceptionInterceptor) {
        this.isClosed = false;
        this.owningResultSet = owner;
        this.columnIndexOfXml = index;
        this.fromResultSet = true;
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    public MysqlSQLXML(final ExceptionInterceptor exceptionInterceptor) {
        this.isClosed = false;
        this.fromResultSet = false;
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    @Override
    public synchronized void free() throws SQLException {
        try {
            this.stringRep = null;
            this.asDOMResult = null;
            this.asSAXResult = null;
            this.inputFactory = null;
            this.outputFactory = null;
            this.owningResultSet = null;
            this.workingWithResult = false;
            this.isClosed = true;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized String getString() throws SQLException {
        try {
            this.checkClosed();
            this.checkWorkingWithResult();
            if (this.fromResultSet) {
                return this.owningResultSet.getString(this.columnIndexOfXml);
            }
            return this.stringRep;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    private synchronized void checkClosed() throws SQLException {
        if (this.isClosed) {
            throw SQLError.createSQLException(Messages.getString("MysqlSQLXML.0"), this.exceptionInterceptor);
        }
    }
    
    private synchronized void checkWorkingWithResult() throws SQLException {
        if (this.workingWithResult) {
            throw SQLError.createSQLException(Messages.getString("MysqlSQLXML.1"), "S1009", this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized void setString(final String str) throws SQLException {
        try {
            this.checkClosed();
            this.checkWorkingWithResult();
            this.stringRep = str;
            this.fromResultSet = false;
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    public synchronized boolean isEmpty() throws SQLException {
        this.checkClosed();
        this.checkWorkingWithResult();
        return !this.fromResultSet && (this.stringRep == null || this.stringRep.length() == 0);
    }
    
    @Override
    public synchronized InputStream getBinaryStream() throws SQLException {
        try {
            this.checkClosed();
            this.checkWorkingWithResult();
            return this.owningResultSet.getBinaryStream(this.columnIndexOfXml);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized Reader getCharacterStream() throws SQLException {
        try {
            this.checkClosed();
            this.checkWorkingWithResult();
            return this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    @Override
    public <T extends Source> T getSource(final Class<T> clazz) throws SQLException {
        try {
            this.checkClosed();
            this.checkWorkingWithResult();
            if (clazz == null || clazz.equals(SAXSource.class)) {
                InputSource inputSource = null;
                if (this.fromResultSet) {
                    inputSource = new InputSource(this.owningResultSet.getCharacterStream(this.columnIndexOfXml));
                }
                else {
                    inputSource = new InputSource(new StringReader(this.stringRep));
                }
                return (T)new SAXSource(inputSource);
            }
            if (clazz.equals(DOMSource.class)) {
                try {
                    final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
                    builderFactory.setNamespaceAware(true);
                    final DocumentBuilder builder = builderFactory.newDocumentBuilder();
                    InputSource inputSource2 = null;
                    if (this.fromResultSet) {
                        inputSource2 = new InputSource(this.owningResultSet.getCharacterStream(this.columnIndexOfXml));
                    }
                    else {
                        inputSource2 = new InputSource(new StringReader(this.stringRep));
                    }
                    return (T)new DOMSource(builder.parse(inputSource2));
                }
                catch (Throwable t) {
                    final SQLException sqlEx = SQLError.createSQLException(t.getMessage(), "S1009", t, this.exceptionInterceptor);
                    throw sqlEx;
                }
            }
            if (clazz.equals(StreamSource.class)) {
                Reader reader = null;
                if (this.fromResultSet) {
                    reader = this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
                }
                else {
                    reader = new StringReader(this.stringRep);
                }
                return (T)new StreamSource(reader);
            }
            if (clazz.equals(StAXSource.class)) {
                try {
                    Reader reader = null;
                    if (this.fromResultSet) {
                        reader = this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
                    }
                    else {
                        reader = new StringReader(this.stringRep);
                    }
                    return (T)new StAXSource(this.inputFactory.createXMLStreamReader(reader));
                }
                catch (XMLStreamException ex) {
                    final SQLException sqlEx = SQLError.createSQLException(ex.getMessage(), "S1009", ex, this.exceptionInterceptor);
                    throw sqlEx;
                }
            }
            throw SQLError.createSQLException(Messages.getString("MysqlSQLXML.2", new Object[] { clazz.toString() }), "S1009", this.exceptionInterceptor);
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.exceptionInterceptor);
        }
    }
    
    @Override
    public synchronized OutputStream setBinaryStream() throws SQLException {
        try {
            this.checkClosed();
            this.checkWorkingWithResult();
            this.workingWithResult = true;
            return this.setBinaryStreamInternal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    private synchronized OutputStream setBinaryStreamInternal() throws SQLException {
        return this.asByteArrayOutputStream = new ByteArrayOutputStream();
    }
    
    @Override
    public synchronized Writer setCharacterStream() throws SQLException {
        try {
            this.checkClosed();
            this.checkWorkingWithResult();
            this.workingWithResult = true;
            return this.setCharacterStreamInternal();
        }
        catch (CJException ex) {
            throw SQLExceptionsMapping.translateException(ex, this.exceptionInterceptor);
        }
    }
    
    private synchronized Writer setCharacterStreamInternal() throws SQLException {
        return this.asStringWriter = new StringWriter();
    }
    
    @Override
    public synchronized <T extends Result> T setResult(final Class<T> clazz) throws SQLException {
        try {
            this.checkClosed();
            this.checkWorkingWithResult();
            this.workingWithResult = true;
            this.asDOMResult = null;
            this.asSAXResult = null;
            this.saxToReaderConverter = null;
            this.stringRep = null;
            this.asStringWriter = null;
            this.asByteArrayOutputStream = null;
            if (clazz == null || clazz.equals(SAXResult.class)) {
                this.saxToReaderConverter = new SimpleSaxToReader();
                return (T)(this.asSAXResult = new SAXResult(this.saxToReaderConverter));
            }
            if (clazz.equals(DOMResult.class)) {
                return (T)(this.asDOMResult = new DOMResult());
            }
            if (clazz.equals(StreamResult.class)) {
                return (T)new StreamResult(this.setCharacterStreamInternal());
            }
            if (clazz.equals(StAXResult.class)) {
                try {
                    if (this.outputFactory == null) {
                        this.outputFactory = XMLOutputFactory.newInstance();
                    }
                    return (T)new StAXResult(this.outputFactory.createXMLEventWriter(this.setCharacterStreamInternal()));
                }
                catch (XMLStreamException ex) {
                    final SQLException sqlEx = SQLError.createSQLException(ex.getMessage(), "S1009", ex, this.exceptionInterceptor);
                    throw sqlEx;
                }
            }
            throw SQLError.createSQLException(Messages.getString("MysqlSQLXML.3", new Object[] { clazz.toString() }), "S1009", this.exceptionInterceptor);
        }
        catch (CJException ex2) {
            throw SQLExceptionsMapping.translateException(ex2, this.exceptionInterceptor);
        }
    }
    
    private Reader binaryInputStreamStreamToReader(final ByteArrayOutputStream out) {
        try {
            String encoding = "UTF-8";
            try {
                final ByteArrayInputStream bIn = new ByteArrayInputStream(out.toByteArray());
                final XMLStreamReader reader = this.inputFactory.createXMLStreamReader(bIn);
                int eventType = 0;
                while ((eventType = reader.next()) != 8) {
                    if (eventType == 7) {
                        final String possibleEncoding = reader.getEncoding();
                        if (possibleEncoding != null) {
                            encoding = possibleEncoding;
                            break;
                        }
                        break;
                    }
                }
            }
            catch (Throwable t) {}
            return new StringReader(new String(out.toByteArray(), encoding));
        }
        catch (UnsupportedEncodingException badEnc) {
            throw new RuntimeException(badEnc);
        }
    }
    
    protected String readerToString(final Reader reader) throws SQLException {
        final StringBuilder buf = new StringBuilder();
        int charsRead = 0;
        final char[] charBuf = new char[512];
        try {
            while ((charsRead = reader.read(charBuf)) != -1) {
                buf.append(charBuf, 0, charsRead);
            }
        }
        catch (IOException ioEx) {
            final SQLException sqlEx = SQLError.createSQLException(ioEx.getMessage(), "S1009", ioEx, this.exceptionInterceptor);
            throw sqlEx;
        }
        return buf.toString();
    }
    
    protected synchronized Reader serializeAsCharacterStream() throws SQLException {
        this.checkClosed();
        if (this.workingWithResult) {
            if (this.stringRep != null) {
                return new StringReader(this.stringRep);
            }
            if (this.asDOMResult != null) {
                return new StringReader(this.domSourceToString());
            }
            if (this.asStringWriter != null) {
                return new StringReader(this.asStringWriter.toString());
            }
            if (this.asSAXResult != null) {
                return this.saxToReaderConverter.toReader();
            }
            if (this.asByteArrayOutputStream != null) {
                return this.binaryInputStreamStreamToReader(this.asByteArrayOutputStream);
            }
        }
        return this.owningResultSet.getCharacterStream(this.columnIndexOfXml);
    }
    
    protected String domSourceToString() throws SQLException {
        try {
            final DOMSource source = new DOMSource(this.asDOMResult.getNode());
            final Transformer identity = TransformerFactory.newInstance().newTransformer();
            final StringWriter stringOut = new StringWriter();
            final Result result = new StreamResult(stringOut);
            identity.transform(source, result);
            return stringOut.toString();
        }
        catch (Throwable t) {
            final SQLException sqlEx = SQLError.createSQLException(t.getMessage(), "S1009", t, this.exceptionInterceptor);
            throw sqlEx;
        }
    }
    
    protected synchronized String serializeAsString() throws SQLException {
        this.checkClosed();
        if (this.workingWithResult) {
            if (this.stringRep != null) {
                return this.stringRep;
            }
            if (this.asDOMResult != null) {
                return this.domSourceToString();
            }
            if (this.asStringWriter != null) {
                return this.asStringWriter.toString();
            }
            if (this.asSAXResult != null) {
                return this.readerToString(this.saxToReaderConverter.toReader());
            }
            if (this.asByteArrayOutputStream != null) {
                return this.readerToString(this.binaryInputStreamStreamToReader(this.asByteArrayOutputStream));
            }
        }
        return this.owningResultSet.getString(this.columnIndexOfXml);
    }
    
    class SimpleSaxToReader extends DefaultHandler
    {
        StringBuilder buf;
        private boolean inCDATA;
        
        SimpleSaxToReader() {
            this.buf = new StringBuilder();
            this.inCDATA = false;
        }
        
        @Override
        public void startDocument() throws SAXException {
            this.buf.append("<?xml version='1.0' encoding='UTF-8'?>");
        }
        
        @Override
        public void endDocument() throws SAXException {
        }
        
        @Override
        public void startElement(final String namespaceURI, final String sName, final String qName, final Attributes attrs) throws SAXException {
            this.buf.append("<");
            this.buf.append(qName);
            if (attrs != null) {
                for (int i = 0; i < attrs.getLength(); ++i) {
                    this.buf.append(" ");
                    this.buf.append(attrs.getQName(i)).append("=\"");
                    this.escapeCharsForXml(attrs.getValue(i), true);
                    this.buf.append("\"");
                }
            }
            this.buf.append(">");
        }
        
        @Override
        public void characters(final char[] buffer, final int offset, final int len) throws SAXException {
            if (!this.inCDATA) {
                this.escapeCharsForXml(buffer, offset, len, false);
            }
            else {
                this.buf.append(buffer, offset, len);
            }
        }
        
        @Override
        public void ignorableWhitespace(final char[] ch, final int start, final int length) throws SAXException {
            this.characters(ch, start, length);
        }
        
        public void startCDATA() throws SAXException {
            this.buf.append("<![CDATA[");
            this.inCDATA = true;
        }
        
        public void endCDATA() throws SAXException {
            this.inCDATA = false;
            this.buf.append("]]>");
        }
        
        public void comment(final char[] ch, final int start, final int length) throws SAXException {
            this.buf.append("<!--");
            for (int i = 0; i < length; ++i) {
                this.buf.append(ch[start + i]);
            }
            this.buf.append("-->");
        }
        
        Reader toReader() {
            return new StringReader(this.buf.toString());
        }
        
        private void escapeCharsForXml(final String str, final boolean isAttributeData) {
            if (str == null) {
                return;
            }
            for (int strLen = str.length(), i = 0; i < strLen; ++i) {
                this.escapeCharsForXml(str.charAt(i), isAttributeData);
            }
        }
        
        private void escapeCharsForXml(final char[] buffer, final int offset, final int len, final boolean isAttributeData) {
            if (buffer == null) {
                return;
            }
            for (int i = 0; i < len; ++i) {
                this.escapeCharsForXml(buffer[offset + i], isAttributeData);
            }
        }
        
        private void escapeCharsForXml(final char c, final boolean isAttributeData) {
            switch (c) {
                case '<': {
                    this.buf.append("&lt;");
                    break;
                }
                case '>': {
                    this.buf.append("&gt;");
                    break;
                }
                case '&': {
                    this.buf.append("&amp;");
                    break;
                }
                case '\"': {
                    if (!isAttributeData) {
                        this.buf.append("\"");
                        break;
                    }
                    this.buf.append("&quot;");
                    break;
                }
                case '\r': {
                    this.buf.append("&#xD;");
                    break;
                }
                default: {
                    if ((c >= '\u0001' && c <= '\u001f' && c != '\t' && c != '\n') || (c >= '\u007f' && c <= '\u009f') || c == '\u2028' || (isAttributeData && (c == '\t' || c == '\n'))) {
                        this.buf.append("&#x");
                        this.buf.append(Integer.toHexString(c).toUpperCase());
                        this.buf.append(";");
                        break;
                    }
                    this.buf.append(c);
                    break;
                }
            }
        }
    }
}
