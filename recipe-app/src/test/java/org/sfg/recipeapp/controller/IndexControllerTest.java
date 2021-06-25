package org.sfg.recipeapp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sfg.recipeapp.domain.Recipe;
import org.sfg.recipeapp.service.RecipeService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

class IndexControllerTest {

	@Mock
	RecipeService recipeService;

	@Mock
	Model model;

	IndexController controller;

	MockMvc mockMvc;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		controller = new IndexController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	public void testEndpoint() throws Exception {
		// @formatter:off
		 mockMvc.perform(get("/"))
         .andExpect(status().isOk())
         .andExpect(view().name("index"));
 
// @formatter:on

	}

	@Test
	public void getIndexPage() throws Exception {

		//given
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());

		Recipe recipe = new Recipe();
		recipe.setId(1L);

		recipes.add(recipe);

		when(recipeService.getRecipes()).thenReturn(recipes);

		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

		//when
		String viewName = controller.getIndexPage(model);


		//then
		assertEquals("index", viewName);
		verify(recipeService, times(1)).getRecipes();
		verify(model, times(1)).addAttribute(ArgumentMatchers.eq("recipes"), argumentCaptor.capture());
		Set<Recipe> setInController = argumentCaptor.getValue();
		assertEquals(2, setInController.size());
	}

}
