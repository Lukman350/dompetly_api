package dev.lukmann.user;

import dev.lukmann.exceptions.ServicesException;
import dev.lukmann.user.domain.User;
import dev.lukmann.user.dto.UpdatePinRequest;
import dev.lukmann.utils.StringUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public boolean updatePin(String userId, UpdatePinRequest updatePinRequest) {
        User user = userRepository.findById(UUID.fromString(userId));

        if (user == null) {
            throw new ServicesException(Response.Status.NOT_FOUND, "User not found");
        }

        Integer pin;

        try {
            pin = Math.toIntExact(StringUtil.decodeBase64ToInteger(updatePinRequest.getPin()));
        } catch (IllegalArgumentException e) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Invalid pin format");
        }

        if (user.getPin() != null && !user.getPin().equals(pin)) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "Current pin is incorrect");
        }

        if (!updatePinRequest.getNewPin().equals(updatePinRequest.getConfirmNewPin())) {
            throw new ServicesException(Response.Status.BAD_REQUEST, "New pin and confirm pin do not match");
        }

        user.setPin(pin);
        userRepository.persist(user);

        return true;
    }
}
