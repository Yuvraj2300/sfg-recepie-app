package org.sfg.recipeapp.service;

import java.util.Optional;

import org.sfg.recipeapp.commands.IngredientCommand;
import org.sfg.recipeapp.converters.IngredientCommandToIngredient;
import org.sfg.recipeapp.converters.IngredientToIngredientCommand;
import org.sfg.recipeapp.domain.Ingredient;
import org.sfg.recipeapp.domain.Recipe;
import org.sfg.recipeapp.repositories.RecipeRepository;
import org.sfg.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {



	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository uomRepo;





	public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.uomRepo = unitOfMeasureRepository;
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

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand command) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

		if (!recipeOptional.isPresent()) {

			//todo toss error if not found!
			log.error("Recipe not found for id: " + command.getRecipeId());
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();

			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(command.getId())).findFirst();

			if (ingredientOptional.isPresent()) {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setAmount(command.getAmount());
				ingredientFound.setUom(uomRepo.findById(command.getUom().getId()).orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
			} else {
				//add new Ingredient
				Ingredient ingredient = ingredientCommandToIngredient.convert(command);
				ingredient.setRecipe(recipe);
				recipe.addIngredient(ingredient);
			}

			Recipe savedRecipe = recipeRepository.save(recipe);

			// @formatter:off
			Optional<Ingredient> saveIngredientOptional	=	savedRecipe.getIngredients().stream()
																.filter(recipeIngredients->recipeIngredients.getId().equals(command.getId()))
																		.findFirst(); 
			
			if(!saveIngredientOptional.isPresent()) {
				saveIngredientOptional	=	savedRecipe.getIngredients().stream()
						.filter(recipeIng->recipeIng.getDescription().equals(command.getDescription()))
						.filter(recipeIng->recipeIng.getAmount().equals(command.getAmount()))
						.filter(recipeIng->recipeIng.getUom().equals(command.getUom()))
					.findFirst();
			}
			// @formatter:on

			return ingredientToIngredientCommand.convert(saveIngredientOptional.get());
		}

	}

	@Override
	public void deleteById(Long recipeId, Long idToDelete) {
		log.trace("Deleting ingredient: " + recipeId + ":" + idToDelete);

		Optional<Recipe> recipeIdOpt = recipeRepository.findById(recipeId);

		if (recipeIdOpt.isPresent()) {
			Recipe recipe = recipeIdOpt.get();
			log.trace("found recipe");

			// @formatter:off
			Optional<Ingredient> ingredOpt = recipe
												.getIngredients()
													.stream()
													.filter(ing->ing.getId().equals(idToDelete))
													.findFirst();
			// @formatter:on

			if (ingredOpt.isPresent()) {
				log.trace("ingred found");

				Ingredient ingredient = ingredOpt.get();

				ingredient.setRecipe(null);
				recipe.getIngredients().remove(ingredOpt.get());
				recipeRepository.save(recipe);
			}
		} else {
			log.trace("Recipe ID was not found");
		}


	}

}
