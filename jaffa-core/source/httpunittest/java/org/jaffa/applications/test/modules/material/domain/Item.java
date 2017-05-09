// .//GEN-BEGIN:1_be
/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.test.modules.material.domain;

import org.jaffa.datatypes.*;
import org.jaffa.metadata.*;
import org.jaffa.persistence.Persistent;
import org.jaffa.persistence.exceptions.*;
import org.jaffa.exceptions.FrameworkException;

// .//GEN-END:1_be
// Add additional imports//GEN-FIRST:imports




// .//GEN-LAST:imports
// .//GEN-BEGIN:2_be
/**
 * Auto Generated Persistent class for the ITEM table.
 * @author  Auto-Generated
 */
public class Item extends Persistent {

    /** Holds value of property itemId. */
    private java.lang.String m_itemId;

    /** Holds value of property sc. */
    private java.lang.String m_sc;

    /** Holds value of property part. */
    private java.lang.String m_part;

    /** Holds value of property serial. */
    private java.lang.String m_serial;

    /** Holds value of property qty. */
    private java.lang.Double m_qty;


    // .//GEN-END:2_be
    // .//GEN-BEGIN:itemId_be
    /** Getter for property itemId.
     * @return Value of property itemId.
     */
    public java.lang.String getItemId() {
        return m_itemId;
    }
    
    /** Setter for property itemId.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param itemId New value of property itemId.
     */
    public void setItemId(java.lang.String itemId) {
        m_itemId = itemId;
    }
    
    /** Use this method to update the property itemId.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param itemId New value of property itemId.
     * @throws ValidationException if an invalid value is passed.
     * @throws UpdatePrimaryKeyException if this domain object was loaded from the database.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateItemId(java.lang.String itemId)
    throws ValidationException, UpdatePrimaryKeyException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if ( m_itemId == null ? itemId == null : m_itemId.equals(itemId) )
            return;

        // this is part of the primary key.. do not update if its a database occurence.
        if (isDatabaseOccurence())
            throw new UpdatePrimaryKeyException();

        validateItemId(itemId);
        // .//GEN-END:itemId_be
        // Add custom code before setting the value//GEN-FIRST:itemId


        // .//GEN-LAST:itemId
        // .//GEN-BEGIN:itemId_1_be
        super.update();
        setItemId(itemId);
        // .//GEN-END:itemId_1_be
        // Add custom code after setting the value//GEN-FIRST:itemId_3


        // .//GEN-LAST:itemId_3
        // .//GEN-BEGIN:itemId_2_be
    }
    
    /** Use this method to validate a value for the property itemId.
     * @param itemId Value to be validated for the property itemId.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public void validateItemId(java.lang.String itemId)
    throws ValidationException, FrameworkException {
        // .//GEN-END:itemId_2_be
        // Add custom code before validation//GEN-FIRST:itemId_1


        // .//GEN-LAST:itemId_1
        // .//GEN-BEGIN:itemId_3_be
        FieldValidator.validate(itemId, (StringFieldMetaData) ItemMeta.META_ITEM_ID, true);
        // .//GEN-END:itemId_3_be
        // Add custom code after a successful validation//GEN-FIRST:itemId_2


        // .//GEN-LAST:itemId_2
        // .//GEN-BEGIN:itemId_4_be
    }
    // .//GEN-END:itemId_4_be
    // .//GEN-BEGIN:sc_be
    /** Getter for property sc.
     * @return Value of property sc.
     */
    public java.lang.String getSc() {
        return m_sc;
    }
    
    /** Setter for property sc.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param sc New value of property sc.
     */
    public void setSc(java.lang.String sc) {
        m_sc = sc;
    }
    
