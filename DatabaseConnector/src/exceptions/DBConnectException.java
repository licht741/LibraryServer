package exceptions;

public class DBConnectException extends Exception {
    public DBConnectException() {}
    public DBConnectException(String message) {
        super(message);
    }
}
