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

/*
 * AddTest.java
 *
 * Created on April 1, 2002, 5:47 PM
 */

package org.jaffa.security;

import junit.framework.TestCase;
import java.util.Arrays;
import java.io.UnsupportedEncodingException;
import java.io.File;
import javax.crypto.SecretKey;
import java.io.InputStream;
import java.io.FileNotFoundException;
import org.jaffa.util.SplitString;
import java.util.HashMap;
import java.lang.System;
import java.util.StringTokenizer;


/**
 *
 * @author GautamJ
 */
public class EncryptionHelperTest extends TestCase {

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public EncryptionHelperTest(String name) {
        super(name);
    }

    /** Sets up the fixture, by creating the UOW. This method is called before a test is executed.
     */
    protected void setUp() {
    }

    /** Tears down the fixture, by closing the UOW. This method is called after a test is executed.
     */
    protected void tearDown() {
    }

/*
    EncryptionHelper.encryptObjectForURL(source
    EncryptionHelper.encryptStringForURL(source
    EncryptionHelper.getObjectFromEncryptedURL(data
    EncryptionHelper.getStringFromEncryptedURL(data
*/

    public void testBasicFunctions() {
        try {
            byte b = 0x0D;
            assertEquals("Test1 : toHex", 'D', EncryptionHelper.toHex(b));

            char c = 'C';
            assertEquals("Test2 : fromHex",  0x0C, EncryptionHelper.fromHex(c));

            byte[] ba = new byte[] {(byte)0xc5, (byte)0x4f};
            String baStr = EncryptionHelper.intoHexString(ba);
            assertEquals("Test3 : intoHexString", "C54F", baStr );

            byte[] ba2 = EncryptionHelper.fromHexString("C54F");
            assertTrue("Test4 : fromHexString", Arrays.equals(ba, ba2) );

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testBasicFunctions2() {
        try {
            String a1 = "This is a strange test";
            String a2 = EncryptionHelper.intoString( EncryptionHelper.intoBytes(a1) );
            assertEquals("Test1. 8-Bit String to/From byte[]", a1, a2);

            try {
                String b1 = "This is a strange test\u03a0";
                EncryptionHelper.intoBytes(b1);
                fail("No Exception Thrown");
            } catch (UnsupportedEncodingException e) {
                assertTrue("Test2. Exception Thrown", true);
            }

            String c1 = "This is a strange test\u03a0";
            byte[] c1b = EncryptionHelper.intoBytes16(c1);
            String c2 = EncryptionHelper.intoString16(c1b);
            byte[] c2b = EncryptionHelper.intoBytes16(c2);
            assertEquals("Test3. 16-Bit String to/From byte[]", c1, c2);

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testCreateKey() {
        File keyFile = null;
        try {
            String filename = "sample.key";
            String fullname = null;
            String cp = System.getProperty("java.class.path");
            StringTokenizer stknzr = new StringTokenizer(cp, File.pathSeparator);
            while (stknzr.hasMoreTokens()) {
                String dirName = stknzr.nextToken();
                File dir = new File(dirName);
                if (dir.isDirectory()) {
                    fullname = dirName + File.separatorChar + filename;
                    keyFile = new File(fullname);
                    break;
                }
            }

            if (keyFile == null)
                fail("Cannot find any directory in the classpath " + cp);

            // Clean up key file
            if(keyFile.exists())
                assertTrue("Setup: old key file deleted", keyFile.delete());

            EncryptionHelper.main(new String[] {fullname});
            assertTrue("Test1a. Create Key File (new)", keyFile.exists());

            EncryptionHelper.main(new String[] {fullname});
            assertTrue("Test1b. Create Key File (overwrite)", keyFile.exists());

            SecretKey sk = EncryptionHelper.readKey(keyFile);
            this.assertNotNull("Test2. Read back Secuirty Key", sk);

            SecretKey sk2 = EncryptionHelper.readKeyClassPath("sample.key");
            this.assertNotNull("Test3. Read back Secuirty Key via Class Path", sk2);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        } finally {
            // Clean up key file
            if(keyFile != null && keyFile.exists())
                keyFile.delete();
        }
    }

    /** See if we can encrypt a string for use as a url, and then un-encrypt it
     */
    public void testEncryptString() {
        try {
            String source = "This is another Test With Data !@#$^%&*()";
            SecretKey key = EncryptionHelper.createKey();
            assertNotNull("Test 1. Created a new Key", key);

            String data = EncryptionHelper.encryptStringForURL(source, key);
            String dest = EncryptionHelper.getStringFromEncryptedURL(data, key);
            assertEquals("Test 2. EncryptStringURL", source, dest );

            SecretKey key2 = EncryptionHelper.createKey();
            assertNotNull("Test 3. Created another new Key", key2);

            // Test for using wrong decrypt key
            try {
                String dest2 = EncryptionHelper.getStringFromEncryptedURL(data, key2);
                fail("Should throw a wrong key exception");
            } catch (java.security.GeneralSecurityException e) {
                assertTrue("Test 4. Exception if wrong keys used", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testEncryptObject() {
        try {
            HashMap source = new HashMap();
            source.put("freddy","nightmare");
            source.put("weird stuff", new Integer(1000));
            source.put("unicode","\u03a0 this is PI");

            SecretKey key = EncryptionHelper.createKey();
            assertNotNull("Test 1. Created a new Key", key);

            String data = EncryptionHelper.encryptObjectForURL(source, key);
            HashMap dest = (HashMap) EncryptionHelper.getObjectFromEncryptedURL(data, key);
            assertEquals("Test 2. EncryptObjectURL (object)", source, dest );
            assertEquals("Test 2a. EncryptObjectURL (size)", source.size(), dest.size() );
            assertEquals("Test 2b. EncryptObjectURL (keys)", source.keySet(), dest.keySet() );

            // Try to call this method with a non-serializable object...
            try {
                data = EncryptionHelper.encryptObjectForURL(Thread.currentThread(), key);
                fail("Exception should have been raised");
            } catch (java.io.NotSerializableException e) {
                assertTrue("Test 3. Object not serializable", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
