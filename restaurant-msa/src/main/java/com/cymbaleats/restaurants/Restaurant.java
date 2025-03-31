package com.cymbaleats.restaurants;
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

  public Restaurant(String name, long restaurantId, String description, String imageURL,
      List<Cuisine> cuisines) {
    this.name = name;
    this.restaurantId = restaurantId;
    this.description = description;
    this.imageURL = imageURL;
    this.cuisines = cuisines;
  }

  @java.lang.Override
  public java.lang.String toString() {
    final java.lang.StringBuffer sb = new java.lang.StringBuffer("Restaurant{");
    sb.append("restaurantId=").append(restaurantId);
    sb.append(", name='").append(name).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", imageURL='").append(imageURL).append('\'');
    sb.append(", cuisines=").append(cuisines);
    sb.append('}');
    return sb.toString();
  }

  public List<Cuisine> getCuisines() {
    return cuisines;
  }

  public void setCuisines(List<Cuisine> cuisines) {
    this.cuisines = cuisines;
  }

  public String getImageURL() {
    return imageURL;
  }

  public void setImageURL(String imageURL) {
    this.imageURL = imageURL;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(long restaurantId) {
    this.restaurantId = restaurantId;
  }
}