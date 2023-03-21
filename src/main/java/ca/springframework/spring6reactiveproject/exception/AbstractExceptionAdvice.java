package ca.springframework.spring6reactiveproject.exception;


import org.modelmapper.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
public class AbstractExceptionAdvice {


    @ExceptionHandler(WebExchangeBindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationException(WebExchangeBindException ex) {
        return ResponseEntity
                .ok("Validation Exception: " + "Genel hata mesajı ->" + ex.getBody().getType().getSchemeSpecificPart());
    }

}
