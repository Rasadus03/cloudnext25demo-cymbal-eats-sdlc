package com.cymbaleats.shopping.cart;

import java.util.Date;
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
public class ShoppingCartController {

  private static final Logger logger = Logger.getLogger("ShoppingCartController");
  private CartUtils cartUtils = new CartUtils();
  //private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yy-HH:mm:ss");

  @PostMapping("/shopping-cart-api/addUser") // Change to @PostMapping for a proper add operation
  public String addShoppingCartItem(@RequestBody User user) throws ExecutionException, InterruptedException, IOException {
    cartUtils.insertUser(user);
    return "User added";  // Return a more appropriate response (e.g., the created restaurant object)
  }



  @GetMapping("/shopping-cart-api/view-shopping-cart")
  public List<ShoppingCartItem> getUserShoppingCart(@RequestParam(value="user-id")String userId)
      throws ExecutionException, InterruptedException, IOException {
   User user = new User(userId);
    return cartUtils.getUserCart(user);
  }

  @GetMapping("/shopping-cart-api/get-cart-item-count")
  public long getCartItemCount(@RequestParam(value="user-id")String userId)
      throws ExecutionException, InterruptedException, IOException {
    User user = new User(userId);
    return cartUtils.getUserCartItemCount(user);
  }

  @GetMapping("/shopping-cart-api")
  public String ping() throws ExecutionException, InterruptedException, IOException {
    System.out.println("in Ping!!");

    return "A Ping!!!";
  }


  @PostMapping("/shopping-cart-api/add-shopping-cart-item") // Change to @PostMapping for a proper add operation
  public String addShoppingCartItem(@RequestBody ShoppingCartItem item) throws ExecutionException, InterruptedException, IOException {
    ShoppingCartItem currentItem = cartUtils.getMenuItem(item);
    if (currentItem == null) {
      item.setTimeAdded(new Date());
      cartUtils.insertCartItem(item);
    }
    else{
      currentItem.setQuantity(item.getQuantity()+1);
      cartUtils.updateCartItem(currentItem);
    }
    return "Item added/updated";  // Return a more appropriate response (e.g., the created restaurant object)
  }


  @PostMapping("/shopping-cart-api/update-shopping-cart-item-quantity") // Change to @PostMapping for a proper add operation
  public String updateShoppingCartItemQuantity(@RequestBody ShoppingCartItem item) throws ExecutionException, InterruptedException, IOException {
    cartUtils.updateCartItem(item);
    return "Item Updated";  // Return a more appropriate response (e.g., the created restaurant object)
  }

  @PostMapping("/shopping-cart-api/remove-shopping-cart-item") // Change to @PostMapping for a proper add operation
  public String removeShoppingCartItem(@RequestBody ShoppingCartItem item) throws ExecutionException, InterruptedException, IOException {
    cartUtils.deleteCartItem(item);
    return "Item deleted";  // Return a more appropriate response (e.g., the created restaurant object)
  }

  @PostMapping("/shopping-cart-api/clear-shopping-cart") // Change to @PostMapping for a proper add operation
  public String clearShoppingCart(@RequestBody User user ) throws ExecutionException, InterruptedException, IOException {
   cartUtils.clearUserCart(user);
    return "Cart Cleared";  // Return a more appropriate response (e.g., the created restaurant object)
  }


}