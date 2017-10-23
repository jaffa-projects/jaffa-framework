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
import org.jaffa.rules.testmodels.Strings1;
import org.springframework.context.ApplicationContext;

import static org.jaffa.rules.testmodels.StringsConstants.DATA4;

/**
 * @author PaulE
 */
public class StringsTest extends TestCase {

    private ApplicationContext ctx;

    /**
     * Creates new AccessTest
     *
     * @param name The name of the test case.
     */
    public StringsTest(String name) {
        super(name);
    }

    /**
     * Runs the test suite.
     *
     * @param args The input args are not used.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(StringsTest.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestHelper.setupRepos();
    }

    public void testAOPInjection() {
        //assertTrue(org.jboss.aop.Advised.class.isAssignableFrom(Strings1.class));
    }

    /*
        Commenting out until AOP interceptors are finished

        public void testMixedCaseField() {
            try {
                Strings1 obj = new Strings1();
                assertEquals(DATA1, obj.getField1());
                obj.setField1(DATA1);
                obj.validateSuper();
                assertEquals(DATA1, obj.getField1());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }

        public void testLowerCaseField() {
            try {
                Strings1 obj = new Strings1();
                assertEquals(DATA2, obj.getField2());
                obj.setField2(DATA2);
                obj.validateSuper();
                assertEquals(DATA2.toLowerCase(), obj.getField2());
            } catch (Exception e) {
                e.printStackTrace(System.err);
                fail();
            }
        }
    */
    public void testMixedThenUpperCaseField() {
        try {
            Strings1 obj = new Strings1();
            assertEquals(DATA4, obj.getField4());
            obj.setField4(DATA4);
            assertEquals(DATA4, obj.getField4());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }
/*
    Commenting out until AOP interceptors are finished

    public void testUpperCaseField() {
        try {
            Strings1 obj = new Strings1();
            assertEquals(DATA3, obj.getField3());
            obj.setField3(DATA3);
            obj.validateSuper();
            assertEquals(DATA3.toUpperCase(), obj.getField3());
        } catch (Exception e) {
            e.printStackTrace(System.err);
            fail();
        }
    }

    public void testMinLengthField() {
        Strings1 obj = new Strings1();
        try {
            assertEquals(DATA2, obj.getField2());
            String data = DATA2.substring(1, 2);
            obj.setField2(data);
            obj.validateSuper();
            fail();
        } catch (Exception e) {
            TooLittleDataException appExp = (TooLittleDataException) ExceptionHelper.extractException(e, TooLittleDataException.class);
            if (appExp != null) {
                Object[] arguments = appExp.getArguments();
                assertNotNull("The TooLittleDataException should have arguments", arguments);
                assertTrue("The TooLittleDataException should have arguments", arguments.length > 0);
                assertEquals("The TooLittleDataException should have been created for field2", "field2", arguments[0]);
                assertEquals("The TooLittleDataException should have the limit 3", "3", arguments[1]);
            } else {
                e.printStackTrace(System.err);
                fail();
            }
        }
    }

    public void testMaxLengthField() {
        Strings1 obj = new Strings1();
        try {
            assertEquals(DATA3, obj.getField3());
            String data = StringHelper.replicate(DATA3, 10);
            obj.setField3(data);
            obj.validateSuper();
            fail();
        } catch (Exception e) {
            TooMuchDataException appExp = (TooMuchDataException) ExceptionHelper.extractException(e, TooMuchDataException.class);
            if (appExp != null) {
                Object[] arguments = appExp.getArguments();
                assertNotNull("The TooMuchDataException should have arguments", arguments);
                assertTrue("The TooMuchDataException should have arguments", arguments.length > 0);
                //assertEquals("The TooMuchDataException should have been created for field3 with label [title.Jaffa.HistoryNav.Home]", "[title.Jaffa.HistoryNav.Home]", arguments[0]);
                assertEquals("The TooMuchDataException should have been created for field3 with label field3", "field3", arguments[0]);
                assertEquals("The TooMuchDataException should have the limit 10", "10", arguments[1]);
            } else {
                e.printStackTrace(System.err);
                fail();
            }
        }
    }

    public void testMandatory() {
        Strings1 obj = new Strings1();
        try {
            obj.setField1(null);
            try {
                obj.validateSuper();
                fail("testMandatory() should have failed, since we had a null value for the mandatory 'field1'");
            } catch (Exception e) {
                MandatoryFieldException appExp = (MandatoryFieldException) ExceptionHelper.extractException(e, MandatoryFieldException.class);
                if (appExp == null) {
                    ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                    if (appExps != null) {
                        for (ApplicationException ae : appExps.getApplicationExceptionArray()) {
                            if (ae instanceof MandatoryFieldException) {
                                appExp = (MandatoryFieldException) ae;
                                break;
                            }
                        }
                    }
                }
                if (appExp != null) {
                    Object[] arguments = appExp.getArguments();
                    assertNotNull("The MandatoryFieldException should have arguments", arguments);
                    assertTrue("The MandatoryFieldException should have arguments", arguments.length > 0);
                    assertEquals("The MandatoryFieldException should have been created for field1", "field1", arguments[0]);
                } else {
                    e.printStackTrace();
                    fail();
                }
            }

            // This should work
            obj.setField1("SomeValue");
            obj.validateSuper();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testCandidateKey() {
        Strings1 obj = new Strings1();
        try {
            obj.setField3("FIELD31");
            obj.setField4("Field41");
            try {
                obj.validateSuper();
                fail("testCandidateKey() should have failed, since we passed an existing candidate-key");
            } catch (Exception e) {
                DuplicateCandidateKeyException appExp = (DuplicateCandidateKeyException) ExceptionHelper.extractException(e, DuplicateCandidateKeyException.class);
                if (appExp == null) {
                    ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                    if (appExps != null) {
                        for (ApplicationException ae : appExps.getApplicationExceptionArray()) {
                            if (ae instanceof DuplicateCandidateKeyException) {
                                appExp = (DuplicateCandidateKeyException) ae;
                                break;
                            }
                        }
                    }
                }
                if (appExp != null) {
                    Object[] arguments = appExp.getArguments();
                    assertNotNull("The DuplicateCandidateKeyException should have arguments", arguments);
                    assertTrue("The DuplicateCandidateKeyException should have arguments", arguments.length > 1);
                    assertEquals("The DuplicateCandidateKeyException should have been created for object", "Strings1", arguments[0]);
                    assertEquals("The DuplicateCandidateKeyException should have been created for fields", "Field3,Field4", arguments[1]);
                } else {
                    e.printStackTrace();
                    fail();
                }
            }

            // The following has been commented to avoid the hassle of adding more logic to the custom UOW class for handling the IS_NULL criteria
//            // Pass an incomplete candidate-key. Should work
//            obj.setField3(null);
//            obj.setField4("Field41");
//            obj.validateSuper();

            // This should work
            obj.setField3("FIELD31");
            obj.setField4("Field42");
            obj.validateSuper();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testCandidateKey2() {
        Strings1 obj = new Strings1();
        try {
            obj.setField2("field21");
            obj.setField3("FIELD31");
            try {
                obj.validateSuper();
                fail("testCandidateKey() should have failed, since we passed an existing candidate-key");
            } catch (Exception e) {
                DuplicateCandidateKeyException appExp = (DuplicateCandidateKeyException) ExceptionHelper.extractException(e, DuplicateCandidateKeyException.class);
                if (appExp == null) {
                    ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                    if (appExps != null) {
                        for (ApplicationException ae : appExps.getApplicationExceptionArray()) {
                            if (ae instanceof DuplicateCandidateKeyException) {
                                appExp = (DuplicateCandidateKeyException) ae;
                                break;
                            }
                        }
                    }
                }
                if (appExp != null) {
                    Object[] arguments = appExp.getArguments();
                    assertNotNull("The DuplicateCandidateKeyException should have arguments", arguments);
                    assertTrue("The DuplicateCandidateKeyException should have arguments", arguments.length > 1);
                    assertEquals("The DuplicateCandidateKeyException should have been created for object", "Strings1", arguments[0]);
                    assertEquals("The DuplicateCandidateKeyException should have been created for fields", "Field2,Field3", arguments[1]);
                } else {
                    e.printStackTrace();
                    fail();
                }
            }

            // The following has been commented since we have now implemented the primary-key interceptor too!
//            // Now set the primary-key to represent the same row as the candidate-key. This will work
//            obj.setField1("Field11");
//            obj.validateSuper();
//
//            // Now set the primary-key to represent a different row. This should fail
//            try {
//                obj.setField1("Field12");
//                obj.validateSuper();
//                fail("testCandidateKey() should have failed, since we passed an existing candidate-key with a different primary-key");
//            } catch (Exception e) {
//                DuplicateCandidateKeyException appExp = (DuplicateCandidateKeyException) ExceptionHelper.extractException(e, DuplicateCandidateKeyException.class);
//                if(appExp != null) {
//                    Object[] arguments = appExp.getArguments();
//                    assertNotNull("The DuplicateCandidateKeyException should have arguments", arguments);
//                    assertTrue("The DuplicateCandidateKeyException should have arguments", arguments.length > 1);
//                    assertEquals("The DuplicateCandidateKeyException should have been created for object", "Strings1", arguments[0]);
//                    assertEquals("The DuplicateCandidateKeyException should have been created for fields", "Field2,Field3", arguments[1]);
//                } else {
//                    e.printStackTrace();
//                    fail();
//                }
//            }

            // Pass a non-existing candidate-key. This should work
            obj.setField2("Field24");
            obj.setField3("FIELD34");
            obj.validateSuper();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    public void testPrimaryKey() {
        Strings1 obj = new Strings1();
        try {
            obj.setField1("Field12");
            try {
                obj.validateSuper();
                fail("testPrimaryKey() should have failed, since we passed an existing primary-key");
            } catch (Exception e) {
                DuplicateKeyException appExp = (DuplicateKeyException) ExceptionHelper.extractException(e, DuplicateKeyException.class);
                if (appExp == null) {
                    ApplicationExceptions appExps = ExceptionHelper.extractApplicationExceptions(e);
                    if (appExps != null) {
                        for (ApplicationException ae : appExps.getApplicationExceptionArray()) {
                            if (ae instanceof DuplicateKeyException) {
                                appExp = (DuplicateKeyException) ae;
                                break;
                            }
                        }
                    }
                }
                if (appExp != null) {
                    Object[] arguments = appExp.getArguments();
                    assertNotNull("The DuplicateKeyException should have arguments", arguments);
                    assertTrue("The DuplicateKeyException should have arguments", arguments.length > 0);
                    assertEquals("The DuplicateKeyException should have been created for object", "Strings1", arguments[0]);
                } else {
                    e.printStackTrace();
                    fail();
                }
            }

            // This should work
            obj.setField1("Field15");
            obj.validateSuper();

        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
    */
}
