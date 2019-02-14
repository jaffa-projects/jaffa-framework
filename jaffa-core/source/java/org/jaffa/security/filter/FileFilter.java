package org.jaffa.security.filter;


/**
 * This class satisfies Coverity CWE 22 Compliance:
 * An attacker who has control over part of the filename or path might be able to maliciously alter the overall path
 * and access, modify, or test the existence of critical or sensitive files. Particular concerns are the ability to
 * perform directory traversal in a path (for example, ../) or to specify absolute paths.
 *
 * This class should support all languages. From https://www.regular-expressions.info/unicode.html:
 * p{L} = Match any kind of letter from any language
 * p{M} = Match any character intended to be combined with another character
 * p{N} = Match any numeric character in any script
 * p{Z} = Match any kind of whitespace or invisible character
 * p{S} = Match any kind of math symbols or currency signs
 * p{P} = Match any punctuation
 */

public class FileFilter {
    private static final String END_REGEX = "$";
    private static final String START_REGEX = "^";
    private static final String[] DISALLOW_PATH_CHARS = {".."};
    private static final String[] DISALLOW_FILE_CHARS = {"..", "/", "\\"};
    private static final String ALLOWABLE_INPUT = "[\\p{L}\\p{M}\\p{N}\\p{Z}\\p{S}\\p{P}]+";

    /**
     * Check a user-input filename against a whitelist of allowable input, then exclude any file separators or periods,
     * making path traversal or manipulation impossible.
     * @param file  The user-supplied filename
     * @return  The user-input filename if it matches the whitelist, or null if it does not
     */
    public static String filterUserInputFileName(String file) {
        return filterUserInput(file, DISALLOW_FILE_CHARS);
    }

    /**
     * Check a user-input file path against a whitelist of allowable input, then exclude any double periods (making file
     * traversal or manipulation impossible)
     *
     * This function will return the provided string as long as it is not empty, and does not contain a double period. If
     * those conditions are not satisfied, it will return null.
     * @param path The user-input filepath or URL path
     * @return The user-input path if it matches the whitelist, or null if it does not
     */
    public static String filterUserInputPath(String path) {
        return filterUserInput(path, DISALLOW_PATH_CHARS);
    }

    /**
     * Given the user input and allowance criteria, determine whether user input is clean or not
     * @param input The user input
     * @param chars The array or disallowed characters or strings
     * @return  The user input if it matches the whitelist and contains no disallowed characters, or null if not
     */
    private static String filterUserInput(String input, String[] chars) {
        String filteredInput = null;

        String whitelist = START_REGEX + ALLOWABLE_INPUT + END_REGEX;

        if (input.matches(whitelist)) {
            for (String c : chars) {
                if (input.contains(c)) {
                    return null;
                }
            }
            filteredInput = input;
        }
        return filteredInput;
    }
}

