package at.klimo.aoc;

public class ImplementationException extends RuntimeException {
    public ImplementationException(String message) {
        super(message);
    }

    public ImplementationException(Throwable cause) {
        super(cause);
    }
}
