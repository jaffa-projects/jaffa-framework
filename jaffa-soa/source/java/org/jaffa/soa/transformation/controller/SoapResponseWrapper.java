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

/*
 * SoapResponseWrapper.java
 *
 */
package org.jaffa.soa.transformation.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;


/**
 * The custom wrapper for for HttpServlerReponse.
 *
 * <p>
 * The custom response wrapper is getting created and passed
 * to the chain. The custom response wraps the soap message
 * from internal service.
 *
 * @author SaravananN
 *
 */
public class SoapResponseWrapper extends HttpServletResponseWrapper {

    private static Logger log = Logger.getLogger(SoapResponseWrapper.class);
    private StringWriter stringWriter;

    /** Initializes wrapper.
     *  <P>
     *  First, this constructor calls the parent
     *  constructor. That call is crucial so that the response
     *  is stored and thus setHeader, setStatus, addCookie,
     *  and so forth work normally.
     *  <P>
     *  Second, this constructor creates a StringWriter
     *  that will be used to accumulate the response.
     *
     *  @param response. The HttpServletResponse.
     */
    public SoapResponseWrapper(HttpServletResponse response) {
        super(response);
        stringWriter = new StringWriter();
        if (log.isDebugEnabled()) {
            log.debug("In SoapResponseWrapper: " + stringWriter.toString());
        }

    }

    /** When servlets or JSP pages ask for the Writer,
     *  don't give them the real one. Instead, give them
     *  a version that writes into the StringBuffer.
     *  The filter needs to send the contents of the
     *  buffer to the client (usually after modifying it).
     *
     *  @return PrintWriter.
     */
    @Override
    public PrintWriter getWriter() {
        return (new PrintWriter(stringWriter));
    }

    /** Similarly, when resources call getOutputStream,
     * give them a phony output stream that just
     * buffers up the output.
     *
     * @return ServletOutputStream.
     */
    @Override
    public ServletOutputStream getOutputStream() {
        ServletOutputStream sos = new StringOutputStream(stringWriter);
        if (log.isDebugEnabled()) {
            log.debug("In Outputstream :" + stringWriter.toString());
        }
        return sos;
    }

    /** Get a String representation of the entire buffer.
     *  <P>
     *  Be sure <I>not</I> to call this method multiple times
     *  on the same wrapper. The API for StringWriter
     *  does not guarantee that it "remembers" the previous
     *  value, so the call is likely to make a new String
     *  every time.
     *
     *  @return String.
     */
    @Override
    public String toString() {
        return (stringWriter.toString());
    }


    /**
     * Get the underlying StringBuffer
     *
     * @return StringBuffer.
     */
    public StringBuffer getBuffer() {
        return (stringWriter.getBuffer());
    }

    private static class StringOutputStream extends ServletOutputStream {

        private StringWriter stringWriter;

        /**
         * @param stringWriter
         */
        public StringOutputStream(StringWriter stringWriter) {
            this.stringWriter = stringWriter;
        }

        @Override
        public void write(int c) {
            stringWriter.write(c);
        }
    }
}
