package com.moneystats.MoneyStats.statement.DTO;

public class StatementResponseDTO {

    private String response;

    public StatementResponseDTO(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
