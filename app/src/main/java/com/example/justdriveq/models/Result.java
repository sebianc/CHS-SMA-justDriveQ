package com.example.justdriveq.models;

public class Result {
    private String email;
    private boolean result;

    public Result(String email, boolean result) {
        this.email = email;
        this.result = result;
    }

    public Result(){}
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