    /** Use this method to update the property sc.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param sc New value of property sc.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSc(java.lang.String sc)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if ( m_sc == null ? sc == null : m_sc.equals(sc) )
            return;


        validateSc(sc);
        // .//GEN-END:sc_be
        // Add custom code before setting the value//GEN-FIRST:sc


        // .//GEN-LAST:sc
        // .//GEN-BEGIN:sc_1_be
        super.update();
        setSc(sc);
        // .//GEN-END:sc_1_be
        // Add custom code after setting the value//GEN-FIRST:sc_3


        // .//GEN-LAST:sc_3
        // .//GEN-BEGIN:sc_2_be
    }
    
    /** Use this method to validate a value for the property sc.
     * @param sc Value to be validated for the property sc.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public void validateSc(java.lang.String sc)
    throws ValidationException, FrameworkException {
        // .//GEN-END:sc_2_be
        // Add custom code before validation//GEN-FIRST:sc_1


        // .//GEN-LAST:sc_1
        // .//GEN-BEGIN:sc_3_be
        FieldValidator.validate(sc, (StringFieldMetaData) ItemMeta.META_SC, true);
        // .//GEN-END:sc_3_be
        // Add custom code after a successful validation//GEN-FIRST:sc_2


        // .//GEN-LAST:sc_2
        // .//GEN-BEGIN:sc_4_be
    }
    // .//GEN-END:sc_4_be
    // .//GEN-BEGIN:part_be
    /** Getter for property part.
     * @return Value of property part.
     */
    public java.lang.String getPart() {
        return m_part;
    }
    
    /** Setter for property part.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param part New value of property part.
     */
    public void setPart(java.lang.String part) {
        m_part = part;
    }
    
    /** Use this method to update the property part.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param part New value of property part.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updatePart(java.lang.String part)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if ( m_part == null ? part == null : m_part.equals(part) )
            return;


        validatePart(part);
        // .//GEN-END:part_be
        // Add custom code before setting the value//GEN-FIRST:part


        // .//GEN-LAST:part
        // .//GEN-BEGIN:part_1_be
        super.update();
        setPart(part);
        // .//GEN-END:part_1_be
        // Add custom code after setting the value//GEN-FIRST:part_3


        // .//GEN-LAST:part_3
        // .//GEN-BEGIN:part_2_be
    }
    
    /** Use this method to validate a value for the property part.
     * @param part Value to be validated for the property part.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public void validatePart(java.lang.String part)
    throws ValidationException, FrameworkException {
        // .//GEN-END:part_2_be
        // Add custom code before validation//GEN-FIRST:part_1


        // .//GEN-LAST:part_1
        // .//GEN-BEGIN:part_3_be
        FieldValidator.validate(part, (StringFieldMetaData) ItemMeta.META_PART, true);
        // .//GEN-END:part_3_be
        // Add custom code after a successful validation//GEN-FIRST:part_2


        // .//GEN-LAST:part_2
        // .//GEN-BEGIN:part_4_be
    }
    // .//GEN-END:part_4_be
    // .//GEN-BEGIN:serial_be
    /** Getter for property serial.
     * @return Value of property serial.
     */
    public java.lang.String getSerial() {
        return m_serial;
    }
    
    /** Setter for property serial.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param serial New value of property serial.
     */
    public void setSerial(java.lang.String serial) {
        m_serial = serial;
    }
    
    /** Use this method to update the property serial.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param serial New value of property serial.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateSerial(java.lang.String serial)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if ( m_serial == null ? serial == null : m_serial.equals(serial) )
            return;


        validateSerial(serial);
        // .//GEN-END:serial_be
        // Add custom code before setting the value//GEN-FIRST:serial


        // .//GEN-LAST:serial
        // .//GEN-BEGIN:serial_1_be
        super.update();
        setSerial(serial);
        // .//GEN-END:serial_1_be
        // Add custom code after setting the value//GEN-FIRST:serial_3


        // .//GEN-LAST:serial_3
        // .//GEN-BEGIN:serial_2_be
    }
    
    /** Use this method to validate a value for the property serial.
     * @param serial Value to be validated for the property serial.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public void validateSerial(java.lang.String serial)
    throws ValidationException, FrameworkException {
        // .//GEN-END:serial_2_be
        // Add custom code before validation//GEN-FIRST:serial_1


        // .//GEN-LAST:serial_1
        // .//GEN-BEGIN:serial_3_be
        FieldValidator.validate(serial, (StringFieldMetaData) ItemMeta.META_SERIAL, true);
        // .//GEN-END:serial_3_be
        // Add custom code after a successful validation//GEN-FIRST:serial_2


        // .//GEN-LAST:serial_2
        // .//GEN-BEGIN:serial_4_be
    }
    // .//GEN-END:serial_4_be
    // .//GEN-BEGIN:qty_be
    /** Getter for property qty.
     * @return Value of property qty.
     */
    public java.lang.Double getQty() {
        return m_qty;
    }
    
