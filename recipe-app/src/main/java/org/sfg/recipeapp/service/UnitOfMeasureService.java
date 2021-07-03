package org.sfg.recipeapp.service;

import java.util.Set;

import org.sfg.recipeapp.commands.UnitOfMeasureCommand;

public interface UnitOfMeasureService {
	Set<UnitOfMeasureCommand> listAllUoms();
}
