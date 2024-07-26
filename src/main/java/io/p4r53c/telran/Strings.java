package io.p4r53c.telran;

public class Strings {

    private Strings() {
    }

    public static String firstName() {
        return "[A-Z][a-z]{4,}";
    }

    public static String javaVariable() {
        return "^[a-zA-Z_$][a-zA-Z\\d_$]*$";
    }

    public static String conventionalJavaVariable() {
        return "^[a-z]+([A-Z][a-z]*)*$";
    }
}
