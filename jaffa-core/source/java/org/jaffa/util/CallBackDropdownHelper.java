/*
 * CallBackQuery.java
 *
 * Created on March 7, 2005, 12:07 PM
 */
package org.jaffa.util;
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
import javax.servlet.http.HttpServletRequest;
import org.jaffa.components.dto.HeaderDto;
import org.jaffa.middleware.Factory;
import org.jaffa.persistence.AtomicCriteria;
import org.jaffa.presentation.portlet.session.UserSession;
import org.jaffa.security.VariationContext;

/**
 *
 * @author  JonnyR
 */
public class CallBackDropdownHelper {

    private ICodeHelper m_codeHelperTx = null; 
    private HeaderDto m_headerDto = null;    
    /** Creates a new instance of CallBackQuery */
    public CallBackDropdownHelper() {        
    }
    
   public CodeHelperOutElementDto getOptions(HttpServletRequest request, String returnField, String descField, String domainName ,  CriteriaElementDto[] criteriaFields) throws ApplicationExceptions, FrameworkException {
       
        
        CodeHelperOutElementDto m_dropDownCodes = null;
        CodeHelperInDto input = null;
        System.out.println("Still alive1");
        if (m_codeHelperTx == null)
            m_codeHelperTx = (ICodeHelper) Factory.createObject(ICodeHelper.class);
       input = new CodeHelperInDto();
       CodeHelperInElementDto codeHelperInElementDto = new CodeHelperInElementDto();
       codeHelperInElementDto.setCode("sort");
       codeHelperInElementDto.setDomainClassName(domainName);
       codeHelperInElementDto.setCodeFieldName(returnField);
       codeHelperInElementDto.setDescriptionFieldName(descField);
       for (CriteriaElementDto criteriaField : criteriaFields) {
           codeHelperInElementDto.addCriteriaField(criteriaField);

       }
       input.addCodeHelperInElementDto(codeHelperInElementDto);
       // Get the Codes and populate the respective fields
       System.out.println("Still alive2");
       input.setHeaderDto(getHeaderDto(request));
       System.out.println("Still alive3");
       CodeHelperOutDto output = m_codeHelperTx.getCodes(input);
       if (output != null && output.getCodeHelperOutElementDtoCount() > 0) {
           CodeHelperOutElementDto[] codeHelperOutElementDtos = output.getCodeHelperOutElementDtos();
           for (CodeHelperOutElementDto codeHelperOutElementDto : codeHelperOutElementDtos) {
               String code = codeHelperOutElementDto.getCode();
               if (code.equals("sort"))
                   m_dropDownCodes = codeHelperOutElementDto;
           }
       }

        return m_dropDownCodes;
 }
    
  /** Returns the HeaderDto. This can be used for passing the header info to the Tx, where required.
     * @return the HeaderDto.
     */
    protected HeaderDto getHeaderDto(HttpServletRequest request) {
        if (m_headerDto == null) {
            m_headerDto = new HeaderDto();
            m_headerDto.setUserId(UserSession.getUserSession(request).getUserId());
            m_headerDto.setVariation(VariationContext.getVariation());
        }
        return m_headerDto;
    }   
    
}
