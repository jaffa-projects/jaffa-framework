package org.jaffa.sc.apis;

import org.junit.Test;

import static org.junit.Assert.*;

public class TreeNodeTest {

    @Test public void getTextTest() {
        TreeNode testObject = new TreeNode("n1");
        testObject.setText("hi");
        assertEquals("hi", testObject.getText());
    }
}
