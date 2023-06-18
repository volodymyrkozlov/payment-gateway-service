package com.volodymyrkozlow.paymentgateway.exception;

import com.volodymyrkozlow.paymentgateway.dto.error.TransactionDeclinedErrorDetailsDto;
import com.volodymyrkozlow.paymentgateway.dto.error.TransactionDeclinedErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to provide REST response errors.
 */
@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<TransactionDeclinedErrorDto> handle(final EntityNotFoundException ex) {
        final var status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status)
            .body(new TransactionDeclinedErrorDto(false, toErrorDetails(ex)));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<TransactionDeclinedErrorDto> handle(final ConstraintViolationException ex) {
        final var status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
            .body(new TransactionDeclinedErrorDto(false, toErrorDetails(ex)));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<TransactionDeclinedErrorDto> handle(final MethodArgumentNotValidException ex) {
        final var status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status)
            .body(new TransactionDeclinedErrorDto(false, toErrorDetails(ex)));
    }

    private static List<TransactionDeclinedErrorDetailsDto> toErrorDetails(final EntityNotFoundException ex) {
        return Collections.singletonList(new TransactionDeclinedErrorDetailsDto(ex.getMessage(), null));
    }

    private static List<TransactionDeclinedErrorDetailsDto> toErrorDetails(final ConstraintViolationException ex) {
        return ex.getConstraintViolations()
            .stream()
            .map(constraintViolation -> new TransactionDeclinedErrorDetailsDto(
                constraintViolation.getMessage(), constraintViolation.getPropertyPath().toString()))
            .collect(Collectors.toList());
    }

    private static List<TransactionDeclinedErrorDetailsDto> toErrorDetails(final BindingResult bindingResult) {
        final var errors = new ArrayList<TransactionDeclinedErrorDetailsDto>();
        bindingResult.getFieldErrors()
            .forEach(e -> errors.add(new TransactionDeclinedErrorDetailsDto(e.getDefaultMessage(), e.getField())));
        bindingResult.getGlobalErrors()
            .forEach(e -> errors.add(new TransactionDeclinedErrorDetailsDto(e.getDefaultMessage(), e.getObjectName())));

        return errors;
    }
}
