package org.jaffa.datatypes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DateBaseTest {

    @Test
    //JAFFA-686 Incorrect century shown when user provide year in 2 digit format
    public void parseYearWhen2DigitProvided() throws Exception {

        //Scenario 1
        // Arrrage, Act : Passing 2 digit year "56" and expecting out come as 2056
        DateTime dateTime = DateTime.parse("560425","yyMMdd");
        //Asset : Verify the out put is 2056
        assertEquals(Integer.parseInt("2056"), dateTime.year());
        //Scenario 2
        // Arrrage, Act : Passing 2 digit year "15" and expecting out come as 2015
        DateTime dateTime1 = DateTime.parse("150425","yyMMdd");
        assertEquals(Integer.parseInt("2015"), dateTime1.year());
    }
}