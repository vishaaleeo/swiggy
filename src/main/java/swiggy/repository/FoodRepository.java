package swiggy.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import swiggy.domain.Food;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Integer> {

    @Query("select count(f) from Food f where f.restaurantIdentifier=?1 and f.foodName=?2")
    Long countByRestaurantIdentfierAndFoodName(Integer restaurantIdentifier,String foodName);

    @Query("select f from Food f where f.restaurantIdentifier=?1 and f.foodName=?2")
    Food findByRestaurantIdentifierAndFoodName(Integer restaurantIdentifier, String foodName);


    List<Food> findByRestaurantIdentifierAndCategoryIdentifier(Integer restaurantIdentifier,Integer categoryIdentifier);

    List<Food> findByRestaurantIdentifier(Integer restaurantIdentifier);

    //@Query("select distinct(f.foodCategory) from Food f where f.restaurantIdentifier=?1")
  //  List<String> findCategories(Integer restaurantIdentifier);

    //@Query("select f from Food f where f.restaurantIdentifier=?1 and isAvailable=true and f.foodCategory=?2")
    //List<Food> findByFoodCategory(Integer restaurantIdentifier,String foodCategory);
}
