package dev.lukmann.user;

import dev.lukmann.user.domain.User;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, UUID> {

    public User findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }

    public User findByFirebaseUid(String firebaseUid) {
        return find("firebaseUid", firebaseUid).firstResult();
    }

}
