package org.sfg.recipeapp.controller;

import org.sfg.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	RecipeService service;

	public IndexController(RecipeService service) {
		super();
		this.service = service;
	}

	@RequestMapping({ "", "/", "/index" })
	public String getIndexPage(Model model) {
		model.addAttribute("recipes", service.getRecipes());

		return "index";
	}
}
