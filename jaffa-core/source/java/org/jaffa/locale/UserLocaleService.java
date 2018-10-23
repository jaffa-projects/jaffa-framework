package org.jaffa.locale;

/**
 * UserLocaleService which holds the UserLocaleProvider implementation object
 */
public class UserLocaleService {


    private static UserLocaleProvider userLocaleProvider;

    public static UserLocaleProvider getUserLocaleProvider() {
        return userLocaleProvider;
    }

    public static void setUserLocaleProvider(UserLocaleProvider userLocaleProvider) {
        UserLocaleService.userLocaleProvider = userLocaleProvider;
    }

}
