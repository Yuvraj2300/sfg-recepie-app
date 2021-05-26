package org.sfg.recipeapp.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.sfg.recipeapp.domain.Recipe;
import org.sfg.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepo;

	public RecipeServiceImpl(RecipeRepository repo) {
		this.recipeRepo = repo;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepo.findAll().iterator().forEachRemaining(r -> {
			recipeSet.add(r);
		});

		return recipeSet;
	}



	@Override
	public Recipe findById(Long l) {

		Optional<Recipe> recipeOptional = recipeRepo.findById(l);

		if (!recipeOptional.isPresent()) {
			throw new RuntimeException("Recipe Not Found!");
		}

		return recipeOptional.get();
	}
}
