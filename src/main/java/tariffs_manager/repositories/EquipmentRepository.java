package tariffs_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import tariffs_manager.entities.Equipment;

public interface EquipmentRepository extends CrudRepository<Equipment, Integer> {
}
