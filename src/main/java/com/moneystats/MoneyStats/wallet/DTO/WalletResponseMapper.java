package com.moneystats.MoneyStats.wallet.DTO;


import com.moneystats.MoneyStats.statement.DTO.StatementResponse;
import com.moneystats.MoneyStats.statement.DTO.StatementResponseDTO;
import com.moneystats.MoneyStats.wallet.WalletService;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class WalletResponseMapper {

    static final Map<WalletResponse.Type, Response.Status> statueses = null;

    public Response toResponse(WalletResponse response) {
        switch (response.getType()){
            case WALLET_DELETED:
                return Response.status(Response.Status.OK)
                        .entity(new StatementResponseDTO("WALLET_DELETED"))
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            case WALLET_ADDED:
                return Response.status(Response.Status.OK)
                        .entity(new StatementResponseDTO("WALLET_ADDED"))
                        .type(MediaType.APPLICATION_JSON)
                        .build();
        }
        WalletResponse.Type type = response.getType();
        return Response.status(statueses.getOrDefault(type, Response.Status.INTERNAL_SERVER_ERROR))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
