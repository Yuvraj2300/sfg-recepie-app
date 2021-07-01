package org.sfg.recipeapp.controller;

import org.sfg.recipeapp.service.IngredientService;
import org.sfg.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class IngredientController {

	private final RecipeService recipeService;

	private final IngredientService ingredService;





	public IngredientController(RecipeService recipeService, IngredientService ingredService) {
		super();
		this.recipeService = recipeService;
		this.ingredService = ingredService;
	}





	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.trace("Listing ingredients for recipe id : {} ", recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

		return "recipe/ingredient/list";
	}



	@GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		log.trace("Trying to show you the ingredient for ingredientId {} with recipeId {}", id, recipeId);
		model.addAttribute("ingredient", ingredService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(id)));

		return "recipe/ingredient/show";
	}
}
