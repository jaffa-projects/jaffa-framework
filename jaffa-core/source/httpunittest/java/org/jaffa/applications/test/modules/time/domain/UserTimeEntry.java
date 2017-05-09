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
 * Auto Generated Persistent class for the UserTimeEntry table.
 * @author  Auto-Generated
 */
public class UserTimeEntry extends Persistent {

    private static final Logger log = Logger.getLogger(UserTimeEntry.class);

    /** Holds value of property userName. */
    private java.lang.String m_userName;

    /** Holds value of property projectCode. */
    private java.lang.String m_projectCode;

    /** Holds value of property activity. */
    private java.lang.String m_activity;

    /** Holds value of property task. */
    private java.lang.String m_task;

    /** Holds value of property periodStart. */
    private org.jaffa.datatypes.DateTime m_periodStart;

    /** Holds value of property periodEnd. */
    private org.jaffa.datatypes.DateTime m_periodEnd;

    /** Check if the domain object exists for the input Primary Key.
     * @return true if the domain object exists for the input Primary Key.
     * @throws FrameworkException Indicates some system error
     */
    public static boolean exists(UOW uow, java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws FrameworkException {
        return findByPK(uow, userName, projectCode, task, periodStart, periodEnd) != null ? true : false;
    }

    /** Returns the domain object for the input Primary Key.
     * @return the domain object for the input Primary Key. A null is returned if the domain object is not found.
     * @throws FrameworkException Indicates some system error
     */
    public static UserTimeEntry findByPK(UOW uow, java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) throws FrameworkException {
        boolean localUow = false;
        try {
            if (uow == null || !uow.isActive()) {
                uow = new UOW();
                localUow = true;
            }
            Criteria criteria = findByPKCriteria(userName, projectCode, task, periodStart, periodEnd);
            Iterator itr = uow.query(criteria).iterator();
            if (itr.hasNext())
                return (UserTimeEntry) itr.next();
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
    public static Criteria findByPKCriteria(java.lang.String userName, java.lang.String projectCode, java.lang.String task, org.jaffa.datatypes.DateTime periodStart, org.jaffa.datatypes.DateTime periodEnd) {
        Criteria criteria = new Criteria();
        criteria.setTable(UserTimeEntryMeta.getName());
        criteria.addCriteria(UserTimeEntryMeta.USER_NAME, userName);
        criteria.addCriteria(UserTimeEntryMeta.PROJECT_CODE, projectCode);
        criteria.addCriteria(UserTimeEntryMeta.TASK, task);
        criteria.addCriteria(UserTimeEntryMeta.PERIOD_START, periodStart);
        criteria.addCriteria(UserTimeEntryMeta.PERIOD_END, periodEnd);
        return criteria;
    }
    // .//GEN-END:2_be
    // .//GEN-BEGIN:userName_be
    /** Getter for property userName.
     * @return Value of property userName.
     */
    public java.lang.String getUserName() {
        return m_userName;
    }
    
    /** Use this method to update the property userName.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param userName New value of property userName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setUserName(java.lang.String userName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_userName == null ? userName == null : m_userName.equals(userName))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        userName = validateUserName(userName);
        // .//GEN-END:userName_be
        // Add custom code before setting the value//GEN-FIRST:userName


        // .//GEN-LAST:userName
        // .//GEN-BEGIN:userName_1_be
        super.update();
        super.addInitialValue(UserTimeEntryMeta.USER_NAME, m_userName);
        m_userName = userName;
        // .//GEN-END:userName_1_be
        // Add custom code after setting the value//GEN-FIRST:userName_3


        // .//GEN-LAST:userName_3
        // .//GEN-BEGIN:userName_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setUserName() method.
     * @param userName New value of property userName.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateUserName(java.lang.String userName)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setUserName(userName);
    }

    /** Use this method to validate a value for the property userName.
     * @param userName Value to be validated for the property userName.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateUserName(java.lang.String userName)
    throws ValidationException, FrameworkException {
        // .//GEN-END:userName_2_be
        // Add custom code before validation//GEN-FIRST:userName_1


        // .//GEN-LAST:userName_1
        // .//GEN-BEGIN:userName_3_be
        userName = FieldValidator.validate(userName, (StringFieldMetaData) UserTimeEntryMeta.META_USER_NAME, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserTimeEntryMeta.getName(), UserTimeEntryMeta.USER_NAME, userName, this.getUOW());

        // .//GEN-END:userName_3_be
        // Add custom code after a successful validation//GEN-FIRST:userName_2


        // .//GEN-LAST:userName_2
        // .//GEN-BEGIN:userName_4_be
        return userName;
    }
    // .//GEN-END:userName_4_be
    // .//GEN-BEGIN:projectCode_be
    /** Getter for property projectCode.
     * @return Value of property projectCode.
     */
    public java.lang.String getProjectCode() {
        return m_projectCode;
    }
    
    /** Use this method to update the property projectCode.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param projectCode New value of property projectCode.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setProjectCode(java.lang.String projectCode)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_projectCode == null ? projectCode == null : m_projectCode.equals(projectCode))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        projectCode = validateProjectCode(projectCode);
        // .//GEN-END:projectCode_be
        // Add custom code before setting the value//GEN-FIRST:projectCode


        // .//GEN-LAST:projectCode
        // .//GEN-BEGIN:projectCode_1_be
        super.update();
        super.addInitialValue(UserTimeEntryMeta.PROJECT_CODE, m_projectCode);
        m_projectCode = projectCode;
        // .//GEN-END:projectCode_1_be
        // Add custom code after setting the value//GEN-FIRST:projectCode_3


        // .//GEN-LAST:projectCode_3
        // .//GEN-BEGIN:projectCode_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setProjectCode() method.
     * @param projectCode New value of property projectCode.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateProjectCode(java.lang.String projectCode)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setProjectCode(projectCode);
    }

    /** Use this method to validate a value for the property projectCode.
     * @param projectCode Value to be validated for the property projectCode.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public java.lang.String validateProjectCode(java.lang.String projectCode)
    throws ValidationException, FrameworkException {
        // .//GEN-END:projectCode_2_be
        // Add custom code before validation//GEN-FIRST:projectCode_1


        // .//GEN-LAST:projectCode_1
        // .//GEN-BEGIN:projectCode_3_be
        projectCode = FieldValidator.validate(projectCode, (StringFieldMetaData) UserTimeEntryMeta.META_PROJECT_CODE, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserTimeEntryMeta.getName(), UserTimeEntryMeta.PROJECT_CODE, projectCode, this.getUOW());

        // .//GEN-END:projectCode_3_be
        // Add custom code after a successful validation//GEN-FIRST:projectCode_2


        // .//GEN-LAST:projectCode_2
        // .//GEN-BEGIN:projectCode_4_be
        return projectCode;
    }
    // .//GEN-END:projectCode_4_be
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
        super.addInitialValue(UserTimeEntryMeta.ACTIVITY, m_activity);
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
        activity = FieldValidator.validate(activity, (StringFieldMetaData) UserTimeEntryMeta.META_ACTIVITY, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserTimeEntryMeta.getName(), UserTimeEntryMeta.ACTIVITY, activity, this.getUOW());

        // .//GEN-END:activity_3_be
        // Add custom code after a successful validation//GEN-FIRST:activity_2


        // .//GEN-LAST:activity_2
        // .//GEN-BEGIN:activity_4_be
        return activity;
    }
    // .//GEN-END:activity_4_be
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
        super.addInitialValue(UserTimeEntryMeta.TASK, m_task);
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
        task = FieldValidator.validate(task, (StringFieldMetaData) UserTimeEntryMeta.META_TASK, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserTimeEntryMeta.getName(), UserTimeEntryMeta.TASK, task, this.getUOW());

        // .//GEN-END:task_3_be
        // Add custom code after a successful validation//GEN-FIRST:task_2


        // .//GEN-LAST:task_2
        // .//GEN-BEGIN:task_4_be
        return task;
    }
    // .//GEN-END:task_4_be
    // .//GEN-BEGIN:periodStart_be
    /** Getter for property periodStart.
     * @return Value of property periodStart.
     */
    public org.jaffa.datatypes.DateTime getPeriodStart() {
        return m_periodStart;
    }
    
    /** Use this method to update the property periodStart.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param periodStart New value of property periodStart.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPeriodStart(org.jaffa.datatypes.DateTime periodStart)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_periodStart == null ? periodStart == null : m_periodStart.equals(periodStart))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        periodStart = validatePeriodStart(periodStart);
        // .//GEN-END:periodStart_be
        // Add custom code before setting the value//GEN-FIRST:periodStart


        // .//GEN-LAST:periodStart
        // .//GEN-BEGIN:periodStart_1_be
        super.update();
        super.addInitialValue(UserTimeEntryMeta.PERIOD_START, m_periodStart);
        m_periodStart = periodStart;
        // .//GEN-END:periodStart_1_be
        // Add custom code after setting the value//GEN-FIRST:periodStart_3


        // .//GEN-LAST:periodStart_3
        // .//GEN-BEGIN:periodStart_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPeriodStart() method.
     * @param periodStart New value of property periodStart.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePeriodStart(org.jaffa.datatypes.DateTime periodStart)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPeriodStart(periodStart);
    }

    /** Use this method to validate a value for the property periodStart.
     * @param periodStart Value to be validated for the property periodStart.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validatePeriodStart(org.jaffa.datatypes.DateTime periodStart)
    throws ValidationException, FrameworkException {
        // .//GEN-END:periodStart_2_be
        // Add custom code before validation//GEN-FIRST:periodStart_1


        // .//GEN-LAST:periodStart_1
        // .//GEN-BEGIN:periodStart_3_be
        periodStart = FieldValidator.validate(periodStart, (DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_START, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserTimeEntryMeta.getName(), UserTimeEntryMeta.PERIOD_START, periodStart, this.getUOW());

        // .//GEN-END:periodStart_3_be
        // Add custom code after a successful validation//GEN-FIRST:periodStart_2


        // .//GEN-LAST:periodStart_2
        // .//GEN-BEGIN:periodStart_4_be
        return periodStart;
    }
    // .//GEN-END:periodStart_4_be
    // .//GEN-BEGIN:periodEnd_be
    /** Getter for property periodEnd.
     * @return Value of property periodEnd.
     */
    public org.jaffa.datatypes.DateTime getPeriodEnd() {
        return m_periodEnd;
    }
    
    /** Use this method to update the property periodEnd.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param periodEnd New value of property periodEnd.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void setPeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if (m_periodEnd == null ? periodEnd == null : m_periodEnd.equals(periodEnd))
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        periodEnd = validatePeriodEnd(periodEnd);
        // .//GEN-END:periodEnd_be
        // Add custom code before setting the value//GEN-FIRST:periodEnd


        // .//GEN-LAST:periodEnd
        // .//GEN-BEGIN:periodEnd_1_be
        super.update();
        super.addInitialValue(UserTimeEntryMeta.PERIOD_END, m_periodEnd);
        m_periodEnd = periodEnd;
        // .//GEN-END:periodEnd_1_be
        // Add custom code after setting the value//GEN-FIRST:periodEnd_3


        // .//GEN-LAST:periodEnd_3
        // .//GEN-BEGIN:periodEnd_2_be
    }
    
    /** This method is present for backwards compatibility only.
     * It merely invokes the setPeriodEnd() method.
     * @param periodEnd New value of property periodEnd.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        setPeriodEnd(periodEnd);
    }

    /** Use this method to validate a value for the property periodEnd.
     * @param periodEnd Value to be validated for the property periodEnd.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public org.jaffa.datatypes.DateTime validatePeriodEnd(org.jaffa.datatypes.DateTime periodEnd)
    throws ValidationException, FrameworkException {
        // .//GEN-END:periodEnd_2_be
        // Add custom code before validation//GEN-FIRST:periodEnd_1


        // .//GEN-LAST:periodEnd_1
        // .//GEN-BEGIN:periodEnd_3_be
        periodEnd = FieldValidator.validate(periodEnd, (DateTimeFieldMetaData) UserTimeEntryMeta.META_PERIOD_END, true);

        // Invoke the Dynamic Rules Engine
        RulesEngine.doAllValidationsForDomainField(UserTimeEntryMeta.getName(), UserTimeEntryMeta.PERIOD_END, periodEnd, this.getUOW());

        // .//GEN-END:periodEnd_3_be
        // Add custom code after a successful validation//GEN-FIRST:periodEnd_2


        // .//GEN-LAST:periodEnd_2
        // .//GEN-BEGIN:periodEnd_4_be
        return periodEnd;
    }
    // .//GEN-END:periodEnd_4_be
    // .//GEN-BEGIN:toString_1_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<UserTimeEntry>");
        buf.append("<userName>"); if (m_userName != null) buf.append(m_userName); buf.append("</userName>");
        buf.append("<projectCode>"); if (m_projectCode != null) buf.append(m_projectCode); buf.append("</projectCode>");
        buf.append("<activity>"); if (m_activity != null) buf.append(m_activity); buf.append("</activity>");
        buf.append("<task>"); if (m_task != null) buf.append(m_task); buf.append("</task>");
        buf.append("<periodStart>"); if (m_periodStart != null) buf.append(m_periodStart); buf.append("</periodStart>");
        buf.append("<periodEnd>"); if (m_periodEnd != null) buf.append(m_periodEnd); buf.append("</periodEnd>");
        // .//GEN-END:toString_1_be
        // Add custom debug information//GEN-FIRST:toString_1


        // .//GEN-LAST:toString_1
        // .//GEN-BEGIN:toString_2_be
        buf.append(super.toString());
        buf.append("</UserTimeEntry>");
        return buf.toString();
    }
    // .//GEN-END:toString_2_be
    // .//GEN-BEGIN:clone_1_be
    /** Returns a clone of the object.
     * @throws CloneNotSupportedException if cloning is not supported. This should never happen.
     * @return a clone of the object.
     */
    public Object clone() throws CloneNotSupportedException {
        UserTimeEntry obj = (UserTimeEntry) super.clone();
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
