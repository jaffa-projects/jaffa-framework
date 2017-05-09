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

package org.jaffa.components.codehelper.dto;

import java.util.*;

/** The output of the ICodeHelper, will consist of a List of instances of this class.
 * An instance will be created corresponding to each domainClassName passed in the input.
 *
 * @author  GautamJ
 */
public class CodeHelperOutElementDto {

    /** Holds value of property code. */
    private String code;

    /** Holds value of property domainClassName. */
    private String domainClassName;

    /** Holds value of property codeHelperOutCodeDtos. */
    private List codeHelperOutCodeDtos;

    /** Creates new CodeHelperInElementDto */
    public CodeHelperOutElementDto() {
    }

    /** Getter for property code.
     * @return Value of property code.
     */
    public String getCode() {
        return code;
    }

    /** Setter for property code.
     * @param code New value of property code.
     */
    public void setCode(String code) {
        this.code = code;
    }

    /** Getter for property domainClassName.
     * @return Value of property domainClassName.
     */
    public String getDomainClassName() {
        return domainClassName;
    }

    /** Setter for property domainClassName.
     * @param domainClassName New value of property domainClassName.
     */
    public void setDomainClassName(String domainClassName) {
        this.domainClassName = domainClassName;
    }


    /** Add a codeHelperOutCodeDto to the list
     * @param codeHelperOutCodeDto A codeHelperOutCodeDto
     */
    public void addCodeHelperOutCodeDto(CodeHelperOutCodeDto codeHelperOutCodeDto) {
        if (codeHelperOutCodeDtos == null)
            codeHelperOutCodeDtos = new ArrayList();
        codeHelperOutCodeDtos.add(codeHelperOutCodeDto);
    }

    /** Add a codeHelperOutCodeDto at the specified position in the list
     * @param codeHelperOutCodeDto A codeHelperOutCodeDto
     * @param index The position in the list
     */
    public void setCodeHelperOutCodeDto(CodeHelperOutCodeDto codeHelperOutCodeDto, int index) {
        if (codeHelperOutCodeDtos == null)
            codeHelperOutCodeDtos = new ArrayList();

        //-- check bounds for index
        if (index < 0 || index > codeHelperOutCodeDtos.size())
            throw new IndexOutOfBoundsException();

        codeHelperOutCodeDtos.set(index, codeHelperOutCodeDto);
    }

    /** Recreate the internal list with the input array of codeHelperOutCodeDto
     * @param codeHelperOutCodeDtos An array of codeHelperOutCodeDto
     */
    public void setCodeHelperOutCodeDtos(CodeHelperOutCodeDto[] codeHelperOutCodeDtos) {
        this.codeHelperOutCodeDtos = Arrays.asList(codeHelperOutCodeDtos);
    }

    /** Clear the list of codeHelperOutCodeDto
     */
    public void clearCodeHelperOutCodeDtos() {
        if (codeHelperOutCodeDtos != null)
            codeHelperOutCodeDtos.clear();
    }

    /** Remove a codeHelperOutCodeDto from the list
     * @param codeHelperOutCodeDto The codeHelperOutCodeDto to be removed
     * @return true if this list contained the specified element
     */
    public boolean removeCodeHelperOutCodeDto(CodeHelperOutCodeDto codeHelperOutCodeDto) {
        if (codeHelperOutCodeDtos != null)
            return codeHelperOutCodeDtos.remove(codeHelperOutCodeDto);
        else
            return false;
    }

    /** Return a codeHelperOutCodeDto at the specified position in the list
     * @param index The position in the list
     * @return The codeHelperOutCodeDto
     */
    public CodeHelperOutCodeDto getCodeHelperOutCodeDto(int index) {
        //-- check bounds for index
        if (codeHelperOutCodeDtos == null || index < 0 || index > codeHelperOutCodeDtos.size())
            throw new IndexOutOfBoundsException();

        return (CodeHelperOutCodeDto) codeHelperOutCodeDtos.get(index);
    }

    /** Returns an array of codeHelperOutCodeDto
     * @return An array of codeHelperOutCodeDto
     */
    public CodeHelperOutCodeDto[] getCodeHelperOutCodeDtos() {
        if (codeHelperOutCodeDtos != null)
            return (CodeHelperOutCodeDto[]) codeHelperOutCodeDtos.toArray(new CodeHelperOutCodeDto[0]);
        else
            return null;
    }

    /** Returns the number of codeHelperOutCodeDto in the list
     * @return The number of codeHelperOutCodeDto in the list
     */
    public int getCodeHelperOutCodeDtoCount() {
        if (codeHelperOutCodeDtos != null)
            return codeHelperOutCodeDtos.size();
        else
            return 0;
    }



    /** Returns diagnostic information.
     * @return diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<CodeHelperOutElementDto>");
        buf.append("<code>"); if (code != null) buf.append(code); buf.append("</code>");
        buf.append("<domainClassName>"); if (domainClassName != null) buf.append(domainClassName); buf.append("</domainClassName>");

        buf.append("<codeHelperOutCodeDtos>");
        if (codeHelperOutCodeDtos != null) {
            for (Iterator i = codeHelperOutCodeDtos.iterator(); i.hasNext(); )
                buf.append(i.next());
        }
        buf.append("</codeHelperOutCodeDtos>");

        buf.append("</CodeHelperOutElementDto>");
        return buf.toString();
    }

}
