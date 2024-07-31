package io.p4r53c.telran;

import java.util.Arrays;

import javax.lang.model.SourceVersion;

public class Strings {

    private static final String FIRST_NAME_PATTERN = "[A-Z][a-z]{4,}";
    private static final String JAVA_VARIABLE_PATTERN = "^(?!_?$)[a-zA-Z_$][\\w$]*$";
    private static final String CONVENTIONAL_JAVA_VARIABLE_PATTERN = "^[a-z]+([A-Z][a-z]*)*$";

    // CW 11
    // private static final String NUMBER_0_TO_300_PATTERN_FIRST =
    // "^(300|[12]\\d{2}|[1-9]\\d?|0)$"; // My solution
    // private static final String NUMBER_0_TO_300_PATTERN_CW =
    // "^[1-9]\\d?|[1-2]\\d{2}|300$|0$"; // CW

    // Positive Lookahead
    private static final String NUMBER_0_TO_300_PATTERN_PL = "^(300|[12]\\d{2}|[1-9]\\d?|(?=0$)0)$";

    // private static final String IPV4_OCTET_CORRECT_PATTERN =
    // "^(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]\\d?|0)$"; // No "00" and "000"
    private static final String IPV4_OCTET_PATTERN = "([0-1]?\\d{1,2}|2([0-4]\\d|5[0-5]))";

    private static final String[] PROVIDED_JAVA_KEYWORDS = { "abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "false",
            "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native",
            "new", "null", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true",
            "try", "void", "volatile", "while" };

    // CW 11 Consts
    private static final String DECIMAL_PATTERN = "\\d*\\.?\\d+(_\\d+)*(e[+-]?\\d+)?[dDfF]?";
    private static final String INTEGER_PATTERN = "\\d+(_\\d+)+[dDfF]?";
    private static final String HEX_PATTERN = "0[xX][0-9a-fA-F]+[lL]?";
    private static final String VARIABLE_PATTERN = "[a-zA-Z_$][\\w$]*";
    private static final String BRACKET_PATTERN = "[()]";

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

    // ----- CW 11 -----

    /**
     * Returns a string representing the regular expression pattern for valid
     * numbers from 0 to 300.
     *
     * @return a string representing the regular expression pattern
     */
    public static String number0to300() {
        return NUMBER_0_TO_300_PATTERN_PL;
    }

    /**
     * Returns a regular expression pattern for a valid IPv4 octet.
     *
     * @return a string representing the regular expression pattern for a valid IPv4
     *         octet
     */
    public static String ipV4Octet() {
        return IPV4_OCTET_PATTERN;
    }

    /**
     * Returns a regular expression pattern for a valid IPv4 address.
     *
     * @return a string representing the regular expression pattern for a valid IPv4
     *         address
     */
    public static String ipV4() {
        String octetExp = ipV4Octet();
        return String.format("^%s(\\.%s){3}$", octetExp, octetExp);
    }

    /**
     * Returns a string containing all Java names from the input string.
     *
     * @param names the input string containing names separated by whitespace
     * @return a string containing all Java names from the input string
     */
    public static String stringWithJavaNames(String names) {
        String[] tokens = names.split("\\s+");
        int index = getJavaNameIndex(tokens, -1);
        String result = "";

        if (index >= 0) {
            result = tokens[index];
        }

        while ((index = getJavaNameIndex(tokens, index)) > 0) {
            result += " " + tokens[index];
        }

        return result;
    }

    // ----- HW 11 -----

    /**
     * Checks if the given arithmetic expression is valid.
     *
     * @param expression the arithmetic expression to be checked
     * @return true if the expression is valid, false otherwise
     */
    public static boolean isArithmeticExpression(String expression) {
        boolean isValid = true;

        if (expression == null || expression.isEmpty()) {
            isValid = false;
        } else {
            isValid = hasValidBrackets(expression) && hasValidOperandsAndOperators(expression);
        }

        return isValid;
    }

    // --- CW 11 Private Methods ---

    /**
     * Finds the index of the first Java name in the given array of tokens starting
     * from the specified index.
     *
     * @param tokens the array of tokens to search
     * @param i      the starting index
     * @return the index of the first Java name, or -1 if no Java name is found
     */
    private static int getJavaNameIndex(String[] tokens, int i) {
        i++;
        while (i < tokens.length && !isJavaName(tokens[i])) {
            i++;
        }
        return i < tokens.length ? i : -1;
    }

