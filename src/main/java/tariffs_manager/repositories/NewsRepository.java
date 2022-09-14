package tariffs_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import tariffs_manager.entities.News;

public interface NewsRepository extends CrudRepository<News, Integer> {
}
