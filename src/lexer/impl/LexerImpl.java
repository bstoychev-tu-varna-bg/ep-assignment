package lexer.impl;

import exception.LexicalException;
import io.api.ResourceReader;
import io.api.Source;
import io.impl.ResourceReaderImpl;
import io.impl.SourceImpl;
import lexer.api.Lexer;
import token.api.Token;
import token.impl.TokenImpl;
import token.impl.TokenType;
import util.CompilerTestHelper;

import java.io.File;
import java.io.IOException;

public final class LexerImpl implements Lexer<TokenType> {
    private Source source;
    private char currentChar;
    private int line;
    private int position;
    private static final char EOF = '\uffff';

    public LexerImpl(Source source) {
        this.source = source;
        this.currentChar = source.getCurrentChar();
    }

    @Override
    public Token<TokenType> nextToken() {
        currentChar = source.getCurrentChar();
        line = source.getLineNumber();
        position = source.getPosition() + 1;
        while (currentChar != EOF) {
            switch (currentChar) {
                case ' ':
                case '\t':
                    handleSpaceAndTabs();
                    continue;
                case '=':
                    return handleTwoCharOperator('=', TokenType.BECOMES, TokenType.EQUALS);
                case '>':
                    return handleTwoCharOperator('=', TokenType.GREATER, TokenType.GREATER_EQ);
                case '<':
                    return handleTwoCharOperator('=', TokenType.LESS, TokenType.LESS_EQ);
                case '!':
                    return handleTwoCharOperator('=', TokenType.NOTEQUALS);
                case '/':
                    return handleSlash();

                case '+':
                    return retTokenAndAdvance(TokenType.PLUS);
                case '-':
                    return retTokenAndAdvance(TokenType.MINUS);
                case '[':
                    return retTokenAndAdvance(TokenType.LSQUARE);
                case ']':
                    return retTokenAndAdvance(TokenType.RSQUARE);
                case '{':
                    return retTokenAndAdvance(TokenType.LBRACKET);
                case '}':
                    return retTokenAndAdvance(TokenType.RBRACKET);
                case '(':
                    return retTokenAndAdvance(TokenType.LPAREN);
                case ')':
                    return retTokenAndAdvance(TokenType.RPAREN);
                case ';':
                    return retTokenAndAdvance(TokenType.SEMICOLON);
                case '*':
                    return retTokenAndAdvance(TokenType.MUL);
                case ',':
                    return retTokenAndAdvance(TokenType.COMMA);

                default:
                    if (isLetter(currentChar)) {
                        return handleIdentifier();
                    }
                    if (isDigit(currentChar)) {
                        return handleDigit();
                    }
                    return retTokenAndAdvance(TokenType.OTHER, currentChar + "");
            }
        }
        return null;
    }

    private void handleSpaceAndTabs() {
        while (currentChar == ' ' || currentChar == '\t') {
            currentChar = source.next();
        }
        line = source.getLineNumber();
        position = source.getPosition() + 1;
    }

    private Token<TokenType> handleTwoCharOperator(char secondChar, TokenType firstMatchedToken, TokenType secondMatchedToken) {
        if (source.next() == secondChar) {
            return retTokenAndAdvance(secondMatchedToken);
        }
        return retToken(firstMatchedToken);
    }

    private Token<TokenType> handleTwoCharOperator(char secondChar, TokenType matchedToken) {
        if (source.next() == secondChar) {
            return retTokenAndAdvance(matchedToken);
        }
        return retToken(TokenType.OTHER);
    }

    private Token<TokenType> handleSlash() {
        if (source.next() == '/') {
            int currentLineNum = source.getLineNumber();
            while (currentLineNum == source.getLineNumber()) {
                source.next();
            }
            return nextToken();
        }
        return retToken(TokenType.DIV);
    }

    private Token<TokenType> retTokenAndAdvance(TokenType token) {
        source.next();
        return new TokenImpl(token, position, line);
    }

    private Token<TokenType> retTokenAndAdvance(TokenType token, String text) {
        source.next();
        return new TokenImpl(token, text, position, line);
    }

    private Token<TokenType> retToken(TokenType token) {
        return new TokenImpl(token, position, line);
    }

    private Token<TokenType> retToken(TokenType token, String text) {
        return new TokenImpl(token, text, position, line);
    }

    private Token<TokenType> handleIdentifier() {
        StringBuilder sb = new StringBuilder();
        sb.append(currentChar);
        currentChar = source.next();
        while (isLetter(currentChar) || isDigit(currentChar)) {
            sb.append(currentChar);
            currentChar = source.next();
        }
        String res = sb.toString();
        if (TokenType.isKeyword(res)) {
            return retToken(TokenType.valueOf(res.toUpperCase()));
        }
        return retToken(TokenType.IDENTIFIER, res);
    }

    private Token<TokenType> handleDigit() {
        StringBuilder sb = new StringBuilder();
        while (isDigit(currentChar)) {
            sb.append(currentChar);
            currentChar = source.next();
        }
        String digit = sb.toString();
        try {
            Integer.parseInt(digit);
        } catch (NumberFormatException e) {
            throw new LexicalException("Not a valid integer " + digit + ".", line, position);
        }
        return retToken(TokenType.NUMBER, digit);
    }

    private boolean isLetter(char ch) {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }

    private boolean isDigit(char ch) {
        return ch >= '0' && ch <= '9';
    }

    public static void main(String[] args) throws IOException {
        ResourceReader resourceReader = new ResourceReaderImpl(new File("resources/sample.txt"));
        Lexer<TokenType> lexer = new LexerImpl(new SourceImpl(resourceReader));
        System.out.println(CompilerTestHelper.getTokensAsString(lexer));
    }

}
