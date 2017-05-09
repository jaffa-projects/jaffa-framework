// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.transaction.domain;

import javax.persistence.*;
import org.jaffa.datatypes.*;
import org.jaffa.datatypes.adapters.*;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.persistence.Persistent;
import org.jaffa.beans.factory.config.StaticContext;
import java.io.Serializable;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the J_TRANS_DEPENDS_SWEEPER$VIEW table.
 * @author  Auto-Generated
 */
@Entity
@Table(name="J_TRANS_DEPENDS_SWEEPER$VIEW")
@SqlResultSetMapping(name="TransactionDependencySweeperView",entities={@EntityResult(entityClass=TransactionDependencySweeperView.class)})
public class TransactionDependencySweeperView extends Persistent {


    /** Holds value of property m_compositeKey. */
    @EmbeddedId
    private CompositeKey m_compositeKey;
    
    /** Holds value of property transactionId. */
    @Transient
    private java.lang.String m_transactionId;

    /** Holds value of property dependsOnId. */
    @Transient
    private java.lang.String m_dependsOnId;

    /** Holds value of property status. */
    @Transient
    private java.lang.String m_status;

    // .//GEN-END:2_be

    /**
     * Default Constructor
     *
     * Calls the Static Context Factory to allow Spring to initialize this object
     */
    public TransactionDependencySweeperView() {
        StaticContext.configure(this);
    }

    /** Getter for property m_compositeKey.
     * @return Value of property m_compositeKey.
     */
    public CompositeKey getCompositeKey() {
        return m_compositeKey;
    }

    /** Setter for property m_compositeKey.
     * @sets Value of property m_compositeKey.
     */
    public void setCompositeKey(CompositeKey compositeKey) {
        this.m_compositeKey = compositeKey;
    }     
    // .//GEN-BEGIN:transactionId_be
    /** Getter for property transactionId.
     * @return Value of property transactionId.
     */
    public java.lang.String getTransactionId() {
        if(m_transactionId!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setTransactionId(m_transactionId);
        }
        return getCompositeKey()!=null ? getCompositeKey().getTransactionId() : null;
    }
    
    /** Setter for property transactionId.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param transactionId New value of property transactionId.
     */
    public void setTransactionId(java.lang.String transactionId) {
        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
        
        getCompositeKey().setTransactionId(transactionId);
    }
    // .//GEN-END:transactionId_be
    // .//GEN-BEGIN:dependsOnId_be
    /** Getter for property dependsOnId.
     * @return Value of property dependsOnId.
     */
    public java.lang.String getDependsOnId() {
        if(m_dependsOnId!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setDependsOnId(m_dependsOnId);
        }
        return getCompositeKey()!=null ? getCompositeKey().getDependsOnId() : null;
    }
    
    /** Setter for property dependsOnId.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param dependsOnId New value of property dependsOnId.
     */
    public void setDependsOnId(java.lang.String dependsOnId) {
        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
        
        getCompositeKey().setDependsOnId(dependsOnId);
    }
    // .//GEN-END:dependsOnId_be
    // .//GEN-BEGIN:status_be
    /** Getter for property status.
     * @return Value of property status.
     */
    public java.lang.String getStatus() {
        if(m_status!=null){
            if(getCompositeKey() == null)
                setCompositeKey(new CompositeKey());

            getCompositeKey().setStatus(m_status);
        }
        return getCompositeKey()!=null ? getCompositeKey().getStatus() : null;
    }
    
    /** Setter for property status.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param status New value of property status.
     */
    public void setStatus(java.lang.String status) {
        if(getCompositeKey() == null)
            setCompositeKey(new CompositeKey());
        
        getCompositeKey().setStatus(status);
    }
    // .//GEN-END:status_be
    // .//GEN-BEGIN:3_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<TransactionDependencySweeperView>");
        buf.append("<transactionId>"); if (getTransactionId() != null) buf.append(getTransactionId()); buf.append("</transactionId>");
        buf.append("<dependsOnId>"); if (getDependsOnId() != null) buf.append(getDependsOnId()); buf.append("</dependsOnId>");
        buf.append("<status>"); if (m_status != null) buf.append(m_status); buf.append("</status>");
        buf.append(super.toString());
        buf.append("</TransactionDependencySweeperView>");
        return buf.toString();
    }

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate() throws ApplicationExceptions, FrameworkException {
        super.validate();
    }

    // .//GEN-LAST:custom

/**
 * Auto Generated Persistent class for the CompositeKey.
 * @author  Auto-Generated
 */
    @Embeddable
    public static class CompositeKey implements Serializable{
    
        /** Holds value of property transactionId. */
        @Column(name="TRANSACTION_ID")
        private java.lang.String m_transactionId;
    
        /** Holds value of property dependsOnId. */
        @Column(name="DEPENDS_ON_ID")
        private java.lang.String m_dependsOnId;
    
        /** Holds value of property status. */
        @Column(name="STATUS")
        private java.lang.String m_status;

        /** Getter for property m_transactionId.
        * @return Value of property m_transactionId.
        */
        public java.lang.String getTransactionId() {
            return m_transactionId;
        }  

        /** Setter for property m_transactionId.
        * @sets Value of property m_transactionId.
        */
        public void setTransactionId(java.lang.String transactionId){
            m_transactionId = transactionId;
        }    

        /** Getter for property m_dependsOnId.
        * @return Value of property m_dependsOnId.
        */
        public java.lang.String getDependsOnId() {
            return m_dependsOnId;
        }  

        /** Setter for property m_dependsOnId.
        * @sets Value of property m_dependsOnId.
        */
        public void setDependsOnId(java.lang.String dependsOnId){
            m_dependsOnId = dependsOnId;
        }    

        /** Getter for property m_status.
        * @return Value of property m_status.
        */
        public java.lang.String getStatus() {
            return m_status;
        }  

        /** Setter for property m_status.
        * @sets Value of property m_status.
        */
        public void setStatus(java.lang.String status){
            m_status = status;
        }    
        /**
         * Compares this candidateKey with another candidateKey object.
         * Returns a true if both the objects have the same candidate key.
         *
         * @param obj the other Persistent object.
         * @return a true if both the objects have the same candidate key.
         */
        public boolean equals(Object obj) {
            if (obj != null && this.getClass() == obj.getClass()) {
                boolean equals = true;
                equals = equals && (getTransactionId() == null ? ((TransactionDependencySweeperView.CompositeKey) obj).getTransactionId() == null : getTransactionId().equals(((TransactionDependencySweeperView.CompositeKey) obj).getTransactionId()));
                equals = equals && (getDependsOnId() == null ? ((TransactionDependencySweeperView.CompositeKey) obj).getDependsOnId() == null : getDependsOnId().equals(((TransactionDependencySweeperView.CompositeKey) obj).getDependsOnId()));
                return equals;
            }
            return super.equals(obj);
        }

        /**
         * Returns the hashCode of this object based on it's candidate key.
         *
         * @return the hashCode of this object based on it's candidate key.
         */
        public int hashCode() {
            int i = 0;
            i += getTransactionId()!=null ? getTransactionId().hashCode() : 0;
            i += getDependsOnId()!=null ? getDependsOnId().hashCode() : 0;
            return i;
        } 
    }
}
