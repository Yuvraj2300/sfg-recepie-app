package org.sfg.recipeapp.converters;



import org.sfg.recipeapp.commands.CategoryCommand;
import org.sfg.recipeapp.domain.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import lombok.Synchronized;

/**
 * Created by jt on 6/21/17.
 */
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
	@Nullable
	@Override
	public Category convert(CategoryCommand source) {
		if (source == null) {
			return null;
		}

		final Category category = new Category();
		category.setId(source.getId());
		category.setDescription(source.getDescription());
		return category;
	}
}
