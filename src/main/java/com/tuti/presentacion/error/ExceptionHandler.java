package com.tuti.presentacion.error;

public class ExceptionHandler {
    private int statusCode;
    private String message;
    private String path;

    public ExceptionHandler(int statusCode, String message, String path) {
        this.statusCode = statusCode;
        this.message = message;
        this.path = path;
    }

    // Getters y setters
    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
