package blogApplication.repo;

import blogApplication.models.ShopLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends CrudRepository<ShopLocation, Integer> {
}
