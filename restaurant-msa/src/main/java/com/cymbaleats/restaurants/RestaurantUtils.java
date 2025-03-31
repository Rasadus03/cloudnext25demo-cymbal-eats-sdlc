package com.cymbaleats.restaurants;
import com.google.cloud.spanner.KeySet;
import com.google.cloud.spanner.Statement;
import com.google.cloud.spring.data.spanner.core.SpannerQueryOptions;
import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
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
public class RestaurantUtils {


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
  public void insertRestuarant(Restaurant restaurant) {
    init();
    createTablesIfNotExists();
    this.spannerTemplate.insert(restaurant);
  }
  public List<Restaurant> getAllRestuarants() {
    // Delete all of the rows in the Singer table.
    //this.spannerTemplate.delete(Singer.class, KeySet.all());
    init();
    List<Restaurant> allRestaurants = this.spannerTemplate
        .query(Restaurant.class, Statement.of("SELECT *,\n"
                + "       ARRAY(SELECT AS STRUCT c.restaurantId, c.couisineId, c.couisineName "
                + "             FROM Cuisines AS c "
                + "             WHERE c.restaurantId = r.restaurantId) as cuisines "
                + "FROM Restaurants AS r"),
            new SpannerQueryOptions().setAllowPartialRead(false));
    return allRestaurants;
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
  }

}