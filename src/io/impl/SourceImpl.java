package io.impl;

import io.api.ResourceReader;
import io.api.Source;

public final class SourceImpl implements Source {
    public static final char EOF = '\uffff';
    private ResourceReader resourceReader;

    public SourceImpl(ResourceReader resourceReader) {
        this.resourceReader = resourceReader;
    }

    @Override
    public int getPosition() {
        return resourceReader.getPosition();
    }

    @Override
    public int getLineNumber() {
        return resourceReader.getLineNumber();
    }

    @Override
    public char getCurrentChar() {
        return resourceReader.getCurrentCharacter();
    }

    @Override
    public char next() {
        return resourceReader.getNextCharacter();
    }
}
