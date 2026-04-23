package com.example.timetabledevelop.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. CustomException 통합 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustom(CustomException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getStatus().value(), e.getMessage()));
    }

    // 2. Validation 처리 (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException e) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    // 3. DB 중복 에러 (unique constraint)
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate() {
        return ResponseEntity
                .status(409)
                .body(new ErrorResponse(409, "이미 존재하는 데이터입니다."));
    }

    // 4. 예상 못한 에러 (필수)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAll(Exception e) {
        return ResponseEntity
                .status(500)
                .body(new ErrorResponse(500, "서버 오류가 발생했습니다."));
    }
}