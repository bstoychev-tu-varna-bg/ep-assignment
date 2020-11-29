package token.api;

public interface Token<Type> {
    Type getTokenType();

    String getLexeme();

    String getText();

    int getPosition();

    int getLine();
}
