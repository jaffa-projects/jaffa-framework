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
import org.jaffa.components.dto.HeaderDto;

/** The is the input to the ICodeHelper. It will contain one or more CodeHelperInElementDto objects.
 * In turn, each CodeHelperInElementDto may contain zero or more CriteriaField objects, for specifying additional criteria.
 *
 * @author  GautamJ
 */
public class CodeHelperInDto {

    /** Holds value of property headerDto. */
    private HeaderDto headerDto;

    /** Holds value of property codeHelperInElementDtos. */
    private List codeHelperInElementDtos;

    /** Creates new CodeHelperInElementDto */
    public CodeHelperInDto() {
    }



    /** Getter for property headerDto.
     * @return Value of property headerDto.
     */
    public HeaderDto getHeaderDto() {
        return headerDto;
    }

    /** Setter for property headerDto.
     * @param headerDto New value of property headerDto.
     */
    public void setHeaderDto(HeaderDto headerDto) {
        this.headerDto = headerDto;
    }

    /** Add a codeHelperInElementDto to the list
     * @param codeHelperInElementDto A codeHelperInElementDto
     */
    public void addCodeHelperInElementDto(CodeHelperInElementDto codeHelperInElementDto) {
        if (codeHelperInElementDtos == null)
            codeHelperInElementDtos = new ArrayList();
        codeHelperInElementDtos.add(codeHelperInElementDto);
    }

    /** Add a codeHelperInElementDto at the specified position in the list
     * @param codeHelperInElementDto A codeHelperInElementDto
     * @param index The position in the list
     */
    public void setCodeHelperInElementDto(CodeHelperInElementDto codeHelperInElementDto, int index) {
        if (codeHelperInElementDtos == null)
            codeHelperInElementDtos = new ArrayList();

        //-- check bounds for index
        if (index < 0 || index > codeHelperInElementDtos.size())
            throw new IndexOutOfBoundsException();

        codeHelperInElementDtos.set(index, codeHelperInElementDto);
    }

    /** Recreate the internal list with the input array of codeHelperInElementDto
     * @param codeHelperInElementDtos An array of codeHelperInElementDto
     */
    public void setCodeHelperInElementDtos(CodeHelperInElementDto[] codeHelperInElementDtos) {
        this.codeHelperInElementDtos = Arrays.asList(codeHelperInElementDtos);
    }

    /** Clear the list of codeHelperInElementDto
     */
    public void clearCodeHelperInElementDtos() {
        if (codeHelperInElementDtos != null)
            codeHelperInElementDtos.clear();
    }

    /** Remove a codeHelperInElementDto from the list
     * @param codeHelperInElementDto The codeHelperInElementDto to be removed
     * @return true if this list contained the specified element
     */
    public boolean removeCodeHelperInElementDto(CodeHelperInElementDto codeHelperInElementDto) {
        if (codeHelperInElementDtos != null)
            return codeHelperInElementDtos.remove(codeHelperInElementDto);
        else
            return false;
    }

    /** Return a codeHelperInElementDto at the specified position in the list
     * @param index The position in the list
     * @return The codeHelperInElementDto
     */
    public CodeHelperInElementDto getCodeHelperInElementDto(int index) {
        //-- check bounds for index
        if (codeHelperInElementDtos == null || index < 0 || index > codeHelperInElementDtos.size())
            throw new IndexOutOfBoundsException();

        return (CodeHelperInElementDto) codeHelperInElementDtos.get(index);
    }

    /** Returns an array of codeHelperInElementDto
     * @return An array of codeHelperInElementDto
     */
    public CodeHelperInElementDto[] getCodeHelperInElementDtos() {
        if (codeHelperInElementDtos != null)
            return (CodeHelperInElementDto[]) codeHelperInElementDtos.toArray(new CodeHelperInElementDto[0]);
        else
            return null;
    }

    /** Returns the number of codeHelperInElementDto in the list
     * @return The number of codeHelperInElementDto in the list
     */
    public int getCodeHelperInElementDtoCount() {
        if (codeHelperInElementDtos != null)
            return codeHelperInElementDtos.size();
        else
            return 0;
    }


    /** Returns diagnostic information.
     * @return diagnostic information.
     */
    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("<CodeHelperInDto>");
        buf.append("<headerDto>"); if (headerDto != null) buf.append( headerDto.toString() ); buf.append("</headerDto>");
        buf.append("<codeHelperInElementDtos>");
        if (codeHelperInElementDtos != null) {
            for (Iterator i = codeHelperInElementDtos.iterator(); i.hasNext(); )
                buf.append(i.next());
        }
        buf.append("</codeHelperInElementDtos>");
        buf.append("</CodeHelperInDto>");
        return buf.toString();
    }

}
