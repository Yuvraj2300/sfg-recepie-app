package org.sfg.recipeapp.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.service.RecipeService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class IngredientControllerTest {

	@Mock
	RecipeService recipeService;

	IngredientController controller;

	MockMvc mockMvc;



	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);

		controller = new IngredientController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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

}
