package com.example.weatherApp.customExceptions;


public class CustomExceptionDto {

    private String message;
    private String details;
    private String hint;
    protected CustomExceptionDto() {
    }


    public CustomExceptionDto(
            String message, String details, String hint, String nextActions, String support) {
        this.message = message;
        this.details = details;
        this.hint = hint;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}