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

package org.jaffa.components.codehelper.tx;

import org.apache.log4j.Logger;
import org.jaffa.components.codehelper.ICodeHelper;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.components.codehelper.dto.CodeHelperInDto;
import org.jaffa.components.codehelper.dto.CodeHelperInElementDto;
import org.jaffa.components.codehelper.dto.CodeHelperOutDto;
import org.jaffa.components.codehelper.dto.CodeHelperOutElementDto;
import org.jaffa.components.codehelper.dto.CodeHelperOutCodeDto;
import org.jaffa.components.codehelper.dto.CriteriaElementDto;
import org.jaffa.components.finder.*;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;
import org.jaffa.util.BeanHelper;
import java.util.Iterator;
import org.jaffa.persistence.AtomicCriteria;

/** This is the default implementation of the ICodeHelper interface. It will retrieve codes for each domainClassName passed in the input.
 * It will use reflection to determine the fields to return. Additional criteria can also be passed in the input, to restrict the output.
 * For complex queries, create custom implementations of the interface, rather than making this generic implementation complex !!!
 *
 * @author  GautamJ
 */
public class CodeHelperTx implements ICodeHelper {

    private static final Logger log = Logger.getLogger(CodeHelperTx.class);
    private static final String DEFAULT_APPEND_BEGIN_MARKER = " (";
    private static final String DEFAULT_APPEND_END_MARKER = ")";

    /**
     * This should be invoked, when done with the helper. This will ensure that all the acquired resources are released.
     */
    public void destroy() {
        // do nothing
    }

    /**
     * Retrieves the Codes for the specified domainClassName passed in the input.
     * @param input The input dto, containing a list of domainClass to retrieve.
     * @return The Codes for each domainClassName passed in the input.
     * @throws ApplicationExceptions This will be thrown if any invalid data is passed.
     * @throws FrameworkException Indicates some system error.
     */
    public CodeHelperOutDto getCodes(CodeHelperInDto input)
    throws ApplicationExceptions, FrameworkException {
        UOW uow = null;
        try {
            // Print Debug Information for the input
            if (log.isDebugEnabled()) {
                log.debug("Input: " + (input != null ? input.toString() : null));
            }

            // Create the output
            CodeHelperOutDto output = new CodeHelperOutDto();

            // create the UOW
            uow = new UOW();

            if (input != null && input.getCodeHelperInElementDtoCount() > 0) {
                CodeHelperInElementDto[] inputElements = input.getCodeHelperInElementDtos();
                for (int i = 0; i < inputElements.length; i++)
                    output.addCodeHelperOutElementDto( processInputElement(uow, inputElements[i]) );
            }

            // Print Debug Information for the output
            if (log.isDebugEnabled()) {
                log.debug("Output: " + (output != null ? output.toString() : null));
            }
            return output;
        } finally {
            if (uow != null)
                uow.rollback();
        }
    }




    private CodeHelperOutElementDto processInputElement(UOW uow, CodeHelperInElementDto inputElement)
    throws ApplicationExceptions, FrameworkException {
        Criteria c = new Criteria();
        c.setTable(inputElement.getDomainClassName());
        if (inputElement.getCriteriaFieldCount() > 0) {
            CriteriaElementDto[] criteriaFields = inputElement.getCriteriaFields();
            for (int i = 0; i < criteriaFields.length; i++) {
                CriteriaElementDto criteriaField = criteriaFields[i];
                addCriteria(criteriaField, c);
            }
        }
        c.addOrderBy(inputElement.getCodeFieldName(), Criteria.ORDER_BY_ASC);

        Iterator i = uow.query(c).iterator();
        CodeHelperOutElementDto outputElement = new CodeHelperOutElementDto();
        outputElement.setCode(inputElement.getCode());
        outputElement.setDomainClassName(inputElement.getDomainClassName());
        try {
            while (i.hasNext()) {
                Object domainObject = i.next();
                Object code = BeanHelper.getField(domainObject, inputElement.getCodeFieldName());
                Object description = BeanHelper.getField(domainObject, inputElement.getDescriptionFieldName());

                CodeHelperOutCodeDto codeDto = new CodeHelperOutCodeDto();
                codeDto.setCode(code);
                codeDto.setDescription(formatDescription(inputElement, code, description));
                outputElement.addCodeHelperOutCodeDto(codeDto);
            }
        } catch (NoSuchMethodException e) {
            String str = "The CodeHelperTx could not introspect the Domain Class " + inputElement.getDomainClassName() + " for the code/description field";
            log.warn(str, e);
        }
        return outputElement;
    }

