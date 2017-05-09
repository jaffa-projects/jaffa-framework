package org.jaffa.security.jdbcsecurity.domain;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Persistent;
import org.jaffa.persistence.engines.jdbcengine.IStoredProcedure;
import org.jaffa.persistence.engines.jdbcengine.configservice.ConfigurationService;

import java.util.List;

public class JdbcSecuritySetContext extends Persistent implements IStoredProcedure {

    private String userId;

    private String[] role;

    private String localId;

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public JdbcSecuritySetContext() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String[] getRole() {
        return role;
    }

    public void setRole(String[] role) {
        this.role = role;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }




    // *** Implementation for the IStoredProcedure interface

    /** Returns an array of field names, which correspond to the parameters of the StoredProcedure.
     * @return An array of parameters to the StoredProcedure.
     */
    public String[] getParameters() {
        return new String[] {"userId", "role", "localId"};
    }

    /** Returns an array of Strings signifying the SQL type for each of the parameter returned in the getParameters() method.
     * eg. STRING, BOOLEAN etc. etc.
     * @return An array of Strings signifying the SQL type of the parameters.
     */
    public String[] getParamSqlTypes() {
        return new String[] {"STRING", "ARRAY", "STRING"};
    }

    /** Returns an array of integers signifying the directions (input, output or input/output)
     * for each of the parameter returned in the getParameters() method.
     * @return An array of integers signifying the directions of the parameters.
     */
    public int[] getParamDirections() {
            return new int[] {IN, IN, IN};
    }

    /** Returns a String having the call to the StoredProcedure.
     * @return a String having the call to the StoredProcedure.
     */
    public String prepareCall() {
        String engineType = ConfigurationService.getInstance().getDatabase("default").getEngine();
        if ("oracle".equals(engineType)){
            return "{call jaffa_sec.set_userid(?,?,?)}";
        }else
            throw new UnsupportedOperationException("Stored Procedure has not been written to support " + engineType);

    }
}
