package com.moneystats.authentication;

import com.moneystats.MoneyStats.response.ErrorResponseDTO;
import com.moneystats.MoneyStats.statement.StatementException;
import com.moneystats.authentication.DTO.AuthErrorResponseDTO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Map;

public class AuthenticationExceptionMapper implements ExceptionMapper<AuthenticationException> {

    static final Map<AuthenticationException.Type, Response.Status> statueses;
    static final Map<AuthenticationException.Type, AuthErrorResponseDTO> bodies;

    static final AuthErrorResponseDTO unkonwnErrorResponse = new AuthErrorResponseDTO("UNKNOW ERROR");

    static {
        statueses = Map.of(
                AuthenticationException.Type.DATABASE_ERROR,
                Response.Status.SERVICE_UNAVAILABLE,
                AuthenticationException.Type.UNAUTHORIZED,
                Response.Status.UNAUTHORIZED,
                AuthenticationException.Type.NOT_ALLOWED,
                Response.Status.FORBIDDEN,
                AuthenticationException.Type.INVALID_AUTH_CREDENTIAL_DTO,
                Response.Status.BAD_REQUEST,
                AuthenticationException.Type.INVALID_AUTH_INPUT_DTO,
                Response.Status.BAD_REQUEST,
                AuthenticationException.Type.WRONG_CREDENTIAL,
                Response.Status.UNAUTHORIZED,
                AuthenticationException.Type.INVALID_TOKEN_DTO,
                Response.Status.UNAUTHORIZED,
                AuthenticationException.Type.AUTH_CREDENTIAL_DTO_NOT_FOUND,
                Response.Status.NOT_FOUND);

        bodies = Map.of(
                AuthenticationException.Type.DATABASE_ERROR,
                new AuthErrorResponseDTO("DATABASE_ERROR"),
                AuthenticationException.Type.NOT_ALLOWED,
                new AuthErrorResponseDTO("NOT_ALLOWED"),
                AuthenticationException.Type.UNAUTHORIZED,
                new AuthErrorResponseDTO("UNAUTHORIZED"),
                AuthenticationException.Type.INVALID_AUTH_CREDENTIAL_DTO,
                new AuthErrorResponseDTO("INVALID_AUTH_CREDENTIAL_DTO"),
                AuthenticationException.Type.INVALID_AUTH_INPUT_DTO,
                new AuthErrorResponseDTO("INVALID_AUTH_INPUT_DTO"),
                AuthenticationException.Type.WRONG_CREDENTIAL,
                new AuthErrorResponseDTO("WRONG_CREDENTIAL"),
                AuthenticationException.Type.INVALID_TOKEN_DTO,
                new AuthErrorResponseDTO("INVALID_TOKEN_DTO"),
                AuthenticationException.Type.AUTH_CREDENTIAL_DTO_NOT_FOUND,
                new AuthErrorResponseDTO("AUTH_CREDENTIAL_DTO_NOT_FOUND"));
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
    public Response toResponse(AuthenticationException exception) {
        AuthenticationException.Type type = exception.getType();
        return Response.status(statueses.getOrDefault(type, Response.Status.INTERNAL_SERVER_ERROR))
                .entity(bodies.getOrDefault(type, unkonwnErrorResponse))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
