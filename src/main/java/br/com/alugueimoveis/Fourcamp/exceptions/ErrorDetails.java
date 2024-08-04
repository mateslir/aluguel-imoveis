package br.com.alugueimoveis.Fourcamp.exceptions;

import org.springframework.http.HttpStatus;

public class ErrorDetails{
    private HttpStatus status;
    private String message;
    private String details;
    public ErrorDetails(HttpStatus status, String message, String details) {
        super();
        this.status = status;
        this.message = message;
        this.details = details;
    }
    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public String getDetails() {
        return details;
    }
}