    /**
     * Checks if the given string is a valid Java name.
     *
     * @param string the string to be checked
     * @return true if the string is a valid Java name, false otherwise
     */
    private static boolean isJavaName(String string) {
        return string.matches(javaVariable()) && Arrays.binarySearch(PROVIDED_JAVA_KEYWORDS, string) < 0;
    }

    // --- HW 11 Private Methods ---

    /**
     * Checks if the given arithmetic expression has valid brackets.
     *
     * @param expression the arithmetic expression to be checked
     * @return true if the expression has valid brackets, false otherwise
     */
    private static boolean hasValidBrackets(String expression) {
        int balance = 0;
        boolean isValid = true;

        int i = 0;
        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
            }
            if (balance < 0) {
                isValid = false;
                break;
            }
            i++;
        }

        if (balance != 0) {
            isValid = false;
        }
        return isValid;
    }

    /**
     * Checks if the given arithmetic expression has valid operands and operators.
     *
     * @param expression the arithmetic expression to be checked
     * @return true if the expression has valid operands and operators, false
     *         otherwise
     */
    private static boolean hasValidOperandsAndOperators(String expression) {
        char[] chars = expression.toCharArray();

        boolean lastWasOperator = true;
        boolean hasOperator = false;
        boolean valid = true;

        int i = 0;

        while (i < chars.length && valid) {
            char c = chars[i];
            if (isOperator(c)) {
                hasOperator = true;
                if (lastWasOperator) {
                    valid = false;
                } else {
                    lastWasOperator = true;
                    i++;
                }
            } else if (isOperandCharacter(c)) {
                lastWasOperator = false;

                String operand = getOperand(chars, i);

                if (!isValidOperand(operand)) {
                    valid = false;
                } else {
                    i += getOperandLength(chars, i);
                }
            } else if (Character.isWhitespace(c) || String.valueOf(c).matches(BRACKET_PATTERN)) {
                i++;
            } else {
                valid = false;
            }
        }
        return valid && hasOperator && !lastWasOperator;
    }

    /**
     * Returns a string representing the regular expression pattern for a valid operand.
     *
     * @return a regular expression pattern for a valid operand
     */
    private static String getOperandPattern() {
        return String.format("%s|%s|%s|%s", DECIMAL_PATTERN, INTEGER_PATTERN, HEX_PATTERN, VARIABLE_PATTERN);
    }

    /**
     * Checks if the given character is an operator.
     *
     * @param c the character to be checked
     * @return true if the character is an operator, false otherwise
     */
    private static boolean isOperator(char c) {
        String operatorPattern = "[+\\-*/%]";
        return String.valueOf(c).matches(operatorPattern);
    }

    /**
     * Checks if the given character is a valid operand character.
     *
     * @param c the character to be checked
     * @return true if the character is a valid operand character, false otherwise
     */
    private static boolean isOperandCharacter(char c) {
        return Character.isLetterOrDigit(c) || c == '_' || c == '$' || c == '.';
    }

    /**
     * Checks if the given string is a valid operand by matching it against a
     * pattern
     * and checking if it is not a keyword in the source version.
     *
     * @param operand the string to be checked for validity
     * @return true if the string is a valid operand, false otherwise
     */
    private static boolean isValidOperand(String operand) {
        return operand.matches(getOperandPattern()) && !SourceVersion.isKeyword(operand);
    }

    /**
     * Retrieves a substring from the given character array starting at the
     * specified index
     * until a character is encountered that is not a letter, digit, underscore,
     * dollar sign,
     * or period. The retrieved substring is returned as a string.
     *
     * @param chars the character array to retrieve the substring from
     * @param index the starting index of the substring
     * @return the retrieved substring as a string
     */
    private static String getOperand(char[] chars, int index) {
        StringBuilder operand = new StringBuilder();

        while (index < chars.length
                && (Character.isLetterOrDigit(chars[index])
                        || isOperandCharacter(chars[index])
                        || Character.isWhitespace(chars[index]))) {
            operand.append(chars[index]);
            index++;
        }
        return operand.toString().trim();
    }

    /**
     * Retrieves the length of the operand substring starting from the specified
     * index
     * in the given character array. The substring consists of characters that are
     * letters, digits, underscores, dollar signs, or periods.
     *
     * @param chars the character array to retrieve the substring from
     * @param index the starting index of the substring
     * @return the length of the operand substring
     */
    private static int getOperandLength(char[] chars, int index) {
        int length = 0;

        while (index + length < chars.length
                && (Character.isLetterOrDigit(chars[index + length])
                        || isOperandCharacter(chars[index + length]))) {
            length++;
        }
        return length;
    }
}
