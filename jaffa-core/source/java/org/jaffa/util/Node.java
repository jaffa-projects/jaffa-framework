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

package org.jaffa.util;

import org.apache.log4j.*;
import java.io.*;
import java.util.*;

/** An instance of this class can represent a node in a tree.
 * Each node will have a system generated unique identifier.
 * It may have a name (which can be non-unique).
 * It can be linked to a Parent node or be the Root of the tree.
 * It can have child nodes.
 * It will have a value Object.
 * Additionally it can have a map of attributes.
 */
public class Node {
    private static Logger log = Logger.getLogger(Node.class);
    private static long c_nextId = 0;


    /** A unique identifier for a node which is system-generated */
    private String m_id = null;

    /** Name for a node. Multiple Nodes can share the same name. Can be null */
    private String m_name = null;

    /** Value for a node. Can be null */
    private Object m_value = null;

    /** Parent node. Can be null if no parent exists */
    private Node m_parent = null;

    /** Root node. Will have the same value as m_id if the node is a root */
    private Node m_root = null;

    /** This will maintain name-value pairs for the node */
    private Map m_attributes = null;

    /** This will maintain the child nodes */
    private Collection m_children = null;

    /** This will maintain the child nodes, grandchildren, grand-grand-children & so on...
     * as id-node pairs in the root node only */
    private Map m_family = null;

    /** A temporary field */
    private transient Collection m_childrenCol = null;

    /** Creates new Node */
    public Node() {
        this(null);
    }

    /** Creates new Node specifying a name.
     * @param name The node name.
     */
    public Node(String name) {
        m_id = Node.getNextId();
        m_name = name;

        // treat the node as a ROOT initially
        m_root = this;
    }

    //*** getters only ***
    /** Returns the unique identifier of the node.
     * @return the unique identifier of the node.
     */
    public String getId() {
        return m_id;
    }

    /** Returns the Parent node, or null if this node has no parent.
     * @return the Parent node, or null if this node has no parent.
     */
    public Node getParent() {
        return m_parent;
    }

    /** Returns the root node of the tree to which this node belongs.
     * @return the root node of the tree to which this node belongs.
     */
    public Node getRoot() {
        return m_root;
    }

    /** Returns a collection of child nodes.
     * @return a collection of child nodes.
     */
    public Collection getChildren() {
        if (m_children == null)
            m_childrenCol = null;
        else if (m_childrenCol == null)
            m_childrenCol = new Node.Children();
        return m_childrenCol;
    }


    /** Returns the 1st child which matches the name.
     * @param name The node name.
     * @return  the 1st child which matches the name.
     */
    public Node getChildByName(String name) {
        Node node = null;
        if ( m_children != null && !m_children.isEmpty() ) {
            for (Iterator itr = m_children.iterator(); itr.hasNext();) {
                node = (Node) itr.next();
                if ( node.getName() == null ? name == null : node.getName().equals(name) )
                    break;
                else
                    node = null;
            }
        }
        return node;
    }

    /** Returns the child by id.
     * @param id The node identifier.
     * @return the child by id.
     */
    public Node getChildById(String id) {
        Node node = getFromFamilyById(id);
        if (node == null || m_children == null || m_children.isEmpty()
        || !m_children.contains(node) )
            node = null;
        return node;
    }

    /** Returns a node from the family by id.
     * @param id The node identifier.
     * @return a node from the family by id.
     */
    public Node getFromFamilyById(String id) {
        if (id != null && m_root!= null && m_root.m_family != null)
            return (Node) m_root.m_family.get(id);
        else
            return null;
    }

    //*** getters & setters ***
    /** Getter for property name.
     * @return Value of property name.
     */
    public String getName() {
        return m_name;
    }

    /** Setter for property name.
     * @param name New value of property name.
     */
    public void setName(String name) {
        m_name = name;
    }

    /** Getter for property value.
     * @return Value of property value.
     */
    public Object getValue() {
        return m_value;
    }

    /** Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(Object value) {
        m_value = value;
    }

    /** Getter for property attributes.
     * @return Value of property attributes.
     */
    public Map getAttributes() {
        return m_attributes;
    }

    /** Setter for property attributes.
     * @param attributes New value of property attributes.
     */
    public void setAttributes(Map attributes) {
        m_attributes = attributes;
    }

    /** Returns the attribute for the specified key.
     * @param key The attribute key.
     * @return the attribute for the specified key.
     */
    public Object getAttribute(Object key) {
        if (m_attributes != null)
            return m_attributes.get(key);
        else
            return null;
    }

    /** Adds an attribute. Will replace an existing attribute having the same key.
     * @param key The attribute key.
     * @param value The attribute value.
     * @return previous value associated with specified key, or null if there was no attribute for key.
     */
    public Object setAttribute(Object key, Object value) {
        if (m_attributes == null)
            m_attributes = new ListMap();
        return m_attributes.put(key, value);
    }


    //*** Additional Methods ***

