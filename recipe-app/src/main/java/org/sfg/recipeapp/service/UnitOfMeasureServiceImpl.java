package org.sfg.recipeapp.service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.sfg.recipeapp.commands.UnitOfMeasureCommand;
import org.sfg.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import org.sfg.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private final UnitOfMeasureRepository uomRepo;
	private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;


	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepo, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		super();
		this.uomRepo = uomRepo;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}



	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		// @formatter:off
		return	StreamSupport.stream(uomRepo.findAll().spliterator(), false)
					.map(unitOfMeasureToUnitOfMeasureCommand::convert)
						.collect(Collectors.toSet());
		// @formatter:on
	}

}
