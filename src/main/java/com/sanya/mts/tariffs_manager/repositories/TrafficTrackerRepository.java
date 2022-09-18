package com.sanya.mts.tariffs_manager.repositories;

import com.sanya.mts.tariffs_manager.entities.TrafficTracker;
import org.springframework.data.repository.CrudRepository;

public interface TrafficTrackerRepository extends CrudRepository<TrafficTracker, Integer> {
}
