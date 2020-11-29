package io.api;

public interface ResourceReader {

    char getCurrentCharacter();

    char getNextCharacter();

    int getPosition();

    int getLineNumber();
}
