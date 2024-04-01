package org.orgless.carboncellassignment.exceptionhandler;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * All pathways that may throw irreconcilable exceptions are intercepted by this class.
 * This class return structured JSON objects to the client giving information about the failed request.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
            UsernameNotFoundException.class,
            NoSuchElementException.class,
            IllegalArgumentException.class,
            BadCredentialsException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GenericErrorResponse> exceptionHandler(Exception e) {
        GenericErrorResponse error = GenericErrorResponse.builder()
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.value())
                .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)))
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(
        MethodArgumentNotValidException.class
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> validationExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> response = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            response.put("Invalid field received -> " + field, errorMessage);
        });

        response.put("timeStamp", LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
