package org.sfg.recipeapp.repositories;

import java.util.Optional;

import org.sfg.recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	Optional<Category> findByDescription(String description);
}
