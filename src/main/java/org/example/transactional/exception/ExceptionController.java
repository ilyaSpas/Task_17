package org.example.transactional.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler
    private ResponseEntity<CustomExceptionResponse> handleException(CustomerNotFoundException e) {
        CustomExceptionResponse response = new CustomExceptionResponse("Customer not founded!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CustomExceptionResponse> handleException(OrderNotFoundException e) {
        CustomExceptionResponse response = new CustomExceptionResponse("Order not founded!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CustomExceptionResponse> handleException(ProductNotFoundException e) {
        CustomExceptionResponse response = new CustomExceptionResponse("Product not founded!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CustomExceptionResponse> handleException(ThereAreNotEnoughFundsException e) {
        CustomExceptionResponse response = new CustomExceptionResponse("There are not enough funds!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CustomExceptionResponse> handleException(ProductOutOfStockException e) {
        CustomExceptionResponse response = new CustomExceptionResponse("Product is over!");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
