package dev.lukmann.user;

import dev.lukmann.BaseResponseDto;
import dev.lukmann.user.dto.UpdatePinRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/api/authenticated/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Users Management")
public class UserResource {

    @Inject
    UserService userService;

    @PUT
    @Path("/update-pin")
    @RolesAllowed("user")
    public Response updatePin(
            @Context SecurityContext securityContext,
            @Valid UpdatePinRequest updatePinRequest
    ) {
        String userId = securityContext.getUserPrincipal().getName();

        boolean updated = userService.updatePin(userId, updatePinRequest);

        BaseResponseDto<?> response = new BaseResponseDto<>(
                updated,
                updated ? "Pin updated successfully" : "Failed to update pin"
        );

        return Response
                .status(updated ? Response.Status.OK : Response.Status.INTERNAL_SERVER_ERROR)
                .entity(response)
                .build();
    }
}
