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

/** An application can use this class to wrap an ApplicationException and add a context to it.
 * This is useful when processing collections of data. The context can be used to pinpoint the data-set which encountered errors.
 * It is recommended to use the dot-notation for the context.
 * For eg: If we were processing a User graph, then the context can be of the types:
 *    users.0 (The source exception was raised while processing the 1st user record)
 *    users.1.userroles.2 (The source exception was raised while processing the 3rd userrole of the 2nd user record)
 */
public class ApplicationExceptionWithContext extends ApplicationException {

    /** This is used to locate the error message for the ApplicationExceptionWithContext. */
    private static final String ERROR_CODE = "exception.org.jaffa.exceptions.ApplicationExceptionWithContext.messageWithContext";

    /** Constructs a new ApplicationExceptionWithContext.
     * @param context the context under which the cause exception was raised.
     * @param cause the cause (which is saved for later retrieval by the Throwable.getCause() method). A null value is not permitted.
     */
    public ApplicationExceptionWithContext(String context, ApplicationException cause) {
        super(determineErrorCode(context, cause.getMessage()), determineArguments(context, cause.getLocalizedMessage(), cause.getArguments()), cause);
    }

    /** Returns the default ERROR_CODE.
     * However if the context is null, the errorCode of the cause is returned.
     * @param context the context under which the cause exception was raised.
     * @return the default ERROR_CODE.
     */
    static String determineErrorCode(String context, String originalErrorCode) {
        return context != null ? ERROR_CODE : originalErrorCode;
    }

    /** Returns an argument array containing the context and the localizedMessage from the cause.
     * However if the context is null, the arguments array of the cause is returned.
     * @param context the context under which the cause exception was raised.
     * @return an argument array containing the context and the localizedMessage from the cause.
     */
    static Object[] determineArguments(String context, String originalLocalizedMessage, Object[] originalArguments) {
        if (context != null) {
            // Many a times the context may contain array indicators, which could 
            // be misleading since the square-brackets are used to reference other tokens.
            // Hence escape the square-brackets which surround digits.
            // For example, transform 'A[0].B[1]' to 'A\[0\].B\[1\]'
            return new Object[]{context.replaceAll("\\[(\\d+)\\]", "\\\\[$1\\\\]"), originalLocalizedMessage};
        } else
            return originalArguments;
    }
}
