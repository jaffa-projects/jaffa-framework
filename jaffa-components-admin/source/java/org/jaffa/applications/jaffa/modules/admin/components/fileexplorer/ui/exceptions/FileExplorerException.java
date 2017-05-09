package org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.exceptions;

import org.jaffa.exceptions.ApplicationException;

/** This exception can be thrown for various error conditions that may
 * occur while parsing the xml file
 *
 * @author PaulE
 * @version 1.0
 */
public class FileExplorerException extends ApplicationException {

    public static final String ILLEGAL_ACCESS = "exception.org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.exceptions.FileExplorerException.illegalAccess";
    public static final String INVALID_ROOT = "exception.org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.exceptions.FileExplorerException.invalidRoot";
    public static final String ROOT_NOT_FOLDER = "exception.org.jaffa.applications.jaffa.modules.admin.components.fileexplorer.ui.exceptions.FileExplorerException.rootNotFolder";

    /** Create New Instance of the FileExplorerException, passing in no context.
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     */
    public FileExplorerException(String propertyName) {
        this(propertyName, null);
    }

    /** Create New Instance of the FileExplorerException
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameter0 Context to be used when displaying the error, referenced as {0}
     */
    public FileExplorerException(String propertyName, Object parameter0) {
        this(propertyName, new Object[] {parameter0});
    }

    /** Create New Instance of the FileExplorerException
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameter0 Context to be used when displaying the error, referenced as {0}
     * @param parameter1 Context to be used when displaying the error, referenced as {1}
     */
    public FileExplorerException(String propertyName, Object parameter0, Object parameter1) {
        this(propertyName, new Object[] {parameter0, parameter1});
    }

    /** Create New Instance of the FileExplorerException
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameter0 Context to be used when displaying the error, referenced as {0}
     * @param parameter1 Context to be used when displaying the error, referenced as {1}
     * @param parameter2 Context to be used when displaying the error, referenced as {2}
     */
    public FileExplorerException(String propertyName, Object parameter0, Object parameter1, Object parameter2) {
        this(propertyName, new Object[] {parameter0, parameter1, parameter2});
    }

    /** Create New Instance of the FileExplorerException, passing in an array of context
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameter0 Context to be used when displaying the error, referenced as {0}
     * @param parameter1 Context to be used when displaying the error, referenced as {1}
     * @param parameter2 Context to be used when displaying the error, referenced as {2}
     * @param parameter3 Context to be used when displaying the error, referenced as {3}
     */
    public FileExplorerException(String propertyName, Object parameter0, Object parameter1, Object parameter2, Object parameter3) {
        this(propertyName, new Object[] {parameter0, parameter1, parameter2, parameter3});
    }

    /** Create New Instance of the FileExplorerException
     * @param propertyName Type of Error to be raised, See the Static Values for this class
     * @param parameters Context to be used when displaying the error. Each element in the array is referened as {0},{1},..
     */
    public FileExplorerException(String propertyName, Object[] parameters) {
        super(propertyName, parameters);
    }
}
