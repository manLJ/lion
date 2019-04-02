package com.lion.core.exception.handler;

/**
 * @author Lianhong
 */
public class RestServiceError {

    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    static RestServiceError build(String code, String message) {
        RestServiceError error = new RestServiceError();
        error.code = code;
        error.description = message;
        return error;
    }
}
