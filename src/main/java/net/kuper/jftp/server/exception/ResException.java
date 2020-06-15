package net.kuper.jftp.server.exception;

public class ResException extends RuntimeException {

    public ResException(String message) {
        super(message);
    }

    public ResException(String message, Throwable cause) {
        super(message, cause);
    }
}
