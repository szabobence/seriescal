package com.finnhvman.seriescal.services.store.jpa;

import com.finnhvman.seriescal.services.store.jpa.entities.SeasonEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SeasonCrudRepository extends CrudRepository<SeasonEntity, Long> {

    List<SeasonEntity> findAll();

    List<SeasonEntity> findByIdIn(List<Long> ids);

    List<SeasonEntity> findByPage(String page);

}
