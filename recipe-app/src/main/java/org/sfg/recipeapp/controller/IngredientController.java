package org.sfg.recipeapp.controller;

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



	public IngredientController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}


	@GetMapping("/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable String recipeId, Model model) {
		log.trace("Listing ingredients for recipe id : {} ", recipeId);
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(recipeId)));

		return "recipe/ingredient/list";
	}

}
