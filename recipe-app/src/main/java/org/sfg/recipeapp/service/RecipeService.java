package org.sfg.recipeapp.service;

import java.util.Set;

import org.sfg.recipeapp.domain.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipes();
}