    /** Removes an attribute .
     * @param key The attribute key.
     * @return the attribute that was removed, or null if there was no such attribute.
     */
    public Object removeAttribute(Object key) {
        if (m_attributes != null)
            return m_attributes.remove(key);
        else
            return null;
    }

    /** Returns true if the node is its own root.
     * @return true if the node is its own root.
     */
    public boolean isRoot() {
        return this == m_root;
    }

    /** Returns true if the node has childen.
     * @return true if the node has childen.
     */
    public boolean hasChildren() {
        return m_children != null && !m_children.isEmpty() ;
    }

    /** Returns true if the parent node has any more child nodes after the current node.
     * @return true if the parent node has any more child nodes after the current node.
     */
    public boolean parentHasMoreChildren() {
        boolean result = false;
        Node parent = getParent();
        if (parent != null) {
            int i = ( (List) parent.m_children ).indexOf(this);
            if (i < (parent.m_children.size() - 1) )
                result = true;
        }
        return result;
    }

    /** Makes the node its own root. Will remove links from existing parent node, if any.
     */
    public void makeRoot() {
        if ( !isRoot() ) {
            // set the new root for this node as well all its children
            setRoot(m_root, this);

            // remove this node from the old Parent node's childList
            if (m_parent != null)
                m_parent.removeFromChildren(this);

            // reset the parent
            m_parent = null;
        }
    }


    /** Adds a child node. Will add a link both ways.
     * @param node The child node.
     */
    public void addChild(Node node) {
        if (node != null) {
            Node oldParentNode = node.getParent();
            Node oldRootNode = node.getRoot();

            // take care of the parent references
            if (oldParentNode != null)
                oldParentNode.removeFromChildren(node);
            node.m_parent = this;

            // reset the family, in case 'node' is a root
            node.m_family = null;

            // add the 'node' to the children collection
            addToChildren(node);

            // reset the root refernces
            if (m_root != oldRootNode)
                node.setRoot(oldRootNode, m_root);
        }
    }

    /** Removes a child node. Will add a link both ways.
     * @param node The child node.
     * @return true if the child node was removed.
     */
    public boolean removeChild(Node node) {
        boolean result = false;
        if (node != null) {
            result = removeFromChildren(node);

            // just make the node a Root. This will remove all old links
            node.makeRoot();
        }
        return result;
    }

    /** Removes the 1st child that matches the name
     * @param name The node name.
     * @return true if the child node was removed.
     */
    public boolean removeChild(String name) {
        boolean result = false;
        Node node = getChildByName(name);
        if (node != null)
            result = removeChild(node);
        return result;
    }

    /** Remove all children.
     * @return true if any child node was removed.
     */
    public boolean removeChildren() {
        Collection col = getChildren();
        if ( col != null && !col.isEmpty() ) {
            int i = m_children.size();
            for (Iterator itr = col.iterator(); itr.hasNext();) {
                itr.next();
                itr.remove();
            }
            return i != m_children.size();
        } else
            return false;
    }



    /** A helper routine to print the contents of a node.
     * @param writer The writer to which the node contents will be printed.
     * @throws IOException if any I/O error occurs.
     */
    public void printNode(java.io.Writer writer)
    throws java.io.IOException {
        printNode(writer, null, "    ");
    }

    /** A helper routine to print the contents of a node.
     * @param writer The writer to which the node contents will be printed.
     * @param pad The pad string to be used.
     * @param padIncrement The increment string to be appended to the pad string at each successive level of child nodes.
     * @throws IOException if any I/O error occurs.
     */
    public void printNode(java.io.Writer writer, String pad,
    String padIncrement) throws java.io.IOException {
        pad = (pad == null ? "" : pad) + (padIncrement == null ? "" : padIncrement);

        // print the main fields
        writer.write( pad + "Id=" + getId()
        + ", Name=" + getName()
        + ", Value=" + getValue()
        + ", Root=" + ( getRoot() == null ? "null" : getRoot().getId().toString() )
        + ", Parent=" + ( getParent() == null ? "null" : getParent().getId().toString() ) );

        // add the attributes, if any
        if ( getAttributes() != null && !(getAttributes().isEmpty()) ) {
            writer.write(", Attributes=");
            for (Iterator itr = getAttributes().entrySet().iterator(); itr.hasNext();) {
                Map.Entry me = (Map.Entry) itr.next();
                writer.write(me.getKey() + "=" + me.getValue()
                + (itr.hasNext() ? ", " : "") );
            }
        }
        writer.write('\n');

        // print the family, if any
        if ( m_family != null && !m_family.isEmpty() ) {
            writer.write(pad + "Family=");
            for (Iterator itr = m_family.keySet().iterator(); itr.hasNext();) {
                writer.write( itr.next()
                + (itr.hasNext() ? ", " : "") );
            }
            writer.write('\n');
        }

        // recursively print the children
        if ( getChildren() != null && !(getChildren().isEmpty()) ) {
            for (Iterator itr = getChildren().iterator(); itr.hasNext(); )
                ( (Node) itr.next() ).printNode(writer, pad, padIncrement );
        }
    }




