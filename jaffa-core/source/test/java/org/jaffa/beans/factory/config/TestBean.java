package org.jaffa.beans.factory.config;

/**
 * A bean that is used for testing.
 */
public class TestBean {
    private String testString;
    private Integer testInteger;

    /**
     * return The String primitive value set on the POJO
     */
    public String getTestString() {
        return testString;
    }

    /**
     * @param testString the String primitive value to set on this POJO
     */
    public void setTestString(String testString) {
        this.testString = testString;
    }

    /**
     * return The Integer primitive value set on the POJO
     */
    public Integer getTestInteger() {
        return testInteger;
    }

    /**
     * @param testInteger the Integer primitive value to set on this POJO
     */
    public void setTestInteger(Integer testInteger) {
        this.testInteger = testInteger;
    }
}
