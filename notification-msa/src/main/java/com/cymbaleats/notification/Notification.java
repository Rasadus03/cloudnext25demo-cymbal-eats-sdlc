package com.cymbaleats.notification;
import com.google.cloud.spring.data.spanner.core.mapping.NotMapped;
import com.google.cloud.spring.data.spanner.core.mapping.PrimaryKey;
import com.google.cloud.spring.data.spanner.core.mapping.Table;

import java.util.Date;
import java.util.List;
import lombok.Data;

@Table(name = "Notifications")
public class Notification {
  @PrimaryKey
    private  String userId;
  @PrimaryKey (keyOrder = 2)
  private long orderId;
  @PrimaryKey (keyOrder = 3)
  private long notificationId;
private Date time;
  private String notification;
  private Status status;
  public static enum Status {
    Read,
    Unread
  }

  

  public Notification(String userId, long orderId, long notificationId, String notification, Status status, Date time) {
    this.userId = userId;
    this.orderId = orderId;
    this.notificationId = notificationId;
    this.notification = notification;
    this.status = status;
    this.time = time;
  }
  @Override
  public String toString() {
        final StringBuffer sb = new StringBuffer("Notification{");
        sb.append("userId='").append(userId).append('\'');
        sb.append(", orderId='").append(orderId).append('\'');
        sb.append(", notificationId='").append(notificationId).append('\'');
        sb.append(", notification=").append(notification);
        sb.append(", status=").append(status);
        sb.append(", time=").append(time);
        sb.append('}');
        return sb.toString();
  }

  
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public long getOrderId() {
    return orderId;
  }
  public void setOrderId(long orderId) {
    this.orderId = orderId;
  }
  public long getNotificationId() {
    return notificationId;
  }
  public void setNotificationId(long notifcationId) {
    this.notificationId = notifcationId;
  }
  public String getNotification() {
    return notification;
  }
  public void setNotification(String notification) {
    this.notification = notification;
  }
  public Status getStatus() {
    return status;
  }
  public void setStatus(Status status) {
    this.status = status;
  }
  public Date getTime() {
    return time;
  }
  public void setTime(Date time) {
    this.time = time;
  }

  
}