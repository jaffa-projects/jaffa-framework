/******************************************************
 * Code Generated From JAFFA Framework Default Pattern
 * 
 * The JAFFA Project can be found at http://jaffa.sourceforge.net
 * and is available under the Lesser GNU Public License
 ******************************************************/ 
package org.jaffa.applications.jaffa.modules.user.components.requestpassword.tx;

import org.apache.log4j.Logger;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;

import org.jaffa.applications.jaffa.modules.user.components.requestpassword.IRequestPassword;


/** Transaction Controller for RequestPassword components.
*/
public class RequestPasswordTx implements IRequestPassword {

    private static Logger log = Logger.getLogger(RequestPasswordTx.class);
    
    /**
     * This should be invoked, when done with the component.
     */
    public void destroy() {
        // do nothing
    }
}
