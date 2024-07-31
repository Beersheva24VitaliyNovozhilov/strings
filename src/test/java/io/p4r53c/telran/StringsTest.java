package io.p4r53c.telran;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import javax.lang.model.SourceVersion;

import org.junit.jupiter.api.Test;

class StringsTest {

        @Test
        void testFirstName() {
                String regex = Strings.firstName();

                String[] validNames = { "Vitaliy", "Vitaly" };
                String[] invalidNames = { "Vitaly1", "Vitaliy2", "" };

                Arrays.stream(validNames).forEach(name -> assertTrue(name.matches(regex), "Expected valid: " + name));
                Arrays.stream(invalidNames)
                                .forEach(name -> assertFalse(name.matches(regex), "Expected invalid: " + name));
        }

        @Test
        void testJavaVariable() {
                String regex = Strings.javaVariable();

                String[] validVariableNames = { "variable1", "variable", "my_var", "isSynchronized", "__", "____",
                                "$var" };

                String[] invalidVariableNames = { "1variable", "int", "", "synchronized", "_", ".var", "v@r",
                                "@var", "~var", "var~", "var-var" };

                Arrays.stream(validVariableNames)
                                .forEach(variableName -> assertTrue(
                                                variableName.matches(regex) && !SourceVersion.isKeyword(variableName,
                                                                SourceVersion.latest()),
                                                "Expected valid: " + variableName));

                Arrays.stream(invalidVariableNames)
                                .forEach(variableName -> assertFalse(
                                                variableName.matches(regex) && !SourceVersion.isKeyword(variableName,
                                                                SourceVersion.latest()),
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

        // --- CW 11 ---
        @Test
        void testNumber0to300() {
                String regex = Strings.number0to300();

                String[] validNumbers = { "0", "300", "299", "1", "11" };
                String[] invalidNumbers = { "01", "04", "004", "301", "400", "4000" };

                Arrays.stream(validNumbers).forEach(number -> assertTrue(number.matches(regex),
                                "Expected valid: " + number));

                Arrays.stream(invalidNumbers).forEach(number -> assertFalse(number.matches(regex),
                                "Expected invalid: " + number));
        }

        @Test
        void testIpV4Octets() {
                String regex = Strings.ipV4Octet();

                String[] validOctets = { "0", "00", "000", "10", "127", "172", "192", "244", "255" };
                String[] invalidOctets = { "0000", "x", "-1", "1111", "256", "*", "1 " };

                Arrays.stream(validOctets).forEach(octet -> assertTrue(octet.matches(regex),
                                "Expected valid: " + octet));

                Arrays.stream(invalidOctets).forEach(octet -> assertFalse(octet.matches(regex),
                                "Expected invalid: " + octet));
        }

        @Test
        void testIpV4() {
                String regex = Strings.ipV4();

                // Valid IP addresses
                String[] validIpAddresses = {
                                "0.0.0.0",
                                "8.8.8.8",
                                "127.0.0.1",
                                "192.168.0.1",
                                "10.0.0.1",
                                "172.16.0.1",
                                "255.255.255.255",
                                "244.178.44.111"
                };

                String[] invalidIpAddresses = {
                                "0.0.0",
                                "0.0.0.0.0",
                                "255.255.255.256",
                                "0.0.0.256",
                                "0.0.256.0",
                                "0,0,256,0",
                                "0.*.0.0"
                };

                Arrays.stream(validIpAddresses).forEach(ip -> assertTrue(ip.matches(regex),
                                "Expected valid: " + ip));

                Arrays.stream(invalidIpAddresses).forEach(ip -> assertFalse(ip.matches(regex),
                                "Expected invalid: " + ip));
        }

        @Test
        void testStringWithJavaNames() {
                String names = "123 1a _ abs int enum null lmn";
                String expected = "abs lmn";
                assertEquals(expected, Strings.stringWithJavaNames(names));
        }

        // --- HW 11 ---
        @Test
        void testValidIsArithmeticExpression() {
                String[] validExpressions = {
                                "(((a + b) * c))",
                                "(a + b) * c",
                                "3 + (2 / 1) * 5",
                                "a + b - c * d / e",
                                "(a + b) % (c / d)",
                                "(a + b) * (c - d) / (e + f)",
                                "1 + 2",
                                "1 + 2_000_000", // thousand separator notation
                                "(a % b) * 2_000_000",
                                "y + 2.1234e3", // Exp
                                "y + 2d", // Double
                                "y + 2f", // Float
                                "0xAB + 0xFF", // Hex
                                "1 % 2",
                                "3.14 + 2.71 * (3.14 + 2.71)",
                                "3.14 + .71", // it's legal, 3.85.
                                "a + __",
                                "a + var",
                                "3 + $var",
                                "(6 + 8) / my_var",
                                "(iSSynchronized + _var) + 1",
                                "(synchronized1 + _var) + 1",
                                "ab + 10",
                                "(ab + c) + 10"
                };

                Arrays.stream(validExpressions)
                                .forEach(expression -> assertTrue(Strings.isArithmeticExpression(expression),
                                                "Expected valid: " + expression));
        }

        @Test
        void testInvalidIsArithmeticExpression() {
                String[] invalidExpressions = {
                                "3 * (4 + 5",
                                "(3 + 2) * (4 / 2))",
                                "a + (b - c) * d / e)",
                                "(1 . c) * d % e)",
                                "a + + b",
                                "1 + 2_000_",
                                "a + 3 *",
                                "a b + 10", // No more operands merging
                                "a b c + 10",
                                "(a b c) + 10",
                                "* a + b",
                                "123 456",
                                "()",
                                "((a))",
                                "a++",
                                "(a + 5) * ++b",
                                "a + _",
                                "a + synchronized",
                                "21",
                                "1 % ",
                                "",
                                " ",
                                null
                };

                Arrays.stream(invalidExpressions)
                                .forEach(expression -> assertFalse(Strings.isArithmeticExpression(expression),
                                                "Expected invalid: " + expression));
        }
}
