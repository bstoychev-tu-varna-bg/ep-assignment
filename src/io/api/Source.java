package io.api;

public interface Source {
    char getCurrentChar();

    int getLineNumber();

    int getPosition();

    char next();
}
