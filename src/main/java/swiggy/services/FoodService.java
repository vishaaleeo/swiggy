package swiggy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swiggy.domain.Category;
import swiggy.domain.Food;
import swiggy.repository.CategoryRepository;
import swiggy.repository.FoodRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;


    @Autowired
    CategoryRepository categoryRepository;



    EncodingDecoding encodingDecoding=new EncodingDecoding();


    public String createFood(Food food) {

        food=decode(food);

        if(foodRepository.countByRestaurantIdentfierAndFoodName(food.getRestaurantIdentifier(),food.getFoodName())>0)
            return "{\"message\" : \"existing\"}";

        food.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        food.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        food.setDeleteFlag(false);
        if(food.getIsAvailable()==null || food.getIsAvailable().equals("")) {
            food.setIsAvailable(true);
        }

        Category category=categoryRepository.findByCategoryNameAndRestaurantIdentifier(food.getFoodCategory(),food.getRestaurantIdentifier());
        if(category==null) {

            food.setCategoryIdentifier(createCategory(new Category(food.getFoodCategory(),food.getRestaurantIdentifier())));
        }
        else {
            category.setFoodCount(category.getFoodCount()+1);
            updateCategoryCount(category);
            food.setCategoryIdentifier(category.getCategoryIdentifier());
        }



        Food savedFood= foodRepository.save(food);
        return toJson(savedFood);
    }

    public String updateFood(Food food){

        food=decode(food);

        Food toUpdateFood=null;

        if(food.getFoodIdentifier()==null) {
            toUpdateFood = foodRepository.findByRestaurantIdentifierAndFoodName(food.getRestaurantIdentifier(), food.getFoodName());
        }
        else {
            toUpdateFood= foodRepository.findOne(food.getFoodIdentifier());
        }
        if(toUpdateFood==null) {
            return "{\"message\" : \"invalid food\" }";
        }
        else {


            if(food.getFoodType()!=null) {
                toUpdateFood.setFoodType(food.getFoodType());
            }

            if(food.getFoodDescription()!=null) {
                toUpdateFood.setFoodDescription(food.getFoodDescription());
            }

            if(food.getFoodCost()!=null) {
                toUpdateFood.setFoodCost(food.getFoodCost());
            }

            if(food.getIsCustomizable()!=null) {
                toUpdateFood.setIsCustomizable(food.getIsCustomizable());
            }

            if(food.getIsAvailable()!=null) {
                toUpdateFood.setIsAvailable(food.getIsAvailable());
            }
            toUpdateFood.setUpdatedTime(new Timestamp(System.currentTimeMillis()));

            food= foodRepository.save(toUpdateFood);
            return toJson(food);
        }
    }

    public String deleteFood(Food food) {

        food=decode(food);

        Food toDeleteFood=null;
        if(food.getFoodIdentifier()==null) {
            toDeleteFood = foodRepository.findByRestaurantIdentifierAndFoodName(food.getRestaurantIdentifier(), food.getFoodName());
        }
        else {
            toDeleteFood= foodRepository.findOne(food.getFoodIdentifier());
        }
        if (toDeleteFood == null) {
            return "{\"message\" : \"invalid food\"}";
        }

        toDeleteFood.setDeleteFlag(true);

        Category category=categoryRepository.findOne(food.getCategoryIdentifier());
        category.setFoodCount(category.getFoodCount()-1);
        updateCategoryCount(category);
        foodRepository.save(toDeleteFood);

        return "{\"message\" : \"deleted\" }";
    }

    public String displayByFoodId(String foodId){

        Integer foodIdentifier =encodingDecoding.decode(foodId);
        Food food=foodRepository.findOne(foodIdentifier);

        return toJson(food);



    }

    public String displayByCategory(String restaurantId ) {

        Integer restaurantIdentifier=encodingDecoding.decode(restaurantId);

        List<Category> categories=categoryRepository.findByRestaurantIdentifier(restaurantIdentifier);

        String json="{ \"categories\":[";

        int iterator=0;

        for(iterator=0;iterator<categories.size()-1;iterator++) {

            json+=toJsonCategory(categories.get(iterator))+",";
        }
        json+=toJsonCategory(categories.get(iterator));

        json+="]}";

        return json;
    }

    public String displayListByCategory(String restaurantId,String categoryId) {

        Integer restaurantIdentifier=encodingDecoding.decode(restaurantId);

        Integer categoryIdentifier=encodingDecoding.decode(categoryId);


        List<Food> foods= foodRepository.findByRestaurantIdentifierAndCategoryIdentifier(restaurantIdentifier,categoryIdentifier);

        if(!foods.isEmpty()) {
            String json = "{ \"foods\":[";

            int iterator = 0;

            for (iterator = 0; iterator < foods.size() - 1; iterator++) {

                json += toJson(foods.get(iterator)) + ",";

            }

            json += toJson(foods.get(iterator));

            json += "]}";

            return json;
        }
        else return "{\"foods\" : [] }";


    }


    public String displayFood(String restaurantId){

        Integer restaurantIdentifier=encodingDecoding.decode(restaurantId);

        List<Food> foods= foodRepository.findByRestaurantIdentifier(restaurantIdentifier);

        if(!foods.isEmpty()) {
            String json = "{ \"foods\":[";

            int iterator = 0;

            for (iterator = 0; iterator < foods.size() - 1; iterator++) {

                json += toJson(foods.get(iterator)) + ",";

            }

            json += toJson(foods.get(iterator));

            json += "]}";

            return json;
        }
        else return "{\"foods\" : [] }";


    }

    public Integer createCategory(Category category) {

        category.setFoodCount(1);
        category.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        category.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        category.setDeleteFlag(false);
        Category category1= categoryRepository.save(category);
        return  category1.getCategoryIdentifier();

    }

    public Category updateCategoryCount(Category category) {

        return categoryRepository.save(category);


    }



    public String toJson(Food food){

        String json="{";
        if(food.getFoodIdentifier()!=null)
            json+="\"foodIdentifier\" : \""+encodingDecoding.encode(food.getFoodIdentifier())+"\",";

        if(food.getFoodName()!=null)
            json+="\"foodName\" : \""+food.getFoodName()+"\",";

        if(food.getFoodType()!=null)
            json+="\"foodType\" : \""+food.getFoodType()+"\",";

        if(food.getFoodCategory()!=null)
            json+="\"categoryId\" : \""+encodingDecoding.encode(food.getCategoryIdentifier())+"\",";

        if(food.getFoodDescription()!=null)
            json+="\"foodDescription\" : \""+food.getFoodDescription()+"\",";

        if(food.getFoodCost()!=null)
            json+="\"foodCost\" : \""+food.getFoodCost()+"\",";

        if(food.getIsCustomizable()!=null)
            json+="\"isCustomizable\" : \""+food.getIsCustomizable()+"\",";

        if(food.getIsAvailable()!=null)
            json+="\"isAvailable\" : \""+food.getIsAvailable()+"\",";

        if(food.getRestaurantIdentifier()!=null)
            json+="\"restaurantIdentifier\" : \""+encodingDecoding.encode(food.getRestaurantIdentifier())+"\"";

        json+="}";

        return json;
    }

    public String toJsonCategory(Category category){

        String json="{";

        if(category.getCategoryIdentifier()!=null)
            json+="\"categoryIdentifier\" : \""+encodingDecoding.encode(category.getCategoryIdentifier())+"\",";

        if(category.getCategoryName()!=null)
            json+="\"categoryName\" : \""+category.getCategoryName()+"\",";

        if(category.getFoodCount()!=null)
            json+="\"foodCount\" : \""+category.getFoodCount()+"\",";

        if(category.getRestaurantIdentifier()!=null)
            json+="\"redstaurantIdentifier\" : \""+encodingDecoding.encode(category.getRestaurantIdentifier())+"\"";

        json+="}";

        return json;

    }

    public Food decode(Food food) {

        if(food.getFoodId()!=null) {
            food.setFoodIdentifier(encodingDecoding.decode(food.getFoodId()));

        }

        if(food.getRestaurantId()!=null) {
            food.setRestaurantIdentifier(encodingDecoding.decode(food.getRestaurantId()));
        }

        if(food.getCategoryId()!=null) {

            food.setCategoryIdentifier(encodingDecoding.decode(food.getCategoryId()));
        }

        return food;




    }

}

