package com.moneystats.MoneyStats.statement.DTO;

import com.moneystats.MoneyStats.statement.StatementException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class StatementResponseMapper {

    static final Map<StatementResponse.Type, Response.Status> statueses = null;

    public Response toResponse(StatementResponse response) {
        switch (response.getType()){
            case STATEMENT_ADDED:
                return Response.status(Response.Status.OK)
                        .entity(new StatementResponseDTO("STATEMENT_ADDED"))
                        .type(MediaType.APPLICATION_JSON)
                        .build();
        }
        StatementResponse.Type type = response.getType();
        return Response.status(statueses.getOrDefault(type, Response.Status.INTERNAL_SERVER_ERROR))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
