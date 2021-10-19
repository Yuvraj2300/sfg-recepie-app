package org.sfg.recipeapp.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.domain.Recipe;
import org.sfg.recipeapp.exceptions.NotFoundException;
import org.sfg.recipeapp.service.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RecipeControllerTest {


	@Mock
	RecipeService recipeService;

	RecipeController controller;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		controller = new RecipeController(recipeService);
	}

	@Test
	public void testGetRecipe() throws Exception {

		Recipe recipe = new Recipe();
		recipe.setId(1L);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		when(recipeService.findById(ArgumentMatchers.anyLong())).thenReturn(recipe);

		mockMvc.perform(get("/recipe/1/show")).andExpect(status().isOk()).andExpect(view().name("recipe/show")).andExpect(model().attributeExists("recipe"));
	}



	@Test
	public void testGetRecipeNotFound() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(ControllerExceptionHandler.class).build();

		when(recipeService.findById(ArgumentMatchers.anyLong())).thenThrow(NotFoundException.class);

		mockMvc.perform(get("/recipe/1/show")).andExpect(view().name("404error"));
	}



	@Test
	public void testDelete() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		// @formatter:off
		mockMvc.perform(get("/recipe/1/delete"))
        	.andExpect(status().is3xxRedirection())
        		.andExpect(view().name("redirect:/"));
		// @formatter:on

		verify(recipeService, times(1)).deleteById(ArgumentMatchers.anyLong());
	}



	@Test
	void testUpdateRecipe() throws Exception {

		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		when(recipeService.findCommandById(ArgumentMatchers.anyLong())).thenReturn(recipeCommand);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		// @formatter:off
		mockMvc.perform(get("/recipe/1/update"))
        	.andExpect(status().isOk())
        		.andExpect(view().name("recipe/recipeform"))
        		.andExpect(model().attributeExists("recipe"));
		// @formatter:on
	}


	@Test
	void testNewRecipe() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		// @formatter:off
		mockMvc.perform(get("/recipe/new"))
        	.andExpect(status().isOk())
        		.andExpect(view().name("recipe/recipeform"))
        		.andExpect(model().attributeExists("recipe"));
		// @formatter:on
	}



	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(ArgumentMatchers.any())).thenReturn(command);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		// @formatter:off
	        mockMvc.perform(post("/recipe")
	                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
	                .param("id", "")
	                .param("description", "some string")
	                .param("directions", "some directions")
	        )
	                .andExpect(status().is3xxRedirection())
	                .andExpect(view().name("redirect:/recipe/2/show"));

	     // @formatter:on


	}




	@Test
	public void testPostNewRecipeFormValidationFail() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);

		when(recipeService.saveRecipeCommand(ArgumentMatchers.any())).thenReturn(command);

		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "")

		).andExpect(status().isOk()).andExpect(model().attributeExists("recipe")).andExpect(view().name("recipe/recipeform"));
	}
}
