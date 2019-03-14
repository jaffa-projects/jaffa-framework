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
 * EncryptionHelper.java
 *
 * Created on April 19, 2002, 8:46 AM
 */

package org.jaffa.security;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.NoSuchAlgorithmException;

/** This class has some utility functions for encrypting objects using the
 * JCE Security Package.
 *
 * Its main purpose is to be able to take a Object/String and encrypt it, and then
 * convert the encrypted data into a HexString, so that it can be passed arround as
 * a String, and hence used in URL's.
 *
 * A good exmple of this is if you have an Object that you want to pass to a servlet,
 * then you can use this routine to get a HexString version of that object and pass
 * it accross in the URL as a paramater "data=1234567890ABC...", Data will not only be a serialization
 * of the object, it will also be encrypted with a SecretKey, that the recievoing servlet must use
 * when converting it back to an object.
 *
 * The String version of this process is optimized to convert the String in to a UTF-8 byte array.
 * This results in a much smaller string then regular obejct serialization.
 *
 * @author  paule
 * @version 1.0
 */
public class EncryptionHelper {

    /** This is the encryption policy that will be used */
    public static final String ENCRYPT_POLICY = "AES/ECB/PKCS5Padding";
    private static final char[] HEX = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    private static final String HEX_STR = "0123456789ABCDEF";

    /** The default algorithm to use for encryption/decryption. */
    public static final String DEFAULT_CRYPTOGRAPHIC_ALGORITHM = "AES";

    /** This method can be used from the command line for creating a Secret Key.
     * @param args the command line arguments
     * Requires one mandatory parameter, which is the file name to use to write out the SecretKey
     */
    public static void main(String args[]) {
        if (args.length != 1) {
            System.out.println("Missing Parameter. Please supply the filename for writing out the SecretKey");
            return;
        }
        File file = new File(args[0]);
        if (file.exists()) {
            System.out.println("Warning: Existing File Will Be Replaced.");
        }

        try {
            SecretKey secretKey = createKey();

            if (secretKey != null) {
                // Write the newly generated key to a file.
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(secretKey);
                oos.flush();
                oos.close();
                fos.close();
            }
        }
        catch (IOException e) {
            System.err.println("Error Writing Out Key : " + e.getMessage());
        }
    }

    /** This method can be used from the command line for creating a Secret Key.
     * @return Returns the newley generated key, or null if there was an error.
     */
    public static SecretKey createKey() {
        try {
            KeyGenerator kg = KeyGenerator.getInstance(DEFAULT_CRYPTOGRAPHIC_ALGORITHM);
            SecretKey secretKey = kg.generateKey();
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            // Shouldn't happen because we've hardcoded the algorithm to use
            System.err.println("Invalid Algorithm : " + e.getMessage());
            return null;
        }
    }

