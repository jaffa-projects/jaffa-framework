package org.jaffa.locale;

import javax.servlet.http.HttpServletRequest;

public interface UserLocaleProvider {

    public static final String PREF_LOCALE_ID = "jaffa.user.prefLocale";

    public static final String DEF_LOCALE_RULE = "commons.default.language";

    public static final String SELECTABLE_LANGUAGES = "commons.selectable.languages";

    public static final String DEF_LOCALE = "en_US";

    public String getLocale();


    public void saveLocale(String locale);

}
