package io.p4r53c.telran;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StringsUtils {

    private StringsUtils() {
    }

    /**
     * Checks if a given string is a valid Java variable name based on a regular
     * expression and a set of keywords.
     *
     * @param variableName the string to check
     * @return true if the variable name is valid, false otherwise
     */
    public static boolean isValidJavaVariableName(String variableName) {

        // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html
        Set<String> javaKeywords = new HashSet<>(Arrays.asList(
                "abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized",
                "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw",
                "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient",
                "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void",
                "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while"));

        String validationRegex = "^[a-zA-Z_$][a-zA-Z\\d_$]*$";
        return variableName.matches(validationRegex) && !javaKeywords.contains(variableName);
    }

    /**
     * Checks if a given string matches a regular expression pattern for a
     * conventional Java variable name.
     *
     * @param variableName the string to be checked
     * @return true if the string matches the pattern, false otherwise
     */
    public static boolean isConventionalJavaVariableName(String variableName) {
        String conventionalRegex = "^[a-z]+([A-Z][a-z]*)*$";
        return variableName.matches(conventionalRegex);
    }
}
