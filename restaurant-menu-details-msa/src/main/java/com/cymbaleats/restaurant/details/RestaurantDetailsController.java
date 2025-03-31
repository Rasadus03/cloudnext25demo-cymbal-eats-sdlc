package com.cymbaleats.restaurant.details;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class RestaurantDetailsController {

  private static final Logger logger = Logger.getLogger("RestaurantDetailsController");
  private static MenuUtils menuUtils = new MenuUtils();





  @GetMapping("/restaurant-details-api/restaurant-menu")
  public List<MenuItem> getRestaurantMenu(@RequestParam(value="id")String restaurantId)
      throws ExecutionException, InterruptedException, IOException {
    Restaurant restaurant = new Restaurant(Long.parseLong(restaurantId));
    menuUtils.getRestaurantMenu(restaurant);
    return  menuUtils.getRestaurantMenu(restaurant);
  }

  @GetMapping("/restaurant-details-api")
  public String ping() throws ExecutionException, InterruptedException, IOException {
    System.out.println("in Ping!!");

    return "A Ping!!!";
  }


  @GetMapping("/restaurant-details-api/initRestaurantsMenu") // Change to @PostMapping for a proper add operation
  public String initRestaurants() throws ExecutionException, InterruptedException, IOException {
    System.out.println("Intializing the Restaurants Menus!!@");
    ClassPathResource restaurantsJSON = new ClassPathResource("initial-restaurants.json");
    //File restaurantsJSON = ResourceUtils.getFile("../../../initial-restaurants.json");
    System.out.println(restaurantsJSON);
    // System.out.println(restaurantsJSON.getAbsolutePath());
    List<Map> initialRestaurants =
        (List<Map>) (new ObjectMapper().readValue(restaurantsJSON.getInputStream(), HashMap.class)
            .get("mockRestaurants"));

    //System.out.println(((List)initialRestaurants.get("mockRestaurants")).get(0).getClass().getSimpleName());

    for (Map restaurant : initialRestaurants) {
      System.out.println("restaurant id " + restaurant.get("id"));
      System.out.println("restaurant menu " + restaurant.get("menu"));
      // List<Map<String, Object>> currentResturantMenu = getRestaurantMenu(restaurant.get("id")+"");
      List<Map<String, Object>> restaurantMenu = (List<Map<String, Object>>) restaurant.get("menu");
      //if (currentResturantMenu.isEmpty()) {
      for (Map<String, Object> menu : restaurantMenu) {
        MenuItem menuItem = new MenuItem(
            Long.parseLong(restaurant.get("id") + ""),
            Long.parseLong(menu.get("id") + ""),
            menu.get("name") + "",
            menu.get("description") + "",
            menu.get("image") + "",
            Double.parseDouble(menu.get("price") + "")
        );

        System.out.println(menuItem);

        menuUtils.insertMenuItem(menuItem);
      }
    }
    //}

    // Auto-generate ID
    // Or you can specify your own Document ID if needed:
    // ApiFuture<WriteResult> writeResult = db.collection(COLLECTION_NAME).document("your-custom-id").set(data);


    return "Restaurants Menus added";  // Return a more appropriate response (e.g., the created restaurant object)
  }

}