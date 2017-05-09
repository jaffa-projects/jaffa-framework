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

package org.jaffa.persistence.blackboxtests;

import junit.framework.TestCase;
import org.jaffa.persistence.domainobjects.*;
import org.jaffa.persistence.UOW;
import org.jaffa.persistence.Criteria;
import java.util.*;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.jaffa.datatypes.exceptions.*;
import org.jaffa.rules.RulesEngine;
import org.jaffa.security.VariationContext;
import org.jaffa.datatypes.Currency;
import org.jaffa.datatypes.*;

/** This class has various tests for the Dynamic Rules Engine
 *
 * @author GautamJ
 */
public class DynamicRulesTest extends TestCase {

    private UOW m_uow = null;

    /** Assembles and returns a test suite containing all known tests.
     * @return A test suite.
     */
    public static Test suite() {
        return new Wrapper(new TestSuite(DynamicRulesTest.class));
    }

    /** Creates new QueryTest
     * @param name The name of the test case.
     */
    public DynamicRulesTest(String name) {
        super(name);
    }

    /** Sets up the fixture, by creating the UOW. This method is called before a test is executed.
     */
    protected void setUp() {
        try {
            m_uow = new UOW();
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to create a UOW: " + e.toString());
        }
    }

    /** Tears down the fixture, by closing the UOW. This method is called after a test is executed.
     */
    protected void tearDown() {
        try {
            if (m_uow != null)
                m_uow.rollback();
            m_uow = null;
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to rollback a UOW: " + e.toString());
        }
    }

    /** This will directly invoke the doAllValidationsForDomainField() method of the RulesEngine, for validating the rules for the ItemId field of the class Item.
     * The ItemId field has been defined to be mandatory in the core-rules.xml file.
     */
    public void testMandatoryFieldValidator() {
        try {

            // An exception should be raised while updating a mandatory field with a null value
            try {
                RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), "ItemId", null, m_uow);
                fail("No exception was raised while updating a mandatory field with a null value");
            } catch (MandatoryFieldException e) {
                // the exception is expected
            }


