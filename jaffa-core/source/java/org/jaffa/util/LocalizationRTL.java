package org.jaffa.util;

import java.util.regex.Pattern;

/**
 * This class is used for Localization for handling RTL.
 */
public class LocalizationRTL {

	// A regular expression for matching right-to-left language codes.
	private static final Pattern RtlLocalesRe = Pattern
			.compile("^(ar|dv|he|iw|fa|nqo|ps|sd|ug|ur|yi|.*[-_](Arab|Hebr|Thaa|Nkoo|Tfng))"
					+ "(?!.*[-_](Latn|Cyrl)($|-|_))($|-|_)");

	// Default Constructor
	public LocalizationRTL() {
	}

	/**
	 * This method checks the language value against the Patterns if the input
	 * language matches against Patterns returns true else returns false
	 */
	public static boolean isRtlLanguage(String language) {
		return language != null && RtlLocalesRe.matcher(language).find();
	}

}