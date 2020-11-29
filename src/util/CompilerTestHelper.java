package util;

import lexer.api.Lexer;
import token.api.Token;

public final class CompilerTestHelper {

    public static String getTokensAsString(Lexer<?> lexer) {
        StringBuilder sb = new StringBuilder();
        Token token;
        while((token = lexer.nextToken()) != null) {
            sb.append("[ ").append(token.getLexeme()).append(" ] : ").append(token.getText()).append("\n");
        }
        return sb.toString();
    }
}
