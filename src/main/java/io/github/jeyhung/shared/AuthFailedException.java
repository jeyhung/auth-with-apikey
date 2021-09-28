package io.github.jeyhung.shared;

public class AuthFailedException extends RuntimeException {
    public AuthFailedException(String message) {
        super(message);
    }
}
