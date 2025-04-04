package com.cymbaleats.order.management;
import com.cymbaleats.order.management.Order.Status;
import com.google.cloud.spanner.KeySet;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spanner.ResultSet;
import com.google.cloud.spring.data.spanner.core.SpannerQueryOptions;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
import com.google.cloud.spring.data.spanner.core.admin.SpannerSchemaUtils;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;
import org.springframework.beans.factory.annotation.Qualifier;
/**
 * A quick start code for Spring Data Cloud Spanner. It demonstrates how to use SpannerTemplate to
 * execute DML and SQL queries, save POJOs, and read entities.
 */

@Component
public class OrderUtils {


  private SpannerTemplate spannerTemplate;

  private SpannerSchemaUtils spannerSchemaUtils;


  private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

  private void init(){
    if (spannerTemplate == null || spannerSchemaUtils == null || spannerDatabaseAdminTemplate == null) {
      spannerTemplate = DataSourceConfiguration.spannerTemplate;
      spannerSchemaUtils= DataSourceConfiguration.spannerSchemaUtils;
      spannerDatabaseAdminTemplate =DataSourceConfiguration.spannerDatabaseAdminTemplate;
    }
    createTablesIfNotExists();
  }
  public long insertOrder(Order order) {
    init();
    long orderId = getOrderId();
    System.out.println("Inserting order " + orderId);
    order.setOrderId(orderId);
    for (OrderItem orderItem : order.getOrderItems()) {
      orderItem.setOrderId(orderId);
      orderItem.setUserId(order.getUserId());
    }
    for (Address address : order.getShippingAddress()) {
      address.setOrderId(orderId);
      address.setAddressId(getAddressId());
      address.setUserId(order.getUserId());
  }
    order.setStatus(Status.Submitted);
    order.setEstimatedDeliveryTime(new Date());
    order.setDeliveryTime(new Date());
    this.spannerTemplate.insert(order);
    return orderId;
  }
  public Order getOrderDetails(Order order) {
    // Delete all of the rows in the Singer table.
    //this.spannerTemplate.delete(Singer.class, KeySet.all());
    init();
    List<OrderItem> orderItems = this.spannerTemplate
        .query(OrderItem.class, Statement.of("SELECT *, "
                + "FROM OrderItems WHERE userId ='" +order.getUserId()+"' and orderId = "+order.getOrderId()),
            new SpannerQueryOptions().setAllowPartialRead(false));
    System.out.println(  "Order Loaded"+ orderItems);
    order.setOrderItems(orderItems);
    return order;
  }
  public List<Order> getUserOrder(User user) {
    init();
    List<Order> orders = this.spannerTemplate
        .query(Order.class, Statement.of("SELECT *,\n"
                + "       ARRAY(SELECT AS STRUCT * "
                + "             FROM OrderItems AS itm "
                + "             WHERE ord.orderId = itm.orderId and ord.userId = itm.userId) as orderItems ,\n"
                + "       ARRAY(SELECT AS STRUCT * "
                + "             FROM Address AS add "
                + "             WHERE add.orderId = ord.orderId and add.userId = ord.userId) as shippingAddress "
                + "FROM Orders as ord WHERE userId ='" +user.getUserId()+"'"),
            new SpannerQueryOptions().setAllowPartialRead(false));
    return orders;
  }
  public long getOrderId() {
    long orderId=0;
    ResultSet resultSet= this.spannerTemplate
        .	executeQuery( Statement.of("SELECT IFNULL(max(orderId),0) as orderId "
                + "FROM Orders"),
            new SpannerQueryOptions().setAllowPartialRead(false));
   // System.out.println("result=" +resultSet.next());
    while (resultSet.next()) {
          orderId=resultSet.getLong("orderId");
          System.out.println("result=" +orderId);
    }
    return orderId+1;
  }
  public long getAddressId() {
    long addressId=0;
    ResultSet resultSet= this.spannerTemplate
        .	executeQuery( Statement.of("SELECT IFNULL(max(addressId),0) as addressId "
                + "FROM Address"),
            new SpannerQueryOptions().setAllowPartialRead(false));
    while (resultSet.next()) {
      addressId=resultSet.getLong("addressId");
    }
    return addressId+1;
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

    if (!this.spannerDatabaseAdminTemplate.tableExists("MenuItems")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              OrderItem.class)), true);
    }
    if (!this.spannerDatabaseAdminTemplate.tableExists("Users")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              User.class)), true);
    }
    if (!this.spannerDatabaseAdminTemplate.tableExists("ShoppingCartItems")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              ShoppingCartItem.class)), true);
    }
    if (!this.spannerDatabaseAdminTemplate.tableExists("Orders")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              Order.class)), true);
    }
    if (!this.spannerDatabaseAdminTemplate.tableExists("OrderItems")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              OrderItem.class)), true);
    }
    if (!this.spannerDatabaseAdminTemplate.tableExists("Address")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              Address.class)), true);
    }
    if (!this.spannerDatabaseAdminTemplate.tableExists("Notifications")) {
      this.spannerDatabaseAdminTemplate.executeDdlStrings(
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(
              Notification.class)), true);
    }
  }

}