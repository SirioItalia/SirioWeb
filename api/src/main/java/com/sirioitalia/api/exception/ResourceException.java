package com.sirioitalia.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Long resourceId;
    private String errorCode;
    private HttpStatus status;

    public ResourceException(String message) {
        super(message);
    }

    public ResourceException(Long resourceId, String message) {
        super(message);
        this.resourceId = resourceId;
    }
    public ResourceException(Long resourceId, String errorCode, String message) {
        super(message);
        this.resourceId = resourceId;
        this.errorCode = errorCode;
    }

    public ResourceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ResourceException(String errorCode, String message, HttpStatus status) {
        super(message + status, null, true, true);
        this.errorCode = errorCode;
        this.status = status;
    }

    public ResourceException(String message, Throwable cause, HttpStatus status) {
        super(message + status, cause, true, true);
        this.status = status;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}