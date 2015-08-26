package biz.podoliako.carwash.services.exeption;


public class NamingRuntimeException extends  RuntimeException{
    public NamingRuntimeException() { super(); }
    public NamingRuntimeException (String message) { super(message); }
    public NamingRuntimeException(String message, Throwable cause) { super(message, cause); }
    public NamingRuntimeException(Throwable cause) { super(cause); }

}
