package br.com.sonda.aeronave.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.Instant;


@RestControllerAdvice
public class AeronavesExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardFieldsException> entityNotFound(EntityNotFoundException entityNotFoundException, HttpServletRequest httpServletRequest){
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardFieldsException customError = new StandardFieldsException(Instant.now(), status.value(), " ",
                httpServletRequest.getRequestURI());
        return ResponseEntity.status(status).body(customError);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomFieldsException> methodArgumentNotValid(MethodArgumentNotValidException notValid, HttpServletRequest httpServletRequest){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomFieldsException argumentErrorField = new CustomFieldsException(Instant.now(), status.value(),
                httpServletRequest.getRequestURI(), notValid.getFieldError().getField(), notValid.getFieldError().getDefaultMessage());

        return ResponseEntity.badRequest().body(argumentErrorField);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomFieldsException> messageNotReable(HttpMessageNotReadableException notValid, HttpServletRequest httpServletRequest){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomFieldsException argumentErrorField = new CustomFieldsException(Instant.now(), status.value(),
                httpServletRequest.getRequestURI(),  " ", notValid.getMessage());

        return ResponseEntity.badRequest().body(argumentErrorField);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomFieldsException> integrityViolation(DataIntegrityViolationException notValid, HttpServletRequest httpServletRequest){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        CustomFieldsException customFieldsException = new CustomFieldsException(Instant.now(), status.value(), httpServletRequest.getRequestURI(),
                "id", notValid.getClass().getSimpleName());
        return ResponseEntity.badRequest().body(customFieldsException);
    }
}