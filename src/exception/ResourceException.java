package exception;

public final class ResourceException extends RuntimeException {
    public ResourceException(String message, int lineNumber, int position) {
        super(AppendLineNumberAndPositionToMessage(message, lineNumber, position));
    }

    private static String AppendLineNumberAndPositionToMessage(String message, int lineNumber, int position) {
        return message + " At line: " + lineNumber + " Position: " + position;
    }
}
