package lexer.api;

import token.api.Token;

public interface Lexer<TokenType> {
    Token<TokenType> nextToken();
}
