package bank.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Result Found")
public class KeywordNotFoundException extends RuntimeException {

    public KeywordNotFoundException() {
    }

    public KeywordNotFoundException(String message) {
        super(message);
    }

    public KeywordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeywordNotFoundException(Throwable cause) {
        super(cause);
    }

    public KeywordNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                    boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
