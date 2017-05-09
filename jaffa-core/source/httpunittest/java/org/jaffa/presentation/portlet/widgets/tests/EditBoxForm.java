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

import org.jaffa.metadata.DateOnlyFieldMetaData;
import org.jaffa.metadata.DateTimeFieldMetaData;
import org.jaffa.metadata.DecimalFieldMetaData;
import org.jaffa.presentation.portlet.FormBase;
import org.jaffa.presentation.portlet.widgets.model.EditBoxModel;
import org.jaffa.presentation.portlet.widgets.controller.EditBoxController;
import org.jaffa.presentation.portlet.widgets.model.WidgetModel;
import org.jaffa.metadata.FieldMetaData;
import org.jaffa.metadata.IntegerFieldMetaData;
import org.jaffa.metadata.StringFieldMetaData;

/**
 *
 * @author  GautamJ
 * @version
 */
public class EditBoxForm extends FormBase {
    public static final String NAME = "editBoxForm";

    private EditBoxModel w_fieldLinkedToCCAndCached = null;
    private EditBoxModel w_fieldLinkedToCC = null;
    private EditBoxModel w_fieldWithCachedModel = null;
    private EditBoxModel w_fieldWithKeyBoard = null;
    private EditBoxModel w_fieldWithValidation = null;
    private EditBoxModel w_textArea = null;
    private EditBoxModel w_dataTypeString = null;
    private EditBoxModel w_dataTypeInteger = null;
    private EditBoxModel w_dataTypeDecimal = null;
    private EditBoxModel w_dataTypeCurrency = null;
    private EditBoxModel w_dataTypeDateTime = null;
    private EditBoxModel w_dataTypeDateOnly = null;


    // **** fieldLinkedToCCAndCached ****
    public String getFieldLinkedToCCAndCached() {
        return ( (EditBoxComponent) getComponent() ).getFieldLinkedToCCAndCached();
    }

    public void setFieldLinkedToCCAndCached(String value) {
        ( (EditBoxComponent) getComponent() ).setFieldLinkedToCCAndCached(value);
    }

    public WidgetModel getFieldLinkedToCCAndCachedWM() {
        if (w_fieldLinkedToCCAndCached == null) {
            w_fieldLinkedToCCAndCached = (EditBoxModel) getWidgetCache().getModel("fieldLinkedToCCAndCached");
            if (w_fieldLinkedToCCAndCached == null) {
                FieldMetaData meta = new StringFieldMetaData("SomeName1",
                "SomeToken1", Boolean.FALSE, null, null, FieldMetaData.LOWER_CASE);
                w_fieldLinkedToCCAndCached = new EditBoxModel(meta);
                getWidgetCache().addModel("fieldLinkedToCCAndCached", w_fieldLinkedToCCAndCached);
                w_fieldLinkedToCCAndCached.setValue( getFieldLinkedToCCAndCached() );
            }
        }
        return w_fieldLinkedToCCAndCached;
    }

    public void setFieldLinkedToCCAndCachedWV(String value) {
        EditBoxModel m = (EditBoxModel) getFieldLinkedToCCAndCachedWM();
        EditBoxController.updateModel(value, m);
    }


    // **** fieldLinkedToCC ****
    public String getFieldLinkedToCC() {
        return ( (EditBoxComponent) getComponent() ).getFieldLinkedToCC();
    }

    public void setFieldLinkedToCC(String value) {
        ( (EditBoxComponent) getComponent() ).setFieldLinkedToCC(value);
    }

    public WidgetModel getFieldLinkedToCCWM() {
        if (w_fieldLinkedToCC == null) {
            FieldMetaData meta = new StringFieldMetaData("SomeName2",
            "SomeToken2", Boolean.FALSE, null, null, FieldMetaData.UPPER_CASE);
            w_fieldLinkedToCC = new EditBoxModel(meta);
            w_fieldLinkedToCC.setValue( getFieldLinkedToCC() );
        }
        return w_fieldLinkedToCC;
    }

    public void setFieldLinkedToCCWV(String value) {
        EditBoxModel m = (EditBoxModel) getFieldLinkedToCCWM();
        EditBoxController.updateModel(value, m);
    }


    // **** fieldWithCachedModel ****
    public String getFieldWithCachedModel() {
        return ((EditBoxModel) getFieldWithCachedModelWM()).getValue();
    }

    public void setFieldWithCachedModel(String value) {
        setFieldWithCachedModelWV(value);
    }

    public WidgetModel getFieldWithCachedModelWM() {
        if (w_fieldWithCachedModel == null) {
            w_fieldWithCachedModel = (EditBoxModel) getWidgetCache().getModel("fieldWithCachedModel");
            if (w_fieldWithCachedModel == null) {
                w_fieldWithCachedModel = new EditBoxModel();
                w_fieldWithCachedModel.setMandatory(true);
                getWidgetCache().addModel("fieldWithCachedModel", w_fieldWithCachedModel);
                w_fieldWithCachedModel.setValue( getFieldWithCachedModel() );
            }
        }
        return w_fieldWithCachedModel;
    }

    public void setFieldWithCachedModelWV(String value) {
        EditBoxModel m = (EditBoxModel) getFieldWithCachedModelWM();
        EditBoxController.updateModel(value, m);
    }


    // **** fieldWithKeyBoard ****
    public String getFieldWithKeyBoard() {
        return ((EditBoxModel) getFieldWithKeyBoardWM()).getValue();
    }

    public void setFieldWithKeyBoard(String value) {
        setFieldWithKeyBoardWV(value);
    }

    public WidgetModel getFieldWithKeyBoardWM() {
        if (w_fieldWithKeyBoard == null) {
            w_fieldWithKeyBoard = new EditBoxModel();
            w_fieldWithKeyBoard.setValue( getFieldWithKeyBoard() );
        }
        return w_fieldWithKeyBoard;
    }