            // An exception should be raised while updating a mandatory field with an empty string
            try {
                RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), "ItemId", "", m_uow);
                fail("No exception was raised while updating a mandatory field with an empty string");
            } catch (MandatoryFieldException e) {
                // the exception is expected
            }


            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField(ItemMeta.getName(), "ItemId", "IID1", m_uow);
            } catch (MandatoryFieldException e) {
                e.printStackTrace();
                fail("Exception was raised while updating a mandatory field with a non-null value");
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This will create an instance of the Item domain class and update its Condition field, which internally will invoke the RulesEngine for validating the field.
     * The Condition field has been defined to be a valid foreign-key in the core-rules.xml file.
     */
    public void testForeignKeyFieldValidator() {
        try {
            Item item = (Item) m_uow.newPersistentInstance(Item.class);

            // The following update should flow through without any exceptions
            try {
                item.updateCondition("Z-TESTSYCD-01");
            } catch (InvalidForeignKeyException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid foreign-key value");
            }

            // The following invalid foreign-key should raise an exception
            try {
                item.updateCondition("ZZZZZZ");
                fail("No exception was raised while updating a field with an invalid foreign-key value");
            } catch (InvalidForeignKeyException e) {
                // the exception is expected
            }

            // No checks should be performed while updating a non-mandatory field with a null value
            try {
                item.updateCondition(null);
            } catch (InvalidForeignKeyException e) {
                e.printStackTrace();
                fail("Exception was raised while updating a non-mandatory field with a null value");
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This will create an instance of the Item domain class and update its Status1 field, which internally will invoke the RulesEngine for validating the field.
     * The Status1 field has been defined to be a valid generic-foreign-key in the core-rules.xml file.
     */
    public void testGenericForeignKeyFieldValidator() {
        try {
            Item item = (Item) m_uow.newPersistentInstance(Item.class);

            // The following update should flow through without any exceptions
            try {
                item.updateStatus1("Y");
            } catch (InvalidGenericForeignKeyException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid generic-foreign-key value");
            }

            // The following invalid value should raise an exception
            try {
                item.updateStatus1("Z");
                fail("No exception was raised while updating a field with an invalid generic-foreign-key value");
            } catch (InvalidGenericForeignKeyException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This will create an instance of the Item domain class and update its Status3 field, which internally will invoke the RulesEngine for validating the field.
     * The Status3 field has been assigned a list of valid values in the core-rules.xml file.
     */
    public void testInListFieldValidator() {
        try {
            Item item = (Item) m_uow.newPersistentInstance(Item.class);

            // The following update should flow through without any exceptions
            try {
                item.updateStatus3("P");
            } catch (PatternMismatchException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid in-list value");
            }

            // The following invalid value should raise an exception
            try {
                item.updateStatus3("K");
                fail("No exception was raised while updating a field with an invalid in-list value");
            } catch (PatternMismatchException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This will create an instance of the Item domain class and update its Status2 field, which internally will invoke the RulesEngine for validating the field.
     * The Status2 field has been defined to extend the Status1 rules, which in turn has been defined to be a valid generic-foreign-key value in the core-rules.xml file.
     */
    public void testExtendsFieldValidation() {
        try {
            Item item = (Item) m_uow.newPersistentInstance(Item.class);

            // The following update should flow through without any exceptions
            try {
                item.updateStatus2("Y");
            } catch (InvalidGenericForeignKeyException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid generic-foreign-key value");
            }

            // The following invalid value should raise an exception
            try {
                item.updateStatus2("Z");
                fail("No exception was raised while updating a field with an invalid generic-foreign-key value");
            } catch (InvalidGenericForeignKeyException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This will set the variation to 'VAR1' and then invoke the rules for the Status1, Status2 and Status3 fields of Item
     */
    public void testOverridesFieldValidation() {
        try {
            VariationContext.setVariation("VAR1");
            Item item = (Item) m_uow.newPersistentInstance(Item.class);

            // *** The Status1 field is defined only in the core-rules.xml file ***
            // The following update should flow through without any exceptions
            try {
                item.updateStatus1("Y");
            } catch (InvalidGenericForeignKeyException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid generic-foreign-key value");
            }

            // The following invalid value should raise an exception
            try {
                item.updateStatus1("Z");
                fail("No exception was raised while updating a field with an invalid generic-foreign-key value");
            } catch (InvalidGenericForeignKeyException e) {
                // the exception is expected
            }




            // *** The Status2 field is also defined in the VAR1-rules.xml file ***
            // The following update should pass the generic-field-key rule, but should fail the integer rule
            try {
                item.updateStatus2("Y");
                fail("No exception was raised while updating a field with an invalid integer value");
            } catch (FormatIntegerException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                item.updateStatus2("3");
                fail("No exception was raised while updating a field with an invalid generic-foreign-key value");
            } catch (InvalidGenericForeignKeyException e) {
                // the exception is expected
            }

            // The following update should pass the generic-field-key rule, as well as the integer rule
            try {
                item.updateStatus2("1");
            } catch (InvalidGenericForeignKeyException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid generic-foreign-key value");
            } catch (FormatIntegerException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid integer value");
            }




            // *** The Status3 field is also defined in the VAR1-rules.xml file, but it overrides the core-rules.xml ***
            // The following update should flow through without any exceptions
            try {
                item.updateStatus3("I");
            } catch (PatternMismatchException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid in-list value");
            }

            // The following invalid value (though it is valid in core-rules.xml) should raise an exception
            try {
                item.updateStatus3("P");
                fail("No exception was raised while updating a field with an invalid in-list value");
            } catch (PatternMismatchException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This should perform the mandatory field validations for a domain object
     */
    public void testDomainObjectValidation() {
        try {
            Item item = (Item) m_uow.newPersistentInstance(Item.class);
            try {
                RulesEngine.doMandatoryValidationsForDomainObject(item, m_uow);
                fail("No exception was raised while trying to validate a domain object with null values for the mandatory fields");
            } catch (Exception e) {
                // the exception is expected
            }

            item.updateItemId("IID1");
            try {
                RulesEngine.doMandatoryValidationsForDomainObject(item, m_uow);
            } catch (Exception e) {
                e.printStackTrace();
                fail("Exception was raised while trying to validate a domain object with non-null values for the mandatory fields");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This tests the StringFieldValidator.
     */
    public void testStringFieldValidator() {
        try {
            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "StringField", "ABCDE", m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid string value");
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "StringField", "PQRSTU", m_uow);
                fail("No exception was raised while updating a field with an invalid string value");
            } catch (TooMuchDataException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "StringField", "XYZ", m_uow);
                fail("No exception was raised while updating a field with an invalid string value");
            } catch (PatternMismatchException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This tests the BooleanFieldValidator.
     */
    public void testBooleanFieldValidator() {
        try {
            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "BooleanField", "true", m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid boolean value");
            }

            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "BooleanField", Boolean.FALSE, m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid boolean value");
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "BooleanField", "zzzzz", m_uow);
                fail("No exception was raised while updating a field with an invalid boolean value");
            } catch (PatternMismatchException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This tests the CurrencyFieldValidator.
     */
    public void testCurrencyFieldValidator() {
        try {
            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "CurrencyField", "101", m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid Currency value");
            }

            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "CurrencyField", new Currency(101), m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid Currency value");
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "CurrencyField", "zzzzz", m_uow);
                fail("No exception was raised while updating a field with an invalid Currency value");
            } catch (FormatCurrencyException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "CurrencyField", "99", m_uow);
                fail("No exception was raised while updating a field with an invalid Currency value");
            } catch (BelowMinimumException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "CurrencyField", "5001", m_uow);
                fail("No exception was raised while updating a field with an invalid Currency value");
            } catch (ExceedsMaximumException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "CurrencyField", "101.12345678", m_uow);
                fail("No exception was raised while updating a field with an invalid Currency value");
            } catch (TooMuchDataException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This tests the DateOnlyFieldValidator.
     */
    public void testDateOnlyFieldValidator() {
        try {
            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateOnlyField", "4/22/2003", m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid DateOnly value");
            }

            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateOnlyField", new DateOnly(2003, DateOnly.APRIL, 22), m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid DateOnly value");
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateOnlyField", "zzzzz", m_uow);
                fail("No exception was raised while updating a field with an invalid DateOnly value");
            } catch (FormatDateOnlyException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateOnlyField", "4/22/1999", m_uow);
                fail("No exception was raised while updating a field with an invalid DateOnly value");
            } catch (BelowMinimumException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateOnlyField", "4/22/2013", m_uow);
                fail("No exception was raised while updating a field with an invalid DateOnly value");
            } catch (ExceedsMaximumException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This tests the DateTimeFieldValidator.
     */
    public void testDateTimeFieldValidator() {
        try {
            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateTimeField", "4/22/2003", m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid DateTime value");
            }

            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateTimeField", new DateTime(2003, DateTime.APRIL, 22), m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid DateTime value");
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateTimeField", "zzzzz", m_uow);
                fail("No exception was raised while updating a field with an invalid DateTime value");
            } catch (FormatDateTimeException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateTimeField", "4/22/1999", m_uow);
                fail("No exception was raised while updating a field with an invalid DateTime value");
            } catch (BelowMinimumException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DateTimeField", "4/22/2013", m_uow);
                fail("No exception was raised while updating a field with an invalid DateTime value");
            } catch (ExceedsMaximumException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }


    /** This tests the DecimalFieldValidator.
     */
    public void testDecimalFieldValidator() {
        try {
            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DecimalField", "101", m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid Decimal value");
            }

            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DecimalField", new Double(101), m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid Decimal value");
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DecimalField", "zzzzz", m_uow);
                fail("No exception was raised while updating a field with an invalid Decimal value");
            } catch (FormatDecimalException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DecimalField", "99", m_uow);
                fail("No exception was raised while updating a field with an invalid Decimal value");
            } catch (BelowMinimumException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DecimalField", "5001", m_uow);
                fail("No exception was raised while updating a field with an invalid Decimal value");
            } catch (ExceedsMaximumException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "DecimalField", "101.12345678", m_uow);
                fail("No exception was raised while updating a field with an invalid Decimal value");
            } catch (TooMuchDataException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    /** This tests the IntegerFieldValidator.
     */
    public void testIntegerFieldValidator() {
        try {
            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "IntegerField", "101", m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid Integer value");
            }

            // The following update should flow through without any exceptions
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "IntegerField", new Long(101), m_uow);
            } catch (ValidationException e) {
                e.printStackTrace();
                fail("Exception was raised while updating the field with a valid Integer value");
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "IntegerField", "zzzzz", m_uow);
                fail("No exception was raised while updating a field with an invalid Integer value");
            } catch (FormatIntegerException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "IntegerField", "99", m_uow);
                fail("No exception was raised while updating a field with an invalid Integer value");
            } catch (BelowMinimumException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "IntegerField", "5001", m_uow);
                fail("No exception was raised while updating a field with an invalid Integer value");
            } catch (ExceedsMaximumException e) {
                // the exception is expected
            }

            // The following invalid value should raise an exception
            try {
                RulesEngine.doAllValidationsForDomainField("Dummy", "IntegerField", "50000", m_uow);
                fail("No exception was raised while updating a field with an invalid Integer value");
            } catch (TooMuchDataException e) {
                // the exception is expected
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

}
