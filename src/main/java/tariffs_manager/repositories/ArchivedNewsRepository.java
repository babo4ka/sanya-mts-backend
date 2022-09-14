package tariffs_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import tariffs_manager.entities.ArchivedNews;

public interface ArchivedNewsRepository extends CrudRepository<ArchivedNews, Integer> {
}
