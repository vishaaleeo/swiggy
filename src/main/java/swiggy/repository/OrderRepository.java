package swiggy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swiggy.domain.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {


    @Query("select max(r.orderGroupIdentifier) from Order r ")
    Integer findMaxGroupOrderIdentifierByUser();

    @Query("select r from Order r where r.userIdentifier=?1 and r.orderStatus='oncart' and r.deleteFlag!=true")
    List<Order> findOnCart(Integer userIdentifier);

    @Query("select r from Order r where r.orderGroupIdentifier=?1 and r.deleteFlag!=true")
    List<Order> findByOrderGroupIdentifier(Integer orderGroupIdentifier);

    @Modifying
    @Query("update Order r set r.offerIdentifier=?1 where r.orderGroupIdentifier=?2")
    List<Order> saveOffer(Integer offerIdentifier,Integer orderGroupIdentifier);

    @Query("select sum(r.orderCost) from Order r where r.deleteFlag!=true group by r.orderGroupIdentifier ")
    List<Integer> findTotalCost(Integer orderGroupIdentifier);



    @Query("select r.restaurantIdentifier, sum(r.orderCost) from Order r where r.userIdentifier=?1 and r.deleteFlag!=true group by r.orderGroupIdentifier")
   // @Query(value = "select r.restaurant_identifier,sum(r.order_cost) from Order r where r.userIdentifier=?1 group by r.orderGroupIdentifier ",nativeQuery = true)
    List<Object[]> findOrders(Integer userIdentifier);

    List<Order>findByUserIdentifier(Integer userIdentifier);

}
