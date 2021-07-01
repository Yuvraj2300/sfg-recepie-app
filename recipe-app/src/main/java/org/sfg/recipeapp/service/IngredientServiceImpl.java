package org.sfg.recipeapp.service;

import java.util.Optional;

import org.sfg.recipeapp.commands.IngredientCommand;
import org.sfg.recipeapp.converters.IngredientToIngredientCommand;
import org.sfg.recipeapp.domain.Recipe;
import org.sfg.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {



	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final RecipeRepository recipeRepository;




	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.recipeRepository = recipeRepository;
	}



	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {
			//todo impl error handling
			log.trace("recipe id not found. Id: " + recipeId);
		}

		Recipe recipe = recipeOptional.get();

		// @formatter:off
		Optional<IngredientCommand> ingredCommand	=	recipe.getIngredients().stream()
															.filter(ingred->ingred.getId().equals(ingredId))
																.map(ingred->ingredientToIngredientCommand.convert(ingred)).findFirst();
		// 	@formatter:on

		if (!ingredCommand.isPresent()) {
			log.trace("Ingredient id not found: " + ingredId);
		}

		return ingredCommand.get();
	}

}
