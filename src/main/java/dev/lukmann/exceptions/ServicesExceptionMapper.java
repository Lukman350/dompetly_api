package dev.lukmann.exceptions;

import dev.lukmann.BaseResponseDto;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServicesExceptionMapper implements ExceptionMapper<ServicesException> {
    @Override
    public Response toResponse(ServicesException e) {
        return Response.status(e.getStatus().getStatusCode())
                .entity(new BaseResponseDto<>(false, null, e.getMessage())).build();
    }
}
