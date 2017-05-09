/*
 * IAdditionalDataObject.java
 *
 * Created on Jun 1, 2007, 5:43 PM
 *
 * This Interface should be implemented by the additional data component
 * called from FormSelection when the additional data component needs to
 * create and provide a non-persisted data object to FormSelection and the
 * FormProcessor.
 *
 */

package org.jaffa.modules.printing.components.formselectionmaintenance;

import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

/**
 *
 * @author DennisL
 */

public interface IAdditionalDataObject {

    public void setAdditionalDataObject(java.lang.Object additionalDataObject);

    public java.lang.Object getAdditionalDataObject();

}