    /** Read a file that should contain a serialized Secret key
     *
     * @return The secret key object
     * @param file The file object that points to the key file
     * @throws ClassNotFoundException If the SecretKey class is not available
     * @throws IOException If the specfied file can't be loaded
     */
    public static SecretKey readKey(File file)
    throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        SecretKey secretKey = (SecretKey) ois.readObject();
        ois.close();
        fis.close();
        return secretKey;
    }

    /** Read a file that should contain a serialized Secret key, the file
     * is read as a resource on the classpath
     *
     * @return The secret key object
     * @param name The resource name that points to the key file
     * @throws ClassNotFoundException If the SecretKey class is not available
     * @throws IOException If the specfied file can't be loaded
     */
    public static SecretKey readKeyClassPath(String name)
    throws IOException, ClassNotFoundException {
        InputStream is = EncryptionHelper.class.getClassLoader().getResourceAsStream(name);
        if(is == null)
            is = ClassLoader.getSystemResourceAsStream(name);
        if(is == null)
            throw new FileNotFoundException(name);
        ObjectInputStream ois = new ObjectInputStream(is);
        SecretKey secretKey = (SecretKey) ois.readObject();
        ois.close();
        is.close();
        return secretKey;
    }

    /** Converts a String (based on an 8-bit character set) into an byte array.
     * There will be one byte per charater in the string.
     * @param in The string to be converted
     * @throws UnsupportedEncodingException Is thrown if there are any unsupported characters in the string (ie. greater that 8-bits)
     * @return  The byte[] for the string
     */
    public static byte[] intoBytes(String in)
    throws UnsupportedEncodingException {
        byte[] data = new byte[in.length()];
        char[] a = in.toCharArray();
        for(int i = 0, j = 0; i<a.length; i++) {
            char c = a[i];
            byte hi = (byte) (c >> 8);
            if(hi != 0)
                throw new  UnsupportedEncodingException("Non UTF-8 Characters In String, Use 16-Bit version!");
            byte lo = (byte) (c & 0xFF);
            data[j++] = lo;
        }
        return data;
    }

    /** Converts a byte array into a string. It assumes that 8-bits represents a byte.
     * There should there for be one character per byte.
     * @param in byte[] to be converted
     * @return  Converted string
     */
    public static String intoString(byte[] in) {
        StringBuffer b = new StringBuffer();
        for(int i = 0; i<in.length;) {
            byte hi = 0; //in[i++];
            byte lo = in[i++];
            char c = (char) (hi * 0xFF + lo);
            b.append(c);
        }
        return b.toString();
    }

    /** Converts a String into an byte array.
     * There will be two bytes per charater in the string.
     * @param in The string to be converted
     * @return  The byte[] for the string
     */
    public static byte[] intoBytes16(String in) {
        byte[] data = new byte[in.length()*2];
        char[] a = in.toCharArray();
        for(int i = 0, j = 0; i<a.length; i++) {
            char c = a[i];
            byte hi = (byte) (c >> 8);
            byte lo = (byte) (c & 0xFF);
            data[j++] = hi;
            data[j++] = lo;
        }
        return data;
    }

    /** Converts a byte array into a string. It assumes that 16-bits represents a byte.
     * @param in byte[] to be converted
     * @return  Converted string
     */
    public static String intoString16(byte[] in) {
        StringBuffer b = new StringBuffer();
        for(int i = 0; i<in.length;) {
            byte hi = in[i++] ;
            byte lo = in[i++] ;
            char c = (char) (hi * 256 + lo + (lo<0?256:0));
            b.append(c);
        }
        return b.toString();
    }

    /** Converts a byte[] into a hex string representation. Each byte will be represented
     * by a 2-digit hex number (00-FF).
     * @param in The byte[] to convert
     * @return  The string containing the Hex representation
     */
    public static String intoHexString(byte[] in) {
        StringBuffer b = new StringBuffer();
        for(int i = 0; i<in.length; i++) {
            byte bt = in[i];
            b.append(toHex( (byte) (bt >> 4) ));
            b.append(toHex(bt));
        }
        return b.toString();
    }

    /** Convert a String of hex values into a byte[]. Each two characters in the string
     * represent 1 byte.
     * @param in The hex string to be converted
     * @return  A byte[] of the real data
     */
    public static byte[] fromHexString(String in) {
        byte[] data = new byte[in.length()/2];
        for(int i = 0, j=0; i<in.length();) {
            byte hi = fromHex(in.charAt(i++));
            byte lo = fromHex(in.charAt(i++));
            data[j++] = (byte) ( (hi << 4) + lo );
        }
        return data;
    }

    /** Utility function to convert a number into a hex character.
     * Takes the lowest 4 bits and converts it to a character '0'..'F'
     * @param b The byte to convert
     * @return  The Hex character
     */
    public static char toHex(byte b) {
        return HEX[(int) (b & 0x0F)];
    }

    /** Utility function to convert a hex character to a number.
     * The character must be '0'..'F', the byte will be 0-15.
     * @param c The character to convert
     * @return  The number as a byte
     */
    public static byte fromHex(char c) {
        return (byte)HEX_STR.indexOf(c);
    }
}
