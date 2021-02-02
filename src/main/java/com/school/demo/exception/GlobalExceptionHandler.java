package com.school.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NoSuchDataException.class, InvalidArgumentException.class})
    public ResponseEntity<Object> exceptionHandling(CustomException exception) {
        return new ResponseEntity<>(Collections.singletonMap("error", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
