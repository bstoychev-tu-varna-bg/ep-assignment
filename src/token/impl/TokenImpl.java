package token.impl;

import token.api.Token;

public final class TokenImpl implements Token<TokenType> {

    private TokenType type;
    private String text;
    private int line;
    private int position;

    public TokenImpl(TokenType type, String text, int position, int lineNumber) {
        this.type = type;
        this.text = text;
        this.position = position;
        this.line = lineNumber;
    }

    public TokenImpl(TokenType type, int position, int lineNumber) {
        this(type, type.value, position, lineNumber);
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }

    @Override
    public String getLexeme() {
        return type.name();
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getLine() {
        return line;
    }
}
