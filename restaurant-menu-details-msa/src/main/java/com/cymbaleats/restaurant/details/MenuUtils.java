package com.cymbaleats.restaurant.details;
import com.google.cloud.spanner.KeySet;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spring.data.spanner.core.SpannerQueryOptions;
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
public class MenuUtils {


  private SpannerTemplate spannerTemplate;

  private SpannerSchemaUtils spannerSchemaUtils;


  private SpannerDatabaseAdminTemplate spannerDatabaseAdminTemplate;

  private void init(){
    if (spannerTemplate == null || spannerSchemaUtils == null || spannerDatabaseAdminTemplate == null) {
      spannerTemplate = DataSourceConfiguration.spannerTemplate;
      spannerSchemaUtils= DataSourceConfiguration.spannerSchemaUtils;
      spannerDatabaseAdminTemplate =DataSourceConfiguration.spannerDatabaseAdminTemplate;
    }
  }
  public void insertMenuItem(MenuItem menuItem) {
    init();
    createTablesIfNotExists();
    this.spannerTemplate.insert(menuItem);
  }
  public List<MenuItem> getRestaurantMenu(Restaurant restaurant) {
    // Delete all of the rows in the Singer table.
    //this.spannerTemplate.delete(Singer.class, KeySet.all());
    init();
    List<MenuItem> restaurantMenu = this.spannerTemplate
        .query(MenuItem.class, Statement.of("SELECT *, "
                + "FROM MenuItems WHERE restaurantId =" +restaurant.getRestaurantId()),
            new SpannerQueryOptions().setAllowPartialRead(false));
    return restaurantMenu;
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
          Collections.singletonList(this.spannerSchemaUtils.getCreateTableDdlString(MenuItem.class)), true);
    }
  }

}