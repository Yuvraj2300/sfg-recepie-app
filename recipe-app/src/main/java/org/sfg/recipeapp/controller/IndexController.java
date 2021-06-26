package org.sfg.recipeapp.controller;

import org.sfg.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IndexController {

	RecipeService service;

	public IndexController(RecipeService service) {
		super();
		this.service = service;
	}

	@RequestMapping({ "", "/", "/index" })
	public String getIndexPage(Model model) {
		log.info("getting the landing page");
		model.addAttribute("recipes", service.getRecipes());

		return "index";
	}
}
