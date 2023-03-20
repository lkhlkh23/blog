package bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Result Found")
public class SearchResultNotFoundException extends RuntimeException {

    public SearchResultNotFoundException() {
    }

    public SearchResultNotFoundException(String message) {
        super(message);
    }

    public SearchResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public SearchResultNotFoundException(Throwable cause) {
        super(cause);
    }

    public SearchResultNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
