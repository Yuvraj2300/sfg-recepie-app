package org.sfg.recipeapp.service;

import java.util.HashSet;
import java.util.Set;

import org.sfg.recipeapp.domain.Recipe;
import org.sfg.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository repo;

	public RecipeServiceImpl(RecipeRepository repo) {
		this.repo = repo;
	}

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipeSet = new HashSet<>();
		repo.findAll().iterator().forEachRemaining(r -> {
			recipeSet.add(r);
		});

		return recipeSet;
	}

}
