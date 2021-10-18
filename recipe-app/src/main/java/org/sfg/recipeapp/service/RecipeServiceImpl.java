package org.sfg.recipeapp.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.converters.RecipeCommandToRecipe;
import org.sfg.recipeapp.converters.RecipeToRecipeCommand;
import org.sfg.recipeapp.domain.Recipe;
import org.sfg.recipeapp.exceptions.NotFoundException;
import org.sfg.recipeapp.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepo;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;


	public RecipeServiceImpl(RecipeRepository recipeRepo, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
		super();
		this.recipeRepo = recipeRepo;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}



	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepo.findAll().iterator().forEachRemaining(r -> {
			recipeSet.add(r);
		});

		return recipeSet;
	}



	@Override
	public Recipe findById(Long l) {

		Optional<Recipe> recipeOptional = recipeRepo.findById(l);

		if (!recipeOptional.isPresent()) {
			throw new NotFoundException("Recipe Not Found. For ID value: " + l.toString());
		}

		return recipeOptional.get();
	}

	@Override
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe deatachedRecipe = recipeCommandToRecipe.convert(command);

		Recipe savedRecipe = recipeRepo.save(deatachedRecipe);
		log.trace("Saved recipeId : " + savedRecipe.getId());
		return recipeToRecipeCommand.convert(savedRecipe);
	}



	@Override
	@Transactional
	public RecipeCommand findCommandById(Long l) {
		return recipeToRecipeCommand.convert(findById(l));
	}



	@Override
	public void deleteById(Long id) {
		recipeRepo.deleteById(id);
	}
}
