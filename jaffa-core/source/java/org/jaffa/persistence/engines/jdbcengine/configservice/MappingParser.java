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

package org.jaffa.persistence.engines.jdbcengine.configservice;

import org.apache.log4j.Logger;
import java.util.*;
import org.jaffa.datatypes.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/** This is the handler for the mapping files. It parses the file and creates an appropriate ClassMetaData object.
 */
public class MappingParser extends DefaultHandler {
    private static final Logger log = Logger.getLogger(MappingParser.class);
    private static final String CLASS = "class";
    private static final String NAME = "name";
    private static final String FIELD = "field";
    private static final String USE_MEMBER = "use-member";
    private static final String PRIMARY_KEY = "primary-key";
    private static final String AUTOGEN_KEY = "autogen-key";
    private static final String TYPE = "type";
    private static final String MAP_TO = "map-to";
    private static final String TABLE = "table";
    private static final String LOCKABLE = "lockable";
    private static final String SQL = "sql";
    private static final String RPAD = "rpad";
    
    private ClassMetaData m_meta;
    private String m_fieldName;
    
    
    /** Receive notification of the start of an element.
     * @param uri The uri.
     * @param sName The local name (without prefix), or the empty string if Namespace processing is not being performed.
     * @param qName The qualified name (with prefix), or the empty string if qualified names are not available.
     * @param atts The specified or defaulted attributes
     */
    public void startElement(String uri, String sName, String qName, Attributes atts) {
        if(sName.equals(CLASS)) {
            m_meta = new ClassMetaData(atts.getValue(NAME));
        } else if(sName.equals(FIELD)) {
            m_fieldName = atts.getValue(NAME);
            if ((atts.getValue(PRIMARY_KEY)) != null) {
                Boolean bool = null;
                bool = Parser.parseBoolean(atts.getValue(PRIMARY_KEY));
                if(bool != null && bool.booleanValue()) {
                    bool = Parser.parseBoolean(atts.getValue(AUTOGEN_KEY));
                    m_meta.addKeyField(m_fieldName, atts.getValue(TYPE), (bool != null ? bool.booleanValue() : false));
                } else
                    m_meta.addAttribute(m_fieldName, atts.getValue(TYPE));
            } else {
                m_meta.addAttribute(m_fieldName, atts.getValue(TYPE));
            }
            String memberName = atts.getValue(USE_MEMBER);
            if (memberName != null)
                m_meta.addMember(m_fieldName, memberName);
        } else if(sName.equals(MAP_TO)) {
            m_meta.setTable(atts.getValue(TABLE));
            if (atts.getValue(LOCKABLE) != null)
                m_meta.setLockable(Parser.parseBoolean(atts.getValue(LOCKABLE)).booleanValue());
        } else if(sName.equals(SQL)) {
            m_meta.addSqlName(m_fieldName, atts.getValue(NAME));
            m_meta.addSqlType(m_fieldName, atts.getValue(TYPE).toUpperCase());
            if (atts.getValue(RPAD) != null)
                m_meta.addRpad(m_fieldName, Integer.valueOf(atts.getValue(RPAD)));
        }
    }
    
    
    /** Returns a ClassMetaData object corresponding to a mapping file.
     * @return a ClassMetaData object corresponding to a mapping file.
     */
    public ClassMetaData getMetaData() {
        return m_meta;
    }
}

