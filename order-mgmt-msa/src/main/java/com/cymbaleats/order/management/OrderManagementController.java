package com.cymbaleats.order.management;


import java.lang.reflect.Method;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import  java.util.logging.Level;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Date;
import com.google.cloud.Timestamp;

import reactor.core.publisher.Mono;

@RestController
public class OrderManagementController {

  private static final Logger logger = Logger.getLogger("ShoppingCartController");
  private OrderUtils orderUtils = new OrderUtils();
  private static Map<String, Method> orderItemsMethods = new HashMap<>();
  private static RestTemplate restTemplate = new RestTemplate();
  private static final String notificationSvcUrl = "https://cymbal-eats.org/notification/addnotification";

  @GetMapping("/order-mgmt-api/list-orders")
  public List<Order> getUserOrders(@RequestParam(value="user-id")String userId)
      throws ExecutionException, InterruptedException, IOException {
    User user = new User(userId);
    return orderUtils.getUserOrder(user);
  }

  @PostMapping("/order-mgmt-api/get-order-details")
  public Order getUserOrderDetailss(@RequestBody Order order)
      throws ExecutionException, InterruptedException, IOException {
    return orderUtils.getOrderDetails(order);
  }

  @GetMapping("/order-mgmt-api")
  public String ping() throws ExecutionException, InterruptedException, IOException {
    System.out.println("in Ping!!");

    return "A Ping!!!";
  }


  @PostMapping("/order-mgmt-api/place-order") // Change to @PostMapping for a proper add operation
  public String placeAnOrder(@RequestBody Order order) throws ExecutionException, InterruptedException, IOException {
   long orderId = orderUtils.insertOrder(order);
   Date date = new Date();
   Notification notification = new Notification(order.getUserId(),  orderId,  "Order "+orderId+" Submitted", Notification.Status.Unread, date);
   HttpEntity<Notification> request = new HttpEntity<>(notification);
   String response = restTemplate.postForObject(notificationSvcUrl, request, String.class);
   date.setTime(new Date().getTime()+ 1000000);
   notification = new Notification(order.getUserId(),  orderId,  "Order "+orderId+" Being Prepared", Notification.Status.Unread, date );
   request = new HttpEntity<>(notification);
   response = restTemplate.postForObject(notificationSvcUrl, request, String.class);
   date.setTime(new Date().getTime()+ 4000000);
   notification = new Notification(order.getUserId(),  orderId,  "Order "+orderId+" Prepared", Notification.Status.Unread, date );
   request = new HttpEntity<>(notification);
   response = restTemplate.postForObject(notificationSvcUrl, request, String.class);
   date.setTime(new Date().getTime()+ 5000000);
   notification = new Notification(order.getUserId(),  orderId,  "Order "+orderId+" Ready for Delivery", Notification.Status.Unread, date );
   request = new HttpEntity<>(notification);
   response = restTemplate.postForObject(notificationSvcUrl, request, String.class);
   date.setTime(new Date().getTime()+ 6000000);
   notification = new Notification(order.getUserId(),  orderId,  "Order "+orderId+" Ready out for Delivery", Notification.Status.Unread, date );
   request = new HttpEntity<>(notification);
   response = restTemplate.postForObject(notificationSvcUrl, request, String.class);
   System.out.println("After Calling Notrfication + "+ response);
    return "Order Placed";  // Return a more appropriate response (e.g., the created restaurant object)
  }

}