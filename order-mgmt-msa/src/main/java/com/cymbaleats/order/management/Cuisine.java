package com.cymbaleats.order.management;
import com.google.cloud.spring.data.spanner.core.mapping.NotMapped;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;
import java.util.List;
import lombok.Data;

@Table(name = "Cuisines")
public class Cuisine {
  @PrimaryKey
  private long restaurantId;
  @PrimaryKey (keyOrder = 2)
  private long couisineId;

  private String couisineName;



  public Cuisine(long couisineId, String couisineName, long restaurantId) {
    this.couisineId = couisineId;
    this.couisineName = couisineName;
    this.restaurantId = restaurantId;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Cuisine{");
    sb.append("couisineId=").append(couisineId);
    sb.append(", couisineName='").append(couisineName).append('\'');
    sb.append(", restaurantId='").append(restaurantId).append('\'');
    sb.append('}');
    return sb.toString();
  }

  public long getRestaurantId() {
    return restaurantId;
  }

  public void setRestaurantId(long restaurantId) {
    this.restaurantId = restaurantId;
  }

  public String getCouisineName() {
    return couisineName;
  }

  public void setCouisineName(String couisineName) {
    this.couisineName = couisineName;
  }

  public long getCouisineId() {
    return couisineId;
  }

  public void setCouisineId(long couisineId) {
    this.couisineId = couisineId;
  }
}