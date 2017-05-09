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

package org.jaffa.exceptions;

import org.jaffa.util.MessageHelper;

/** This is the base class for all Jaffa exceptions.
 */
public abstract class CustomException extends Exception {

    /** Holds value of property arguments. */
    protected Object[] m_arguments;

    /** Creates an exception with the errorCode.
     * @param errorCode the errorCode
     */
    protected CustomException(String errorCode) {
        this(errorCode, null);
    }

    /** Creates an exception with the errorCode and a cause.
     * @param errorCode the errorCode.
     * @param arguments the arguments, if any, that need to be merged into the error message from the resource bundle.
     */
    protected CustomException(String errorCode, Object[] arguments) {
        this(errorCode, arguments, null);
    }

    /** Creates an exception with the errorCode and a cause.
     * @param errorCode the errorCode.
     * @param arguments the arguments, if any, that need to be merged into the error message from the resource bundle.
     * @param cause the cause.
     */
    protected CustomException(String errorCode, Object[] arguments, Throwable cause) {
        super(errorCode, cause);
        m_arguments = arguments;
    }

    /** Getter for property arguments.
     * @return Value of property arguments.
     */
    public Object[] getArguments() {
        return m_arguments;
    }

    /**
     * Sets the arguments.
     * @param arguments the arguments.
     */
    public void setArguments(Object[] arguments) {
        this.m_arguments = arguments;
    }

    /** This will look up the errorCode in the default resource bundle and return an appropriate message.
     * The default resource bundle is specified in the framework.properties.
     * If no message for the corresponding 'errorCode' it found it will returned
     * the error code with the list of arguments for this error.
     * @return the message from the default resource bundle for the errorCode.
     */
    public String getLocalizedMessage() {
        String message = MessageHelper.findMessage(getMessage(), m_arguments);
        if(message == null) {
            message = super.getMessage();
            StringBuilder sb = new StringBuilder(message != null ? message : "");
            if(m_arguments!=null) {
                sb.append(" [ ");
                for(int i=0;i<m_arguments.length;i++) {
                    sb.append(i==0?"":", ");
                    if(m_arguments[i]==null)
                        sb.append("null");
                    else
                        sb.append('"').append(m_arguments[i].toString()).append('"');
                }
                sb.append(" ]");
            }
            return sb.toString();
        } else
            return message;
    }

    /** Returns the message from the base class.
     * @return the message from the base class.
     */
    public String getMessage() {
        return super.getMessage();
    }
}

