package com.moneystats.MoneyStats.wallet;

import com.moneystats.MoneyStats.response.ErrorResponseDTO;
import com.moneystats.MoneyStats.statement.StatementException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Map;

public class WalletExceptionMapper implements ExceptionMapper<WalletException> {

    static final Map<WalletException.Type, Response.Status> statueses;
    static final Map<WalletException.Type, ErrorResponseDTO> bodies;

    static final ErrorResponseDTO unkonwnErrorResponse = new ErrorResponseDTO("UNKNOW ERROR");

    static {
        statueses = Map.of(
                WalletException.Type.USER_NOT_FOUND,
                Response.Status.NOT_FOUND,
                WalletException.Type.WALLETDTO_NULL,
                Response.Status.NO_CONTENT,
                WalletException.Type.CATEGORY_NOT_FOUND,
                Response.Status.NOT_FOUND,
                WalletException.Type.INVALID_WALLET_DTO,
                Response.Status.BAD_REQUEST,
                WalletException.Type.WALLET_NOT_FOUND,
                Response.Status.NOT_FOUND,
                WalletException.Type.STATEMENT_NOT_FOUND,
                Response.Status.NOT_FOUND);

        bodies = Map.of(
                WalletException.Type.USER_NOT_FOUND,
                new ErrorResponseDTO("USER_NOT_FOUND"),
                WalletException.Type.WALLETDTO_NULL,
                new ErrorResponseDTO("WALLETDTO_NULL"),
                WalletException.Type.CATEGORY_NOT_FOUND,
                new ErrorResponseDTO("CATEGORY_NOT_FOUND"),
                WalletException.Type.INVALID_WALLET_DTO,
                new ErrorResponseDTO("INVALID_WALLET_DTO"),
                WalletException.Type.WALLET_NOT_FOUND,
                new ErrorResponseDTO("WALLET_NOT_FOUND"),
                WalletException.Type.STATEMENT_NOT_FOUND,
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
    public Response toResponse(WalletException exception) {
        WalletException.Type type = exception.getType();
        return Response.status(statueses.getOrDefault(type, Response.Status.INTERNAL_SERVER_ERROR))
                .entity(bodies.getOrDefault(type, unkonwnErrorResponse))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
