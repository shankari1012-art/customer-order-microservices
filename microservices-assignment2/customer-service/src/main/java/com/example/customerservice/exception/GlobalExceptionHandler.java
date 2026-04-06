package com.example.customerservice.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ For your "Customer not found" / "Order not found" (ResponseStatusException)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ApiError body = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getReason(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(body);
    }

    // ✅ For validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiError body = new ApiError(
                400,
                "Bad Request",
                msg,
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(body);
    }

    // ✅ Handles JSON parse errors (bad JSON body)
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleBadJson(Exception ex, HttpServletRequest request) {
        ApiError body = new ApiError(
                400,
                "Bad Request",
                "Invalid JSON request body",
                request.getRequestURI()
        );
        return ResponseEntity.badRequest().body(body);
    }

    // ✅ Fallback (any unexpected errors)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAny(Exception ex, HttpServletRequest request) {
        ApiError body = new ApiError(
                500,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(500).body(body);
    }
}
