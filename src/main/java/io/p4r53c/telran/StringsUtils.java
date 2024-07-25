package io.p4r53c.telran;

import java.util.Set;

public class StringsUtils {

    private StringsUtils() {
    }

    /**
     * Checks if a given string is a valid Java variable name based on a regular
     * expression and a set of keywords.
     *
     * @param variableName the string to check
     * @param regex        the regular expression used to validate the variable name
     * @param keywords     the set of keywords to check against
     * @return true if the variable name is valid, false otherwise
     */
    public static boolean isValidJavaVariableName(String variableName, String regex, Set<String> keywords) {
        return variableName.matches(regex) && !keywords.contains(variableName);
    }

    /**
     * Checks if a given string matches a regular expression pattern for a
     * conventional Java variable name.
     *
     * @param variableName the string to be checked
     * @param regex        the regular expression pattern to match against
     * @return true if the string matches the pattern, false otherwise
     */
    public static boolean isConventionalJavaVariableName(String variableName, String regex) {
        return variableName.matches(regex);
    }

}
