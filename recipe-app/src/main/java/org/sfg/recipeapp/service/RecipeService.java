package org.sfg.recipeapp.service;

import java.util.Set;

import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.domain.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipes();

	Recipe findById(Long l);

	RecipeCommand findCommandById(Long l);

	RecipeCommand saveRecipeCommand(RecipeCommand command);
}
