package org.jaffa.datatypes;


import org.jaffa.datatypes.exceptions.FormatDecimalException;
import org.jaffa.presentation.portlet.session.LocaleContext;
import org.junit.Assert;
import org.junit.Test;

import java.util.Locale;

public class ParserTest {

    //Test Locale English parse
    @Test
    public void test_ParseDecimal_EN() {
        Double aDouble = null;
        try {
            LocaleContext.setLocale(new Locale("en", "US"));
            aDouble = Parser.parseDecimal("1000.0", null);
            Assert.assertEquals(1000.0, aDouble.doubleValue(), 0.00);
        } catch (FormatDecimalException e) {
            Assert.fail(e.getMessage());
        }
    }

    //Test Locale Norwegian Bokmal parse
    @Test
    public void test_ParseDecimal_NB() {
        Double aDouble = null;
        try {
            LocaleContext.setLocale(new Locale("nb", "NO"));
            aDouble = Parser.parseDecimal("100000.0", null);
            Assert.assertEquals(100000.0, aDouble.doubleValue(), 0.00);
        } catch (FormatDecimalException e) {
            Assert.fail(e.getMessage());
        }
    }

    //Test Locale Arabic parse
    @Test
    public void test_ParseDecimal_AR() {
        Double aDouble = null;
        try {
            LocaleContext.setLocale(new Locale("ar", "OM"));
            aDouble = Parser.parseDecimal("1000.0", null);
            Assert.assertEquals(1000.0, aDouble.doubleValue(), 0.00);
        } catch (FormatDecimalException e) {
            Assert.fail(e.getMessage());
        }
    }
}
