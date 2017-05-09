package org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui.exceptions;

import org.jaffa.exceptions.FrameworkException;

/** This exception can be thrown when reading or writing the files associated with the DefaultValueEditor component.
 */
public class DefaultValueEditorException extends FrameworkException {
    
    private static final String ERROR_CODE = "exception.org.jaffa.applications.jaffa.modules.admin.components.defaultvalueeditor.ui.exceptions.DefaultValueEditorException";

    /** One of the reasons for throwing this exception */
    public static final String READ_FAILED = "readFailed";
    /** One of the reasons for throwing this exception */
    public static final String WRITE_FAILED = "writeFailed";

    /** Creates an exception with the errorCode.
     * @param subCode The reason for the exception. This should be on of the statics defined in this class.
     */
    public DefaultValueEditorException(String subCode) {
        this(subCode, null);
    }

    /** Creates an exception with the errorCode and a cause.
     * @param subCode The reason for the exception. This should be on of the statics defined in this class.
     * @param arguments the arguments, if any, that need to be merged into the error message from the resource bundle.
     */
    public DefaultValueEditorException(String subCode, Object[] arguments) {
        this(subCode, arguments, null);
    }

    /** Creates an exception with the errorCode and a cause.
     * @param subCode The reason for the exception. This should be on of the statics defined in this class.
     * @param arguments the arguments, if any, that need to be merged into the error message from the resource bundle.
     * @param cause the cause.
     */
    public DefaultValueEditorException(String subCode, Object[] arguments, Throwable cause) {
        super(ERROR_CODE + '.' + subCode, arguments, cause);
    }

}
