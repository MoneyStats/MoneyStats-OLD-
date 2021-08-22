package com.moneystats.MoneyStats.statement;

import com.moneystats.MoneyStats.response.ErrorResponseDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Map;

public class StatementExceptionMapper implements ExceptionMapper<StatementException> {

    static final Map<StatementException.Type, Response.Status> statueses;
    static final Map<StatementException.Type, ErrorResponseDTO> bodies;

    static final ErrorResponseDTO unkonwnErrorResponse = new ErrorResponseDTO("UNKNOW ERROR");

    static {
        statueses = Map.of(
                        StatementException.Type.INVALID_STATEMENT_DTO,
                        Response.Status.BAD_REQUEST,
                        StatementException.Type.USER_NOT_FOUND,
                        Response.Status.NOT_FOUND,
                        StatementException.Type.WALLET_NOT_FOUND,
                        Response.Status.NOT_FOUND,
                        StatementException.Type.STATEMENT_NOT_FOUND,
                        Response.Status.NOT_FOUND);

        bodies = Map.of(
                StatementException.Type.INVALID_STATEMENT_DTO,
                new ErrorResponseDTO("INVALID_STATEMENT_DTO"),
                StatementException.Type.USER_NOT_FOUND,
                new ErrorResponseDTO("USER_NOT_FOUND"),
                StatementException.Type.WALLET_NOT_FOUND,
                new ErrorResponseDTO("WALLET_NOT_FOUND"),
                StatementException.Type.STATEMENT_NOT_FOUND,
                new ErrorResponseDTO("STATEMENT_NOT_FOUND"));
    }

    /**
     * Map an exception to a {@link Response}. Returning
     * {@code null} results in a {@link Response.Status#NO_CONTENT}
     * response. Throwing a runtime exception results in a
     * {@link Response.Status#INTERNAL_SERVER_ERROR} response.
     *
     * @param exception the exception to map to a response.
     * @return a response mapped from the supplied exception.
     */
    @Override
    public Response toResponse(StatementException exception) {
        StatementException.Type type = exception.getType();
        return Response.status(statueses.getOrDefault(type, Response.Status.INTERNAL_SERVER_ERROR))
                .entity(bodies.getOrDefault(type, unkonwnErrorResponse))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
