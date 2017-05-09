/*
 * ====================================================================
 * JAFFA - Java Application Framework For Aerospace
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
 * 1.   Redistributions of source code must retain copyright statements and notices.
 *      Redistributions must also contain a copy of this document.
 * 2.   Redistributions in binary form must reproduce the above copyright notice,
 *      this list of conditions and the following disclaimer in the documentation
 *      and/or other materials provided with the distribution.
 * 3.   The name "JAFFA" must not be used to endorse or promote products derived from
 *      this Software without prior written permission. For written permission,
 *      please contact mail to: jaffagroup@yahoo.com.
 * 4.   Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *      appear in their names without prior written permission.
 * 5.   Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
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
package org.jaffa.dwr.converters;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.convert.BaseV20Converter;
import org.directwebremoting.dwrp.ProtocolConstants;
import org.directwebremoting.dwrp.SimpleOutboundVariable;
import org.directwebremoting.extend.Converter;
import org.directwebremoting.extend.InboundContext;
import org.directwebremoting.extend.InboundVariable;
import org.directwebremoting.extend.MarshallException;
import org.directwebremoting.extend.OutboundContext;
import org.directwebremoting.extend.OutboundVariable;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.DateTime;
import org.jaffa.datatypes.Parser;
import org.jaffa.datatypes.exceptions.FormatDateTimeException;
import org.jaffa.session.ContextManagerFactory;

/**
 * An implementation of Converter for Dates.
 * @author Joe Walker [joe at eireneh dot com]
 * @version $Id: JaffaDateConverter.java,v 1.2 2008-01-17 17:22:17 ut_paule Exp $
 */
public class JaffaDateConverter extends BaseV20Converter implements Converter {

    private static final Logger log = Logger.getLogger(JaffaDateConverter.class);
    private static final String RULE_NAME_USE_SERVER_TIME = "jaffa.widgets.useServerTime";

    // The following format is used to marshal dates between server and client.
    // It'll also be used to store date values in string fields.
    // NOTE: It is important to add a character, say 'T', to the formatted string, so that it
    // is not treated as a numeric 'milliseconds since 1970' value.
    private static final String FORMAT_DATE_TIME = "yyyyMMdd'T'HHmmssSSS";
    private static final String CHAR_ENCODER = "UTF-8";

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertInbound(java.lang.Class, org.directwebremoting.InboundVariable, org.directwebremoting.InboundContext)
     */
    public Object convertInbound(Class paramType, InboundVariable iv, InboundContext inctx) throws MarshallException {
        // Error out if an unsupported class is passed
        if (paramType != DateOnly.class && paramType != DateTime.class) {
            log.warn("Unsupported input. Class=" + paramType);
            throw new MarshallException(paramType);
        }

        //Extract Char Encoding from the Web Context
        String charEncoding = null;
        WebContext context = WebContextFactory.get();
        if(context!=null) {
        	HttpServletRequest req = context.getHttpServletRequest();
        	if(req!=null)
        		charEncoding = req.getCharacterEncoding();
        }
        if(charEncoding==null)
        	charEncoding = CHAR_ENCODER;
        
        String value = iv.getValue();

        // If the text is null then the whole bean is null
        if (value.trim().equals(ProtocolConstants.INBOUND_NULL))
            return null;

        Object output;
        try {
            // Handle a millisecond input
            long millis = 0;
            if (value.length() > 0)
                millis = Long.parseLong(value);

            //DWR returns null dates as '0', so we must return a null in this case
            if (millis == 0)
                return null;

            Boolean useServerTime = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty(RULE_NAME_USE_SERVER_TIME));
            if (useServerTime != null && useServerTime) {
                if (log.isInfoEnabled())
                    log.info("A String representation of a date should be posted by the client, since the application rule '" + RULE_NAME_USE_SERVER_TIME + "' is set");
            }
            output = paramType == DateOnly.class ? new DateOnly(millis) : new DateTime(millis);
        } catch (NumberFormatException e) {
            try {
                // Handle a String input
                if (log.isDebugEnabled())
                    log.debug("Error in parsing '" + value + "' as milliseconds since 1970. Will attempt to parse it as a String representation of a date");
                output = paramType == DateOnly.class ? DateTime.toDateOnly(Parser.parseDateTime(URLDecoder.decode(value,charEncoding), FORMAT_DATE_TIME)) : Parser.parseDateTime(URLDecoder.decode(value,charEncoding), FORMAT_DATE_TIME);
            } catch (FormatDateTimeException e1) {
                if (log.isDebugEnabled())
                    log.debug("Error in parsing Date '" + value + "' using the format '" + FORMAT_DATE_TIME + '\'', e1);
                throw new MarshallException(DateOnly.class, e1);
            } catch (UnsupportedEncodingException e1) {
                if (log.isDebugEnabled())
                    log.debug("Error in encoding Date '" + value + "' using the format '" + charEncoding + '\'', e1);
                throw new MarshallException(DateOnly.class, e1);
            }
        }
        if (log.isDebugEnabled())
            log.debug("Inbound '" + value + "' converted to '" + output + '\'');
        return output;
    }

    /* (non-Javadoc)
     * @see org.directwebremoting.Converter#convertOutbound(java.lang.Object, org.directwebremoting.OutboundContext)
     */
    public OutboundVariable convertOutbound(Object data, OutboundContext outctx) throws MarshallException {
        // Error out if an unsupported class is passed
        if (!(data instanceof DateOnly) && !(data instanceof DateTime)) {
            log.warn("Unsupported input. Class=" + data.getClass() + ", data=" + data);
            throw new MarshallException(data.getClass());
        }

        // Create a javascipt Date object.
        // When using server time, Pass the individual date elements. This will ensure that a similar date will be constructed in the client's timezone.
        // When not using server time, pass the millisecond since 1970 value. This will result in a date adjusted to the client's timezone.
        StringBuilder jsDateConstructor = new StringBuilder("new Date(");
        Boolean useServerTime = Parser.parseBoolean((String) ContextManagerFactory.instance().getProperty(RULE_NAME_USE_SERVER_TIME));
        if (data instanceof DateOnly) {
            DateOnly d = (DateOnly) data;
            if (useServerTime != null && useServerTime)
                jsDateConstructor.append(d.year()).append(',').append(d.month()).append(',').append(d.day());
            else
                jsDateConstructor.append(d.timeInMillis());
        } else {
            DateTime d = (DateTime) data;
            if (useServerTime != null && useServerTime)
                jsDateConstructor.append(d.year()).append(',').append(d.month()).append(',').append(d.day()).append(',').append(d.hourOfDay()).append(',').append(d.minute()).append(',').append(d.second()).append(',').append(d.milli());
            else
                jsDateConstructor.append(d.timeInMillis());
        }
        String output = jsDateConstructor.append(')').toString();
        if (log.isDebugEnabled())
            log.debug("Outbound '" + data + "' converted to '" + output + '\'');
        return new SimpleOutboundVariable(output, outctx, true);
    }
}
