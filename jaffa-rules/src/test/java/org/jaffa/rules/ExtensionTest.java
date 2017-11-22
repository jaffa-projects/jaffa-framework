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

package org.jaffa.rules;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.jaffa.datatypes.DateOnly;
import org.jaffa.datatypes.exceptions.InvalidForeignKeyException;
import org.jaffa.rules.testmodels.Extension1;
import org.jaffa.util.ExceptionHelper;
import org.springframework.context.ApplicationContext;


/**
 * @author PaulE
 */
public class ExtensionTest extends TestCase {

    private static Logger log = Logger.getLogger(ExtensionTest.class);
    private ApplicationContext ctx;

    /**
     * Creates new ExtensionTest
     *
     * @param name The name of the test case.
     */
    public ExtensionTest(String name) {
        super(name);
    }

    /**
     * Runs the test suite.
     *
     * @param args The input args are not used.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(ExtensionTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.setupRepos();
    }

    public void testAOPInjection() {
        //assertTrue(org.jboss.aop.Advised.class.isAssignableFrom(Extension1.class));
    }

    /*
    Commenting out until AOP interceptors are finished

    public void testDuplicateInDifferentMetaData() {
        log.debug("testDuplicateInDifferentMetaData");
        try {
            Extension1 obj = new Extension1();
            assertEquals("value2", obj.getField2());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
*/

    /*
    Commenting out until AOP interceptors are finished

    public void testDuplicateInDifferentFiles() {
        log.debug("testDuplicateInDifferentFiles");
        try {
            Extension1 obj = new Extension1();
            assertEquals("value2", obj.getField3());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    */

