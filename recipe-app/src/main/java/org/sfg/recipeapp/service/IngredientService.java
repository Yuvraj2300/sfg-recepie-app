package org.sfg.recipeapp.service;

import org.sfg.recipeapp.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredId);

}
