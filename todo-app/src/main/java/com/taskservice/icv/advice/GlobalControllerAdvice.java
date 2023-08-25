package com.taskservice.icv.advice;

import com.taskservice.icv.advice.constants.ErrorCodes;
import com.taskservice.icv.advice.exception.NoSuchTodoException;
import com.taskservice.icv.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(NoSuchTodoException.class)
    public ResponseEntity<ErrorResponse> handle(NoSuchTodoException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.builder()
                        .code(e.getCode())
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.builder()
                        .code(ErrorCodes.UNKNOWN)
                        .message(e.getMessage())
                        .build());
    }
}