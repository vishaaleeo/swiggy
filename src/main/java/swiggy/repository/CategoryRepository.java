package swiggy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swiggy.domain.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Category findByCategoryNameAndRestaurantIdentifier(String categoryName,Integer restaurantIdentifier);

    List<Category> findByRestaurantIdentifier(Integer restaurantIdentifier);

}
