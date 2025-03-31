package com.cymbaleats.restaurant.details;

import com.google.cloud.spring.data.spanner.core.mapping.Interleaved;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import java.util.List;


@Table(name = "MenuItems")
public class MenuItem {
  @PrimaryKey
  private long restaurantId;
  @PrimaryKey (keyOrder = 2)
  private long menuItemId;

  private String name;

  private String description;

  private String imageURL;

  private double price;

  public MenuItem(long restaurantId, long menuItemId, String name, String description,
      String imageURL, double price) {
    this.restaurantId = restaurantId;
    this.menuItemId = menuItemId;
    this.name = name;
    this.description = description;
    this.imageURL = imageURL;
    this.price = price;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("MenuItem{");
    sb.append("restaurantId=").append(restaurantId);
    sb.append(", menuItemId=").append(menuItemId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", imageURL='").append(imageURL).append('\'');
    sb.append(", price=").append(price);
    sb.append('}');
    return sb.toString();
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public long getMenuItemId() {
    return menuItemId;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getImageURL() {
    return imageURL;
  }

  public double getPrice() {
    return price;
  }
}