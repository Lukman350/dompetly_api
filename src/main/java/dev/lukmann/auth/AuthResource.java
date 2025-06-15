package dev.lukmann.auth;

import dev.lukmann.BaseResponseDto;
import dev.lukmann.exceptions.ServicesException;
import dev.lukmann.user.UserService;
import dev.lukmann.user.domain.User;
import dev.lukmann.user.dto.UserDto;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Path("/api/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService;

    @POST
    @Path("/register")
    public Response signUp(@Valid UserDto userDto) {
        try {
            User user = userService.registerUser(userDto);

            return Response.ok(new BaseResponseDto<>(true, user, "User has been registered successfully"))
                    .build();
        } catch (ServicesException e) {
            log.error("Error occurred: ", e);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new BaseResponseDto<>(false, null, e.getMessage()))
                    .build();
        } catch (Exception e) {
            log.error("Error occurred: ", e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BaseResponseDto<>(false, null, "Oopss... An error occured on the server"))
                    .build();
        }
    }

    public static class LoginRequest {
        @NotBlank(message = "Email field is required")
        @Email(message = "Email field must be an valid email address")
        public String email;
        @NotBlank(message = "Password field is required")
        public String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @POST
    @Path("/login")
    public Response signIn(@Valid LoginRequest loginRequest) {
        try {
            String token = userService.loginUser(loginRequest.email, loginRequest.password);

            return Response.ok(new BaseResponseDto<>(true, Map.of("token", token), "Login successfully"))
                    .build();
        } catch (ServicesException e) {
            log.error("Error occurred: ", e);

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new BaseResponseDto<>(false, null, e.getMessage()))
                    .build();
        } catch (Exception e) {
            log.error("Error occurred: ", e);

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new BaseResponseDto<>(false, null, "Oopss... An error occured on the server"))
                    .build();
        }
    }

}
