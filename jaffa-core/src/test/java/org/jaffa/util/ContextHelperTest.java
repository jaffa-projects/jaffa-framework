package org.jaffa.util;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * tests the ContextHelper utility methods.
 */
public class ContextHelperTest {

    @Test
    public void testGetContext() throws IOException {
        String contextSalience = ContextHelper.getContextSalience("jar:file:/abc.jar!/META-INF/jaffa-transaction-config.xml");
        assertNotNull(contextSalience);
        contextSalience = ContextHelper.getContextSalience("jar:file:/bbc.jar!/META-INF/jaffa-transaction-config.xml");
        assertNotNull(contextSalience);
        contextSalience = ContextHelper.getContextSalience("jar:file:/abc.jar!/META-INF/jaffa-transaction-config.xml");
        assertNotNull(contextSalience);
        contextSalience = ContextHelper.getContextSalience("jar:file:/ydf.jar!/META-INF/jaffa-transaction-config.xml");
        assertNotNull(contextSalience);

        assertEquals(3,ContextHelper.contextSalienceMap.entrySet().size());
    }

    @Test
    public void testGetContextFromClass() throws IOException {
        String contextSalience = ContextHelper.getManifestParameter(ContextHelper.class);

        assertNull(contextSalience);
    }

}
