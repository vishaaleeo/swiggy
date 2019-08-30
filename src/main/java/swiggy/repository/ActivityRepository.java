package swiggy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swiggy.domain.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity,Integer> {
}
