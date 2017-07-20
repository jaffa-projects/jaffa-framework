package org.apache.log4j.jdbcplus.helper;

import java.util.UUID;

public class JDBCIDHandler implements org.apache.log4j.jdbcplus.JDBCIDHandler{

	public Object getID() {
		return UUID.randomUUID().toString();
	}

}
