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

package org.jaffa.presentation.portlet.widgets.tests;

import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.RadioButtonModel;
import org.jaffa.presentation.portlet.widgets.controller.RadioButtonController;
import org.jaffa.presentation.portlet.widgets.model.WidgetModel;

/**
 *
 * @author  GautamJ
 * @version
 */
public class RadioButtonForm extends FormBase {
    public static final String NAME = "radioButtonForm";

    private String m_fieldWithCachedModel = null;
    private String m_fieldWithValidation = null;

    private RadioButtonModel w_fieldLinkedToCCAndCached = null;
    private RadioButtonModel w_fieldLinkedToCC = null;
    private RadioButtonModel w_fieldWithCachedModel = null;
    private RadioButtonModel w_fieldWithValidation = null;



    // **** fieldLinkedToCCAndCached ****
    public String getFieldLinkedToCCAndCached() {
        return ( (RadioButtonComponent) getComponent() ).getFieldLinkedToCCAndCached();
    }

    public void setFieldLinkedToCCAndCached(String value) {
        ( (RadioButtonComponent) getComponent() ).setFieldLinkedToCCAndCached(value);
    }

    public WidgetModel getFieldLinkedToCCAndCachedWM() {
        if (w_fieldLinkedToCCAndCached == null) {
            w_fieldLinkedToCCAndCached = (RadioButtonModel) getWidgetCache().getModel("fieldLinkedToCCAndCached");
            if (w_fieldLinkedToCCAndCached == null) {
                w_fieldLinkedToCCAndCached = new RadioButtonModel(getFieldLinkedToCCAndCached());
                getWidgetCache().addModel("fieldLinkedToCCAndCached", w_fieldLinkedToCCAndCached);
            }
        }
        return w_fieldLinkedToCCAndCached;
    }

    public void setFieldLinkedToCCAndCachedWV(String value) {
        RadioButtonModel m = (RadioButtonModel) getFieldLinkedToCCAndCachedWM();
        RadioButtonController.updateModel(value, m);
    }


    // **** fieldLinkedToCC ****
    public String getFieldLinkedToCC() {
        return ( (RadioButtonComponent) getComponent() ).getFieldLinkedToCC();
    }

    public void setFieldLinkedToCC(String value) {
        ( (RadioButtonComponent) getComponent() ).setFieldLinkedToCC(value);
    }

    public WidgetModel getFieldLinkedToCCWM() {
        if (w_fieldLinkedToCC == null) {
            w_fieldLinkedToCC = new RadioButtonModel(getFieldLinkedToCC());
        }
        return w_fieldLinkedToCC;
    }

    public void setFieldLinkedToCCWV(String value) {
        RadioButtonModel m = (RadioButtonModel) getFieldLinkedToCCWM();
        RadioButtonController.updateModel(value, m);
    }


    // **** fieldWithCachedModel ****
    public String getFieldWithCachedModel() {
        return m_fieldWithCachedModel;
    }

    public void setFieldWithCachedModel(String value) {
        m_fieldWithCachedModel = value;
        if (w_fieldWithCachedModel != null)
            setFieldWithCachedModelWV((new Boolean(value)).toString());
    }

    public WidgetModel getFieldWithCachedModelWM() {
        if (w_fieldWithCachedModel == null) {
            w_fieldWithCachedModel = (RadioButtonModel) getWidgetCache().getModel("fieldWithCachedModel");
            if (w_fieldWithCachedModel == null) {
                w_fieldWithCachedModel = new RadioButtonModel(getFieldWithCachedModel());
                getWidgetCache().addModel("fieldWithCachedModel", w_fieldWithCachedModel);
            }
        }
        return w_fieldWithCachedModel;
    }

    public void setFieldWithCachedModelWV(String value) {
        RadioButtonModel m = (RadioButtonModel) getFieldWithCachedModelWM();
        RadioButtonController.updateModel(value, m);
    }


    // **** fieldWithValidation ****
    public String getFieldWithValidation() {
        return m_fieldWithValidation;
    }

    public void setFieldWithValidation(String value) {
        m_fieldWithValidation = value;
        if (w_fieldWithValidation != null)
            setFieldWithValidationWV((new Boolean(value)).toString());
    }

    public WidgetModel getFieldWithValidationWM() {
        if (w_fieldWithValidation == null) {
            w_fieldWithValidation = new RadioButtonModel(getFieldWithValidation());
        }
        return w_fieldWithValidation;
    }

    public void setFieldWithValidationWV(String value) {
        RadioButtonModel m = (RadioButtonModel) getFieldWithValidationWM();
        RadioButtonController.updateModel(value, m);
    }



    public void initForm() {
        super.initForm();
        m_fieldWithCachedModel = "FWCMValue1";
        m_fieldWithValidation = "FWVValue2";
    }

    // @todo:validate
}
