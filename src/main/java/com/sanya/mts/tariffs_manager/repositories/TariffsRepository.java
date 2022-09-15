package com.sanya.mts.tariffs_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import com.sanya.mts.tariffs_manager.entities.Tariff;

public interface TariffsRepository extends CrudRepository<Tariff, Integer> {
}
