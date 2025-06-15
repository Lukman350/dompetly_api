package dev.lukmann.exceptions;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;
import java.util.Map;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<Map<String, String>> errors = exception.getConstraintViolations()
                .stream()
                .map(violation -> Map.of(
                        "field", extractField(violation.getPropertyPath().toString()),
                        "message", violation.getMessage()
                ))
                .toList();

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of(
                        "success", false,
                        "message", "Validation Failed",
                        "violations", errors
                ))
                .build();
    }

    private String extractField(String path) {
        String[] parts = path.split("\\.");
        return parts.length > 0 ? parts[parts.length - 1] : path;
    }
}
