package com.dashboard.saas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {


    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCategoryNotFoundException(
            CategoryNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("message", "Category Not Found");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ProductAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleProductAlreadyExistException(
            ProductAlreadyExistException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("message", "Product Already Exists Exception");

        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
    }


    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(
            ProductNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("message", "Product Not Found Exception");

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VariantAlreadyExistForML.class)
    public ResponseEntity<Map<String, Object>> handleCategoryAlreadyExistException(
            VariantAlreadyExistForML ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("message", "VariantAlreadyExistForML Exception");

        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    public ResponseEntity<Map<String, Object>> handleCategoryAlreadyExistException(
            CategoryAlreadyExistException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("message", "CategoryAlreadyExist Exception");

        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
    }



    @ExceptionHandler(VariantNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCategoryVariantNotFoundException(
            VariantNotFoundException ex) {

        Map<String, Object> response = new HashMap<>();
        response.put("status", false);
        response.put("message", "VariantNotFoundException ");

        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred: " + ex.getMessage());
    }


}
