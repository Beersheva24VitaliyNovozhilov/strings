package io.p4r53c.telran;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // HW 11 Consts
    private static final String HEX_LITERAL = "0[xX][0-9a-fA-F]+";
    private static final String INT_LITERAL = "\\d+(_\\d+)*";
    private static final String FLOAT_LITERAL = "((\\d+(_\\d+)*\\.\\d*(_\\d+)*)|(\\.\\d+(_\\d+)*))([eE][-+]?\\d+)?[fFdD]?|"
            + INT_LITERAL + "[fFdD]";
    private static final String IDENTIFIER = "[a-zA-Z_$][a-zA-Z0-9_$]*";
    private static final String OPERATOR = "([*/+\\-%%])";

    private static Pattern masterRegex;

    static {

        masterRegex = Pattern.compile(getMasterRegex());

    }

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

        if (expression == null || expression.trim().isEmpty()) {
            isValid = false;
        } else if (!hasValidBrackets(expression)) {
            isValid = false;
        } else {
            Matcher matcher = masterRegex.matcher(expression);

            if (!matcher.matches()) {
                isValid = false;
            } else {
                isValid = checkOperands(matcher);
            }
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
     * Checks if the given expression has valid brackets.
     *
     * @param expression the expression to be checked
     * @return true if the expression has valid brackets, false otherwise
     */
    private static boolean hasValidBrackets(String expression) {
        int bracketCount = 0;
        int i = 0;

        while (i < expression.length()) {
            char c = expression.charAt(i);
            if (c == '(') {
                bracketCount++;
            } else if (c == ')') {
                bracketCount--;
                if (bracketCount < 0) {
                    break;
                }
            }
            i++;
        }

        return bracketCount == 0;
    }

    /**
     * Checks if the given Matcher object has valid operands.
     *
     * @param matcher the Matcher object to check
     * @return true if all operands are valid, false otherwise
     */
    private static boolean checkOperands(Matcher matcher) {
        int i = 1;
        boolean valid = true;

        while (i <= matcher.groupCount()) {
            String op = matcher.group(i);
            if (op != null && !op.isEmpty() && !op.matches(HEX_LITERAL) && !op.matches(FLOAT_LITERAL)
                    && !op.matches(INT_LITERAL) && (op.equals("_") || SourceVersion.isKeyword(op))) {
                valid = false;
                break;
            }
            i += 2;
        }

        return valid;
    }

    /**
     * Generates a regular expression pattern for a valid arithmetic expression.
     *
     * @return a string representing the regular expression pattern
     */
    private static String getMasterRegex() {
        return String.format(
                "^\\s*\\(*\\s*%s\\s*(\\)*\\s*%s\\s*\\(*\\s*%s\\s*\\)*)+\\s*$",
                getValidOperand(), OPERATOR, getValidOperand());
    }

    /**
     * Returns a regular expression pattern for a valid operand.
     *
     * @return a string representing the regular expression pattern for a valid
     *         operand
     */
    private static String getValidOperand() {
        return String.format("(%s|%s|%s|%s)", HEX_LITERAL, FLOAT_LITERAL, INT_LITERAL, IDENTIFIER);
    }
}
