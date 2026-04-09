package faw.backend.exceptions;

public class DuplicateDestinationException extends RuntimeException {
    public DuplicateDestinationException(String message) {
        super(message);
    }
}