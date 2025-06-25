package dev.lukmann.auth;

import dev.lukmann.BaseResponseDto;
import dev.lukmann.auth.dto.LoginDto;
import dev.lukmann.auth.dto.RegisterDto;
import dev.lukmann.user.dto.UserDto;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Slf4j
@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Authentication")
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/register")
    public Response signUp(@Valid RegisterDto registerDto) {
        UserDto user = authService.registerUser(registerDto);

        return Response.ok(new BaseResponseDto<>(true, user, "User has been registered successfully"))
                .build();
    }

    @POST
    @Path("/login")
    public Response signIn(@Valid LoginDto logindto) {
        UserDto user = authService.loginUser(logindto);

        return Response.ok(new BaseResponseDto<>(true, user, "Login successfully"))
                .build();
    }

}
