package org.jaffa.persistence;

import org.jaffa.persistence.engines.jdbcengine.configservice.ClassMetaData;
import org.jaffa.persistence.engines.jdbcengine.configservice.ConfigurationService;


public abstract class InnerAtomicCriteria extends Criteria {

	/** Add a criteria entry. The operator '=' is used.
     * @param name The name of the field.
     * @param value The value to be assigned to the field.
     */
    public void addCriteria(String tableName, String name, Object value) {
        super.addCriteria(new CriteriaEntry(name, RELATIONAL_EQUALS, value, false ,tableName));
    }
    
    /** Add a criteria entry.
     * @param name The name of the field.
     * @param operator The operator to be used in the assignment.
     * @param value The value to be assigned to the field.
     */
    public void addCriteria(String tableName, String name, int operator, Object value) {
        super.addCriteria(new CriteriaEntry(name, operator, value, false,tableName));
    }
    
    /** Add a criteria entry. This method is to be used for unary operator like 'null' and 'not null'.
     * @param name The name of the field.
     * @param operator The operator to be used in the assignment.
     */
    public void addCriteria(String tableName, String name, int operator) {
        super.addCriteria(new CriteriaEntry(name, operator, null, false,tableName));
    }
    
    /** Add an OR criteria entry. The operator '=' is used.
     * @param name The name of the field.
     * @param value The value to be assigned to the field.
     */
    public void addOrCriteria(String tableName, String name, Object value) {
        CriteriaEntry criteriaEntry = new CriteriaEntry(name, RELATIONAL_EQUALS, value, false , tableName);
        criteriaEntry.setLogic(CriteriaEntry.LOGICAL_OR);
        addCriteria(criteriaEntry);
    }
    
    /** Add an OR criteria entry.
     * @param name The name of the field.
     * @param operator The operator to be used in the assignment.
     * @param value The value to be assigned to the field.
     */
    public void addOrCriteria(String tableName, String name, int operator, Object value) {
        CriteriaEntry criteriaEntry = new CriteriaEntry(name, operator, value, false, tableName);
        criteriaEntry.setLogic(CriteriaEntry.LOGICAL_OR);
        addCriteria(criteriaEntry);
    }
    
    /** Add an OR criteria entry. This method is to be used for unary operator like 'null' and 'not null'.
     * @param name The name of the field.
     * @param operator The operator to be used in the assignment.
     */
    public void addOrCriteria(String tableName, String name, int operator) {
        CriteriaEntry criteriaEntry = new CriteriaEntry(name, operator, null, false,tableName);
        criteriaEntry.setLogic( CriteriaEntry.LOGICAL_OR );
        addCriteria(criteriaEntry);
    }
	
    /*private ClassMetaData getMeta(String tableName) {
    	if(tableName != null) { 
    		ClassMetaData meta=ConfigurationService.getInstance().getMetaData(tableName);
    		return meta;
    	} else {
    		return null;
    	}
    }*/
}
