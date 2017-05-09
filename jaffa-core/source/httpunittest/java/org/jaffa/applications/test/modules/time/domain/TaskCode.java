// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.test.modules.time.domain;

import org.apache.log4j.Logger;
import java.util.*;
import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.rules.RulesEngine;
import org.jaffa.persistence.*;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.security.SecurityManager;
import org.jaffa.util.*;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.RelatedDomainObjectFoundException;
import org.jaffa.exceptions.DuplicateKeyException;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.exceptions.ApplicationExceptions;
// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the TaskCode table.
 * @author  Auto-Generated
 */
public class TaskCode extends Persistent {

    private static final Logger log = Logger.getLogger(TaskCode.class);

    /** Holds value of property task. */
    private java.lang.String m_task;

    /** Holds value of property activity. */
    private java.lang.String m_activity;

    /** Holds value of property description. */
    private java.lang.String m_description;

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String task) throws FrameworkException {
        return findByPK(uow, task) != null ? true : false;
    }

    /** Returns the domain object for the input Primary Key.
     * @return the domain object for the input Primary Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static TaskCode findByPK(UOW uow, java.lang.String task) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(task);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (TaskCode) itr.next();
            else
                return null;
        } finally {
            if (localUow && uow != null)
                uow.rollback();
        }
    }

    /** Returns a Criteria object for retrieving the domain object based on the input Primary Key.
     * @return a Criteria object for retrieving the domain object based on the input Primary Key.
     */
    public static Criteria findByPKCriteria(java.lang.String task) {
        Criteria criteria = new Criteria();
        criteria.setTable(TaskCodeMeta.getName());
        criteria.addCriteria(TaskCodeMeta.TASK, task);
        return criteria;
    }
    // .//GEN-END:2_be
    // .//GEN-BEGIN:task_be
    /** Getter for property task.
     * @return Value of property task.
     */
    public java.lang.String getTask() {
        return m_task;
    }
    
    /** Use this method to update the property task.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param task New value of property task.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setTask(java.lang.String task)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_task == null ? task == null : m_task.equals(task))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        task = validateTask(task);
        // .//GEN-END:task_be
        // Add custom code before setting the value//GEN-FIRST:task


        // .//GEN-LAST:task
        // .//GEN-BEGIN:task_1_be
        super.update();
        super.addInitialValue(TaskCodeMeta.TASK, m_task);
        m_task = task;
        // .//GEN-END:task_1_be
        // Add custom code after setting the value//GEN-FIRST:task_3


        // .//GEN-LAST:task_3
        // .//GEN-BEGIN:task_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setTask() method.
     * @param task New value of property task.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateTask(java.lang.String task)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setTask(task);
    }

    /** Use this method to validate a value for the property task.
     * @param task Value to be validated for the property task.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateTask(java.lang.String task)
    throws ValidationException, FrameworkException {
        // .//GEN-END:task_2_be
        // Add custom code before validation//GEN-FIRST:task_1


        // .//GEN-LAST:task_1
        // .//GEN-BEGIN:task_3_be
        task = FieldValidator.validate(task, (StringFieldMetaData) TaskCodeMeta.META_TASK, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TaskCodeMeta.getName(), TaskCodeMeta.TASK, task, this.getUOW());

        // .//GEN-END:task_3_be
        // Add custom code after a successful validation//GEN-FIRST:task_2


        // .//GEN-LAST:task_2
        // .//GEN-BEGIN:task_4_be
        return task;
    }
    // .//GEN-END:task_4_be
    // .//GEN-BEGIN:activity_be
    /** Getter for property activity.
     * @return Value of property activity.
     */
    public java.lang.String getActivity() {
        return m_activity;
    }
    
    /** Use this method to update the property activity.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param activity New value of property activity.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setActivity(java.lang.String activity)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_activity == null ? activity == null : m_activity.equals(activity))
            return;


        activity = validateActivity(activity);
        // .//GEN-END:activity_be
        // Add custom code before setting the value//GEN-FIRST:activity


        // .//GEN-LAST:activity
        // .//GEN-BEGIN:activity_1_be
        super.update();
        super.addInitialValue(TaskCodeMeta.ACTIVITY, m_activity);
        m_activity = activity;
        // .//GEN-END:activity_1_be
        // Add custom code after setting the value//GEN-FIRST:activity_3


        // .//GEN-LAST:activity_3
        // .//GEN-BEGIN:activity_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setActivity() method.
     * @param activity New value of property activity.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateActivity(java.lang.String activity)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setActivity(activity);
    }

    /** Use this method to validate a value for the property activity.
     * @param activity Value to be validated for the property activity.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateActivity(java.lang.String activity)
    throws ValidationException, FrameworkException {
        // .//GEN-END:activity_2_be
        // Add custom code before validation//GEN-FIRST:activity_1


        // .//GEN-LAST:activity_1
        // .//GEN-BEGIN:activity_3_be
        activity = FieldValidator.validate(activity, (StringFieldMetaData) TaskCodeMeta.META_ACTIVITY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TaskCodeMeta.getName(), TaskCodeMeta.ACTIVITY, activity, this.getUOW());

        // .//GEN-END:activity_3_be
        // Add custom code after a successful validation//GEN-FIRST:activity_2


        // .//GEN-LAST:activity_2
        // .//GEN-BEGIN:activity_4_be
        return activity;
    }
    // .//GEN-END:activity_4_be
    // .//GEN-BEGIN:description_be
    /** Getter for property description.
     * @return Value of property description.
     */
    public java.lang.String getDescription() {
        return m_description;
    }
    
    /** Use this method to update the property description.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param description New value of property description.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setDescription(java.lang.String description)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_description == null ? description == null : m_description.equals(description))
            return;


        description = validateDescription(description);
        // .//GEN-END:description_be
        // Add custom code before setting the value//GEN-FIRST:description


        // .//GEN-LAST:description
        // .//GEN-BEGIN:description_1_be
        super.update();
        super.addInitialValue(TaskCodeMeta.DESCRIPTION, m_description);
        m_description = description;
        // .//GEN-END:description_1_be
        // Add custom code after setting the value//GEN-FIRST:description_3


        // .//GEN-LAST:description_3
        // .//GEN-BEGIN:description_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setDescription() method.
     * @param description New value of property description.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateDescription(java.lang.String description)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setDescription(description);
    }

    /** Use this method to validate a value for the property description.
     * @param description Value to be validated for the property description.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateDescription(java.lang.String description)
    throws ValidationException, FrameworkException {
        // .//GEN-END:description_2_be
        // Add custom code before validation//GEN-FIRST:description_1


        // .//GEN-LAST:description_1
        // .//GEN-BEGIN:description_3_be
        description = FieldValidator.validate(description, (StringFieldMetaData) TaskCodeMeta.META_DESCRIPTION, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(TaskCodeMeta.getName(), TaskCodeMeta.DESCRIPTION, description, this.getUOW());

        // .//GEN-END:description_3_be
        // Add custom code after a successful validation//GEN-FIRST:description_2


        // .//GEN-LAST:description_2
        // .//GEN-BEGIN:description_4_be
        return description;
    }
    // .//GEN-END:description_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<TaskCode>");
        buf.append("<task>"); if (m_task != null) buf.append(m_task); buf.append("</task>");
        buf.append("<activity>"); if (m_activity != null) buf.append(m_activity); buf.append("</activity>");
        buf.append("<description>"); if (m_description != null) buf.append(m_description); buf.append("</description>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</TaskCode>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        TaskCode obj = (TaskCode) super.clone();
        return obj;
    }
    // .//GEN-END:clone_1_be
    // .//GEN-BEGIN:performForeignKeyValidations_1_be
    /** This method ensures that the modified foreign-keys are valid.
     * @throws ApplicationExceptions if an invalid foreign key is set.
     * @throws FrameworkException Indicates some system error
     */
    public void performForeignKeyValidations() throws ApplicationExceptions, FrameworkException {
        ApplicationExceptions appExps = new ApplicationExceptions();
        if (appExps.size() > 0)
            throw appExps;
    }
    // .//GEN-END:performForeignKeyValidations_1_be
    // .//GEN-BEGIN:performPreDeleteReferentialIntegrity_1_be
    /** This method is triggered by the UOW, before adding this object to the Delete-Store.
     * This will raise an exception if any associated/aggregated objects exist.
     * This will cascade delete all composite objects.
     * @throws PreDeleteFailedException if any error occurs during the process.
     */
    public void performPreDeleteReferentialIntegrity() throws PreDeleteFailedException {
    }
    // .//GEN-END:performPreDeleteReferentialIntegrity_1_be
    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
