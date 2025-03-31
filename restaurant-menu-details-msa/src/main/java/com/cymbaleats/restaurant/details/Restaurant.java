package com.cymbaleats.restaurant.details;
import com.google.cloud.spring.data.spanner.core.mapping.Interleaved;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import java.util.Date;
import java.util.List;
import lombok.Data;


@Table(name = "Restaurants")
public class Restaurant {
  @PrimaryKey
  private long restaurantId;

  private String name;

  private String description;

  private String imageURL;

  @Interleaved
  private List<Cuisine> cuisines;

  @Interleaved
  private List<MenuItem> menuItems;
  public Restaurant(long restaurantId) {
    this.restaurantId = restaurantId;
  }

  public Restaurant(long restaurantId, String name, String description, String imageURL,
      List<Cuisine> cuisines, List<MenuItem> menuItems) {
    this.restaurantId = restaurantId;
    this.name = name;
    this.description = description;
    this.imageURL = imageURL;
    this.cuisines = cuisines;
    this.menuItems = menuItems;
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(long restaurantId) {
    this.restaurantId = restaurantId;
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

  public List<Cuisine> getCuisines() {
    return cuisines;
  }

  public void setCuisines(List<Cuisine> cuisines) {
    this.cuisines = cuisines;
  }

  public List<MenuItem> getMenuItems() {
    return menuItems;
  }

  public void setMenuItems(List<MenuItem> menuItems) {
    this.menuItems = menuItems;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Restaurant{");
    sb.append("restaurantId=").append(restaurantId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", imageURL='").append(imageURL).append('\'');
    sb.append(", cuisines=").append(cuisines);
    sb.append(", menuItems=").append(menuItems);
    sb.append('}');
    return sb.toString();
  }
}