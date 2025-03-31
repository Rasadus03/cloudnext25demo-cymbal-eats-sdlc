package com.cymbaleats.shopping.cart;
import com.google.cloud.spanner.KeySet;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spring.data.spanner.core.SpannerQueryOptions;
import java.awt.MenuItem;
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
public class CartUtils {


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
  public void insertUser(User user) {
    init();
    this.spannerTemplate.insert(user);
  }
  public void insertCartItem(ShoppingCartItem menuItem) {
    init();
    this.spannerTemplate.insert(menuItem);
  }

  public void updateCartItem(ShoppingCartItem menuItem) {
    init();
    this.spannerTemplate.update(menuItem);
  }
  public void deleteCartItem(ShoppingCartItem menuItem) {
    init();
    this.spannerTemplate.delete(menuItem);
  }
  public void clearUserCart(User user) {
    init();
    List<ShoppingCartItem> cart = getUserCart(user);
      this.spannerTemplate.deleteAll(cart);
  }
  public List<ShoppingCartItem> getUserCart(User user) {
    // Delete all of the rows in the Singer table.
    //this.spannerTemplate.delete(Singer.class, KeySet.all());
    init();
    List<ShoppingCartItem> restaurantMenu = this.spannerTemplate
        .query(ShoppingCartItem.class, Statement.of("SELECT * "
                + "FROM ShoppingCartItems WHERE userId ='" +user.getUserId()+"'"),
            new SpannerQueryOptions().setAllowPartialRead(false));
    return restaurantMenu;
  }

  public ShoppingCartItem getMenuItem(ShoppingCartItem menuItem) {
    // Delete all of the rows in the Singer table.
    //this.spannerTemplate.delete(Singer.class, KeySet.all());
    init();
    List<ShoppingCartItem> restaurantMenu = this.spannerTemplate
        .query(ShoppingCartItem.class, Statement.of("SELECT * "
                + "FROM ShoppingCartItems WHERE userId ='" +menuItem.getUserId()+"' and menuItemId="+
                menuItem.getMenuItemId()+" and restaurantId= "+ menuItem.getRestaurantId()),
            new SpannerQueryOptions().setAllowPartialRead(false));
    return restaurantMenu.size()>0 ?restaurantMenu.get(0): null;
  }

  public long getUserCartItemCount(User user) {
    init();
    return getUserCart(user).size();
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
              ShoppingCartItem.class)), true);
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
  }

}