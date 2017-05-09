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

package org.jaffa.components.menu;

/** This object describes the Options for each Group. The
 * details of the Option are read in from the XML file.
 */
public class Option {
    private String name;
    private String icon;
    private String compURL;
    private boolean URLTrue;

    /** Getter for URLTrue property. URLTrue determines whether
     * this Option will run a Component or a URL.
     * @return True - If Option executes
     */
    public boolean getURLTrue() {
        return URLTrue;
    }

    /** Setter for property URLTrue-defines whether the link to execute is a URL or a Component
     * @param URLTrue True - Link is URL
     * False - Link is Component
     */
    public void setURLTrue(boolean URLTrue) {
        this.URLTrue = URLTrue;
    }

    /** Getter for the property Name - Name of the Option
     * @return String containing Name of the Option
     */
    public String getName(){
        return name;
    }

    /** Setter for the property Name - Name of the Option
     * @param name Name of the Option
     */
    public void setName(String name){
        if (name == null || name.length() == 0)
            this.name = null;
        else
            this.name = name;
    }

    /** Getter for the property Icon - path to the graphics of the Icon
     * @return path to the graphics of the Icon
     */
    public String getIcon(){
        return icon;
    }

    /** Setter for the property Icon - path to the graphics of the Icon
     * @param icon path to the graphics of the Icon
     */
    public void setIcon(String icon){
        if (icon == null || icon.length() == 0)
            this.icon = null;
        else
            this.icon = icon;
    }

    /** Getter for property CompURL - link to be executed for the option. Can be a Component or a URL
     * @return String containing the link to be executed for the option. Can be a Component or a URL.
     */
    public String getCompURL(){
        return compURL;
    }

    /** Getter for property CompURL - link to be executed for the option. Can be a Component or a URL
     * @param compURL Link to be executed for the option. Can be a Component or a URL
     */
    public void setCompURL(String compURL){
        if (compURL == null || compURL.length() == 0)
            this.compURL = null;
        else
            this.compURL = compURL;
    }

    /** Returns the Debug information
     * @return Returns the debug information
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<Option>");
        buf.append("<name>"); if (name != null) buf.append(name); buf.append("</name>");
        buf.append("<icon>"); if (icon != null) buf.append(icon); buf.append("</icon>");
        buf.append("<compURL>"); if (compURL != null) buf.append(compURL); buf.append("</compURL>");
        buf.append("<URLTrue>"); buf.append(URLTrue); buf.append("</URLTrue>");
        buf.append("</Option>");
        return buf.toString();
    }



}
