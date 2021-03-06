package org.jaffa.datatypes.adapters;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.jaffa.datatypes.DateOnly;


import java.io.Serializable;
import java.sql.*;


public class DateOnlyType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.TIMESTAMP};
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return DateOnly.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == y || !(x == null || y == null) && x.equals(y);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor s, Object o)
            throws HibernateException, SQLException {
        DateOnly result = null;
        Timestamp timestamp = resultSet.getTimestamp(names[0]);
        if (timestamp != null && !resultSet.wasNull()) {
            result = new DateOnly(timestamp.getTime());
        }
        return result;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor s) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.TIMESTAMP);
        }else{
            preparedStatement.setTimestamp(i, ((DateOnly)o).timestamp());
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o!=null ? new DateOnly(((DateOnly)o).timeInMillis()) : null;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) deepCopy(o);
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return deepCopy(serializable);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return deepCopy(o);
    }

}
