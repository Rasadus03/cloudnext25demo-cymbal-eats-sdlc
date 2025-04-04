package com.cymbaleats.notification;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.google.cloud.spring.data.spanner.core.SpannerQueryOptions;
import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerSchemaUtils;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spanner.ResultSet;
/**
 * A quick start code for Spring Data Cloud Spanner. It demonstrates how to use SpannerTemplate to
 * execute DML and SQL queries, save POJOs, and read entities.
 */

@Component
public class NotifcationUtils {


  private SpannerTemplate spannerTemplate;

  private SpannerSchemaUtils spannerSchemaUtils;


  private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

  private void init(){
    if (spannerTemplate == null || spannerSchemaUtils == null || spannerDatabaseAdminTemplate == null) {
      spannerTemplate = DataSourceConfiguration.spannerTemplate;
      spannerSchemaUtils= DataSourceConfiguration.spannerSchemaUtils;
      spannerDatabaseAdminTemplate =DataSourceConfiguration.spannerDatabaseAdminTemplate;
      createTablesIfNotExists();
    }
  }
  public void insertNotification(Notification notification) {
    init();
    long notificationId = getNotificationId();
    System.out.println("Inserting notification " + notificationId);
    notification.setNotificationId(notificationId);
    this.spannerTemplate.insert(notification);
  }

  public void updateNotificationStatus(Notification notification) {
    init();
    this.spannerTemplate.update(notification);
  }
  
  public List<Notification> getUserNotification(User user) {
    // Delete all of the rows in the Singer table.
    //this.spannerTemplate.delete(Singer.class, KeySet.all());
    init();
    List<Notification> notification = this.spannerTemplate
        .query(Notification.class, Statement.of("SELECT * "
                + "FROM Notifications WHERE userId ='" +user.getUserId()+"'"),
            new SpannerQueryOptions().setAllowPartialRead(false));
    return notification;
  }


  public long getUserUnreadNotificationCount(User user) {
    // Delete all of the rows in the Singer table.
    //this.spannerTemplate.delete(Singer.class, KeySet.all());
    init();
    List<Notification> notification = this.spannerTemplate
        .query(Notification.class, Statement.of("SELECT * "
                + "FROM Notifications WHERE userId ='" +user.getUserId()+"' AND status='Unread'"),
            new SpannerQueryOptions().setAllowPartialRead(false));
    return notification.size();
  }
public long getNotificationId() {
    long notificationId=0;
    ResultSet resultSet= this.spannerTemplate
        .	executeQuery( Statement.of("SELECT IFNULL(max(notificationId),0) as notificationId "
                + "FROM Notifications"),
            new SpannerQueryOptions().setAllowPartialRead(false));
   // System.out.println("result=" +resultSet.next());
    while (resultSet.next()) {
      notificationId=resultSet.getLong("notificationId");
          System.out.println("result=" +notificationId);
    }
    return notificationId+1;
  }

  void createTablesIfNotExists() {
    if (!this.spannerDatabaseAdminTemplate.tableExists("Cuisines")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(Cuisine.class)), true);
    }

    if (!this.spannerDatabaseAdminTemplate.tableExists("Restaurants")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(Restaurant.class)), true);
    }

    if (!this.spannerDatabaseAdminTemplate.tableExists("Users")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              User.class)), true);
    }

    if (!this.spannerDatabaseAdminTemplate.tableExists("Notifications")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              Notification.class)), true);
    }
  }

}