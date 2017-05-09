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

import org.jaffa.metadata.*;
import java.text.*;
import junit.framework.*;

public class FieldValidatorTest extends TestCase {
    private    String f_in = null;
    private    Object f_out = null;
    private    FieldMetaData f_meta = null;

    public FieldValidatorTest(java.lang.String testName) {
        super(testName);
    }

    public static void main(java.lang.String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public static Test suite() {
        TestSuite suite = new TestSuite(FieldValidatorTest.class);

        return suite;
    }

    public void setUp() {
    }

    public void tearDown() {
        String f_in = null;
        Object f_out = null;
        FieldMetaData f_meta = null;
    }



    public void testValidateBoolean() {
        try {
            f_in = null;
            f_meta = new BooleanFieldMetaData("someName", "someToken",
            new Boolean(true), "Yes,No", "(yes|true|1)|(no|false|0)" );
            f_out = FieldValidator.validateData(f_in, f_meta);
            System.out.println( "Boolean: fin " + f_in + " fout " + f_out);

            f_in = "yES";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "Boolean: fin " + f_in + " fout " + f_out);

            f_in = "0";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "Boolean: fin " + f_in + " fout " + f_out);
        } catch (ValidationException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testValidateString() {
        try {
            f_in = null;
            f_meta = new StringFieldMetaData("someName", "someToken",
            new Boolean(true), "FALSE", new Integer(10), FieldMetaData.UPPER_CASE );
            f_out = FieldValidator.validateData(f_in, f_meta);
            System.out.println( "String: fin " + f_in + " fout " + f_out);

            f_in = "faLSe";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "String: fin " + f_in + " fout " + f_out);
        } catch (ValidationException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testValidateInteger() {
        try {
            f_in = null;
            f_meta = new IntegerFieldMetaData("someName", "someToken",
            new Boolean(true), null, new Long(10), new Long(100), new Integer(2) );
            f_out = FieldValidator.validateData(f_in, f_meta);
            System.out.println( "Integer: fin " + f_in + " fout " + f_out);

            f_in = "99";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "Integer: fin " + f_in + " fout " + f_out);
        } catch (ValidationException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testValidateDecimal() {
        try {
            f_in = null;
            f_meta = new DecimalFieldMetaData("someName", "someToken",
            new Boolean(true), null, new Double(10), new Double(100), new Integer(2), new Integer(3)  );
            f_out = FieldValidator.validateData(f_in, f_meta);
            System.out.println( "Decimal: fin " + f_in + " fout " + f_out);

            f_in = "99.001";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "Decimal: fin " + f_in + " fout " + f_out);
        } catch (ValidationException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testValidateCurrency() {
        try {
            f_in = null;
            f_meta = new CurrencyFieldMetaData("someName", "someToken",
            new Boolean(true), null, new Currency(10), new Currency(100), new Integer(3), new Integer(2)  );
            f_out = FieldValidator.validateData(f_in, f_meta);
            System.out.println( "Currency: fin " + f_in + " fout " + f_out);

            f_in = "$99.99";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "Currency: fin " + f_in + " fout " + f_out);
        } catch (ValidationException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testValidateDateOnly() {
        try {
            f_in = null;
            f_meta = new DateOnlyFieldMetaData("someName", "someToken",
            new Boolean(true), null, DateOnly.parse("n+1"), DateOnly.parse("n+10") );
            f_out = FieldValidator.validateData(f_in, f_meta);
            System.out.println( "DateOnly: fin " + f_in + " fout " + f_out);

            f_in = "n+2";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "DateOnly: fin " + f_in + " fout " + f_out);
        } catch (ValidationException e) {
            e.printStackTrace();
            fail();
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testValidateDateTime() {
        try {
            f_in = null;
            f_meta = new DateTimeFieldMetaData("someName", "someToken",
            new Boolean(true), null, DateTime.parse("n+1h"), DateTime.parse("n+10") );
            f_out = FieldValidator.validateData(f_in, f_meta);
            System.out.println( "DateTime: fin " + f_in + " fout " + f_out);

            f_in = "n+10d-2h";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "DateTime: fin " + f_in + " fout " + f_out);
        } catch (ValidationException e) {
            e.printStackTrace();
            fail();
        } catch (ParseException e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testValidateRaw() {
        try {
            f_in = null;
            f_meta = new RawFieldMetaData("someName", "someToken",
            new Boolean(true) );
            f_out = FieldValidator.validateData(f_in, f_meta);
            System.out.println( "Raw: fin " + f_in + " fout " + f_out);

            f_in = "LotsaData";
            f_out = FieldValidator.validateData(f_in, f_meta, true);
            System.out.println( "Raw: fin " + f_in + " fout " + f_out
            + " byteToString " + new String( (byte[]) f_out) );
        } catch (ValidationException e) {
            e.printStackTrace();
            fail();
        }
    }
}

