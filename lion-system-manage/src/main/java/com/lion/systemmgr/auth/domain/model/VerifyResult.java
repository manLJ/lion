package com.lion.systemmgr.auth.domain.model;

/**
 * Created by JACK on 2015/9/9.
 */
public class VerifyResult {
    private String message = "";
    private boolean passed = false;
    private String token;

    public VerifyResult(boolean passed) {
        this.passed = passed;
    }

    public VerifyResult(boolean passed, String message) {
        this.passed = passed;
        this.message = message;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
}
