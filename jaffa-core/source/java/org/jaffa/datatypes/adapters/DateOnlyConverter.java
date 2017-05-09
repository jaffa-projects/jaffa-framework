package org.jaffa.datatypes.adapters;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.sql.Date;

import org.jaffa.datatypes.DateOnly;


@Convert
public class DateOnlyConverter implements AttributeConverter<DateOnly, Date>{

    @Override
    public Date convertToDatabaseColumn(DateOnly value) {
        return value!=null ? value.sqlDate() : null;
    }

    @Override
    public DateOnly convertToEntityAttribute(Date value) {
        return value != null ? new DateOnly(value) : null;
    }
}