    public void testMinLengthOverride() {
        log.debug("testMinLengthOverride");
        try {
            Extension1 obj = new Extension1();
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj);
            assertEquals(new Integer(2), w.getMinLength());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testMaxLengthOverride() {
        log.debug("testMaxLengthOverride");
        try {
            Extension1 obj = new Extension1();
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field3", obj);
            assertEquals(new Integer(20), w.getMaxLength());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
    /*
        Commenting out until AOP interceptors are finished

        public void testLabel() {
            log.debug("testLabel");
            try {
                // This will obtain the label for the condition 'field1 == null'
                Extension1 obj = new Extension1();
                obj.setField1(null);
                IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj);
                assertEquals("label to use when field1 is null", w.getLabel());
                // This will obtain the label for the condition 'field1 != null'
                obj.setField1("value2");
                w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj);
                assertEquals("label to use when field1 is not null", w.getLabel());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }*/

    public void testLabelPassingClass() {
        log.debug("testLabel passing a Class instead of any other Object");
        try {
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(Extension1.class.getName(), "field1", null);
            assertNull("Label should have been null, since all the defined labels are conditional, and no bean was passed for condition-evaluation", w.getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    /*
        Commenting out until AOP interceptors are finished

        public void testDuplicateInSameMetaData() {
            log.debug("testDuplicateInSameMetaData");
            try {
                Extension1 obj = new Extension1();

                //ClassAdvisorHelper.debugListInterceptors(obj.getClass());

                assertEquals("value2", obj.getField1());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
 */
    public void testLabelAtObjectLevelPassingClass() {
        log.debug("testLabelAtObjectLevelPassingClass passing a Class instead of any other Object");
        try {
            IObjectRuleIntrospector w = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(Extension1.class.getName(), null);
            assertNull("Label should have been null, since all the defined labels are conditional, and no bean was passed for condition-evaluation", w.getLabel());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

        /*
    Commenting out until AOP interceptors are finished

        public void testLabelAtObjectLevel() {
            log.debug("testLabelAtObjectLevel");
            try {
                // This will obtain the label for the condition 'field1 == null'
                Extension1 obj = new Extension1();
                obj.setField1(null);
                IObjectRuleIntrospector w = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(obj.getClass().getName(), obj);
                assertEquals("object label to use when field1 is null", w.getLabel());

                // This will obtain the label for the condition 'field1 != null'
                obj.setField1("value2");
                w = RulesEngineFactory.getRulesEngine().getObjectRuleIntrospector(obj.getClass().getName(), obj);
                assertEquals("object label to use when field1 is not null", w.getLabel());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
*/

    public void testMinAndMaxValue() {
        log.debug("testMinAndMaxValue");
        try {
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(Extension1.class.getName(), "field4", null);
            assertEquals(1d, w.getMinValue());
            assertEquals(100d, w.getMaxValue());

            w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(Extension1.class.getName(), "field5", null);
            assertEquals(1L, w.getMinValue());
            assertEquals(100L, w.getMaxValue());

            // Note: This test may fail if it is executed on the stroke of midnight
            w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(Extension1.class.getName(), "field6", null);
            assertEquals(DateOnly.addDay(new DateOnly(), -10), w.getMinValue());
            assertEquals(new DateOnly(), w.getMaxValue());

            w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(Extension1.class.getName(), "field7", null);
            assertEquals(1, w.getMinValue());
            assertEquals(100, w.getMaxValue());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

        /*
    Commenting out until AOP interceptors are finished

        public void testMinValue() {
            log.debug("testMinValue");
            try {
                Extension1 obj = createExtension1();
                try {
                    obj.setField4(0.9);
                    obj.validate();
                    fail("The invocation Extension1.setField4(new Double(0.9)) should have failed since the minimum value is 1");
                } catch (Exception e) {
                    BelowMinimumException appExp = (BelowMinimumException) ExceptionHelper.extractException(e, BelowMinimumException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField4(1d);
                obj.validate();
                obj.setField4(1.1);
                obj.validate();

                try {
                    obj.setField5((long) -1);
                    obj.validate();
                    fail("The invocation Extension1.setField5(new Long(-1)) should have failed since the minimum value is 1");
                } catch (Exception e) {
                    BelowMinimumException appExp = (BelowMinimumException) ExceptionHelper.extractException(e, BelowMinimumException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField5(1L);
                obj.validate();
                obj.setField5(2L);
                obj.validate();

                try {
                    obj.setField6(Parser.parseDateOnly("t - 11"));
                    obj.validate();
                    fail("The invocation Extension1.setField6(new DateOnly(t - 11)) should have failed since the minimum value is 't-10'");
                } catch (Exception e) {
                    // A custom ApplicationException is expected
                    ApplicationException appExp = (ApplicationException) ExceptionHelper.extractException(e, ApplicationException.class);
                    if (appExp == null)
                        throw e;
                    assertEquals("aCustomErrorMessageTokenForField6", appExp.getMessage());

                    // The cause for the custom ApplicationException should be a BelowMinimumException
                    assertTrue("The cause of the custom ApplicationException should be an instance of BelowMinimumException", appExp.getCause() != null && appExp.getCause() instanceof BelowMinimumException);
                }
                obj.setField6(Parser.parseDateOnly("t - 10"));
                obj.validate();
                obj.setField6(Parser.parseDateOnly("t - 9"));
                obj.validate();

                try {
                    obj.setField7(0);
                    obj.validate();
                    fail("The invocation Extension1.setField7(0) should have failed since the minimum value is 1");
                } catch (Exception e) {
                    BelowMinimumException appExp = (BelowMinimumException) ExceptionHelper.extractException(e, BelowMinimumException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField7(1);
                obj.validate();
                obj.setField7(2);
                obj.validate();

            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
        */

        /*
    Commenting out until AOP interceptors are finished

        public void testMaxValue() {
            log.debug("testMaxValue");
            try {
                Extension1 obj = createExtension1();
                try {
                    obj.setField4(100.5);
                    obj.validate();
                    fail("The invocation Extension1.setField4(new Double(100.5)) should have failed since the maximum value is 100");
                } catch (Exception e) {
                    ExceedsMaximumException appExp = (ExceedsMaximumException) ExceptionHelper.extractException(e, ExceedsMaximumException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField4(100d);
                obj.validate();
                obj.setField4(99.5);
                obj.validate();

                try {
                    obj.setField5(101L);
                    obj.validate();
                    fail("The invocation Extension1.setField5(new Long(101)) should have failed since the maximum value is 100");
                } catch (Exception e) {
                    ExceedsMaximumException appExp = (ExceedsMaximumException) ExceptionHelper.extractException(e, ExceedsMaximumException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField5(100L);
                obj.validate();
                obj.setField5(99L);
                obj.validate();

                try {
                    obj.setField6(Parser.parseDateOnly("t + 1"));
                    obj.validate();
                    fail("The invocation Extension1.setField6(new DateOnly(t + 1)) should have failed since the maximum value is 't'");
                } catch (Exception e) {
                    ExceedsMaximumException appExp = (ExceedsMaximumException) ExceptionHelper.extractException(e, ExceedsMaximumException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField6(Parser.parseDateOnly("t"));
                obj.validate();
                obj.setField6(Parser.parseDateOnly("t - 1"));
                obj.validate();

                try {
                    obj.setField7(101);
                    obj.validate();
                    fail("The invocation Extension1.setField7(101) should have failed since the maximum value is 100");
                } catch (Exception e) {
                    ExceedsMaximumException appExp = (ExceedsMaximumException) ExceptionHelper.extractException(e, ExceedsMaximumException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField7(100);
                obj.validate();
                obj.setField7(99);
                obj.validate();

            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
        */


        /*
    Commenting out until AOP interceptors are finished


        public void testInListDates() {
            log.debug("testInListDates");
            try {
                Extension1 obj = createExtension1();
                try {
                    obj.setField8(Parser.parseDateOnly("t+1"));
                    obj.validate();
                    fail("The invocation Extension1.setField8(t+1) should have failed since it is not in the list of valid values");
                } catch (Exception e) {
                    PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField8(new DateOnly(2004, DateOnly.FEBRUARY, 15));
                obj.validate();
                obj.setField8(Parser.parseDateOnly("t"));
                obj.validate();
                obj.setField8(Parser.parseDateOnly("t-1"));
                obj.validate();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
        */

        /*
    Commenting out until AOP interceptors are finished

        public void testNumericalMinLength() {
            log.debug("testNumericalMinLength");
            try {
                Extension1 obj = createExtension1();
                try {
                    obj.setField9(1);
                    obj.validate();
                    fail("The invocation Extension1.setField9(1) should have failed since its less than the min-length");
                } catch (Exception e) {
                    TooLittleDataException appExp = (TooLittleDataException) ExceptionHelper.extractException(e, TooLittleDataException.class);
                    if (appExp == null)
                        throw e;
                }
                try {
                    obj.setField9(10);
                    obj.validate();
                    fail("The invocation Extension1.setField9(10) should have failed since its less than the min-length");
                } catch (Exception e) {
                    TooLittleDataException appExp = (TooLittleDataException) ExceptionHelper.extractException(e, TooLittleDataException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField9(1000);
                obj.validate();
                obj.setField9(1001);
                obj.validate();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
    */
/*
    Commenting out until AOP interceptors are finished

        public void testNumericalMaxLength() {
            log.debug("testNumericalMinLength");
            try {
                Extension1 obj = createExtension1();
                try {
                    obj.setField9(100000);
                    obj.validate();
                    fail("The invocation Extension1.setField9(100000) should have failed since its greater than the max-length");
                } catch (Exception e) {
                    TooMuchDataException appExp = (TooMuchDataException) ExceptionHelper.extractException(e, TooMuchDataException.class);
                    if (appExp == null)
                        throw e;
                }
                try {
                    obj.setField9(1000.001);
                    obj.validate();
                    fail("The invocation Extension1.setField9(1000.001) should have failed since its fractional part is greater than the max-length");
                } catch (Exception e) {
                    TooMuchDataException appExp = (TooMuchDataException) ExceptionHelper.extractException(e, TooMuchDataException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField9(10000);
                obj.validate();
                obj.setField9(1000.11);
                obj.validate();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
*/

    /*
        Commenting out until AOP interceptors are finished

            public void testPattern() {
                log.debug("testPattern");
                try {
                    Extension1 obj = createExtension1();
                    try {
                        obj.setField3("xyz");
                        obj.validate();
                        fail("The invocation Extension1.setField3(xyz) should have failed since it does not match the pattern");
                    } catch (Exception e) {
                        PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                        if (appExp == null)
                            throw e;
                    }
                    try {
                        obj.setField3("valuex");
                        obj.validate();
                        fail("The invocation Extension1.setField3(valuex) should have failed since it does not match the pattern");
                    } catch (Exception e) {
                        PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                        if (appExp == null)
                            throw e;
                    }
                    try {
                        obj.setField3("value");
                        obj.validate();
                        fail("The invocation Extension1.setField3(value) should have failed since it does not match the pattern");
                    } catch (Exception e) {
                        PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                        if (appExp == null)
                            throw e;
                    }
                    obj.setField3("value1");
                    obj.validate();
                    obj.setField3("value23");
                    obj.validate();
                } catch (Exception e) {
                    e.printStackTrace(System.err);
                    fail();
                }
            }
    */
    public void testLayout() {
        log.debug("testLayout");
        try {
            Extension1 obj = new Extension1();
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field4", obj);
            assertEquals("[decimalOptional.format]", w.getLayout());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

        /*
    Commenting out until AOP interceptors are finished

        public void testInList() {
            log.debug("testInList");
            try {
                Extension1 obj = createExtension1();
                try {
                    obj.setField1("value5");
                    obj.validate();
                    fail("The invocation Extension1.setField1(value5) should have failed since it is not in the list of valid values");
                } catch (Exception e) {
                    PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField1("value1");
                obj.validate();
                obj.setField1("value2");
                obj.validate();
                obj.setField1("value3");
                obj.validate();
                obj.setField1("value4");
                obj.validate();

                try {
                    obj.setField2("value5");
                    obj.validate();
                    fail("The invocation Extension1.setField2(value5) should have failed since it is not in the list of valid values");
                } catch (Exception e) {
                    PatternMismatchException appExp = (PatternMismatchException) ExceptionHelper.extractException(e, PatternMismatchException.class);
                    if (appExp == null)
                        throw e;
                }
                obj.setField2("value1");
                obj.validate();
                obj.setField2("value2");
                obj.validate();
                obj.setField2("value3");
                obj.validate();
                obj.setField2("value4");
                obj.validate();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
        */

    public void testFormatUsingPropertyTranslator() {
        log.debug("testFormatUsingPropertyTranslator");
        try {
            Extension1 obj = new Extension1();
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field1", obj);
            // This should utilize the PropertyTranslator for translating a value as per the layout "[label.module.prop.{0}]"
            assertEquals("[label.module.prop.xyz]", w.format("xyz"));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

        /*
    Commenting out until AOP interceptors are finished

        public void testForeignKey() {
            try {
                Extension1 obj = createExtension1();
                try {
                    obj.setField10("ZZZ");
                    obj.validate();
                    fail("The invocation Extension1.setField10(ZZZ) should have failed since we passed an invalid foreign-key");
                } catch (Exception e) {
                    InvalidForeignKeyException appExp = (InvalidForeignKeyException) ExceptionHelper.extractException(e, InvalidForeignKeyException.class);
                    if (appExp != null) {
                        Object[] arguments = appExp.getArguments();
                        assertNotNull("The InvalidForeignKeyException should have arguments", arguments);
                        assertTrue("The InvalidForeignKeyException should have arguments", arguments.length > 0);
                        assertEquals("The InvalidForeignKeyException should have been created for field10", "field10", arguments[0]);
                    } else {
                        throw e;
                    }
                }
                obj.setField10("KEY2");
                obj.validate();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
        */

    public void testFormat() {
        log.debug("testFormat");
        try {
            Extension1 obj = new Extension1();
            IPropertyRuleIntrospector w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field4", obj);
            // This should utilize the 'decimalOptional.format' layout specified in the rules file
            assertEquals("101", w.format(101d));

            w = RulesEngineFactory.getRulesEngine().getPropertyRuleIntrospector(obj.getClass().getName(), "field9", obj);
            // This should use the default layout, since no layout is specified in the rules file
            assertEquals("101.00", w.format(101d));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testCompositeForeignKey() {
        try {
            Extension1 obj = createExtension1();
            try {
                obj.setField10("KEY1");
                obj.setField5(4L);
                obj.validate();
            } catch (Exception e) {
                InvalidForeignKeyException appExp = (InvalidForeignKeyException) ExceptionHelper.extractException(e, InvalidForeignKeyException.class);
                if (appExp != null) {
                    Object[] arguments = appExp.getArguments();
                    assertNotNull("The InvalidForeignKeyException should have arguments", arguments);
                    assertTrue("The InvalidForeignKeyException should have arguments", arguments.length > 0);
                    assertEquals("The InvalidForeignKeyException should have been created for field10,field5", "field10,field5", arguments[0]);
                } else {
                    throw e;
                }
            }

            // The following is a valid foreign-key and should work
            obj.setField10("KEY1");
            obj.setField5(3L);
            obj.validate();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

        /*
    Commenting out until AOP interceptors are finished

        public void testGenericForeignKeyWithCondition() {
            try {
                Extension1 obj = createExtension1();
                obj.setField1(null); // This will ensure that the generic-foreign-key validation is applied to Field10
                try {
                    obj.setField10("ZZZ");
                    obj.validate();
                    fail("The invocation Extension1.setField10(ZZZ) should have failed since we passed an invalid generic-foreign-key");
                } catch (Exception e) {
                    InvalidGenericForeignKeyException appExp = (InvalidGenericForeignKeyException) ExceptionHelper.extractException(e, InvalidGenericForeignKeyException.class);
                    if (appExp != null) {
                        Object[] arguments = appExp.getArguments();
                        assertNotNull("The InvalidGenericForeignKeyException should have arguments", arguments);
                        assertTrue("The InvalidGenericForeignKeyException should have arguments", arguments.length > 0);
                        assertEquals("The InvalidGenericForeignKeyException should have been created for field10", "field10", arguments[0]);
                    } else {
                        throw e;
                    }
                }
                obj.setField10("VALUEA11");
                obj.validate();
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
*/
        
    /*
Commenting out until AOP interceptors are finished

    public void testGenericForeignKey() {
        try {
            Extension1 obj = createExtension1();
            try {
                obj.setField11(10L);
                obj.validate();
                fail("The invocation Extension1.setField11(10) should have failed since we passed an invalid generic-foreign-key");
            } catch (Exception e) {
                InvalidGenericForeignKeyException appExp = (InvalidGenericForeignKeyException) ExceptionHelper.extractException(e, InvalidGenericForeignKeyException.class);
                if (appExp != null) {
                    Object[] arguments = appExp.getArguments();
                    assertNotNull("The InvalidGenericForeignKeyException should have arguments", arguments);
                    assertTrue("The InvalidGenericForeignKeyException should have arguments", arguments.length > 0);
                    assertEquals("The InvalidGenericForeignKeyException should have been created for field11", "field11", arguments[0]);
                } else {
                    throw e;
                }
            }
            obj.setField11(1L);
            obj.validate();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
*/

    /**
     * Creates an Extension1 instance with valid data.
     */
    private Extension1 createExtension1() {
        Extension1 obj = new Extension1();
        obj.setField9(123); //set to at least 3 digits because of the min-length rule against this property
        obj.setField7(1); //set to at least 1 because of the min-value rule against this property
        return obj;
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        TestHelper.shutdownRepos();
    }
}
