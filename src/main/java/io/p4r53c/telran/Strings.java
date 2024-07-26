package io.p4r53c.telran;

public class Strings {

    private static final String FIRST_NAME_PATTERN = "[A-Z][a-z]{4,}";
    private static final String JAVA_VARIABLE_PATTERN = "^(?!_?$)[a-zA-Z_$][a-zA-Z\\d_$]*$";
    private static final String CONVENTIONAL_JAVA_VARIABLE_PATTERN = "^[a-z]+([A-Z][a-z]*)*$";

    private Strings() {
    }

    /**
     * Returns a regular expression pattern for a valid first name.
     *
     * @return a string representing the regular expression pattern
     */
    public static String firstName() {
        return FIRST_NAME_PATTERN;
    }

    /**
     * Returns a regular expression pattern for a valid Java variable name.
     *
     * @return a string representing the regular expression pattern
     */
    public static String javaVariable() {
        return JAVA_VARIABLE_PATTERN;
    }

    /**
     * Returns a regular expression pattern for a valid conventional Java variable
     * name.
     *
     * @return a string representing the regular expression pattern
     */
    public static String conventionalJavaVariable() {
        return CONVENTIONAL_JAVA_VARIABLE_PATTERN;
    }
}
