package org.sfg.recipeapp.controller;

import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}




	@RequestMapping(value = "recipe/{id}/show", method = { RequestMethod.GET, RequestMethod.GET })
	public String showById(@PathVariable String id, Model model) {
		log.info("trying to show recipe with id {}", id);
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		return "recipe/show";
	}



	@RequestMapping(value = "recipe/{id}/update", method = { RequestMethod.GET, RequestMethod.PUT })
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/recipeform";
	}


	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		log.info("trying to save new model {}", model);
		model.addAttribute("recipe", new RecipeCommand());
		log.info("model updated {}", model);
		return "recipe/recipeform";
	}


	@RequestMapping(value = "recipe/{id}/delete")
	public String deleteById(@PathVariable String id) {
		log.debug("Deleting id: {}", id);

		recipeService.deleteById(Long.valueOf(id));

		return "redirect:/";
	}



	/**
	 * @param command
	 * @return
	 * @implNote So this returns a redirect once the post is completed!
	 */
	@RequestMapping(value = "recipe", method = { RequestMethod.GET, RequestMethod.GET })
	public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
		log.info("trying to save new recipe ::: {}", command.toString());
		RecipeCommand saveRecipe = recipeService.saveRecipeCommand(command);

		return "redirect:/recipe/" + saveRecipe.getId() + "/show";
	}

}