package com.cymbaleats.notification;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class NotificationController {

  private static final Logger logger = Logger.getLogger("NotificationController");
  private NotifcationUtils notificationUtils = new NotifcationUtils();
  //private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yy-HH:mm:ss");

  @GetMapping("/notification/list-notifications")
  public List<Notification> getUserNotifications(@RequestParam(value="user-id")String userId)
      throws ExecutionException, InterruptedException, IOException {
   User user = new User(userId);
    return notificationUtils.getUserNotification(user);
  }

  @GetMapping("/notification/get-notifications-unread-count")
  public long getUserUnreadNotificationsCount(@RequestParam(value="user-id")String userId)
      throws ExecutionException, InterruptedException, IOException {
    User user = new User(userId);
    return notificationUtils.getUserUnreadNotificationCount(user);
  }

  @GetMapping("/notification")
  public String ping() throws ExecutionException, InterruptedException, IOException {
    System.out.println("in Ping!!");

    return "A Ping!!!";
  }


  @PostMapping("/notification/addnotification") // Change to @PostMapping for a proper add operation
  public String addNotification(@RequestBody Notification notification) throws ExecutionException, InterruptedException, IOException {
    System.out.println("In adding notification!!");
      notificationUtils.insertNotification(notification);
    return "Notification Added";  // Return a more appropriate response (e.g., the created restaurant object)
  }


  @PostMapping("/notification/update-notification-status") // Change to @PostMapping for a proper add operation
  public String updateNotificationStatus(@RequestBody Notification notification) throws ExecutionException, InterruptedException, IOException {
    notificationUtils.updateNotificationStatus(notification);
    return "Notification Updated";  // Return a more appropriate response (e.g., the created restaurant object)
  }


}