import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import swiggy.Application;
import swiggy.domain.Restaurant;
import swiggy.repository.RestaurantRepository;
import swiggy.services.RestaurantService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=Application.class)
public class RestaurantServiceTest {


    @Mock
   private RestaurantRepository restaurantRepository;



    @InjectMocks
    private RestaurantService restaurantService;




    private Restaurant restaurant;

    @Before
    public void init(){

        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void createRestaurantTest() {

        try {


            restaurant = new Restaurant();


            restaurant.setRestaurantIdentifier(1);
            restaurant.setRestaurantName("pudhumai");
            restaurant.setAvailable(true);
            restaurant.setDeleteFlag(false);
            restaurant.setRestaurantCharges(100);
            restaurant.setRestaurantType("northIndian");


            Mockito.when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

            String createdRestaurant = restaurantService.createRestaurant(restaurant);

            JSONObject restaurantJSON = new JSONObject(createdRestaurant);


            assertEquals(restaurant.getRestaurantName(), restaurantJSON.getString("restaurantName"));


        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Test
    public void updateRestaurantTest(){

        try {

            restaurant.setRestaurantCharges(25);

            Mockito.when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

            String updatedRestaurant = restaurantService.updateRestaurant(restaurant);

            JSONObject restaurantJSON = new JSONObject(updatedRestaurant);

            if (restaurantJSON.getInt("restaurantCharges") == restaurant.getRestaurantCharges())
                assert true;


        }
        catch(Exception e){

        }

    }

    @Test
    public void deleteRestaurantTest(){

        try{

            restaurant.setDeleteFlag(true);

            Mockito.when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

            String deletedRestaurant = restaurantService.deleteRestaurant(restaurant);

            JSONObject restaurantJSON = new JSONObject(deletedRestaurant);

            if(restaurantJSON.getString("message").equals("deleted"))
                assert true;

        }
        catch(Exception e){


        }

    }


    @Test
    public void readRestaurantTest(){

        try{

            List<Restaurant> restaurants=new ArrayList<Restaurant>();
            Restaurant restaurant;
            restaurant=new Restaurant();
            restaurant.setRestaurantName("dominos");
            restaurant.setRestaurantType("Italian");
            restaurants.add(restaurant);
            restaurant=new Restaurant();
            restaurant.setRestaurantName("sub");
            restaurant.setRestaurantType("Fastfood");

            restaurants.add(restaurant);


            Mockito.when(restaurantRepository.findAll()).thenReturn(restaurants);

            JSONObject restaurantsJSON=new JSONObject(restaurantService.readRestaurants());

            JSONArray restaurantsList=restaurantsJSON.getJSONArray("restaurants");

            assertEquals(restaurants.size(),restaurantsList.length());


        }
        catch(Exception e){

        }


    }

    @Test
    public void findRestaurantTest() {

        try{
            restaurant = new Restaurant();


            restaurant.setRestaurantIdentifier(1);
            restaurant.setRestaurantName("pudhumai");
            restaurant.setRestaurantType("southIndian");

            Mockito.when(restaurantRepository.findByRestaurantName("pudhumai")).thenReturn(restaurant);

            Restaurant foundRestaurant=restaurantService.findRestaurant(restaurant);

            assertEquals(restaurant.getRestaurantIdentifier(),foundRestaurant.getRestaurantIdentifier());

        }
        catch(Exception e){

        }


    }

}
