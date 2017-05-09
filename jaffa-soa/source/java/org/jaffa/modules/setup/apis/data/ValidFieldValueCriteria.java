package org.jaffa.modules.setup.apis.data;

import org.jaffa.components.finder.FinderTx;
import org.jaffa.components.finder.StringCriteriaField;
import org.jaffa.modules.setup.domain.ValidFieldValueMeta;
import org.jaffa.persistence.Criteria;
import org.jaffa.soa.graph.GraphCriteria;

/**
 * The Criteria Data Object, for specifying retrieve profiles for
 * a list of ValidFieldValue Domain Object Graphs.
 * <p>
 * This is a list of ValidFieldValue criteria fields, that when populated, will be used to
 * construct the query clause (see {@link #returnQueryClause(Criteria)} for retrieving a set
 * of ValidFieldValue objects.
 */
public class ValidFieldValueCriteria extends GraphCriteria {

    private StringCriteriaField tableName;
    private StringCriteriaField fieldName;
    private StringCriteriaField legalValue;
    private StringCriteriaField description;
    private StringCriteriaField remarks;

    /**
     * Returns the criteria object used for retrieving ValidFieldValue records.
     * @return the criteria object used for retrieving ValidFieldValue records.
     */
    @Override
    public Criteria returnQueryClause(Criteria c) {
        c = super.returnQueryClause(c);
        c.setTable(ValidFieldValueMeta.getName());
        FinderTx.addCriteria(getTableName(), ValidFieldValueMeta.TABLE_NAME, c);
        FinderTx.addCriteria(getFieldName(), ValidFieldValueMeta.FIELD_NAME, c);
        FinderTx.addCriteria(getLegalValue(), ValidFieldValueMeta.LEGAL_VALUE, c);
        FinderTx.addCriteria(getDescription(), ValidFieldValueMeta.DESCRIPTION, c);
        FinderTx.addCriteria(getRemarks(), ValidFieldValueMeta.REMARKS, c);

        // By default, order by tableName, fieldName, legalValue
        if (c.getOrderBys() == null || c.getOrderBys().size() == 0) {
            c.addOrderBy(ValidFieldValueMeta.TABLE_NAME);
            c.addOrderBy(ValidFieldValueMeta.FIELD_NAME);
            c.addOrderBy(ValidFieldValueMeta.LEGAL_VALUE);
        }
        return c;
    }

    /**
     * @return the tableName
     */
    public StringCriteriaField getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(StringCriteriaField tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the fieldName
     */
    public StringCriteriaField getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(StringCriteriaField fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the legalValue
     */
    public StringCriteriaField getLegalValue() {
        return legalValue;
    }

    /**
     * @param legalValue the legalValue to set
     */
    public void setLegalValue(StringCriteriaField legalValue) {
        this.legalValue = legalValue;
    }

    /**
     * @return the description
     */
    public StringCriteriaField getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(StringCriteriaField description) {
        this.description = description;
    }

    /**
     * @return the remarks
     */
    public StringCriteriaField getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(StringCriteriaField remarks) {
        this.remarks = remarks;
    }
}
