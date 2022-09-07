package tariffs_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import tariffs_manager.entities.Tariff;

public interface TariffsRepository extends CrudRepository<Tariff, Integer> {
}
