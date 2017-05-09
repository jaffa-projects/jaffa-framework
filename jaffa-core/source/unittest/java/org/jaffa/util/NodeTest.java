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
import junit.framework.*;

public class NodeTest extends TestCase {
    // constants
    private static final String ROOT="Root";
    private static final String N1="N1", N11="N11";
    private static final String N2="N2", N21="N21", N22="N22";
    private static final String N3="N3", N31="N31", N32="N32", N33="N33";
    private static final String N4="N4", N41="N41", N42="N42", N43="N43", N44="N44";
    private static final String N5="N5", N51="N51", N511="N511", N5111="N5111", N51111="N51111", N51112="N51112";

    // fixtures
    private Node f_node = null;

    private Node f_node1 = null;
    private Node f_node11 = null;

    private Node f_node2 = null;
    private Node f_node21 = null;
    private Node f_node22 = null;

    private Node f_node3 = null;
    private Node f_node31 = null;
    private Node f_node32 = null;
    private Node f_node33 = null;

    private Node f_node4 = null;
    private Node f_node41 = null;
    private Node f_node42 = null;
    private Node f_node43 = null;
    private Node f_node44 = null;


    private Node f_node5 = null;
    private Node f_node51 = null;
    private Node f_node511 = null;
    private Node f_node5111 = null;
    private Node f_node51111 = null;
    private Node f_node51112 = null;



    public NodeTest(java.lang.String testName) {
        super(testName);
    }

    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(NodeTest.class);

        return suite;
    }


    public void setUp() {
        f_node1 = new Node(N1);
        f_node11 = new Node(N11);
        f_node1.addChild(f_node11);

        f_node2 = new Node(N2);
        f_node21 = new Node(N21);
        f_node22 = new Node(N22);
        f_node2.addChild(f_node21);
        f_node2.addChild(f_node22);

        f_node3 = new Node(N3);
        f_node31 = new Node(N31);
        f_node32 = new Node(N32);
        f_node33 = new Node(N33);
        f_node3.addChild(f_node31);
        f_node3.addChild(f_node32);
        f_node3.addChild(f_node33);

        f_node4 = new Node(N4);
        f_node41 = new Node(N41);
        f_node42 = new Node(N42);
        f_node43 = new Node(N43);
        f_node44 = new Node(N44);
        f_node4.addChild(f_node41);
        f_node4.addChild(f_node42);
        f_node4.addChild(f_node43);
        f_node4.addChild(f_node44);


        f_node5 = new Node(N5);
        f_node51 = new Node(N51);
        f_node511 = new Node(N511);
        f_node5111 = new Node(N5111);
        f_node51111 = new Node(N51111);
        f_node51112 = new Node(N51112);
        f_node5.addChild(f_node51);
        f_node51.addChild(f_node511);
        f_node511.addChild(f_node5111);
        f_node5111.addChild(f_node51111);
        f_node5111.addChild(f_node51112);

        f_node = new Node(ROOT);
        f_node.addChild(f_node1);
        f_node.addChild(f_node2);
        f_node.addChild(f_node3);
        f_node.addChild(f_node4);
        f_node.addChild(f_node5);
    }


    public void tearDown() {
        f_node = null;

        f_node1 = null;
        f_node11 = null;

        f_node2 = null;
        f_node21 = null;
        f_node22 = null;

        f_node3 = null;
        f_node31 = null;
        f_node32 = null;
        f_node33 = null;

        f_node4 = null;
        f_node41 = null;
        f_node42 = null;
        f_node43 = null;
        f_node44 = null;


        f_node5 = null;
        f_node51 = null;
        f_node511 = null;
        f_node5111 = null;
        f_node51111 = null;
        f_node51112 = null;
    }


    /** Test of getId method, of class org.jaffa.util.Node. */
    public void testGetId() {
        System.out.println("testGetId");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getParent method, of class org.jaffa.util.Node. */
    public void testGetParent() {
        System.out.println("testGetParent");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getRoot method, of class org.jaffa.util.Node. */
    public void testGetRoot() {
        System.out.println("testGetRoot");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getChildren method, of class org.jaffa.util.Node. */
    public void testGetChildren() {
        System.out.println("testGetChildren");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getChild method, of class org.jaffa.util.Node. */
    public void testGetChild() {
        System.out.println("testGetChild");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getFromFamilyById method, of class org.jaffa.util.Node. */
    public void testGetFromFamilyById() {
        System.out.println("testGetFromFamilyById");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getName method, of class org.jaffa.util.Node. */
    public void testGetName() {
        System.out.println("testGetName");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of setName method, of class org.jaffa.util.Node. */
    public void testSetName() {
        System.out.println("testSetName");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getValue method, of class org.jaffa.util.Node. */
    public void testGetValue() {
        System.out.println("testGetValue");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of setValue method, of class org.jaffa.util.Node. */
    public void testSetValue() {
        System.out.println("testSetValue");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getAttributes method, of class org.jaffa.util.Node. */
    public void testGetAttributes() {
        System.out.println("testGetAttributes");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of setAttributes method, of class org.jaffa.util.Node. */
    public void testSetAttributes() {
        System.out.println("testSetAttributes");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of getAttribute method, of class org.jaffa.util.Node. */
    public void testGetAttribute() {
        System.out.println("testGetAttribute");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of setAttribute method, of class org.jaffa.util.Node. */
    public void testSetAttribute() {
        System.out.println("testSetAttribute");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of removeAttribute method, of class org.jaffa.util.Node. */
    public void testRemoveAttribute() {
        System.out.println("testRemoveAttribute");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of isRoot method, of class org.jaffa.util.Node. */
    public void testIsRoot() {
        System.out.println("testIsRoot");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of hasChildren method, of class org.jaffa.util.Node. */
    public void testHasChildren() {
        System.out.println("testHasChildren");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of makeRoot method, of class org.jaffa.util.Node. */
    public void testMakeRoot() {
        System.out.println("testMakeRoot");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of addChild method, of class org.jaffa.util.Node. */
    public void testAddChild() {
        System.out.println("testAddChild");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of removeChild method, of class org.jaffa.util.Node. */
    public void testRemoveChild() {
        System.out.println("testRemoveChild");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of removeChildren method, of class org.jaffa.util.Node. */
    public void testRemoveChildren() {
        System.out.println("testRemoveChildren");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

    /** Test of printNode method, of class org.jaffa.util.Node. */
    public void testPrintNode() {
        System.out.println("testPrintNode");

        // Add your test code below by replacing the default call to fail.
        fail("The test case is empty.");
    }

}
