package swiggy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swiggy.domain.Restaurant;
import swiggy.services.RestaurantService;


@Controller
@RequestMapping("/res")
public class RestaurantController {


    @Autowired
    RestaurantService restaurantService;

    @PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createRestaurant(@RequestBody Restaurant restaurant) {

        return new ResponseEntity(restaurantService.createRestaurant(restaurant), HttpStatus.valueOf(200));
    }

    @PostMapping(value="/find",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity findRestaurant(@RequestBody Restaurant restaurant) {

        return new ResponseEntity(restaurantService.findRestaurant(restaurant),HttpStatus.valueOf(200));
    }

    @PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateRestaurant(@RequestBody Restaurant restaurant) {

        return new ResponseEntity(restaurantService.updateRestaurant(restaurant),HttpStatus.valueOf(200));
    }

    @PostMapping(value="/delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteRestaurant(@RequestBody Restaurant restaurant) {

        return new ResponseEntity(restaurantService.deleteRestaurant(restaurant),HttpStatus.valueOf(200));
    }

    @GetMapping(value="/display", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity readRestaurant() {

        String restaurants=restaurantService.readRestaurants();
        return new ResponseEntity(restaurants,HttpStatus.OK);
    }

    @GetMapping(value="/displayRestaurantTypes",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayRestaurantTypes(){

        return new ResponseEntity(restaurantService.displayTypes(),HttpStatus.OK);
    }

    @GetMapping(value="/displayRestaurantByType/{restaurantType}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayRestaurantByType(@PathVariable("restaurantType") String restaurantType){



        return new ResponseEntity(restaurantService.displayRestaurantByType(restaurantType),HttpStatus.OK);

    }
}
