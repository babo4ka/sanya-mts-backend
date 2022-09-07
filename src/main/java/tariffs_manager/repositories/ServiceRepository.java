package tariffs_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import tariffs_manager.entities.Service;

public interface ServiceRepository extends CrudRepository<Service, Integer> {
}