    public void setFieldWithKeyBoardWV(String value) {
        EditBoxModel m = (EditBoxModel) getFieldWithKeyBoardWM();
        EditBoxController.updateModel(value, m);
    }

    // **** fieldWithValidation ****
    public String getFieldWithValidation() {
        return ((EditBoxModel) getFieldWithValidationWM()).getValue();
    }

    public void setFieldWithValidation(String value) {
        setFieldWithValidationWV(value);
    }

    public WidgetModel getFieldWithValidationWM() {
        if (w_fieldWithValidation == null) {
                w_fieldWithValidation = new EditBoxModel();
                w_fieldWithValidation.setValue( getFieldWithValidation() );
        }
        return w_fieldWithValidation;
    }

    public void setFieldWithValidationWV(String value) {
        EditBoxModel m = (EditBoxModel) getFieldWithValidationWM();
        EditBoxController.updateModel(value, m);
    }


    // **** textArea ****
    public String getTextArea() {
        return ((EditBoxModel) getTextAreaWM()).getValue();
    }

    public void setTextArea(String value) {
        setTextAreaWV(value);
    }

    public WidgetModel getTextAreaWM() {
        if (w_textArea == null) {
                w_textArea = new EditBoxModel();
                w_textArea.setValue( getTextArea() );
        }
        return w_textArea;
    }

    public void setTextAreaWV(String value) {
        EditBoxModel m = (EditBoxModel) getTextAreaWM();
        EditBoxController.updateModel(value, m);
    }






   public String getDataTypeString() {
        return ((EditBoxModel) getDataTypeStringWM()).getValue();
    }

    public void setDataTypeString(String value) {
        setDataTypeStringWV(value);
    }

    public WidgetModel getDataTypeStringWM() {
        if (w_dataTypeString == null) {
                w_dataTypeString = new EditBoxModel( new StringFieldMetaData("StringVal",
            "StringVal", Boolean.TRUE, "[0-9]+", new Integer("8"), FieldMetaData.UPPER_CASE , new Integer("4")));
                w_dataTypeString.setValue( getDataTypeString() );
        }
        return w_dataTypeString;
    }

    public void setDataTypeStringWV(String value) {
        EditBoxModel m = (EditBoxModel) getDataTypeStringWM();
        EditBoxController.updateModel(value, m);
    }




   public String getDataTypeInteger() {
        return ((EditBoxModel) getDataTypeIntegerWM()).getValue();
    }

    public void setDataTypeInteger(String value) {
        setDataTypeIntegerWV(value);
    }

    public WidgetModel getDataTypeIntegerWM() {
        if (w_dataTypeInteger == null) {
                w_dataTypeInteger = new EditBoxModel(new IntegerFieldMetaData("IntegerVal", "IntegerVal" ,Boolean.FALSE,"",new Long(100),new Long(200), new Integer("8")));
                w_dataTypeInteger.setValue( getDataTypeInteger() );
        }
        return w_dataTypeInteger;
    }

    public void setDataTypeIntegerWV(String value) {
        EditBoxModel m = (EditBoxModel) getDataTypeIntegerWM();
        EditBoxController.updateModel(value, m);
    }


   public String getDataTypeDecimal() {
        return ((EditBoxModel) getDataTypeDecimalWM()).getValue();
    }

    public void setDataTypeDecimal(String value) {
        setDataTypeDecimalWV(value);
    }

    public WidgetModel getDataTypeDecimalWM() {
        if (w_dataTypeDecimal == null) {
                w_dataTypeDecimal = new EditBoxModel(new DecimalFieldMetaData("DecimalVal", "DecimalVal", Boolean.FALSE,
                                null, new Double("0"), new Double("20.50"), new Integer("4"), new Integer("2")));
                w_dataTypeDecimal.setValue( getDataTypeDecimal() );
        }
        return w_dataTypeDecimal;
    }

    public void setDataTypeDecimalWV(String value) {
        EditBoxModel m = (EditBoxModel) getDataTypeDecimalWM();
        EditBoxController.updateModel(value, m);
    }

   public String getDataTypeDateOnly() {
        return ((EditBoxModel) getDataTypeDateOnlyWM()).getValue();
    }

    public void setDataTypeDateOnly(String value) {
        setDataTypeDateOnlyWV(value);
    }

    public WidgetModel getDataTypeDateOnlyWM() {
        if (w_dataTypeDateOnly == null) {
                w_dataTypeDateOnly = new EditBoxModel(new DateOnlyFieldMetaData());
                w_dataTypeDateOnly.setValue( getDataTypeDateOnly() );
        }
        return w_dataTypeDateOnly;
    }

    public void setDataTypeDateOnlyWV(String value) {
        EditBoxModel m = (EditBoxModel) getDataTypeDateOnlyWM();
        EditBoxController.updateModel(value, m);
    }

   public String getDataTypeDateTime() {
        return ((EditBoxModel) getDataTypeDateTimeWM()).getValue();
    }

    public void setDataTypeDateTime(String value) {
        setDataTypeDateTimeWV(value);
    }

    public WidgetModel getDataTypeDateTimeWM() {
        if (w_dataTypeDateTime == null) {
                w_dataTypeDateTime = new EditBoxModel(new DateTimeFieldMetaData());
                w_dataTypeDateTime.setValue( getDataTypeDateTime() );
        }
        return w_dataTypeDateTime;
    }

    public void setDataTypeDateTimeWV(String value) {
        EditBoxModel m = (EditBoxModel) getDataTypeDateTimeWM();
        EditBoxController.updateModel(value, m);
    }
    // New Validations

}
