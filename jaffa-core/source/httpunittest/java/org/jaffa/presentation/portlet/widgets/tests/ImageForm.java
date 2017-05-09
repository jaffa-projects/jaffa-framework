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
import org.jaffa.presentation.portlet.widgets.model.ImageModel;
import org.jaffa.presentation.portlet.widgets.model.WidgetModel;

/**
 *
 * @author  GautamJ
 * @version
 */
public class ImageForm extends FormBase {
    public static final String NAME = "imageForm";

    private byte[] m_fieldWithCachedModel = null;
    private byte[] m_field1 = null;


    private ImageModel w_fieldLinkedToCCAndCached = null;
    private ImageModel w_fieldLinkedToCC = null;
    private ImageModel w_fieldWithCachedModel = null;
    private ImageModel w_field1 = null;

    // **** fieldLinkedToCCAndCached ****
    public byte[] getFieldLinkedToCCAndCached() {
        return ( (ImageComponent) getComponent() ).getFieldLinkedToCCAndCached();
    }

    public WidgetModel getFieldLinkedToCCAndCachedWM() {
        if (w_fieldLinkedToCCAndCached == null) {
            w_fieldLinkedToCCAndCached = (ImageModel) getWidgetCache().getModel("fieldLinkedToCCAndCached");
            if (w_fieldLinkedToCCAndCached == null) {
                w_fieldLinkedToCCAndCached = new ImageModel(getFieldLinkedToCCAndCached());
                getWidgetCache().addModel("fieldLinkedToCCAndCached", w_fieldLinkedToCCAndCached);
            }
        }
        return w_fieldLinkedToCCAndCached;
    }


    // **** fieldLinkedToCC ****
    public byte[] getFieldLinkedToCC() {
        return ( (ImageComponent) getComponent() ).getFieldLinkedToCC();
    }

    public WidgetModel getFieldLinkedToCCWM() {
        if (w_fieldLinkedToCC == null) {
            w_fieldLinkedToCC = new ImageModel(getFieldLinkedToCC());
        }
        return w_fieldLinkedToCC;
    }


    // **** fieldWithCachedModel ****
    public byte[] getFieldWithCachedModel() {
        return m_fieldWithCachedModel;
    }

    public WidgetModel getFieldWithCachedModelWM() {
        if (w_fieldWithCachedModel == null) {
            w_fieldWithCachedModel = (ImageModel) getWidgetCache().getModel("fieldWithCachedModel");
            if (w_fieldWithCachedModel == null) {
                w_fieldWithCachedModel = new ImageModel(getFieldWithCachedModel());
                getWidgetCache().addModel("fieldWithCachedModel", w_fieldWithCachedModel);
            }
        }
        return w_fieldWithCachedModel;
    }

    // **** field1 ****
    public byte[] getField1() {
        return m_field1;
    }

    public WidgetModel getField1WM() {
        if (w_field1 == null) {
            w_field1 = new ImageModel(getField1());
        }
        return w_field1;
    }




    public void initForm() {
        super.initForm();
        m_fieldWithCachedModel = ImageComponent.getImageBytes("C:\\Sandbox\\Jaffa\\source\\httpunittest\\html\\widgets\\tests\\testimage1_up.gif");
        m_field1= ImageComponent.getImageBytes("C:\\Sandbox\\Jaffa\\source\\httpunittest\\html\\widgets\\tests\\testimage1_down.gif");
    }


}
