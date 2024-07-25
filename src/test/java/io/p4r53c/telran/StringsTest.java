package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringsTest {

    @Test
    void testIsValidJavaVariableName() {
        assertTrue(StringsUtils.isValidJavaVariableName("variable1"));
        assertTrue(StringsUtils.isValidJavaVariableName("variable"));
        assertTrue(StringsUtils.isValidJavaVariableName("my_var"));

        assertFalse(StringsUtils.isValidJavaVariableName("1variable"));
        assertFalse(StringsUtils.isValidJavaVariableName("int"));
        assertFalse(StringsUtils.isValidJavaVariableName(""));

        assertFalse(StringsUtils.isValidJavaVariableName("synchronized"));
        assertTrue(StringsUtils.isValidJavaVariableName("isSynchronized"));
    }

    @Test
    void testIsConventionalJavaVariableName() {
        assertTrue(StringsUtils.isConventionalJavaVariableName("variableName"));

        assertFalse(StringsUtils.isConventionalJavaVariableName("VariableName"));
        assertFalse(StringsUtils.isConventionalJavaVariableName("variable_name"));
        assertFalse(StringsUtils.isConventionalJavaVariableName(""));

        assertTrue(StringsUtils.isConventionalJavaVariableName("variablename"));
    }
}
