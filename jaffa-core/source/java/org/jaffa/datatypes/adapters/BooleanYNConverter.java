package org.jaffa.datatypes.adapters;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class BooleanYNConverter implements AttributeConverter<Boolean, String>{

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return (value == null || !value) ? "N" : "Y";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "Y".equals(value);
    }
}
