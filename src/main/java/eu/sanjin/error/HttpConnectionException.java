package eu.sanjin.error;

public class HttpConnectionException extends RuntimeException {

    public HttpConnectionException(Throwable e) {
        super(e);
    }
}
