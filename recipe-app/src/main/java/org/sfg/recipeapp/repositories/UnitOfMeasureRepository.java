package org.sfg.recipeapp.repositories;

import java.util.Optional;

import org.sfg.recipeapp.domain.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jt on 6/13/17.
 */
public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findByDescription(String description);
}
