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
            assertTrue(Strings.isArithmeticExpression("(((a + b) * c))"));
            assertTrue(Strings.isArithmeticExpression("(a + b) * c"));
            assertTrue(Strings.isArithmeticExpression("3 + (2 / 1) * 5"));
            assertTrue(Strings.isArithmeticExpression("a + b - c * d / e"));
            assertTrue(Strings.isArithmeticExpression("(a + b) % (c / d)"));
            assertTrue(Strings.isArithmeticExpression("(a + b) * (c - d) / (e + f)"));
            assertTrue(Strings.isArithmeticExpression("1 + 2"));
            assertTrue(Strings.isArithmeticExpression("1 + 2_000_000")); // thousand separator notation
            assertTrue(Strings.isArithmeticExpression("(a % b) * 2_000_000"));
            assertTrue(Strings.isArithmeticExpression("y + 2.1234e3")); // Exp
            assertTrue(Strings.isArithmeticExpression("y + 2d")); // Double
            assertTrue(Strings.isArithmeticExpression("y + 2f")); // Float
            assertTrue(Strings.isArithmeticExpression("0xAB + 0xFF")); // Hex
            assertTrue(Strings.isArithmeticExpression("1 % 2"));
            assertTrue(Strings.isArithmeticExpression("3.14 + 2.71 * (3.14 + 2.71)"));
            assertTrue(Strings.isArithmeticExpression("3.14 + .71"));  // it's legal, 3.85.
            assertTrue(Strings.isArithmeticExpression("a + __"));
            assertTrue(Strings.isArithmeticExpression("a + var"));
            assertTrue(Strings.isArithmeticExpression("3 + $var")); 
            assertTrue(Strings.isArithmeticExpression("(6 + 8) / my_var")); 
            assertTrue(Strings.isArithmeticExpression("(iSSynchronized + _var) + 1")); 
            assertTrue(Strings.isArithmeticExpression("(synchronized1 + _var) + 1")); 
        }

        @Test
        void testInvalidIsArithmeticExpression() {
            assertFalse(Strings.isArithmeticExpression("3 * (4 + 5"));
            assertFalse(Strings.isArithmeticExpression("(3 + 2) * (4 / 2))"));
            assertFalse(Strings.isArithmeticExpression("a + (b - c) * d / e)"));
            assertFalse(Strings.isArithmeticExpression("(1 . c) * d % e)"));
            assertFalse(Strings.isArithmeticExpression("a + + b"));
            assertFalse(Strings.isArithmeticExpression("1 + 2_000_"));
            assertFalse(Strings.isArithmeticExpression("a + 3 *"));
            assertFalse(Strings.isArithmeticExpression("* a + b"));
            assertFalse(Strings.isArithmeticExpression("123 456"));
            assertFalse(Strings.isArithmeticExpression("()"));
            assertFalse(Strings.isArithmeticExpression("((a))"));
            assertFalse(Strings.isArithmeticExpression("a++"));
            assertFalse(Strings.isArithmeticExpression("(a + 5) * ++b"));
            assertFalse(Strings.isArithmeticExpression("a + _")); 
            assertFalse(Strings.isArithmeticExpression("a + synchronized"));
            assertFalse(Strings.isArithmeticExpression("21"));
            assertFalse(Strings.isArithmeticExpression("1 % ")); 
            assertFalse(Strings.isArithmeticExpression(""));
            assertFalse(Strings.isArithmeticExpression(" "));
            assertFalse(Strings.isArithmeticExpression(null));
        }
}       

