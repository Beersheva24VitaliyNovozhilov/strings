package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class StringsTest {

    // https://docs.oracle.com/javase/tutorial/java/nutsandbolts/_keywords.html
    private static final Set<String> JAVA_KEYWORDS = new HashSet<>(Arrays.asList(
            "abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized",
            "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw",
            "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient",
            "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void",
            "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while"));

    private static final String JAVA_VAR_VALIDATOR_REGEX = "^[a-zA-Z_$][a-zA-Z\\d_$]*$";
    private static final String LOWER_CAMEL_CASE_STRING_REGEX = "^[a-z]+([A-Z][a-z]*)*$";

    @Test
    void testIsValidJavaVariableName() {
        assertTrue(StringsUtils.isValidJavaVariableName("variable1", JAVA_VAR_VALIDATOR_REGEX, JAVA_KEYWORDS));
        assertTrue(StringsUtils.isValidJavaVariableName("my_var", JAVA_VAR_VALIDATOR_REGEX, JAVA_KEYWORDS));

        assertFalse(StringsUtils.isValidJavaVariableName("1variable", JAVA_VAR_VALIDATOR_REGEX, JAVA_KEYWORDS));
        assertFalse(StringsUtils.isValidJavaVariableName("int", JAVA_VAR_VALIDATOR_REGEX, JAVA_KEYWORDS));
        assertFalse(StringsUtils.isValidJavaVariableName("", JAVA_VAR_VALIDATOR_REGEX, JAVA_KEYWORDS));

        assertFalse(StringsUtils.isValidJavaVariableName("synchronized", JAVA_VAR_VALIDATOR_REGEX, JAVA_KEYWORDS));
        assertTrue(StringsUtils.isValidJavaVariableName("isSynchronized", JAVA_VAR_VALIDATOR_REGEX, JAVA_KEYWORDS));
    }

    @Test
    void testIsConventionalJavaVariableName() {
        assertTrue(StringsUtils.isConventionalJavaVariableName("variableName", LOWER_CAMEL_CASE_STRING_REGEX));

        assertFalse(StringsUtils.isConventionalJavaVariableName("VariableName", LOWER_CAMEL_CASE_STRING_REGEX));
        assertFalse(StringsUtils.isConventionalJavaVariableName("variable_name", LOWER_CAMEL_CASE_STRING_REGEX));
        assertFalse(StringsUtils.isConventionalJavaVariableName("", LOWER_CAMEL_CASE_STRING_REGEX));

        assertTrue(StringsUtils.isConventionalJavaVariableName("variablename", LOWER_CAMEL_CASE_STRING_REGEX));
    }
}
