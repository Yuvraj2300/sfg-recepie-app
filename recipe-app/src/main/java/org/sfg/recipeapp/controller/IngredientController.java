package org.sfg.recipeapp.controller;

import org.sfg.recipeapp.commands.IngredientCommand;
import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.commands.UnitOfMeasureCommand;
import org.sfg.recipeapp.service.IngredientService;
import org.sfg.recipeapp.service.RecipeService;
import org.sfg.recipeapp.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class IngredientController {

	private final RecipeService recipeService;

	private final IngredientService ingredService;

	private final UnitOfMeasureService unitOfMeasureService;


	public IngredientController(RecipeService recipeService, IngredientService ingredService, UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredService = ingredService;
		this.unitOfMeasureService = unitOfMeasureService;
	}


	@GetMapping("/recipe/{recipeId}/ingredient/{ingredId}/update")
	public String updateIngred(@PathVariable String recipeId, @PathVariable String ingredId, Model model) {
		model.addAttribute("ingredient", ingredService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredId)));

		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

		return "recipe/ingredient/ingredientform";
	}



	@PostMapping
	@RequestMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredService.saveIngredientCommand(command);

		log.debug("saved receipe id:" + savedCommand.getRecipeId());
		log.debug("saved ingredient id:" + savedCommand.getId());

		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
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

	@GetMapping("recipe/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable String recipeId, Model model) throws Exception {
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
		if (null == recipeCommand) {
			throw new Exception("Id was null");
		}

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(Long.valueOf(recipeId));
		model.addAttribute("ingredient", ingredientCommand);

		//init uom
		ingredientCommand.setUom(new UnitOfMeasureCommand());

		model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

		return "recipe/ingredient/ingredientform";
	}



	@DeleteMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
		log.trace("Deleting an ingredient with ID : {}", id);
		ingredService.deleteById(Long.valueOf(recipeId), Long.valueOf(id));
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}


}
