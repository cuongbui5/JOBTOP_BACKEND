package com.example.jobs_top.exception;


import com.example.jobs_top.dto.res.BaseResponse;
import jakarta.persistence.OptimisticLockException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class ExceptionHandlerGlobal {
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<BaseResponse> handleOptimisticLockException(OptimisticLockException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new BaseResponse(HttpStatus.CONFLICT.value(), "Transaction failed due to concurrent modification. Please try again."));
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<BaseResponse> handlerNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<BaseResponse> handlerIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(HttpStatus.BAD_REQUEST.value(), "IllegalStateException: " + e.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError != null ? fieldError.getDefaultMessage() : "Invalid input";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse> handlerRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handlerIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new BaseResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new BaseResponse(HttpStatus.CONFLICT.value(), extractDuplicateEntryMessage(e)));
    }

    private String extractDuplicateEntryMessage(DataIntegrityViolationException e) {
        String defaultMessage = "Data integrity violation";
        Throwable cause = e.getCause();

        if (cause != null) {
            String message = cause.getMessage();
            Pattern pattern = Pattern.compile("Duplicate entry '([^']*)'");
            Matcher matcher = pattern.matcher(message);
            if (matcher.find()) {
                return "Đã tồn tại '" + matcher.group(1) + "'";
            }
        }
        return defaultMessage;
    }




}
