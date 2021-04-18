package org.sfg.recipeapp.repositories;

import java.util.Optional;

import org.sfg.recipeapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
	Optional<UnitOfMeasure> findByDescription(String desc);
}
