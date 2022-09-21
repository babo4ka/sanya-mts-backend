package com.sanya.mts.tariffs_manager.repositories;

import com.sanya.mts.tariffs_manager.entities.TrafficTracker;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TrafficTrackerRepository extends CrudRepository<TrafficTracker, Integer> {
    List<TrafficTracker> findByDateBetween(Timestamp start, Timestamp end);
}
