import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import swiggy.Application;
import swiggy.domain.Order;
import swiggy.repository.*;
import swiggy.services.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class OrderServiceTest {


    @Mock
    OrderRepository orderRepository;

    @Mock
    FoodRepository foodRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @Mock
    OfferRepository offerRepository;

    @Mock
    CustomizationRepository customizationRepository;

    @InjectMocks
    OrderService orderService;


    @Test
    public void createOrderTest(){

        Order order =new Order();
        

    }

    @Test
    public void updateOrderTest(){


    }

    @Test
    public void deleteOrderTest(){

    }

    @Test
    public void checkOfferTest(){


    }

    @Test
    public void displayOrderTest(){


    }

    @Test public void onCartDisplayTest(){


    }
}
