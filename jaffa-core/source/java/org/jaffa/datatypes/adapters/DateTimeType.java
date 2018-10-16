package org.jaffa.datatypes.adapters;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.jaffa.datatypes.DateTime;

import java.io.Serializable;
import java.sql.*;


public class DateTimeType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.TIMESTAMP};
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Class returnedClass() {
        return DateTime.class;
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
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor s, Object o) throws HibernateException, SQLException {
        Timestamp timestamp = resultSet.getTimestamp(names[0]);
        if (resultSet.wasNull()) {
            return null;
        }
        return new DateTime(timestamp.getTime());
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object o, int i, SessionImplementor s) throws HibernateException, SQLException {
        if (o == null) {
            preparedStatement.setNull(i, Types.TIMESTAMP);
        }else{
            preparedStatement.setTimestamp(i, ((DateTime)o).timestamp());
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o!=null ? new DateTime(((DateTime) o).timeInMillis()) : null;
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
