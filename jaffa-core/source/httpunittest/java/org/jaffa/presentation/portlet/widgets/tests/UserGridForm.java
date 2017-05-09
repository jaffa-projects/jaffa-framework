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
import org.jaffa.presentation.portlet.widgets.model.WidgetModel;
import org.jaffa.presentation.portlet.widgets.model.GridModel;
import org.jaffa.presentation.portlet.widgets.model.GridModelRow;
import org.jaffa.presentation.portlet.widgets.model.EditBoxModel;
import org.apache.log4j.Logger;
import org.jaffa.presentation.portlet.widgets.controller.UserGridController;
import org.jaffa.presentation.portlet.widgets.model.DateTimeModel;
import org.jaffa.datatypes.DateTime;
import org.jaffa.presentation.portlet.widgets.model.CheckBoxModel;
import org.jaffa.presentation.portlet.widgets.model.ImageModel;
import org.jaffa.presentation.portlet.widgets.model.DropDownModel;

/**
 *
 * @author  GautamJ
 * @version
 */
public class UserGridForm extends FormBase {
    private static Logger log = Logger.getLogger(UserGridForm.class);

    public static final String NAME = "userGridForm";

    private GridModel w_model1 = null;
    private GridModel w_model2 = null;

    // **** gridModel cached ****
    public WidgetModel getModel1WM() {
        if (w_model1 == null) {
            w_model1 = (GridModel) getWidgetCache().getModel("model1");
            if (w_model1 == null) {
                w_model1 = createNewModel();
                getWidgetCache().addModel("model1", w_model1);
            }
        }
        return w_model1;
    }

    public void setModel1WV(String value) {
        if (log.isDebugEnabled())
            log.debug("Invoking the GridController to update the model with\n" + value);
        UserGridController.updateModel(value, (GridModel) getModel1WM(), this);

    }


    // **** gridModel cached ****
    public WidgetModel getModel2WM() {
        if (w_model2 == null) {
            w_model2 = (GridModel) getWidgetCache().getModel("model2");
            if (w_model2 == null) {

                getWidgetCache().addModel("model2", w_model2);
            }
        }
        return w_model2;
    }

    public void setModel2WV(String value) {
        if (log.isDebugEnabled())
            log.debug("Invoking the GridController to update the model with\n" + value);
        UserGridController.updateModel(value, (GridModel) getModel2WM(), this);

    }

    private GridModel createNewModel() {
        GridModel model = new GridModel();

        GridModelRow row;
        row = model.newRow();
        row.addElement("field1", new EditBoxModel("Field11Value") );
        row.addElement("field2", new EditBoxModel("Field12Value") );
        row.addElement("field3", new DateTimeModel() );
        row.addElement("field4", new CheckBoxModel(true) );
        row.addElement("field5", new ImageModel(ImageComponent.getImageBytes("C:\\Sandbox\\Jaffa\\source\\httpunittest\\html\\widgets\\tests\\testimage1_up.gif")) );
        row.addElement("field6", new DropDownModel("Value1") );

        row = model.newRow();
        row.addElement("field1", new EditBoxModel("Field21Value") );
        row.addElement("field2", new EditBoxModel("Field22Value") );
        row.addElement("field3", new DateTimeModel(new DateTime()) );
        row.addElement("field4", new CheckBoxModel(false) );
        row.addElement("field5", new ImageModel(ImageComponent.getImageBytes("C:\\Sandbox\\Jaffa\\source\\httpunittest\\html\\widgets\\tests\\testimage1_down.gif")) );
        row.addElement("field6", new DropDownModel("Value2") );

        row = model.newRow();
        row.addElement("field1", new EditBoxModel("Field31Value") );
        row.addElement("field2", new EditBoxModel("Field32Value") );
        row.addElement("field3", new DateTimeModel(DateTime.addMonth(new DateTime(), 1)) );
        row.addElement("field4", new CheckBoxModel(true) );
        row.addElement("field5", new ImageModel(ImageComponent.getImageBytes("C:\\Sandbox\\Jaffa\\source\\httpunittest\\html\\widgets\\tests\\testimage1_up.gif")) );
        row.addElement("field6", new DropDownModel("Value3") );

        return model;
    }
}
