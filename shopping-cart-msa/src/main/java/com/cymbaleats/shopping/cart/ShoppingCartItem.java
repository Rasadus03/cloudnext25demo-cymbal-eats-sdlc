package com.cymbaleats.shopping.cart;

import com.google.cloud.spring.data.spanner.core.mapping.Interleaved;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import java.util.Date;


@Table(name = "ShoppingCartItems")
public class ShoppingCartItem {

  @PrimaryKey
  private long restaurantId;
  @PrimaryKey (keyOrder = 2)
  private String userId;
  @PrimaryKey(keyOrder = 3)
  private long menuItemId;

  private String name;

  private String description;

  private String imageURL;

  private double price;
  private int quantity;
  private Date timeAdded;
  public ShoppingCartItem(){

  }
  public ShoppingCartItem(long restaurantId, long menuItemId, String name, String description,
      String imageURL, double price, String userId, int quantity, Date timeAdded) {
    this.restaurantId = restaurantId;
    this.menuItemId = menuItemId;
    this.name = name;
    this.description = description;
    this.imageURL = imageURL;
    this.price = price;
    this.userId = userId;
    this.quantity = quantity;
    this.timeAdded = timeAdded;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("ShoppingCartItem{");
    sb.append("restaurantId=").append(restaurantId);
    sb.append(", menuItemId=").append(menuItemId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", imageURL='").append(imageURL).append('\'');
    sb.append(", price=").append(price);
    sb.append(", userId='").append(userId).append('\'');
    sb.append(", quantity=").append(quantity);
    sb.append(", timeAdded=").append(timeAdded);
    sb.append('}');
    return sb.toString();
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(long restaurantId) {
    this.restaurantId = restaurantId;
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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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
}