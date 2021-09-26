package org.sfg.recipeapp.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.service.ImageService;
import org.sfg.recipeapp.service.RecipeService;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class ImageControllerTest {

	@Mock
	ImageService imageService;

	@Mock
	RecipeService recipeService;

	ImageController controller;

	MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);

		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}


	@Test
	public void getImageForm() throws Exception {
		//given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);

		when(recipeService.findCommandById(ArgumentMatchers.anyLong())).thenReturn(command);

		//when
		mockMvc.perform(get("/recipe/1/image")).andExpect(status().isOk()).andExpect(model().attributeExists("recipe"));

		verify(recipeService, times(1)).findCommandById(ArgumentMatchers.anyLong());
	}


	@Test
	public void testUpload() throws Exception {
		MockMultipartFile mockFile = new MockMultipartFile("imageFile", "testFile", "text/plain", "A very beautiful mesmerizing text".getBytes());

		// @formatter:off
		mockMvc.perform(multipart("/recipe/1/image").file(mockFile))
		.andExpect(status().is3xxRedirection())
		.andExpect(header().string("Location", "/recipe/1/show"));
// @formatter:on

		verify(imageService, times(1)).saveImageFile(ArgumentMatchers.anyLong(), ArgumentMatchers.any());

	}

}
