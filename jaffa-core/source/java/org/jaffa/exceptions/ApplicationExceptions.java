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

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import org.jaffa.util.StringHelper;

/** This class is generally thrown by the Transaction controllers. It will contain a collection of ApplicationException objects.
 */
public class ApplicationExceptions extends Exception {

    private List<ApplicationException> m_applicationExceptions = new ArrayList<ApplicationException>();

    /** Constructs a new ApplicationExceptions object.
     */
    public ApplicationExceptions() {
    }

    /** Constructs a new ApplicationExceptions object, adding the input ApplicationException object to the internal collection.
     * @param e An ApplicationException object.
     */
    public ApplicationExceptions(ApplicationException e) {
        m_applicationExceptions.add(e);
    }

    /** Prints this throwable and its backtrace to the standard error stream.
     * Will also print the stacktrace for all the ApplicationException objects in the internal collection.
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /** Prints this throwable and its backtrace to the specified print stream.
     * Will also print the stacktrace for all the ApplicationException objects in the internal collection.
     * @param s PrintStream to use for output.
     */
    public void printStackTrace(PrintStream s) {
        super.printStackTrace(s);
        for (Iterator i = m_applicationExceptions.iterator(); i.hasNext(); )
            ((Throwable) i.next()).printStackTrace(s);
    }

    /** Prints this throwable and its backtrace to the specified print writer.
     * Will also print the stacktrace for all the ApplicationException objects in the internal collection.
     * @param s PrintWriter to use for output.
     */
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
        for (Iterator i = m_applicationExceptions.iterator(); i.hasNext(); )
            ((Throwable) i.next()).printStackTrace(s);
    }

    /** Returns the number of ApplicationException objects in the internal collection.
     * @return the number of ApplicationException objects in the internal collection.
     */
    public int size(){
        return m_applicationExceptions.size();
    }

    /** Returns the iterator on the internal collection of ApplicationException objects.
     * @return the iterator on the internal collection of ApplicationException objects.
     */
    public Iterator iterator(){
        return m_applicationExceptions.iterator();
    }

    /** Adds an ApplicationException object to the internal collection.
     * @param exception the ApplicationException.
     * @return a true if the internal collection changed as a result of the call.
     */
    public boolean add(ApplicationException exception){
        return m_applicationExceptions.add(exception);
    }

    /** Removes an ApplicationException object from the internal collection.
     * @param exception the ApplicationException.
     * @return a true if the internal collection changed as a result of the call.
     */
    public boolean remove(ApplicationException exception){
        return m_applicationExceptions.remove(exception);
    }

    /** Returns an array of ApplicationException objects.
     * @return an array of ApplicationException objects.
     */
    public ApplicationException[] getApplicationExceptionArray() {
        return (ApplicationException[]) m_applicationExceptions.toArray(new ApplicationException[0]);
    }

    /** This is a convenience method to wrap each ApplicationException of this ApplicationExceptions object in an ApplicationExceptionWithContext instance, adding the input context.
     * @param context The context under which the exception was raised. A null value is not permitted.
     */
    public void addContext(String context) {
        for (ApplicationException applicationException : m_applicationExceptions)
            applicationException.addContext(context);
    }

    /** This will return a printable the array of localized messages from all the
     * ApplicationException objects it contains
     * @return the array of message from list of exceptions
     */
    public String getLocalizedMessage() {
        String[] l = new String[m_applicationExceptions.size()];
        for(int i=0;i<m_applicationExceptions.size();i++)
            l[i]=m_applicationExceptions.get(i).getLocalizedMessage();
        return StringHelper.printArray(l);
    }

    /** This will return a printable the array of messages from all the
     * ApplicationException objects it contains
     * @return the array of message from list of exceptions
     */
    public String getMessage() {
        String[] l = new String[m_applicationExceptions.size()];
        for(int i=0;i<m_applicationExceptions.size();i++)
            l[i]=m_applicationExceptions.get(i).getMessage();
        return StringHelper.printArray(l);
    }

}
