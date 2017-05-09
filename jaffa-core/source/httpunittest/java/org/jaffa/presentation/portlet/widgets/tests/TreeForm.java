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
import org.jaffa.presentation.portlet.widgets.model.TreeModel;
import org.jaffa.presentation.portlet.widgets.model.WidgetModel;
import org.jaffa.presentation.portlet.widgets.controller.TreeController;
import org.apache.log4j.Logger;
import org.jaffa.util.Node;
import java.io.StringWriter;
import java.io.IOException;

/**
 *
 * @author  GautamJ
 * @version
 */
public class TreeForm extends FormBase {

   private static Logger log = Logger.getLogger(TreeForm.class);

    public static final String NAME = "treeForm";

    public static final String NODE_TYPE_SESSION = "SessionType";
    public static final String NODE_TYPE_COMPONENT = "ComponentType";


    private TreeModel w_tree = null;

    public TreeModel getTreeWM() {
        if (w_tree == null) {
            w_tree = (TreeModel) getWidgetCache().getModel("tree");
            if (w_tree == null) {
                w_tree = createNewModel();
                getWidgetCache().addModel("tree", w_tree);
            }
        }
        return w_tree;
    }

    public void setTreeWV(String value) {
        if (log.isDebugEnabled())
            log.debug("Invoking the TreeController to update the model with\n" + value);
        TreeController.updateModel(value, getTreeWM() );
    }

    private TreeModel createNewModel() {
        TreeModel model = new TreeModel();

        Node node = new Node();
        model.setRootNode(node);
        node.setName("Root");
        node.setValue("Root Value");
        node.setAttribute(node.getName()+"Attr1",node.getName()+"Attr1Value");
        node.setAttribute(TreeModel.ATTRIBUTE_NODE_TYPE,NODE_TYPE_SESSION);

        Node node2 = new Node();
        node2.setName("Child 1");
        node2.setValue(node2.getName()+" Value");
        node2.setAttribute(node2.getName()+"Attr1",node2.getName()+"Attr1Value");
        node2.setAttribute(TreeModel.ATTRIBUTE_NODE_TYPE,NODE_TYPE_COMPONENT);
        node.addChild(node2);

        node2 = new Node();
        node2.setName("Child 2");
        node2.setValue(node2.getName()+" Value");
        node2.setAttribute(node2.getName()+"Attr1",node2.getName()+"Attr1Value");
        node2.setAttribute(TreeModel.ATTRIBUTE_NODE_TYPE,NODE_TYPE_COMPONENT);
        node.addChild(node2);

        Node node3 = new Node();
        node3.setName("Child 2.1");
        node3.setValue(node3.getName()+" Value");
        node3.setAttribute(node3.getName()+"Attr1",node3.getName()+"Attr1Value");
        node3.setAttribute(TreeModel.ATTRIBUTE_NODE_TYPE,NODE_TYPE_COMPONENT);
        node2.addChild(node3);

        model.setAllExpanded();

        // Added for debug
        try {
            StringWriter sw = new StringWriter();
            model.getRootNode().printNode(sw);
            log.debug(sw.toString());
        } catch (IOException e) {}

        return model;
    }

}
