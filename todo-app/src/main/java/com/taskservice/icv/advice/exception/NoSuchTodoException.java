package com.taskservice.icv.advice.exception;

import com.taskservice.icv.advice.constants.ErrorCodes;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoSuchTodoException extends RuntimeException {

    private final int code;
    private final HttpStatus httpStatus;

    public NoSuchTodoException() {
        super("No such todo!");
        this.code = ErrorCodes.NO_SUCH_TODO;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}

