/*
 * ====================================================================
 * JAFFA - Java Application Framework For All
 *
 * Copyright (C) 2002 JAFFA Development Group
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * Redistribution and use of this software and associated documentation ("Software"),
 * with or without modification, are permitted provided that the following conditions are met:
 * 1.	Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.	Redistributions in binary form must reproduce the above copyright notice,
 * 	this list of conditions and the following disclaimer in the documentation
 * 	and/or other materials provided with the distribution.
 * 3.	The name "JAFFA" must not be used to endorse or promote products derived from
 * 	this Software without prior written permission. For written permission,
 * 	please contact mail to: jaffagroup@yahoo.com.
 * 4.	Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 * 	appear in their names without prior written permission.
 * 5.	Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.jaffa.soa.graph;

import org.jaffa.datatypes.Formatter;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.soa.events.Param;
import org.jaffa.util.BeanHelper;
import org.jaffa.util.ExceptionHelper;

/** This class can be used for encapsulating the error message(s) of an Exception.
 * It has been provided for use in WebServices (and possibly JMS), so as to avoid the headache of mapping custom Exceptions to WSDL.
 * The WebService is expected to wrap its regular output along with an instance of ServiceError.
 */
public class ServiceError {

    private String type;
    private String message;
    private String localizedMessage;
    private String[] arguments;
    private Param[] params;

    /**
     * Generates one ore more instances to encapsulate the input cause.
     * @param cause The exception whose error messages(s) are to be encapsulated.
     * @return one ore more instances to encapsulate the input cause.
     */
    public static ServiceError[] generate(Throwable cause) {
        ServiceError[] output = null;
        try {
            ExceptionHelper.throwAF(cause);
            output = new ServiceError[]{createInstance(cause)};
        } catch (FrameworkException e) {
            output = new ServiceError[]{createInstance(e)};
        } catch (ApplicationExceptions e) {
            ApplicationException[] appExps = e.getApplicationExceptionArray();
            output = new ServiceError[appExps.length];
            for (int i = 0; i < appExps.length; i++)
                output[i] = createInstance(appExps[i]);
        }
        return output;
    }

    /**
     * Getter for property type.
     * Typically contains the fully qualified name of the original Exception class,
     * from which this error was created.
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for property type.
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for property message.
     * Typically contains the message from the original Exception class,
     * from which this error was created.
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for property message.
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for property localizedMessage.
     * Typically contains the localizedMessage from the original Exception class,
     * from which this error was created.
     * @return the localizedMessage
     */
    public String getLocalizedMessage() {
        return localizedMessage;
    }

    /**
     * Setter for property localizedMessage.
     * @param localizedMessage the localizedMessage to set
     */
    public void setLocalizedMessage(String localizedMessage) {
        this.localizedMessage = localizedMessage;
    }

    /**
     * Getter for property arguments.
     * Typically contains the arguments from the original Exception class,
     * from which this error was created.
     * @return the arguments
     */
    public String[] getArguments() {
        return arguments;
    }

    /**
     * Setter for property arguments.
     * @param arguments the arguments to set
     */
    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    /**
     * Getter for property params.
     * Typically contains the params from the original Exception class,
     * from which this error was created.
     * @return the params
     */
    public Param[] getParams() {
        return params;
    }

    /**
     * Setter for property params.
     * @param params the params to set
     */
    public void setParams(Param[] params) {
        this.params = params;
    }

    /**
     * Returns diagnostic information.
     * @return diagnostic information.
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("<ServiceError>");
        if (getType() != null)
            buf.append("<type>").append(getType()).append("</type>");
        if (getMessage() != null)
            buf.append("<message>").append(getMessage()).append("</message>");
        if (getLocalizedMessage() != null)
            buf.append("<localizedMessage>").append(getLocalizedMessage()).append("</localizedMessage>");
        if (getArguments() != null) {
            for (String argument : getArguments())
                buf.append("<argument>").append(argument).append("</argument>");
        }
        if (getParams() != null) {
            for (Param param : getParams())
                buf.append(param);
        }
        buf.append("</ServiceError>");
        return buf.toString();
    }

    /**
     * Creates an instance to encapsulate the input cause.
     * @param cause The exception to be encapsulated.
     * @return an instance that encapsulates the input cause.
     */
    private static ServiceError createInstance(Throwable cause) {
        ServiceError inst = new ServiceError();
        inst.setType(cause.getClass().getName());
        inst.setMessage(cause.getMessage());
        inst.setLocalizedMessage(cause.getLocalizedMessage());

        // Stamp the arguments
        try {
            Object[] arguments = (Object[]) BeanHelper.getField(cause, "arguments");
            if (arguments != null && arguments.length > 0) {
                String[] strArgs = new String[arguments.length];
                for (int i = 0; i < arguments.length; i++)
                    strArgs[i] = Formatter.format(arguments[i]);
                inst.setArguments(strArgs);
            }
        } catch (Exception ignore) {
            // do nothing, since the cause exception may not have the 'public Object[] getArguments()' method
        }

        // Stamp the params
        try {
            inst.setParams((Param[]) BeanHelper.getField(cause, "params"));
        } catch (Exception ignore) {
            // do nothing, since the cause exception may not have the 'public Param[] getParams()' method
        }
        return inst;
    }
}
