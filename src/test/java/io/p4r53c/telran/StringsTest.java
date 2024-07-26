package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import javax.lang.model.SourceVersion;

import org.junit.jupiter.api.Test;

class StringsTest {

    @Test
    void testFirstName() {
        String regex = Strings.getFirstNamePattern();

        String[] validNames = { "Vitaliy", "Vitaly" };
        String[] invalidNames = { "Vitaly1", "Vitaliy2", "" };

        Arrays.stream(validNames).forEach(name -> assertTrue(name.matches(regex), "Expected valid: " + name));
        Arrays.stream(invalidNames).forEach(name -> assertFalse(name.matches(regex), "Expected invalid: " + name));
    }

    @Test
    void testJavaVariable() {
        String regex = Strings.getJavaVariablePattern();

        String[] validVariableNames = { "variable1", "variable", "my_var", "isSynchronized" };
        String[] invalidVariableNames = { "1variable", "int", "", "synchronized" };

        Arrays.stream(validVariableNames)
                .forEach(variableName -> assertTrue(
                        variableName.matches(regex) && !SourceVersion.isKeyword(variableName, SourceVersion.latest()),
                        "Expected valid: " + variableName));

        Arrays.stream(invalidVariableNames)
                .forEach(variableName -> assertFalse(
                        variableName.matches(regex) && !SourceVersion.isKeyword(variableName, SourceVersion.latest()),
                        "Expected invalid: " + variableName));
    }

    @Test
    void testConventionalJavaVariable() {
        String regex = Strings.getConventionalJavaVariablePattern();

        String[] validVariableNames = { "variableName", "variablename" };
        String[] invalidVariableNames = { "VariableName", "variable_name", "" };

        Arrays.stream(validVariableNames).forEach(variableName -> assertTrue(variableName.matches(regex),
                "Expected valid: " + variableName));

        Arrays.stream(invalidVariableNames).forEach(variableName -> assertFalse(variableName.matches(regex),
                "Expected invalid: " + variableName));
    }
}
