package org.jaffa.datatypes.adapters;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class BooleanTFConverter implements AttributeConverter<Boolean, String>{

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return (value == null || !value) ? "F" : "T";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "T".equals(value);
    }
}
