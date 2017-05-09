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

package org.jaffa.util;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/** Used for parsing CSS style markup into native java structures
 *
 * @author PaulE
 * @version 1.0
 */
public class CssParser {

    private static final Logger log = Logger.getLogger(CssParser.class);
    
    /** Parse a css string of the format 
     * <id> { <key> : <value> ;  <key> : <value>} ...
     * @return Map, where the key is the <id> and the value is a Map of key,value pairs
     */
    public static Map parse(String css) throws ParseException {
        Map m = new LinkedHashMap();
        int errorPos=0;
        while(css!=null&&css.length()>0) {
            int pos = css.indexOf('{');
            if(pos==-1) {
                log.debug("Was expecting a { in " + css);
                break;
            }
            String id=css.substring(0,pos);
            if(id!=null) id=id.trim();
            if(id==null||id.length()==0) {
                String err="Error: Was expecting an <id> near position " + errorPos;
                log.debug(err);
                throw new ParseException(err,errorPos);
            }
            css=css.substring(pos+1);
            errorPos+=pos+1;
            //Now we are parsing out the values
            pos = css.indexOf('}');
            if(pos==-1) {
                String err="Error: Was expecting a matching '}' near position " + errorPos;
                log.debug(err);
                throw new ParseException(err,errorPos);
            }
            Map m2 = new LinkedHashMap();
            String props = css.substring(0,pos);
            int errorPos2=0;
            while(props!=null&&props.length()>0) {
                // Look for the : to get the first key
                int pos2 = props.indexOf(':');
                if(pos2==-1) {
                    String err="Error: Was expecting a ':' near position " + (errorPos+errorPos2);
                    log.debug(err);
                    throw new ParseException(err,(errorPos+errorPos2));
                }
                String key=props.substring(0,pos2);
                if(key!=null) key=key.trim();
                if(key==null||key.length()==0) {
                    String err="Error: Was expecting an <key> name near position " + (errorPos+errorPos2);
                    log.debug(err);
                    throw new ParseException(err,(errorPos+errorPos2));
                }
                props=props.substring(pos2+1);
                errorPos2+=pos2+1;
                //Look for a ; or end of string
                pos2 = props.indexOf(';');
                if(pos2==-1) {
                    String value=props;
                    if(value!=null) value=value.trim();
                    m2.put(key,value);
                    break;
                } else {
                    String value=props.substring(0,pos2);
                    if(value!=null) value=value.trim();
                    m2.put(key,value);
                    props=props.substring(pos2+1);
                    errorPos2+=pos2+1;
                }
            }
            m.put(id,m2);
            css=css.substring(pos+1);
            errorPos+=pos+1;
        }
        return m;
    }
    
    /** Converts a Map generated by the parse() function back into a string
     */
    public static String toString(Map css) {
        StringBuffer s = new StringBuffer();
        if(css!=null)
            for(Iterator it=css.keySet().iterator();it.hasNext();) {
                String id=(String)it.next();
                s.append(id).append("{");
                Map opts=(Map)css.get(id);
                if(opts!=null)
                    for(Iterator it2=opts.keySet().iterator();it2.hasNext();) {
                        String key=(String)it2.next();
                        String value=(String)opts.get(key);
                        s.append(key).append(":");
                        if(value!=null) s.append(value);
                        s.append(";");
                    }
                s.append("}");
            }
        return s.toString();
    }
    
    
    public static void main(String[] args) {
        try {
            String css = "j1_comments{replace:insert;prefix:[label.comments.prefix];suffix:[label.comments.prefix]}";
            String out = toString(parse(css));
            System.out.println("CSS In =" + css);
            System.out.println("CSS Out=" + out);

            css = " a b c { replace : insert ;\n prefix\t:\txxx }a{} bb\n{x:1}";
            out = toString(parse(css));
            System.out.println("CSS In =" + css);
            System.out.println("CSS Out=" + out);
        } catch (ParseException e) {
            // This are valid cases and should not have errors;
            e.printStackTrace();
        }
        
        //Missing id
        try {
            parse("{hello:word}");
            System.out.println("ERROR! Exception Not Thrown");
        } catch (ParseException e) {
            System.out.println("Caught Error: " + e.getMessage());
        }
        
        //No closing }
        try {
            parse("a {hello:word");
            System.out.println("ERROR! Exception Not Thrown");
        } catch (ParseException e) {
            System.out.println("Caught Error: " + e.getMessage());
        }
        
        //Missing key value
        try {
            parse("a {:word}");
            System.out.println("ERROR! Exception Not Thrown");
        } catch (ParseException e) {
            System.out.println("Caught Error: " + e.getMessage());
        }
        
        //No : after key
        try {
            parse("a {hello}");
            System.out.println("ERROR! Exception Not Thrown");
        } catch (ParseException e) {
            System.out.println("Caught Error: " + e.getMessage());
        }
    }
}
