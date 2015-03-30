package provider;

/**
 * Created by pixel on 3/28/15.
 */
public class SummaryProviderException extends RuntimeException {
    public SummaryProviderException(String message) {
        super(message);
    }

    public SummaryProviderException(String message, Throwable cause) {
        super(message, cause);
    }
}
