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


/** This is the base class for all application related exceptions.
 * Any specific application exception should extend this and wrapper a unique error code.
 * <p>
 * This has been change from abstract, as there has been two many abuses of this by using
 * anonymous classes in the form of <code>throw new ApplicationException("message token"){};</code>
 * <p>
 * This was abstract to force developers to create specific exceptions, but as it is being by-passed for
 * simple cases, it would be more efficient to make this non-abstract and avoid the overhead of all these
 * anonymous classes.
 * <p>
 * Also it not possible to use the abstract class approach in a beanshell scripting rule, so it would be
 * useful to use this class directly, ie
 * <p><code><xmp>
<script trigger="validate()">
   if(bean.intoMaintenanceType!=null @and bean.outOfMaintenanceType!=null) {
        throw new RuntimeException( org.jaffa.util.MessageHelper.findMessage("error.WorkRecording.Setup.RulesMaintenance.noIntoOutOf", null) );
   }
</script>
 * </xmp></code><p>
 * could be replaced with
 * <p><code><xmp>
<script trigger="validate()">
   if(bean.intoMaintenanceType!=null @and bean.outOfMaintenanceType!=null) {
        throw new org.jaffa.exceptions.ApplicationException("error.WorkRecording.Setup.RulesMaintenance.noIntoOutOf");
   }
</script>
 * </xmp></code><p>
 */
public class ApplicationException extends CustomException {
    
    private String m_context;

    /** Creates an exception with the errorCode.
     * @param errorCode the errorCode
     */
    public ApplicationException(String errorCode) {
        this(errorCode, null);
    }

    /** Creates an exception with the errorCode and a cause.
     * @param errorCode the errorCode.
     * @param arguments the arguments, if any, that need to be merged into the error message from the resource bundle.
     */
    public ApplicationException(String errorCode, Object[] arguments) {
        this(errorCode, arguments, null);
    }

    /** Creates an exception with the errorCode and a cause.
     * @param errorCode the errorCode.
     * @param arguments the arguments, if any, that need to be merged into the error message from the resource bundle.
     * @param cause the cause.
     */
    public ApplicationException(String errorCode, Object[] arguments, Throwable cause) {
        super(errorCode, arguments, cause);
    }
    
    /**
     * Adds a context to the output returned by the getLocalizedMessage() method.
     * @param context a context.
     */
    public void addContext(String context) {
        m_context = context;
    }

    /**
     * Overrides the method of the base class.
     * If a context was added via the addContext() method, then it'll be added into the output.
     * Else the output from the base class will be returned.
     * @return a localized message.
     */
    @Override
    public String getLocalizedMessage() {
        String message = null;
        if (m_context != null) {
            String errorCode = ApplicationExceptionWithContext.determineErrorCode(m_context, getMessage());
            Object[] arguments = ApplicationExceptionWithContext.determineArguments(m_context, super.getLocalizedMessage(), getArguments());
            message = MessageHelper.findMessage(errorCode, arguments);
        }
        if (message == null)
            message = super.getLocalizedMessage();
        return message;
    }
}
