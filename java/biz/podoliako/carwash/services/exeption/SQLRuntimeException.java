package biz.podoliako.carwash.services.exeption;


public class SQLRuntimeException extends RuntimeException{
    public SQLRuntimeException() { super(); }
    public SQLRuntimeException(String message) { super(message); }
    public SQLRuntimeException(String message, Throwable cause) { super(message, cause); }
    public SQLRuntimeException(Throwable cause) { super(cause); }
}
