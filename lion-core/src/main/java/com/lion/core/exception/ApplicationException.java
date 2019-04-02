package com.lion.core.exception;

/**
 * @author Lianhong
 */
public class ApplicationException extends RuntimeException {

    private String code;

    public String getCode() {
        return code;
    }

    public ApplicationException(String message) {
        super(message, null, true, false);
        this.code = "UNKNOWN";
    }

    public ApplicationException(String code, String message) {
        this(message);
        this.code = code;
    }

}
