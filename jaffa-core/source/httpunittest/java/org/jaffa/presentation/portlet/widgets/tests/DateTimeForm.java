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
import org.jaffa.presentation.portlet.widgets.model.DateTimeModel;
import org.jaffa.presentation.portlet.widgets.controller.DateTimeController;
import org.jaffa.presentation.portlet.widgets.model.WidgetModel;
import org.jaffa.datatypes.DateTime;

/**
 *
 * @author  GautamJ
 * @version
 */
public class DateTimeForm extends FormBase {
    public static final String NAME = "dateTimeForm";

    private DateTime m_fieldWithCachedModel = null;
    private DateTime m_fieldWithValidation = null;

    private DateTimeModel w_fieldLinkedToCCAndCached = null;
    private DateTimeModel w_fieldLinkedToCC = null;
    private DateTimeModel w_fieldWithCachedModel = null;
    private DateTimeModel w_fieldWithValidation = null;



    // **** fieldLinkedToCCAndCached ****
    public DateTime getFieldLinkedToCCAndCached() {
        return ( (DateTimeComponent) getComponent() ).getFieldLinkedToCCAndCached();
    }

    public void setFieldLinkedToCCAndCached(DateTime value) {
        ( (DateTimeComponent) getComponent() ).setFieldLinkedToCCAndCached(value);
    }

    public WidgetModel getFieldLinkedToCCAndCachedWM() {
        if (w_fieldLinkedToCCAndCached == null) {
            w_fieldLinkedToCCAndCached = (DateTimeModel) getWidgetCache().getModel("fieldLinkedToCCAndCached");
            if (w_fieldLinkedToCCAndCached == null) {
                w_fieldLinkedToCCAndCached = new DateTimeModel(getFieldLinkedToCCAndCached());
                getWidgetCache().addModel("fieldLinkedToCCAndCached", w_fieldLinkedToCCAndCached);
            }
        }
        return w_fieldLinkedToCCAndCached;
    }

    public void setFieldLinkedToCCAndCachedWV(String value) {
        DateTimeModel m = (DateTimeModel) getFieldLinkedToCCAndCachedWM();
        DateTimeController.updateModel(value, m);
    }


    // **** fieldLinkedToCC ****
    public DateTime getFieldLinkedToCC() {
        return ( (DateTimeComponent) getComponent() ).getFieldLinkedToCC();
    }

    public void setFieldLinkedToCC(DateTime value) {
        ( (DateTimeComponent) getComponent() ).setFieldLinkedToCC(value);
    }

    public WidgetModel getFieldLinkedToCCWM() {
        if (w_fieldLinkedToCC == null) {
            w_fieldLinkedToCC = new DateTimeModel(getFieldLinkedToCC());
        }
        return w_fieldLinkedToCC;
    }

    public void setFieldLinkedToCCWV(String value) {
        DateTimeModel m = (DateTimeModel) getFieldLinkedToCCWM();
        DateTimeController.updateModel(value, m);
    }


    // **** fieldWithCachedModel ****
    public DateTime getFieldWithCachedModel() {
        return m_fieldWithCachedModel;
    }

    public void setFieldWithCachedModel(DateTime value) {
        m_fieldWithCachedModel = value;
        if (w_fieldWithCachedModel != null)
            setFieldWithCachedModelWV(value.toString());
    }

    public WidgetModel getFieldWithCachedModelWM() {
        if (w_fieldWithCachedModel == null) {
            w_fieldWithCachedModel = (DateTimeModel) getWidgetCache().getModel("fieldWithCachedModel");
            if (w_fieldWithCachedModel == null) {
                w_fieldWithCachedModel = new DateTimeModel(getFieldWithCachedModel());
                getWidgetCache().addModel("fieldWithCachedModel", w_fieldWithCachedModel);
            }
        }
        return w_fieldWithCachedModel;
    }

    public void setFieldWithCachedModelWV(String value) {
        DateTimeModel m = (DateTimeModel) getFieldWithCachedModelWM();
        DateTimeController.updateModel(value, m);
    }


    // **** fieldWithValidation ****
    public DateTime getFieldWithValidation() {
        return m_fieldWithValidation;
    }

    public void setFieldWithValidation(DateTime value) {
        m_fieldWithValidation = value;
        if (w_fieldWithValidation != null)
            setFieldWithValidationWV(value.toString());
    }

    public WidgetModel getFieldWithValidationWM() {
        if (w_fieldWithValidation == null) {
                w_fieldWithValidation = new DateTimeModel(getFieldWithValidation());
        }
        return w_fieldWithValidation;
    }

    public void setFieldWithValidationWV(String value) {
        DateTimeModel m = (DateTimeModel) getFieldWithValidationWM();
        DateTimeController.updateModel(value, m);
    }



    public void initForm() {
        super.initForm();

        if (m_fieldWithCachedModel == null)
            m_fieldWithCachedModel = DateTime.addDay(new DateTime(), 2);

        if (m_fieldWithValidation == null)
            m_fieldWithValidation = DateTime.addDay(new DateTime(), 3);    }

    // @todo:validate
}
