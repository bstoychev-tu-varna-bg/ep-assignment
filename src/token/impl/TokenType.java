package token.impl;

import java.util.HashSet;
import java.util.Set;

public enum TokenType {
    INT("int"), BOOL("bool"),
    WHILE("while"), IF("if"), ELSE("else"), ELSEIF("elseif"), RETURN("return"), RETURNS("returns"),
    PROGRAM("program"), TRUE("true"), FALSE("false"),
    SWITCH("switch"), CASE("case"), DEFAULT("default"), FOR("for"),
    IN("in"), FUNCTION("function"), BREAK("break"),

    LSQUARE("["), RSQUARE("]"), LBRACKET("{"), RBRACKET("}"),
    LPAREN("("), RPAREN(")"), COMMA(","), SEMICOLON(";"),

    PLUS("+"), MINUS("-"),

    MUL("*"), DIV("/"),

    AND("and"), OR("or"),

    INCREMENT("++"), DECREMENT("--"),
    MUL_BECOMES("*="), PLUS_BECOMES("+="), MINUS_BECOMES("-="),

    BECOMES("="),

    EQUALS("=="), NOTEQUALS("!="), NOT("not"),
    GREATER(">"), LESS("<"),
    GREATER_EQ(">="), LESS_EQ("<="),

    IDENTIFIER("identifier"), NUMBER("number"), CHAR_LITERAL("character literal"), BOOL_LITERAL("bool literal"),

    OTHER("");

    public final String value;

    TokenType(final String value) {
        this.value = value;
    }

    public static TokenType valueOf(int token) {
        return TokenType.values()[token];
    }

    private static Set<String> keywords = new HashSet<>();

    static {
        keywords.add(IF.value);
        keywords.add(INT.value);
        keywords.add(BOOL.value);
        keywords.add(WHILE.value);
        keywords.add(ELSE.value);
        keywords.add(ELSEIF.value);
        keywords.add(RETURN.value);
        keywords.add(RETURNS.value);
        keywords.add(PROGRAM.value);
        keywords.add(TRUE.value);
        keywords.add(FALSE.value);
        keywords.add(SWITCH.value);
        keywords.add(CASE.value);
        keywords.add(DEFAULT.value);
        keywords.add(FOR.value);
        keywords.add(IN.value);
        keywords.add(IF.value);
        keywords.add(FUNCTION.value);
        keywords.add(BREAK.value);
        keywords.add(AND.value);
        keywords.add(OR.value);
    }

    public static boolean isKeyword(TokenType tokenType) {
        return keywords.contains(tokenType.value);
    }

    public static boolean isKeyword(String keyword) {
        return keywords.contains(keyword);
    }

    private static Set<TokenType> characterLiteral = new HashSet<>();

    static {
        characterLiteral.add(CHAR_LITERAL);
    }

    public static boolean isCharacterLiteral(TokenType tokenType) {
        return characterLiteral.contains(tokenType);
    }

    private static Set<TokenType> primitiveType = new HashSet<>();

    static {
        primitiveType.add(INT);
        primitiveType.add(BOOL);
    }

    public static boolean isPrimitiveType(TokenType tokenType) {
        return primitiveType.contains(tokenType);
    }

    private static Set<TokenType> unaryOperators = new HashSet<>();

    static {
        unaryOperators.add(NOT);
        unaryOperators.add(MINUS);
        unaryOperators.add(PLUS);
        unaryOperators.add(INCREMENT);
        unaryOperators.add(DECREMENT);
        unaryOperators.add(MUL_BECOMES);
        unaryOperators.add(PLUS_BECOMES);
        unaryOperators.add(MINUS_BECOMES);
    }

    public static boolean isUnaryOperator(TokenType tokenType) {
        return unaryOperators.contains(tokenType);
    }

    private static Set<TokenType> relationalOperators = new HashSet<>();

    static {
        relationalOperators.add(EQUALS);
        relationalOperators.add(NOTEQUALS);
        relationalOperators.add(GREATER);
        relationalOperators.add(GREATER_EQ);
        relationalOperators.add(LESS);
        relationalOperators.add(LESS_EQ);
    }

    public static boolean isRelationalOperator(TokenType tokenType) {
        return relationalOperators.contains(tokenType);
    }

    private static Set<TokenType> operatorGroupOne = new HashSet<>();

    static {
        operatorGroupOne.add(PLUS);
        operatorGroupOne.add(MINUS);
        operatorGroupOne.add(OR);
    }

    public static boolean isOperatorGroupOne(TokenType tokenType) {
        return operatorGroupOne.contains(tokenType);
    }

    private static Set<TokenType> operatorGroupTwo = new HashSet<>();

    static {
        operatorGroupTwo.add(MUL);
        operatorGroupTwo.add(DIV);
        operatorGroupTwo.add(AND);
    }

    public static boolean isOperatorGroupTwo(TokenType tokenType) {
        return operatorGroupTwo.contains(tokenType);
    }

    private static Set<TokenType> simpleStatementTerminal = new HashSet<>();

    static {
        simpleStatementTerminal.add(IDENTIFIER);
        simpleStatementTerminal.add(RETURN);
    }

    public static boolean isSimpleStatementTerminal(TokenType tokenType) {
        return simpleStatementTerminal.contains(tokenType) || primitiveType.contains(tokenType);
    }

    private static Set<TokenType> compoundStatementTerminal = new HashSet<>();

    static {
        compoundStatementTerminal.add(IF);
        compoundStatementTerminal.add(WHILE);
        compoundStatementTerminal.add(SWITCH);
        compoundStatementTerminal.add(FOR);
    }

    public static boolean isCompoundStatementTerminal(TokenType tokenType) {
        return compoundStatementTerminal.contains(tokenType);
    }

    public static boolean isStatementTerminal(TokenType tokenType) {
        return isSimpleStatementTerminal(tokenType) ||
                isCompoundStatementTerminal(tokenType);
    }

    private static Set<TokenType> factorTerminal = new HashSet<>();

    static {
        factorTerminal.add(IDENTIFIER);
        factorTerminal.add(NUMBER);
        factorTerminal.add(LPAREN);
    }

    public static boolean isFactorTerminal(TokenType tokenType) {
        return factorTerminal.contains(tokenType);
    }

    public static boolean isLiteralTerminal(TokenType tokenType) {
        return isFactorTerminal(tokenType) || isPrimitiveType(tokenType) || tokenType == CHAR_LITERAL;
    }
}
