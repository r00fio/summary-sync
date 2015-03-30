package dao;

/**
 * Created by pixel on 3/30/15.
 */
public class DaoException extends RuntimeException{
    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
