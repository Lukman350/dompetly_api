package dev.lukmann.notification;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NotificationService {

    @Inject
    NotificationRepository notificationRepository;

}
