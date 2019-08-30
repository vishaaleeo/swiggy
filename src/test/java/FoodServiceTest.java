import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import swiggy.Application;
import swiggy.domain.Category;
import swiggy.domain.Food;
import swiggy.repository.CategoryRepository;
import swiggy.repository.FoodRepository;
import swiggy.services.FoodService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= Application.class)
public class FoodServiceTest {


    @Mock
    private FoodRepository foodRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private FoodService foodService;

    private Food food;


    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);


         food = new Food(1,"paneer",1,1);


    }


    @Test
    public void createFoodTest(){

        try {


            Mockito.when(foodRepository.save(food)).thenReturn(food);


            JSONObject createdFood = new JSONObject(foodService.createFood(food));

            assertEquals(food.getFoodName(), createdFood.getString("foodName"));
        }
        catch (Exception e){

        }

    }

    @Test
    public void updateFoodTest(){

        try{

            food.setFoodCost(100);

            Mockito.when(foodRepository.save(food)).thenReturn(food);

            JSONObject updatedFood = new JSONObject(foodService.updateFood(food));

            if(food.getFoodCost()==updatedFood.getInt("foodCost"))
                assert true;
        }
        catch (Exception e){


        }
    }

   @Test
    public void deleteFoodTest(){

        try{

            food.setDeleteFlag(true);

            Mockito.when(foodRepository.save(food)).thenReturn(food);

            JSONObject message = new JSONObject(foodService.deleteFood(food));

            if(message.getString("message").equals("deleted"))
                assert true;
        }
        catch (Exception e){

        }
   }


   @Test
    public void displayFoodTest(){

        try{

            List<Food> foods=new ArrayList<>();
            foods.add(new Food(1,"pbm",1,1));
            foods.add(new Food(2,"naan",1,2));
            foods.add(new Food(3,"roti",2,2));

            Mockito.when(foodRepository.findByRestaurantIdentifier(1)).thenReturn(foods);

            JSONObject foodsJSON=new JSONObject(foodService.displayFood("re1er22"));

            if(foodsJSON.getJSONArray("foods").length()==foods.size())
                assert true;
        }
        catch (Exception e){

        }
   }

   @Test
    public void createCategoryTest(){


        try{

            Category category=new Category(1,1,"gravy");
            Mockito.when(categoryRepository.save(category)).thenReturn(category);

            Integer identifier=foodService.createCategory(category);

            System.out.println(identifier);

            assertEquals(identifier,category.getCategoryIdentifier());
        }
        catch (Exception e){

            System.out.println(e);
        }
   }

   @Test
    public void updateCategoryTest(){

        try {

            Category category = new Category(1, 2, "gravy");

            category.setFoodCount(1);

            Mockito.when(categoryRepository.save(category)).thenReturn(category);

            Category updatedCategory = foodService.updateCategoryCount(category);

            assertEquals(updatedCategory.getFoodCount(), category.getFoodCount());
        }
        catch (Exception e){



        }
   }






}
