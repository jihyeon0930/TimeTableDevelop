package com.example.timetabledevelop.global.exception;

import org.springframework.http.HttpStatus;

public class DuplicateException extends CustomException {

    public DuplicateException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
