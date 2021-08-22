package com.moneystats.MoneyStats.category;

import com.moneystats.MoneyStats.response.ErrorResponseDTO;
import com.moneystats.MoneyStats.wallet.WalletException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Map;

public class CategoryExceptionMapper implements ExceptionMapper<CategoryException> {

    static final Map<CategoryException.Type, Response.Status> statueses;
    static final Map<CategoryException.Type, ErrorResponseDTO> bodies;

    static final ErrorResponseDTO unkonwnErrorResponse = new ErrorResponseDTO("UNKNOW ERROR");

    static {
        statueses = Map.of(
                CategoryException.Type.CATEGORY_ENTITIES_NOT_FOUND,
                Response.Status.NOT_FOUND,
                CategoryException.Type.CATEGORY_DTOS_NOT_FOUND,
                Response.Status.NOT_FOUND);

        bodies = Map.of(
                CategoryException.Type.CATEGORY_ENTITIES_NOT_FOUND,
                new ErrorResponseDTO("CATEGORY_ENTITIES_NOT_FOUND"),
                CategoryException.Type.CATEGORY_DTOS_NOT_FOUND,
                new ErrorResponseDTO("CATEGORY_DTOS_NOT_FOUND"));
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
    public Response toResponse(CategoryException exception) {
        CategoryException.Type type = exception.getType();
        return Response.status(statueses.getOrDefault(type, Response.Status.INTERNAL_SERVER_ERROR))
                .entity(bodies.getOrDefault(type, unkonwnErrorResponse))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
