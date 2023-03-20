package bank.controller.handler;

import bank.controller.v1.dto.ResponseV1;
import bank.exception.InvalidParameterException;
import bank.exception.KeywordNotFoundException;
import bank.exception.SearchResultNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {InvalidParameterException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseV1<?> invalidParameterException(final Exception e, final WebRequest req) {
        final ResponseV1<Void> response = new ResponseV1();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(e.getMessage());

        return response;
    }

    @ExceptionHandler(value = {KeywordNotFoundException.class, SearchResultNotFoundException.class})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseV1<?> notFoundException(final Exception e, final WebRequest req) {
        final ResponseV1<Void> response = new ResponseV1();
        response.setStatus(HttpStatus.NO_CONTENT.value());
        response.setMessage(e.getMessage());

        return response;
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseV1<?> unknownException(final Exception e, final WebRequest req) {
        final ResponseV1<Void> response = new ResponseV1();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(e.getMessage());

        return response;
    }

}
