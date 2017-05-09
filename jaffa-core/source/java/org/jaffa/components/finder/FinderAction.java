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
 * 1.    Redistributions of source code must retain copyright statements and notices.
 *         Redistributions must also contain a copy of this document.
 * 2.    Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 * 3.    The name "JAFFA" must not be used to endorse or promote products derived from
 *     this Software without prior written permission. For written permission,
 *     please contact mail to: jaffagroup@yahoo.com.
 * 4.    Products derived from this Software may not be called "JAFFA" nor may "JAFFA"
 *     appear in their names without prior written permission.
 * 5.    Due credit should be given to the JAFFA Project (http://jaffa.sourceforge.net).
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

package org.jaffa.components.finder;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;
import org.jaffa.exceptions.ApplicationException;
import org.jaffa.exceptions.FrameworkException;
import org.jaffa.exceptions.ApplicationExceptions;
import org.jaffa.presentation.portlet.ActionBase;
import org.jaffa.presentation.portlet.FormKey;

/** This is the base class for all FinderComponent Actions.
 * @author  GautamJ
 */
public class FinderAction extends ActionBase {

    private static final Logger log = Logger.getLogger(FinderAction.class);

    /** Quits the component and returns the FormKey for the calling screen.
     * @return The FormKey for the caling screen. A null will be returned, if no calling screen was specified.
     */
    public FormKey do_Close_Clicked() {
        return ((FinderForm) form).getComponent().quitAndReturnToCallingScreen();
    }

    /** Invokes the doValidate() method and then the displayResults() method on the component.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_Search_Clicked() {
        FormKey fk = null;
        FinderForm myForm = (FinderForm) form;
        FinderComponent2 myComp = (FinderComponent2) myForm.getComponent();

        if (myForm.doValidate(request)) {
            try {
                myComp.performInquiry();
                fk = myComp.getResultsFormKey(true);
                if (FinderComponent2.EXPORT_TYPE_XML.equals(myComp.getExportType())) {
                    Object dto = myComp.getFinderOutDto();
                    if (dto == null)
                        dto = "";
                    else
                        dto = dto.toString();
                    request.setAttribute(FinderComponent2.ATTRIBUTE_EXPORT_TYPE_XML, dto);
                } else
                    request.removeAttribute(FinderComponent2.ATTRIBUTE_EXPORT_TYPE_XML);
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("Search Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }

        try {
            if (fk == null)
                fk = myComp.displayCriteria();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Search Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        if (fk == null)
            fk = myComp.getCriteriaFormKey();

        return fk;
    }

    /** Start a new search. This invokes the displayCriteria() method on the component.
     * @return The FormKey for the Criteria screen.
     */
    public FormKey do_ModifySearch_Clicked() {
        FormKey fk = null;
        FinderForm myForm = (FinderForm) form;
        FinderComponent2 myComp = (FinderComponent2) myForm.getComponent();

        try {
            fk = myComp.displayCriteria();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("ModifySearch Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }

    /** Re-executes the search, using the same criteria as used before. It invokes the displayResults() method on the component.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_Refresh_Clicked() {
        FormKey fk = null;
        FinderForm myForm = (FinderForm) form;
        FinderComponent2 myComp = (FinderComponent2) myForm.getComponent();

        try {
            fk = myComp.displayResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Refresh Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }

    /** This will increment the value of the property MaxRecords. It then re-executes the search, using the same criteria as used before.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_MoreRecords_Clicked() {
        FormKey fk = null;
        FinderForm myForm = (FinderForm) form;
        FinderComponent2 myComp = (FinderComponent2) myForm.getComponent();
        myComp.incrementMaxRecords();

        try {
            fk = myComp.displayResults();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("Search for More Records Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        // Direct User back to current form
        if (fk == null)
            fk = myComp.getResultsFormKey();
        return fk;
    }


    /** Determines the currentFormKey
     * This event is invoked when a user changes the settings of the UserGrid in the Results screen.
     * @return The FormKey for the Results screen.
     */
    public FormKey do_refresh() {
        FinderForm myForm = (FinderForm) form;
        FinderComponent2 myComp = (FinderComponent2) myForm.getComponent();
        return myComp.getResultsFormKey();
    }

    /**
     * This event is invoked when a user presses the LoadQuery button
     * @return The FormKey for the criteria screen
     */
    public FormKey do_LoadQuery_Clicked() {
        FormKey fk = null;
        FinderForm myForm = (FinderForm) form;
        FinderComponent2 myComp = (FinderComponent2) myForm.getComponent();

        if (myForm.doValidateForLoadQuery(request)) {
            if (myComp.getSavedQueryId() != null) {
                try {
                    myComp.loadQuery();
                    myForm.doValidate(request);
                } catch (ApplicationException e) {
                        if (log.isDebugEnabled())
                            log.debug("ModifySearch Failed");
                        myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
                }
            }
        }
        try {
            fk = myComp.displayCriteria();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("ModifySearch Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        // Direct User back to current form
        if (fk == null)
            fk = myComp.getCriteriaFormKey();
        return fk;
    }

    /**
     * This event is invoked when a user presses the LoadQuery button
     * @return The FormKey for the criteria screen
     */
    public FormKey do_RunQuery_Clicked() {
        do_LoadQuery_Clicked();
        return do_Search_Clicked();
    }

    public FormKey do_SaveQuery_Clicked() {
        FormKey fk = null;
        FinderForm myForm = (FinderForm) form;
        FinderComponent2 myComp = (FinderComponent2) myForm.getComponent();

        if (myForm.doValidate(request) && myForm.doValidateForSaveQuery(request)) {
            try {
                myComp.saveQuery();
                fk = myComp.displayCriteria();
            } catch (ApplicationException e){
                if (log.isDebugEnabled())
                    log.debug("ModifySearch Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (ApplicationExceptions e){
                if (log.isDebugEnabled())
                    log.debug("ModifySearch Failed");
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
            } catch (FrameworkException e) {
                log.error(null, e);
                myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
            }
        }

        // Direct User back to current form
        if (fk == null)
            fk = myComp.getCriteriaFormKey();
        return fk;
    }

    public FormKey do_DeleteQuery_Clicked() {
        FormKey fk = null;
        FinderForm myForm = (FinderForm) form;
        FinderComponent2 myComp = (FinderComponent2) myForm.getComponent();

        if (myForm.doValidateForLoadQuery(request)) {
            if (myComp.getSavedQueryId() != null) {
                try {
                    myComp.deleteQuery();
                    myForm.doValidate(request);
                } catch (ApplicationException e) {
                        if (log.isDebugEnabled())
                            log.debug("ModifySearch Failed");
                        myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
                                    }
            }
        }
        try {
            fk = myComp.displayCriteria();
        } catch (ApplicationExceptions e){
            if (log.isDebugEnabled())
                log.debug("ModifySearch Failed");
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, e);
        } catch (FrameworkException e) {
            log.error(null, e);
            myForm.raiseError(request, ActionMessages.GLOBAL_MESSAGE, "error.framework.general" );
        }

        // Direct User back to current form
        if (fk == null)
            fk = myComp.getCriteriaFormKey();
        return fk;
    }

}
