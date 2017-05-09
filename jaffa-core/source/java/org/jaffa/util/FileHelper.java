/*
 * FileHelper.java
 *
 * Created on June 20, 2008, 3:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.jaffa.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author PaulE
 */
public class FileHelper {
    
    /**
     * Fetch the entire contents of a binary file, and return it in as a byte[].
     * @param file is a file which already exists and can be read.
     */
    public static byte[] loadBinaryFile(File file) throws IOException {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));
        try {
            //Load the file contents into a ByteArrayOutputStream
            int aByte;
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            while ((aByte = input.read()) != -1)
                bytes.write(aByte);
            return bytes.toByteArray();
        } finally {
            if (input != null)
                input.close();
        }
    }

    /**
     * Fetch the entire contents of a text file, and return it in a String.
     * This style of implementation does not throw Exceptions to the caller.
     * 
     * @param file is a file which already exists and can be read.
     */
    public static String loadTextFile(File file) throws IOException {
        //...checks on file are elided
        StringBuffer contents = new StringBuffer();
        
        //use buffering, reading one line at a time
        //FileReader always assumes default encoding is OK!
        BufferedReader input =  new BufferedReader(new FileReader(file));
        try {
            String line = null; //not declared within while loop
            /*
             * readLine is a bit quirky :
             * it returns the content of a line MINUS the newline.
             * it returns null only for the END of the stream.
             * it returns an empty String if two newlines appear in a row.
             */
            while (( line = input.readLine()) != null){
                contents.append(line);
                contents.append(System.getProperty("line.separator"));
            }
        } finally {
            input.close();
        }
       
        return contents.toString();
    }
    
    /**
     * Change the contents of text file in its entirety, overwriting any
     * existing text.
     * 
     * @param file is a file which can be written to.
     * @throws IllegalArgumentException if param does not comply.
     * @throws FileNotFoundException if the file does not exist.
     * @throws IOException if problem encountered during write.
     */
    public static void saveTextFile(File file, String text)
    throws FileNotFoundException, IOException {
        if (file == null) {
            throw new IllegalArgumentException("File should not be null.");
        }
        if(file.exists() && file.isDirectory()) {
            throw new IllegalArgumentException("Should not be a directory: " + file);
        }
        if (file.exists() && !file.canWrite()) {
        }
        
        //use buffering
        Writer output = new BufferedWriter(new FileWriter(file));
        try {
            //FileWriter always assumes default encoding is OK!
            output.write( text );
        } finally {
            output.close();
        }
    }
    
    /** Alphabetically sorts the input array of File objects.
     * However the directories will always appear first in the array, followed by the files.
     * @param files the File objects to be sorted.
     */
    public static void sortFiles(File[] files) {
        if (files != null) {
            // Sort the input files by utilizing the following anonymous Comparator.
            Arrays.sort(files, new Comparator<File>() {

                /** Use the Collator class to perform locale-sensitive String comparison. */
                private Collator m_collator = Collator.getInstance();

                /** Compares its two arguments for order.
                 * Returns a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
                 * @param o1 the first File to be compared.
                 * @param o2 the second File to be compared.
                 * @return a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
                 */
                public int compare(File o1, File o2) {
                    if (o1 == o2)
                        return 0;
                    else if (o1.isDirectory() && o2.isFile())
                        return -1;
                    else if (o1.isFile() && o2.isDirectory())
                        return 1;
                    else
                        return m_collator.compare(o1.getName(), o2.getName());
                }
            });
        }
    }
}
