package io.impl;

import exception.ResourceException;
import io.api.ResourceReader;

import java.io.*;

public final class ResourceReaderImpl implements ResourceReader {
    private BufferedReader bufferedReader;
    private String currentLine;
    private char currentChar;
    private int position;
    private int lineNumber;

    public ResourceReaderImpl(File file) throws FileNotFoundException {
        this.bufferedReader = new BufferedReader(new FileReader(file));
        position = 0;
        lineNumber = 0;
    }

    @Override
    public char getCurrentCharacter() {
        return currentChar;
    }

    @Override
    public char getNextCharacter() {
        readNextCharacter();
        return currentChar;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }

    private void readNextCharacter() {
        if (currentLine == null || position >= currentLine.length()) {
            try {
                readLine();
                if (currentLine == null) {
                    currentChar = '\uffff';
                    return;
                }
            } catch (IOException ex) {
                throw new ResourceException("IO Error: " + ex.getMessage(), lineNumber, position);
            }
        }
        currentChar = currentLine.charAt(position);
        position++;
    }

    private void readLine() throws IOException {
        currentLine = bufferedReader.readLine();
        while (currentLine != null && currentLine.equals("")) {
            currentLine = bufferedReader.readLine();
            lineNumber++;
        }
        position = 0;
        //lineNumber++;
    }
}
