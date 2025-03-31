package com.cymbaleats.order.management;

import java.util.Date;
import java.util.List;
import com.google.cloud.spring.data.spanner.core.mapping.Interleaved;
import com.google.cloud.spring.data.spanner.core.mapping.NotMapped;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;

@Table(name = "Orders")
public class Order {
  @Interleaved
    private List<OrderItem> orderItems;
  @PrimaryKey
    private  String userId;
  @Interleaved
    private List<Address> shippingAddress;
  @PrimaryKey (keyOrder = 2)
    private long orderId;
    private Date estimatedDeliveryTime;
    private Date deliveryTime;
    private Status status;
    private float totalCost;
    public static enum Status {
      Submitted,
      Preparing,
      Prepared,
      ReadyForDelivery,
      OutForDelivery,
      Delivered
    }

  public Order() {
  }

  public Order(List<OrderItem> orderItems, String userId, List<Address> shippingAddress, long orderId,
      Date estimatedDeliveryTime, Date deliveryTime, Status status, float totalCost) {
    this.orderItems = orderItems;
    this.userId = userId;
    this.shippingAddress = shippingAddress;
    this.orderId = orderId;
    this.estimatedDeliveryTime = estimatedDeliveryTime;
    this.deliveryTime = deliveryTime;
    this.status = status;
    this.totalCost = totalCost;
  }

  public List<OrderItem> getOrderItems() {
    return orderItems;
  }

  public void setOrderItems(List<OrderItem> orderItems) {
    this.orderItems = orderItems;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<Address> getShippingAddress() {
    return shippingAddress;
  }

  public void setShippingAddress(List<Address> shippingAddress) {
    this.shippingAddress = shippingAddress;
  }

  public long getOrderId() {
    return orderId;
  }

  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }

  public Date getEstimatedDeliveryTime() {
    return estimatedDeliveryTime;
  }

  public void setEstimatedDeliveryTime(Date estimatedDeliveryTime) {
    this.estimatedDeliveryTime = estimatedDeliveryTime;
  }

  public Date getDeliveryTime() {
    return deliveryTime;
  }

  public void setDeliveryTime(Date deliveryTime) {
    this.deliveryTime = deliveryTime;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public float getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(float totalCost) {
    this.totalCost = totalCost;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Order{");
    sb.append("orderItems=").append(orderItems);
    sb.append(", userId='").append(userId).append('\'');
    sb.append(", shippingAddress=").append(shippingAddress);
    sb.append(", orderId=").append(orderId);
    sb.append(", estimatedDeliveryTime=").append(estimatedDeliveryTime);
    sb.append(", deliveryTime=").append(deliveryTime);
    sb.append(", status=").append(status);
    sb.append(", totalCost=").append(totalCost);
    sb.append('}');
    return sb.toString();
  }
}
