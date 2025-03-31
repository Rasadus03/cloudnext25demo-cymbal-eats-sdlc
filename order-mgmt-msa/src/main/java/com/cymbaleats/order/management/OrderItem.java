package com.cymbaleats.order.management;

import com.google.cloud.spring.data.spanner.core.mapping.Interleaved;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import java.util.Date;


@Table(name = "OrderItems")
public class OrderItem {


  private long restaurantId;
  @PrimaryKey(keyOrder = 2)
  private long orderId;
  @PrimaryKey
  private String userId;
  @PrimaryKey(keyOrder = 3)
  private long menuItemId;

  private String name;

  private String description;

  private String imageURL;

  private double price;
  private int quantity;
  private Date timeAdded;
  public OrderItem(){

  }

  public OrderItem(long restaurantId, long orderId, String userId, long menuItemId, String name,
      String description, String imageURL, double price, int quantity, Date timeAdded) {
    this.restaurantId = restaurantId;
    this.orderId = orderId;
    this.userId = userId;
    this.menuItemId = menuItemId;
    this.name = name;
    this.description = description;
    this.imageURL = imageURL;
    this.price = price;
    this.quantity = quantity;
    this.timeAdded = timeAdded;
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(long restaurantId) {
    this.restaurantId = restaurantId;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public long getMenuItemId() {
    return menuItemId;
  }

  public void setMenuItemId(long menuItemId) {
    this.menuItemId = menuItemId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public Date getTimeAdded() {
    return timeAdded;
  }

  public void setTimeAdded(Date timeAdded) {
    this.timeAdded = timeAdded;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("OrderItem{");
    sb.append("restaurantId=").append(restaurantId);
    sb.append(", orderId='").append(orderId).append('\'');
    sb.append(", userId='").append(userId).append('\'');
    sb.append(", menuItemId=").append(menuItemId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", imageURL='").append(imageURL).append('\'');
    sb.append(", price=").append(price);
    sb.append(", quantity=").append(quantity);
    sb.append(", timeAdded=").append(timeAdded);
    sb.append('}');
    return sb.toString();
  }
}