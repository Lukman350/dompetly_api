package dev.lukmann.exceptions;

import jakarta.ws.rs.core.Response;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServicesException extends Exception {
    private Response.Status status;
    private String message;

    public ServicesException(Response.Status status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
