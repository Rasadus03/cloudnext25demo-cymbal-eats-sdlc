package com.cymbaleats.restaurants;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
@RestController
public class RestaurantsController {

  private static final Logger logger = Logger.getLogger("RestaurantsController");
  private static RestaurantUtils restaurantUtils = new RestaurantUtils();

@GetMapping("/restaurants-api/restaurants")
public List<Restaurant> getAllRestaurants()
    throws ExecutionException, InterruptedException, IOException {
  System.out.println("Loading all restaurants!!");
  try {
    Thread.sleep(1 * 1500);
} catch (InterruptedException ie) {
    Thread.currentThread().interrupt();
}
  return restaurantUtils.getAllRestuarants();
}

@GetMapping("/restaurants-api")
public String ping() throws ExecutionException, InterruptedException, IOException {
  System.out.println("in Ping!!");

  return "A Ping!!!";
}

/*
@GetMapping("/restaurants-api/addRestaurant") // Change to @PostMapping for a proper add operation
public String addRestaurant() throws ExecutionException, InterruptedException, IOException {
  init();
  Map<String, Object> data = new HashMap<>();
  data.put("name", "New Restaurant");
  data.put("city", "Some City");
  data.put("rating", 4.5);

  ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document()
      .set(data); // Auto-generate ID
  // Or you can specify your own Document ID if needed:
  // ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document("your-custom-id").set(data);

  System.out.println("Update time : " + writeResult.get().getUpdateTime());
  return "Restaurant added";  // Return a more appropriate response (e.g., the created restaurant object)
}

*/
@GetMapping("/restaurants-api/initRestaurants") // Change to @PostMapping for a proper add operation
public String initRestaurants() throws ExecutionException, InterruptedException, IOException {
 // init();
  System.out.println("Intializing the Restaurants!!@");
  ClassPathResource restaurantsJSON = new ClassPathResource("initial-restaurants.json");
  System.out.println(restaurantsJSON);
  List<Map> initialRestaurants =
      (List<Map> )(new ObjectMapper().readValue(restaurantsJSON.getInputStream(), HashMap.class).get("mockRestaurants"));

  //System.out.println(((List)initialRestaurants.get("mockRestaurants")).get(0).getClass().getSimpleName());
  //List<Map<String, Object>> currentRestaurants = getAllRestaurants();
  //List restNames = new ArrayList<>(currentRestaurants.size());
 // for(Map<String, Object> restaurant : currentRestaurants) {
  //   restNames.add(restaurant.get("name"));
 // }
  for (Map restaurant : initialRestaurants) {
    //if (!restNames.contains(restaurant.get("name"))) {
    System.out.println("cousine" + restaurant.get("cuisine"));
    List<Cuisine> cuisines = new ArrayList();
    List<String> names = (List<String>) restaurant.get("cuisine");
    int i = 0;
    for (String name : names) {
      Cuisine cuisine = new Cuisine(++i, name, Long.parseLong(restaurant.get("id") + ""));
      System.out.println("I am in" + cuisine);
      cuisines.add(cuisine);
    }

    Restaurant restToBeInserted = new Restaurant(restaurant.get("name") + "",
        Long.parseLong(restaurant.get("id") + ""),
        restaurant.get("description") + "",
        restaurant.get("image") + "", cuisines);
    restaurantUtils.insertRestuarant(restToBeInserted);
  //}
  }
  return "Restaurant added";  // Return a more appropriate response (e.g., the created restaurant object)
}

}