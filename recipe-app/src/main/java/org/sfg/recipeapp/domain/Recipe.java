package org.sfg.recipeapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private Integer source;
	private String url;
	@Lob
	private String directions;

	@Lob
	private Byte[] image;

	@Enumerated(value = EnumType.STRING)
	private Difficulty difficulty;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	private Set<Ingredient> ingredients = new HashSet<Ingredient>();

	@OneToOne(cascade = CascadeType.ALL)
	@Setter(AccessLevel.NONE)
	private Notes notes;

	//@formatter:off
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "recipe_category", 
		joinColumns = @JoinColumn(name="recipe_id"), 
		inverseJoinColumns = @JoinColumn(name="category_id"))
	private Set<Category> categories	=	new	HashSet<Category>();
	//@formatter:on

	public void setNotes(Notes notes) {
		this.notes = notes;
		notes.setRecipe(this);
	}

	public Recipe addIngredient(Ingredient ingredient) {
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
		return this;
	}

}
