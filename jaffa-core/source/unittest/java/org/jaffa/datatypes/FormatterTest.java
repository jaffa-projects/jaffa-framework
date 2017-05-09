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


package org.jaffa.datatypes;

import junit.framework.*;
import org.jaffa.datatypes.ValidationException;

public class FormatterTest extends TestCase {

    public FormatterTest(java.lang.String testName) {
        super(testName);
    }

    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(FormatterTest.class);

        return suite;
    }

    public void setUp() {
    }

    public void tearDown() {
    }

    public void testFormatBoolean() {
        Boolean input = null;
        String layout = null;
        String output = null;

        layout = null;
        input = new Boolean(true);
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseBoolean(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = null;
        input = new Boolean(false);
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseBoolean(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = "The Truth,The Bull-Shit";
        input = new Boolean(true);
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseBoolean(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = "The Truth,The Bull-Shit";
        input = new Boolean(false);
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseBoolean(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();
    }


    public void testFormatInteger() throws ValidationException {
        Long input = null;
        String layout = null;
        String output = null;

        layout = null;
        input = new Long(1234567890);
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseInteger(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = "0,00,00,00,00,00,00,00";
        input = new Long(1234567890);
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseInteger(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();
    }

    public void testFormatDecimal() throws ValidationException {
        Double input = null;
        String layout = null;
        String output = null;

        layout = null;
        input = new Double(1234567890.6576);
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseDecimal(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = "0,00,00,00,00,00,00,00.000000";
        input = new Double(1234567890.6576);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseDecimal(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();
    }

    public void testFormatCurrency() throws ValidationException {
        Currency input = null;
        String layout = null;
        String output = null;

        layout = null;
        input = new Currency(1234567890.6576);
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseCurrency(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = "$0,00,00,00,00,00,00,00.000000";
        input = new Currency(1234567890.6576);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseCurrency(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();
    }

    public void testFormatDateTime() throws ValidationException {
        DateTime input = null;
        String layout = null;
        String output = null;

        layout = null;
        input = new DateTime();
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseDateTime(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = "dd/MMM/yyyy";
        input = new DateTime();
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseDateTime(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();
    }

    public void testFormatDateOnly() throws ValidationException {
        DateOnly input = null;
        String layout = null;
        String output = null;

        layout = null;
        input = new DateOnly();
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseDateOnly(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = "dd/MMM/yyyy";
        input = new DateOnly();
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        input = Parser.parseDateOnly(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        System.out.println();

        layout = "dd/MMM/yyyy";
        output = "n-10";
        input = Parser.parseDateOnly(output, layout);
        System.out.println( "Parsing- Layout: " + layout + " Input: " + output + " Output: " + input );
        output = Formatter.format(input, layout);
        System.out.println( "Formatting- Layout: " + layout + " Input: " + input + " Output: " + output );
        System.out.println();
    }

    public void testFormatRaw() throws ValidationException {
        byte[] input = null;
        String output = null;

        input = new byte[]{1,2,3,4,5,6,7,8,9,0};
        output = Formatter.format(input);
        System.out.println( "Formatting- Input: " + input + " Output: " + output );
        input = Parser.parseRaw(output);
        System.out.println( "Parsing- Input: " + output + " Output: " + input );
        System.out.println();

        output = "1234567890";
        input = Parser.parseRaw(output);
        System.out.println( "Parsing- Input: " + output + " Output: " + input );
        output = Formatter.format(input);
        System.out.println( "Formatting- Input: " + input + " Output: " + output );
        System.out.println();
    }

}

