package org.sfg.recipeapp.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sfg.recipeapp.commands.IngredientCommand;
import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.service.IngredientService;
import org.sfg.recipeapp.service.RecipeService;
import org.sfg.recipeapp.service.UnitOfMeasureService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class IngredientControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	IngredientService ingredService;

	@Mock
	UnitOfMeasureService unitOfMeasureService;

	IngredientController controller;

	MockMvc mockMvc;



	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);

		controller = new IngredientController(recipeService, ingredService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}


	@Test
	public void testUpdateAnIngred() throws Exception {
		IngredientCommand ingredComm = new IngredientCommand();

		//when
		when(ingredService.findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(ingredComm);
		when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

		// @formatter:off
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/ingredientform"))
			.andExpect(model().attributeExists("ingredient"))
			.andExpect(model().attributeExists("uomList"));
		// @formatter:on

	}


	@Test
	public void testSaveOrUpdate() throws Exception {
		//given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);

		//when
		when(ingredService.saveIngredientCommand(ArgumentMatchers.any())).thenReturn(command);

		//then
		mockMvc.perform(post("/recipe/2/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "").param("description", "some string")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

	}



	@Test
	public void testListIngredients() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		when(recipeService.findCommandById(ArgumentMatchers.anyLong())).thenReturn(recipeCommand);

		// @formatter:off
		mockMvc.perform(get("/recipe/122/ingredients"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/ingredient/list"))
			.andExpect(model().attributeExists("recipe"));
		// @formatter:on

		verify(recipeService, times(1)).findCommandById(ArgumentMatchers.anyLong());
	}


	@Test
	public void testShowIngred() throws Exception {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();

		//when
		when(ingredService.findByRecipeIdAndIngredientId(ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong())).thenReturn(ingredientCommand);

		//then
		// @formatter:off
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
			.andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/show"))
				.andExpect(model().attributeExists("ingredient"));
 		// @formatter:on


	}

}
