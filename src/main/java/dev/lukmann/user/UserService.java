package dev.lukmann.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class UserService {

  @Inject
  UserRepository userRepository;
}
