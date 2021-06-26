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




	@RequestMapping(value = "recipe/show/{id}", method = { RequestMethod.GET, RequestMethod.GET })
	public String showById(@PathVariable String id, Model model) {
		log.info("trying to show recipe with id {}", id);
		model.addAttribute("recipe", recipeService.findById(Long.valueOf(id)));
		return "recipe/show";
	}




	@RequestMapping("recipe/new")
	public String newRecipe(Model model) {
		log.info("trying to save new model {}", model);
		model.addAttribute("recipe", new RecipeCommand());
		log.info("model updated {}", model);
		return "recipe/recipeform";
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

		return "redirect:/recipe/show/" + saveRecipe.getId();
	}

}