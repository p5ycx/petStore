package com.agileim.petstore.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class PetExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        PetStoreExceptionResponse badEx = new PetStoreExceptionResponse();
        badEx.setMessage("Pet Id provided is not valid.");
        badEx.setCode("NOT FOUND");
        return new ResponseEntity<>(badEx, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        PetStoreExceptionResponse error = new PetStoreExceptionResponse();
        error.setMessage("Method is not supported");
        error.setCode("UNSUPPORTED");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PetNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(RuntimeException ex, WebRequest request) {
        PetStoreExceptionResponse error = new PetStoreExceptionResponse();
        error.setMessage("Pet not found");
        error.setCode("PET NOT FOUND");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


}
