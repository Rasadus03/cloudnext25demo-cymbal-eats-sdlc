package com.cymbaleats.order.management;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import com.google.cloud.spring.data.spanner.core.mapping.NotMapped;
import com.google.cloud.spring.data.spanner.core.mapping.Interleaved;
import java.util.List;

@Table(name = "Users")
public class User {
  @PrimaryKey
  private String userId;
  private String userName;
  private String email;
  @NotMapped
  private List<ShoppingCartItem> shoppingCartItems;
  @Interleaved
  private List<Order> orders;
  @Interleaved
  private List<Notification> notifications;
  public User(){

  }
  public User(String email, String userName, String userId) {
    this.email = email;
    this.userName = userName;
    this.userId = userId;
  }
  public User(String userId) {
    this.userId = userId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<ShoppingCartItem> getShoppingCartItems() {
    return shoppingCartItems;
  }

  public void setShoppingCartItems(
      List<ShoppingCartItem> shoppingCartItems) {
    this.shoppingCartItems = shoppingCartItems;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("User{");
    sb.append("userId='").append(userId).append('\'');
    sb.append(", userName='").append(userName).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", shoppingCartItems=").append(shoppingCartItems);
    sb.append(", orders=").append(orders);
    sb.append('}');
    return sb.toString();
  }
}
