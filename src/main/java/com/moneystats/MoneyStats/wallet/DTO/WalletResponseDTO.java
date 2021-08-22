package com.moneystats.MoneyStats.wallet.DTO;

public class WalletResponseDTO {

    private String response;

    public WalletResponseDTO(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
