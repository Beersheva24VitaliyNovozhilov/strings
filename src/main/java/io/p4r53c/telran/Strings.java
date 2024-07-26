package io.p4r53c.telran;

public class Strings {

    private Strings() {
    }

    /**
     * Returns a regular expression pattern for a valid first name.
     *
     * @return a string representing the regular expression pattern
     */
    public static String firstName() {
        return "[A-Z][a-z]{4,}";
    }

    /**
     * Returns a regular expression pattern for a valid Java variable name.
     *
     * @return a string representing the regular expression pattern
     */
    public static String javaVariable() {
        return "^[a-zA-Z_$][a-zA-Z\\d_$]*$";
    }

    /**
     * Returns a regular expression pattern for a valid conventional Java variable
     * name.
     *
     * @return a string representing the regular expression pattern
     */
    public static String conventionalJavaVariable() {
        return "^[a-z]+([A-Z][a-z]*)*$";
    }
}
