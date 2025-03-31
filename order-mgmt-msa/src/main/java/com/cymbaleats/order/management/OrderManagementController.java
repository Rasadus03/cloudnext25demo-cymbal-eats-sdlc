package com.cymbaleats.order.management;


import java.lang.reflect.Method;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import  java.util.logging.Level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Date;
import com.google.cloud.Timestamp;

@RestController
public class OrderManagementController {

  private static final Logger logger = Logger.getLogger("ShoppingCartController");
  private OrderUtils orderUtils = new OrderUtils();
  private static Map<String, Method> orderItemsMethods = new HashMap<>();


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
   orderUtils.insertOrder(order);
    return "Order Placed";  // Return a more appropriate response (e.g., the created restaurant object)
  }

}