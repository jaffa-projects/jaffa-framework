/**********************************************************************************************
 * JAFFA - Java Application Framework For All - Copyright (C) 2002 JAFFA Development Group
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License (version 2.1 any later).
 *
 * See http://jaffa.sourceforge.net/site/legal.html for more details.
 *********************************************************************************************/

//////////////////////////////////////
// widgetCache.js - Maintains a cache of all widgets on a web page, per Form.
//                  A complex widget (eg. Grid) will maintain a list of its inner widgets
//
// By Sean Zhou
////////////////////////////////////////////////////

// This variable will hold a Tree of nodes per Form
var formsWithWidgets = new Array();

// The Node class
function Node(element) {
  this.element = element;
  this.parentNode = null;
  this.rootNode = null;
  this.children = null;
  this.family = null; //only maintained at the root node (form node)
}

// Adds a child node to a Node instance
Node.prototype.addChild = function addChildToNode(element) {
  // find the rootNode
  var theRootNode = this.rootNode != null ? this.rootNode : this;
  // find the rootNode.family
  if (theRootNode.family == null)
    theRootNode.family = new Array();
  // find the child node in the family
  var childNode = theRootNode.family[element.id];
  if (childNode == null) {
    childNode = new Node(element);
    theRootNode.family[childNode.element.id] = childNode;
  }
  // register the parent node
  childNode.parentNode = this;
  // register the root node
  childNode.rootNode = theRootNode;
  // add child
  if (this.children == null)
    this.children = new Array();
  var existingChild = this.children[childNode.element.id];
  if (existingChild==null) {
    // make sure the child has not been added before
    var i = this.children.length;
    this.children[i] = childNode;
    this.children[childNode.element.id] = this.children[i];
  }
  return childNode;
}

// Registers a Form
function registerWidgetForm(formElement) {
  var formNode = formsWithWidgets[formElement.id];
  if (formNode == null) {
    formNode = new Node(formElement);
    var i = formsWithWidgets.length;
    formsWithWidgets[i] = formNode;
    formsWithWidgets[formElement.id] = formsWithWidgets[i];
    formNode.family = new Array();
  }
  return formNode;
}

// Remove a Form
function removeWidgetForm(formId) {
  var formNode = formsWithWidgets[formId];
  if (formNode != null) {
    var hasRemoved = false;
    formsWithWidgets[formId] = null;
    for (var i=0; i<formsWithWidgets.length; i++) {
      if (formsWithWidgets[i].element.id == formId) {
        formsWithWidgets[i] = null;
        hasRemoved = true;
      } else if (hasRemoved) {
        formsWithWidgets[i-1] = formsWithWidgets[i];
      }
    }
    if (hasRemoved) {
      formsWithWidgets.length = formsWithWidgets.length-1;
    }
  }
}

// Registers a widget.
// The formElement is mandatory, and if unregistered, will be registered.
// The parentElement, if provided, and if unregistered, will be registered.
// The widget will be added as a child to the parentElement, if provided, else to the Form.
function registerWidget(formElement, parentElement, widgetElement) {
  var formNode = registerWidgetForm(formElement);
  // find the parent in the form
  var parentNode = null
  if (parentElement != null) {
    parentNode = formNode.family[parentElement.id];
    if (parentNode == null) {
      parentNode = formNode.addChild(parentElement);
    } 
  } 
  // add widget into the form or its parent
  if (parentNode == null) {
    // add the widgetElement as a top node
    formNode.addChild(widgetElement);
  } else {
    parentNode.addChild(widgetElement);
  }
}

// Registers a group widget, such as a group of input tags for a set of radio button.
// The formElement is mandatory, and if unregistered, will be registered.
// The parentElement, if provided, and if unregistered, will be registered.
// An array object is created as a wrapper for each set of group widgets. 
// The widget will be added int to the array
// The array object will be added as a child to the parentElement, if provided, else to the Form.
function registerGroupWidget(formElement, parentElement, widgetElement) {
  var formNode = registerWidgetForm(formElement);
  // find the parent in the form
  var parentNode = null
  if (parentElement != null) {
    parentNode = formNode.family[parentElement.id];
    if (parentNode == null) {
      parentNode = formNode.addChild(parentElement);
    } 
  } 

  // find/construct the wrapper array for the group widgets
  var widgetNode = formNode.family[widgetElement.name];
  var nodeElement;
  if (widgetNode == null) {
    nodeElement = new Object();
    nodeElement.id = widgetElement.name;
    nodeElement.isGroupWidget = 'true';
    nodeElement.elements = new Array();
    if (widgetElement.name) nodeElement.name = widgetElement.name;
    if (widgetElement.type) nodeElement.type = widgetElement.type;
    
    // add widget into the form or its parent
    if (parentNode == null) {
      formNode.addChild(nodeElement);
    } else {
      parentNode.addChild(nodeElement);
    }
  } else {
    nodeElement = widgetNode.element;
    // reset the widgetNode's parent if it is not the one given.
    if (parentNode != null) {
      if (widgetNode.parentNode==null) {
        widgetNode.parentNode = parentNode;
      } else if (widgetNode.parentNode.id != parentNode.id) {
        widgetNode.parentNode = parentNode;
      }
    }
  }
  nodeElement.elements[nodeElement.elements.length] = widgetElement;
}

// code for tests
function inspectNodes() {
  var localV = formsWithWidgets;
  var n = localV.length;
  var theForm = localV[0];
  var nc = theForm.children.length;  
  document.write("number of forms = " + n + "<br/>");
  document.write("number of elements in the form = " + nc + "<br/>");
  for (var i=0; i<theForm.children.length; i++) {
    if (theForm.children[i].children != null) {
      var theGrid = theForm.children[i];
      document.write("number of elements in the grid = " + theGrid.children.length + "<br/>");
    }
  }
}
