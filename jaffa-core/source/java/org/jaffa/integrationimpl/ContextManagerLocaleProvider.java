package org.jaffa.integrationimpl;

import org.jaffa.integrationapi.LocaleProvider;
import org.jaffa.session.ContextManagerFactory;
import org.jaffa.util.LocaleHelper;
import org.jaffa.util.MessageHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created by szhou on 11/4/2016.
 */
public class ContextManagerLocaleProvider implements LocaleProvider {
    private final static String PREF_LANGUAGE_ID = "jaffa.user.prefLocale";
    private final static String DEFAULT_LANGUAGE_RULE = "commons.default.language";
    @Override
    public Locale getLocale() {
        String locale = (String) ContextManagerFactory.instance().getProperty(PREF_LANGUAGE_ID);
        if (locale == null) {
            locale = MessageHelper.findMessage(DEFAULT_LANGUAGE_RULE, null);
        }
        return LocaleHelper.string2Locale(locale);
    }

}
