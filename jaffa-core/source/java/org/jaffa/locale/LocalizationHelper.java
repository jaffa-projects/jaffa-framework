package org.jaffa.locale;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.regex.Pattern;
import static org.jaffa.session.ContextManagerFactory.getApplicationRule;

/**
 * This class is used for Localization for handling.
 */
public class LocalizationHelper {

	// A regular expression for matching right-to-left language codes.
	private static final Pattern RtlLocalesRe = Pattern
			.compile("^(ar|dv|he|iw|fa|nqo|ps|sd|ug|ur|yi|.*[-_](Arab|Hebr|Thaa|Nkoo|Tfng))"
					+ "(?!.*[-_](Latn|Cyrl)($|-|_))($|-|_)");


	/**
	 * This method checks the language value against the Patterns if the input
	 * language matches against Patterns returns true else returns false
	 */
	public static boolean isRTL(String locale) {
		return locale != null && RtlLocalesRe.matcher(locale).find();
	}


	/**
	 * This method retrieves the user language selection from the request and returns it after validating it against the apprule
	 * @param request
	 * @return
	 */
	public static String getLanguageFromRequestParam(HttpServletRequest request){
		// Retrieving the lang value from request object
		String language = request.getParameter("lang");

		String userPrefLanguages = getApplicationRule(UserLocaleProvider.SELECTABLE_LANGUAGES);
		if(userPrefLanguages != null && !userPrefLanguages.isEmpty() && language!=null && !language.isEmpty()
				&& Arrays.asList(userPrefLanguages.split("\\s*,\\s*")).contains(language)) {
			return language;
		}else{
			return "";
		}
	}

}