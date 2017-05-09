package org.jaffa.datatypes.adapters;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;
import java.sql.Date;

import org.jaffa.datatypes.DateTime;


@Convert
public class DateTimeConverter implements AttributeConverter<DateTime, Date>{

    @Override
    public Date convertToDatabaseColumn(DateTime value) {
        return value!=null ? value.sqlDate() : null;
    }

    @Override
    public DateTime convertToEntityAttribute(Date value) {
        return value!=null ? new DateTime(value) : null;
    }
}
