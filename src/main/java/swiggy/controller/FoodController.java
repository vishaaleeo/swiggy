package swiggy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swiggy.domain.Food;
import swiggy.services.FoodService;

@Controller
@RequestMapping("/food")
public class FoodController {


    @Autowired
    FoodService foodService;

    @PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createFood(@RequestBody Food food){

        return new ResponseEntity(foodService.createFood(food), HttpStatus.OK);

    }

    @PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateFood(@RequestBody Food food) {

        return new ResponseEntity(foodService.updateFood(food),HttpStatus.OK);
    }

    @PostMapping(value="/delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteFood(@RequestBody Food food) {

        return new ResponseEntity(foodService.deleteFood(food),HttpStatus.OK);
    }


    @GetMapping(value="/displayCategory/{restaurantIdentifier}" , produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity displayCategories( @PathVariable("restaurantIdentifier") String restaurantIdentifier) {
   // public ResponseEntity displayCategories(@RequestBody Integer restaurantIdentifier) {

        return new ResponseEntity(foodService.displayByCategory(restaurantIdentifier),HttpStatus.OK);
    }

    @GetMapping(value="/displayFoodByCategory/{restaurantIdentifier}/{categoryIdentifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayFoodByCategory(@PathVariable("restaurantIdentifier") String restaurantIdentifier ,@PathVariable("categoryIdentifier") String categoryIdentifier ) {
  //  public ResponseEntity displayFoodByCategory(@RequestBody Food food) {
        return new ResponseEntity(foodService.displayListByCategory(restaurantIdentifier,categoryIdentifier),HttpStatus.OK);
    }

    /*
    to display all food of restaurant
     */
    @GetMapping(value="/displayFood/{restaurantIdentifier}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayFood(@PathVariable("restaurantIdentifier") String restaurantIdentifier){


        return new ResponseEntity(foodService.displayFood(restaurantIdentifier),HttpStatus.OK);
    }

    @GetMapping(value="/displayFoodById/{foodId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayFoodById(@PathVariable("foodIdentifier") String foodId) {

        return  new ResponseEntity(foodService.displayByFoodId(foodId),HttpStatus.OK);
    }

}
