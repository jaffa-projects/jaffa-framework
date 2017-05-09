/*
 * FormSelectionException.java
 *
 */
package org.jaffa.modules.printing.components.formselectionmaintenance;

import org.jaffa.datatypes.ValidationException;

/**
 *
 * @author  Vanraj Dav
 */
public class FormSelectionException extends ValidationException {

    public static final String INVALID_COPIES =
            "exception.org.jaffa.modules.printing.components.exceptions.FormSelectionException.InvalidCopies";
    public static final String INVALID_OUTPUT_DESTINATION =
            "exception.org.jaffa.modules.printing.components.exceptions.FormSelectionException.InvalidOutputDestination";
    public static final String INVALID_ADDITIONALCOMPONENT =
            "exception.org.jaffa.modules.printing.components.exceptions.FormSelectionException.InvalidAdditionalComponent";
    public static final String ADDITIONALDATA_NOTFOUND =
            "exception.org.jaffa.modules.printing.components.exceptions.FormSelectionException.AdditionalDataNotFound";
    public static final String NO_FORM_DEFINED_FOR_EVENT =
            "exception.org.jaffa.modules.printing.components.exceptions.FormSelectionException.NoFormDefinedForEvent";
    public static final String FORM_PREVIEW_EVENT_NOT_DEFINED =
            "exception.org.jaffa.modules.printing.components.exceptions.FormSelectionException.FormPreviewEventNotDefined";
    public static final String SECURITY_EXCEPTION =
            "exception.org.jaffa.modules.printing.components.exceptions.FormSelectionException.SecurityException";

    /** Creates an exception with the errorCode.
     * @param errorCode the errorCode
     */
    public FormSelectionException(String errorCode) {
        this(errorCode, null);
    }

    /** Creates an exception with the errorCode.
     * @param errorCode the errorCode
     * @param field the field on which the validation exception is being thrown. This can be set later too.
     */
    public FormSelectionException(String errorCode, String field) {
        this(errorCode, field, null);
    }

    /** Creates an exception with the errorCode and a cause.
     * This will merge the 'field' and the 'arguments' into a single Object array and use that for constructing the message. Hence, it is imperative that the error message has the field as the first argument.
     * @param errorCode the errorCode.
     * @param field the field on which the validation exception is being thrown. This can be set later too.
     * @param arguments the arguments, if any, that need to be merged into the error message from the resource bundle.
     */
    public FormSelectionException(String errorCode, String field, Object[] arguments) {
        super(errorCode, field, arguments, null);
    }
}
