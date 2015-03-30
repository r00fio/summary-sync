package service;

/**
 * Created by pixel on 3/29/15.
 */
public class SummaryServiceException extends RuntimeException{
    public SummaryServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public SummaryServiceException(String message) {
        super(message);
    }
}
