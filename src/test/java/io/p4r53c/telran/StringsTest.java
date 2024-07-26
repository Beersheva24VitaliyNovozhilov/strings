package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import javax.lang.model.SourceVersion;

import org.junit.jupiter.api.Test;

class StringsTest {

    @Test
    void testJavaVariable() {
        String regex = Strings.javaVariable();

        String[] validVariableNames = { "variable1", "variable", "my_var", "isSynchronized" };
        String[] invalidVariableNames = { "1variable", "int", "", "synchronized" };

        Arrays.stream(validVariableNames)
                .forEach(variableName -> assertTrue(
                        variableName.matches(regex) && !SourceVersion.isKeyword(variableName),
                        "Expected valid: " + variableName));

        Arrays.stream(invalidVariableNames)
                .forEach(variableName -> assertFalse(
                        variableName.matches(regex) && !SourceVersion.isKeyword(variableName),
                        "Expected invalid: " + variableName));
    }

    @Test
    void testConventionalJavaVariable() {
        String regex = Strings.conventionalJavaVariable();

        String[] validVariableNames = { "variableName", "variablename" };
        String[] invalidVariableNames = { "VariableName", "variable_name", "" };

        Arrays.stream(validVariableNames).forEach(variableName -> assertTrue(variableName.matches(regex),
                "Expected valid: " + variableName));

        Arrays.stream(invalidVariableNames).forEach(variableName -> assertFalse(variableName.matches(regex),
                "Expected invalid: " + variableName));
    }
}