    /** Setter for property qty.
     * WARNING: This is strictly for use by the Persistence Engine. A developer should never use this method. Instead, use the update(field.Name.Upper1) method.
     * @param qty New value of property qty.
     */
    public void setQty(java.lang.Double qty) {
        m_qty = qty;
    }
    
    /** Use this method to update the property qty.
     * This method will do nothing and simply return if the input value is the same as the current value.
     * Validation will be performed on the input value.
     * This will try to lock the underlying database row, in case CAUTIOUS locking is specified at the time of query.
     * @param qty New value of property qty.
     * @throws ValidationException if an invalid value is passed.
     * @throws ReadOnlyObjectException if a Read-Only object is updated.
     * @throws AlreadyLockedObjectException if the underlying database row is already locked by another process.
     * @throws FrameworkException Indicates some system error
     */
    public void updateQty(java.lang.Double qty)
    throws ValidationException, ReadOnlyObjectException, AlreadyLockedObjectException, FrameworkException {
        // ignore, if the current value and new value are the same
        if ( m_qty == null ? qty == null : m_qty.equals(qty) )
            return;


        validateQty(qty);
        // .//GEN-END:qty_be
        // Add custom code before setting the value//GEN-FIRST:qty


        // .//GEN-LAST:qty
        // .//GEN-BEGIN:qty_1_be
        super.update();
        setQty(qty);
        // .//GEN-END:qty_1_be
        // Add custom code after setting the value//GEN-FIRST:qty_3


        // .//GEN-LAST:qty_3
        // .//GEN-BEGIN:qty_2_be
    }
    
    /** Use this method to validate a value for the property qty.
     * @param qty Value to be validated for the property qty.
     * @throws ValidationException if an invalid value is passed
     * @throws FrameworkException Indicates some system error
     */
    public void validateQty(java.lang.Double qty)
    throws ValidationException, FrameworkException {
        // .//GEN-END:qty_2_be
        // Add custom code before validation//GEN-FIRST:qty_1


        // .//GEN-LAST:qty_1
        // .//GEN-BEGIN:qty_3_be
        FieldValidator.validate(qty, (DecimalFieldMetaData) ItemMeta.META_QTY, true);
        // .//GEN-END:qty_3_be
        // Add custom code after a successful validation//GEN-FIRST:qty_2


        // .//GEN-LAST:qty_2
        // .//GEN-BEGIN:qty_4_be
    }
    // .//GEN-END:qty_4_be
    // .//GEN-BEGIN:3_be
    /** This returns the diagnostic information.
    * @return the diagnostic information.
    */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<Item>");
        buf.append("<itemId>"); if (m_itemId != null) buf.append(m_itemId); buf.append("</itemId>");
        buf.append("<sc>"); if (m_sc != null) buf.append(m_sc); buf.append("</sc>");
        buf.append("<part>"); if (m_part != null) buf.append(m_part); buf.append("</part>");
        buf.append("<serial>"); if (m_serial != null) buf.append(m_serial); buf.append("</serial>");
        buf.append("<qty>"); if (m_qty != null) buf.append(m_qty); buf.append("</qty>");
        buf.append(super.toString());
        buf.append("</Item>");
        return buf.toString();
    }

    // .//GEN-END:3_be
    // All the custom code goes here//GEN-FIRST:custom






    // .//GEN-LAST:custom
}