    //*** Private Methods ***
    private static synchronized String getNextId() {
        return "" + ++c_nextId;
    }


    private void setRoot(Node oldRootNode, Node newRootNode) {
        // Reset the root node
        m_root = newRootNode;

        // remove the reference to this node from the old root
        if (oldRootNode != null)
            oldRootNode.removeFromFamily(this);

        // add a reference to this node from the new root
        if (!isRoot() && newRootNode != null)
            newRootNode.addToFamily(this);

        // call the setRoot() method for each child recursively
        if (m_children != null) {
            for (Iterator itr = m_children.iterator(); itr.hasNext();) {
                Node node = (Node) itr.next();
                node.setRoot(oldRootNode, newRootNode);
            }
        }
    }


    private void addToFamily(Node node) {
        if (m_family == null)
            m_family = new ListMap();
        m_family.put(node.getId(), node);
    }

    private boolean removeFromFamily(Node node) {
        if (m_family != null) {
            int i = m_family.size();
            m_family.remove( node.getId() );
            return i != m_family.size();
        } else
            return false;
    }

    private void addToChildren(Node node) {
        if (m_children == null)
            m_children = new ArrayList();
        m_children.add(node);
    }

    private boolean removeFromChildren(Node node) {
        if (m_children != null)
            return m_children.remove(node);
        else
            return false;
    }

    private Iterator getIterator() {
        return new Node.NodeIterator();
    }





    // *** INNER CLASSES ***
    private class NodeIterator implements Iterator {
        private Iterator m_iterator = null;
        private Object m_lastReturned = null;

        private NodeIterator() {
            m_iterator = Node.this.m_children.iterator();
        }

        /** Returns true if the iteration has more elements.
         * @return true if the iteration has more elements.
         */
        public boolean hasNext() {
            return m_iterator.hasNext();
        }

        /** Returns the next element in the iteration.
         * @return the next element in the iteration.
         */
        public Object next() {
            m_lastReturned = m_iterator.next();
            return m_lastReturned;
        }

        /** Removes from the underlying collection the last element returned by the iterator.
         */
        public void remove() {
            m_iterator.remove();
            Node.this.removeChild( (Node) m_lastReturned );
        }
    }



    private class Children extends AbstractCollection {
        /** Returns the number of elements in this collection.
         * @return the number of elements in this collection.
         */
        public int size() {
            return Node.this.m_children.size();
        }

        /** Returns true if this collection contains the specified element.
         * @param o element whose presence in this collection is to be tested.
         * @return true if this collection contains the specified element.
         */
        public boolean contains(Object o) {
            return Node.this.m_children.contains(o);
        }

        /** Removes all of the elements from this collection.
         */
        public void clear() {
            Node.this.removeChildren();
        }

        /** Returns an iterator over the elements in this collection.
         * @return an iterator over the elements in this collection.
         */
        public Iterator iterator() {
            return Node.this.getIterator();
        }
    }









    /** Test rig
     * @param args The arguments. Not used.
     */
    public static void main(String[] args) {
        try {
            StringWriter sWriter = new StringWriter();

            Node node1 = new Node("Node1");
            Node node2 = new Node("Node2");
            Node node3 = new Node("Node3");
            Node node4 = new Node("Node4");
            node4.setValue("Value for node4");
            node4.setAttribute("key1", "key1Value");
            node4.setAttribute("key2", "key2Value");
            node4.setAttribute("key3", "key3Value");

            node1.addChild(node2);
            node1.addChild(node3);
            node1.addChild(node4);

            Node node5 = new Node("Node5");
            Node node6 = new Node("Node6");
            Node node7 = new Node("Node7");
            Node node8 = new Node("Node8");

            node2.addChild(node5);
            node3.addChild(node6);
            node6.addChild(node7);
            node3.addChild(node8);

            node1.printNode(sWriter);
            sWriter.write('\n');

            sWriter.write("Make Node3 a root\n");
            node3.makeRoot();
            sWriter.write("Contents of Node1\n");
            node1.printNode(sWriter);
            sWriter.write('\n');
            sWriter.write("Contents of Node3\n");
            node3.printNode(sWriter);
            sWriter.write('\n');


            sWriter.write("Add Node3 to Node2, Remove node5 from node2, Remove an attribute\n");
            node2.addChild(node3);
            node4.removeAttribute("key2");
            node2.removeChild(node5);
            sWriter.write("Contents of Node1\n");
            node1.printNode(sWriter);
            sWriter.write('\n');

            sWriter.write("Removing the children from node1\n");
            node1.removeChildren();
            sWriter.write("Contents of Node1\n");
            node1.printNode(sWriter);
            sWriter.write('\n');
            sWriter.write("Contents of Node2\n");
            node2.printNode(sWriter);
            sWriter.write('\n');
            sWriter.write("Contents of Node3\n");
            node3.printNode(sWriter);
            sWriter.write('\n');
            System.out.println( sWriter.toString() );

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
