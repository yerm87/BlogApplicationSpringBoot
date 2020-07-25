package blogApplication.repo;

import blogApplication.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomQueryRepository extends JpaRepository<Car, Integer> {
    @Query(value = "SELECT * FROM Car WHERE name=?1 AND age>=?2", nativeQuery = true)
    List<Car> fetchData(String param, Integer year);

    @Query(value = "SELECT * FROM Car WHERE name= :maker", nativeQuery = true)
    List<Car> getDataByParam(@Param("maker") String name);
}
