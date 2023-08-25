package com.taskservice.icv.advice.constants;

public class ErrorCodes {

    private ErrorCodes() {
        throw new IllegalArgumentException("ErrorCodes class is constant class.");
    }

    public static final int NO_SUCH_TODO = 103000;
    public static final int ID_NOT_FOUND = 103001;
    public static final int UNKNOWN = 103999;
}
