package org.jaffa.transaction.tester;

/**
 * A singleton test transaction
 */
public class TestMessageSingleton {

    private String emailAddress;
    private boolean fail = false;
    private long delay = 0L;
    private long dbQuery = 0L;
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public boolean isFail() {
        return fail;
    }
    public void setFail(boolean fail) {
        this.fail = fail;
    }
    public long getDelay() {
        return delay;
    }
    public void setDelay(long delay) {
        this.delay = delay;
    }
    public long getDbQuery() {
        return dbQuery;
    }
    public void setDbQuery(long dbQuery) {
        this.dbQuery = dbQuery;
    }
}
