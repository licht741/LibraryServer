package exceptions;

import java.sql.SQLException;


public class SomeDBException extends Exception {
    public SomeDBException() {}
    public SomeDBException(String message) {
        super(message);
    }
}
