package org.jaffa.locale;

import org.apache.log4j.Logger;
import org.jaffa.security.VariationContext;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.LocaleHelper;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;

import static org.jaffa.session.ContextManagerFactory.getApplicationRule;

public class UserPrefLocaleService implements UserLocaleProvider{

    private static final Logger log = Logger.getLogger(UserPrefLocaleService.class);

    /**
     * Core implementation that will return the user, common, browser or default locale
     * @return String
     */
    @Override
    public String getLocale() {
        String locale = "";
        if(getApplicationRule(UserLocaleProvider.PREF_LOCALE_ID)!=null){
            locale = getApplicationRule(UserLocaleProvider.PREF_LOCALE_ID);

            if(log.isDebugEnabled()){
                log.debug("Locale found from Application Rule: "+ UserLocaleProvider.PREF_LOCALE_ID +" :"+locale);
            }
        }
        return locale;
    }

    /**
     * Core Implementation Will save to the user.properties
     * @param userSelectedLocale
     */
    @Override
    public void saveLocale(String userSelectedLocale) {
        try {
            ContextManagerFactory.instance().setUserPreference(PREF_LOCALE_ID, userSelectedLocale);
        } catch (IOException e) {
            log.error("Exception while saving User selected Locale", e);
        }
    }


}
