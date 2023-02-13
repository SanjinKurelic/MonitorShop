package eu.sanjin.error;

public class ResponseParseException extends RuntimeException {

    public ResponseParseException(Throwable e) {
        super(e);
    }
}
