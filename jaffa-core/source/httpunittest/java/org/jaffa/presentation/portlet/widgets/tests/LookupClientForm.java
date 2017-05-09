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
import org.jaffa.presentation.portlet.widgets.model.EditBoxModel;
import org.jaffa.presentation.portlet.widgets.controller.EditBoxController;

/**
 *
 * @author  GautamJ
 * @version
 */
public class LookupClientForm extends FormBase {
    public static final String NAME = "lookupClientForm";

    private String m_field1 = null;
    private EditBoxModel w_field1 = null;
    private String m_field2 = null;
    private EditBoxModel w_field2 = null;
    private String m_field3 = null;
    private EditBoxModel w_field3 = null;
    private String m_field4 = null;
    private EditBoxModel w_field4 = null;


    public String getField1() {
        return m_field1;
    }
    public void setField1(String field1) {
        m_field1 = field1;
    }

    public EditBoxModel getField1WM() {
        if (w_field1 == null) {
            w_field1 = (EditBoxModel) getWidgetCache().getModel("field1");
            if (w_field1 == null) {
                if (getField1() != null)
                    w_field1 = new EditBoxModel(getField1());
                else
                    w_field1 = new EditBoxModel();
                getWidgetCache().addModel("field1", w_field1);
            }
        }
        return w_field1;
    }

    public void setField1WV(String value) {
        EditBoxController.updateModel(value, getField1WM());
    }

    public String getField2() {
        return m_field2;
    }
    public void setField2(String field2) {
        m_field2 = field2;
    }

    public EditBoxModel getField2WM() {
        if (w_field2 == null) {
            w_field2 = (EditBoxModel) getWidgetCache().getModel("field2");
            if (w_field2 == null) {
                if (getField2() != null)
                    w_field2 = new EditBoxModel(getField2());
                else
                    w_field2 = new EditBoxModel();
                getWidgetCache().addModel("field2", w_field2);
            }
        }
        return w_field2;
    }

    public void setField2WV(String value) {
        EditBoxController.updateModel(value, getField2WM());
    }

    public String getField3() {
        return m_field3;
    }
    public void setField3(String field3) {
        m_field3 = field3;
    }

    public EditBoxModel getField3WM() {
        if (w_field3 == null) {
            w_field3 = (EditBoxModel) getWidgetCache().getModel("field3");
            if (w_field3 == null) {
                if (getField3() != null)
                    w_field3 = new EditBoxModel(getField3());
                else
                    w_field3 = new EditBoxModel();
                getWidgetCache().addModel("field3", w_field3);
            }
        }
        return w_field3;
    }

    public void setField3WV(String value) {
        EditBoxController.updateModel(value, getField3WM());
    }

    public String getField4() {
        return m_field4;
    }
    public void setField4(String field4) {
        m_field4 = field4;
    }

    public EditBoxModel getField4WM() {
        if (w_field4 == null) {
            w_field4 = (EditBoxModel) getWidgetCache().getModel("field4");
            if (w_field4 == null) {
                if (getField4() != null)
                    w_field4 = new EditBoxModel(getField4());
                else
                    w_field4 = new EditBoxModel();
                getWidgetCache().addModel("field4", w_field4);
            }
        }
        return w_field4;
    }

    public void setField4WV(String value) {
        EditBoxController.updateModel(value, getField4WM());
    }

}
