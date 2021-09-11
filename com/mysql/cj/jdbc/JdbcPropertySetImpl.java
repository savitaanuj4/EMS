
package com.mysql.cj.jdbc;

import com.mysql.cj.conf.PropertyDefinition;
import com.mysql.cj.conf.RuntimeProperty;
import java.sql.SQLException;
import java.util.Iterator;
import com.mysql.cj.conf.PropertyDefinitions;
import java.sql.DriverPropertyInfo;
import java.util.Properties;
import com.mysql.cj.util.StringUtils;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.conf.PropertyKey;
import com.mysql.cj.conf.DefaultPropertySet;

public class JdbcPropertySetImpl extends DefaultPropertySet implements JdbcPropertySet
{
    private static final long serialVersionUID = -8223499903182568260L;
    
    @Override
    public void postInitialization() {
        if (this.getIntegerProperty(PropertyKey.maxRows).getValue() == 0) {
            super.getProperty(PropertyKey.maxRows).setValue(-1, null);
        }
        final String testEncoding = this.getStringProperty(PropertyKey.characterEncoding).getValue();
        if (testEncoding != null) {
            final String testString = "abc";
            StringUtils.getBytes(testString, testEncoding);
        }
        if (this.getBooleanProperty(PropertyKey.useCursorFetch).getValue()) {
            super.getProperty(PropertyKey.useServerPrepStmts).setValue(true);
        }
    }
    
    @Override
    public DriverPropertyInfo[] exposeAsDriverPropertyInfo(final Properties info, final int slotsToReserve) throws SQLException {
        this.initializeProperties(info);
        final int numProperties = PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.size();
        final int listSize = numProperties + slotsToReserve;
        final DriverPropertyInfo[] driverProperties = new DriverPropertyInfo[listSize];
        int i = slotsToReserve;
        for (final PropertyKey propKey : PropertyDefinitions.PROPERTY_KEY_TO_PROPERTY_DEFINITION.keySet()) {
            driverProperties[i++] = this.getAsDriverPropertyInfo(this.getProperty(propKey));
        }
        return driverProperties;
    }
    
    private DriverPropertyInfo getAsDriverPropertyInfo(final RuntimeProperty<?> pr) {
        final PropertyDefinition<?> pdef = pr.getPropertyDefinition();
        final DriverPropertyInfo dpi = new DriverPropertyInfo(pdef.getName(), null);
        dpi.choices = pdef.getAllowableValues();
        dpi.value = ((pr.getStringValue() != null) ? pr.getStringValue() : null);
        dpi.required = false;
        dpi.description = pdef.getDescription();
        return dpi;
    }
}