    private void addCriteria(CriteriaElementDto field, Criteria criteria) {
        if(field != null) {
            String name = field.getFieldName();
            String operator = field.getCriteria() != null ? field.getCriteria().getOperator() : null;
            Object[] values = field.getCriteria() != null ? field.getCriteria().returnValuesAsObjectArray() : null;
            if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NULL))
                criteria.addCriteria(name, Criteria.RELATIONAL_IS_NULL);
            else if (operator != null && operator.equals(CriteriaField.RELATIONAL_IS_NOT_NULL))
                criteria.addCriteria(name, Criteria.RELATIONAL_IS_NOT_NULL);
            else if (values.length > 0) {
                if (operator == null || operator.equals(CriteriaField.RELATIONAL_EQUALS))
                    criteria.addCriteria(name, Criteria.RELATIONAL_EQUALS, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_NOT_EQUALS))
                    criteria.addCriteria(name, Criteria.RELATIONAL_NOT_EQUALS, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN))
                    criteria.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN))
                    criteria.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_GREATER_THAN_EQUAL_TO))
                    criteria.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_SMALLER_THAN_EQUAL_TO))
                    criteria.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_BEGINS_WITH))
                    criteria.addCriteria(name, Criteria.RELATIONAL_BEGINS_WITH, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_ENDS_WITH))
                    criteria.addCriteria(name, Criteria.RELATIONAL_ENDS_WITH, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_LIKE))
                    criteria.addCriteria(name, Criteria.RELATIONAL_LIKE, values[0]);
                else if (operator.equals(CriteriaField.RELATIONAL_BETWEEN)) {
                    criteria.addCriteria(name, Criteria.RELATIONAL_GREATER_THAN_EQUAL_TO, values[0]);
                    if (values.length > 1)
                        criteria.addCriteria(name, Criteria.RELATIONAL_SMALLER_THAN_EQUAL_TO, values[1]);
                } else if (operator.equals(CriteriaField.RELATIONAL_IN)) {
                    if (values.length == 1) {
                        criteria.addCriteria(name, Criteria.RELATIONAL_EQUALS, values[0]);
                    } else {
                        AtomicCriteria atomic = new AtomicCriteria();
                        atomic.addCriteria(name, Criteria.RELATIONAL_EQUALS, values[0]);
                        for (int i = 1; i < values.length; i++)
                            atomic.addOrCriteria(name, Criteria.RELATIONAL_EQUALS, values[i]);
                        criteria.addAtomic(atomic);
                    }
                }
            }
        }
    }


    /** This method is used to format the description field.
     * It returns the description as is, if the 'appendCodeAndDescription' flag is false.
     * Otherwise, it appends the code and description, using the markers specified in the inputElement.
     * If no markers are specified, then the format will be 'code (description)'.
     * @param inputElement The input to the ICodeHelper.
     * @param code The code value.
     * @param description The description for the code.
     * @return a formatted description.
     */
    public static Object formatDescription(CodeHelperInElementDto inputElement, Object code, Object description) {
        Object out = null;
        if (inputElement.isAppendCodeAndDescription()) {
            String beginMarker = null;
            String endMarker = null;
            if (inputElement.getAppendBeginMarker() != null)
                beginMarker = inputElement.getAppendBeginMarker();
            if (inputElement.getAppendEndMarker() != null)
                endMarker = inputElement.getAppendEndMarker();
            if (inputElement.getAppendBeginMarker() == null && inputElement.getAppendEndMarker() == null) {
                beginMarker = DEFAULT_APPEND_BEGIN_MARKER;
                endMarker = DEFAULT_APPEND_END_MARKER;
            }

            StringBuffer buf = new StringBuffer();
            if (code != null)
                buf.append(code);
            if (beginMarker != null)
                buf.append(beginMarker);
            if (description != null)
                buf.append(description);
            if (endMarker != null)
                buf.append(endMarker);
            out = buf.toString();
        } else {
            out = description;
        }
        return out;
    }

    /** Test stub
     * @param args arguments
     */
    public static void main(String[] args) {
        try {
            org.apache.log4j.BasicConfigurator.configure();
            CodeHelperTx tx = new CodeHelperTx();
            int testCode = 1;

            switch(testCode) {
                case 1 : {
                    // test getCode
                    CodeHelperInDto in = new CodeHelperInDto();
                    CodeHelperInElementDto element1 = new CodeHelperInElementDto();
                    element1.setDomainClassName("org.example.applications.app1.modules.request.domain.ReqRemarks");
                    element1.setCodeFieldName("RequestId");
                    element1.setDescriptionFieldName("remarks");
                    //element1.addCriteriaField(new StringCriteriaField("RequestId", "In", new String[] {"REQ02658", "REQ02659", "REQ00645"}));
                    in.addCodeHelperInElementDto(element1);

                    tx.getCodes(in);
                }
                break;
            }
        } catch (ApplicationExceptions e) {
            for (Iterator itr = e.iterator(); itr.hasNext(); )
                ((Exception) itr.next()).printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
