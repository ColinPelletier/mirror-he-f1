package ch.hearc.hef1.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ch.hearc.hef1.model.Notification;
import ch.hearc.hef1.model.User;
import ch.hearc.hef1.model.UserRole;
import ch.hearc.hef1.repository.NotificationRepository;
import ch.hearc.hef1.service.UserService;

@Controller
public class NotificationController {

    @Autowired
    UserService userService;

    @Autowired
    NotificationRepository notificationRepository;

    @GetMapping("/notifications")
    public String recruiting(Map<String, Object> model) {

        User authenticatedUser = userService.getAuthenticatedUser();

        if (userService.isAuthenticated(authenticatedUser)) {

            model.put("notifications", authenticatedUser.getNotifications());
            return "notifications";
        }

        throw new RuntimeException("User needs to be authenticated.");
    }

    @PostMapping("/notification/{strNotificationId}/accept")
    public String notificationAccept(@PathVariable String strNotificationId, Map<String, Object> model) {
        return removeNotification(true, strNotificationId);
    }

    @PostMapping("/notification/{strNotificationId}/decline")
    public String notificationDecline(@PathVariable String strNotificationId, Map<String, Object> model) {
        return removeNotification(false, strNotificationId);
    }

    private String removeNotification(boolean accepted, String strNotificationId) {
        // Check id validity
        long notificationId;

        try {
            notificationId = Long.parseLong(strNotificationId);
        } catch (NumberFormatException e) {
            throw new RuntimeException("ID must be an integer.");
        }

        User authenticatedUser = userService.getAuthenticatedUser();

        if (userService.isAuthenticated(authenticatedUser)) {
            Optional<Notification> notification = notificationRepository.findById(notificationId);

            if (notification.isPresent()) {
                Notification notif = notification.get();
                String redirection = "redirect:/notifications?declined";

                if (accepted) {
                    authenticatedUser.setTeam(notif.getTeam());
                    userService.updateUser(authenticatedUser);
                    redirection = "redirect:/notifications?accepted";
                }

                notificationRepository.delete(notif);
                return redirection;

            } else {
                throw new RuntimeException("The notification that you want to accept or decline doesn't exist.");
            }
        }
        throw new RuntimeException("User needs to be authenticated.");
    }
}
