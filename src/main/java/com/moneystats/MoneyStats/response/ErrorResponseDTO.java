package com.moneystats.MoneyStats.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ErrorResponseDTO implements Serializable {

    private String error;

    public ErrorResponseDTO(@JsonProperty("error") String error) {
        this.error = error;
    }
    public ErrorResponseDTO() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
