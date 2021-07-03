package org.sfg.recipeapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sfg.recipeapp.commands.IngredientCommand;
import org.sfg.recipeapp.converters.IngredientCommandToIngredient;
import org.sfg.recipeapp.converters.IngredientToIngredientCommand;
import org.sfg.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import org.sfg.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import org.sfg.recipeapp.domain.Ingredient;
import org.sfg.recipeapp.domain.Recipe;
import org.sfg.recipeapp.repositories.RecipeRepository;
import org.sfg.recipeapp.repositories.UnitOfMeasureRepository;

class IngredientServiceImplTest {


	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;

	@Mock
	RecipeRepository recipeRepository;

	IngredientService ingredientService;

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepository;

	//init converters
	public IngredientServiceImplTest() {
		this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}


	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		ingredientService = new IngredientServiceImpl(ingredientToIngredientCommand, ingredientCommandToIngredient, recipeRepository, unitOfMeasureRepository);
	}

	@Test
	public void findByRecipeIdAndId() throws Exception {
	}

	@Test
	public void findByRecipeIdAndReceipeIdHappyPath() throws Exception {
		//given
		Recipe recipe = new Recipe();
		recipe.setId(1L);

		Ingredient ingredient1 = new Ingredient();
		ingredient1.setId(1L);

		Ingredient ingredient2 = new Ingredient();
		ingredient2.setId(1L);

		Ingredient ingredient3 = new Ingredient();
		ingredient3.setId(3L);

		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);

		when(recipeRepository.findById(ArgumentMatchers.anyLong())).thenReturn(recipeOptional);

		//then
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

		//when
		assertEquals(Long.valueOf(3L), ingredientCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(ArgumentMatchers.anyLong());
	}

}
