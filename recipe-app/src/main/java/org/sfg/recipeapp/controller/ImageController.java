package org.sfg.recipeapp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.sfg.recipeapp.commands.RecipeCommand;
import org.sfg.recipeapp.service.ImageService;
import org.sfg.recipeapp.service.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ImageController {

	private ImageService imageService;
	private RecipeService recipeService;

	public ImageController(ImageService imageService, RecipeService recipeService) {
		super();
		this.imageService = imageService;
		this.recipeService = recipeService;
	}


	@GetMapping("recipe/{id}/image")
	public String showUploadForm(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/imageuploadform";
	}


	@PostMapping("recipe/{id}/image")
	public String handleImagePost(@PathVariable String id, @RequestParam("imageFile") MultipartFile file) {
		imageService.saveImageFile(Long.valueOf(id), file);
		return "redirect:/recipe/" + id + "/show";
	}


	@GetMapping("recipe/{id}/recipeimage")
	public void renderImage(@PathVariable String id, HttpServletResponse response) throws IOException {
		log.trace("Rendering Image for id - {}", id);
		RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

		Byte[] wrappedImageBytes = recipeCommand.getImage();
		if (null != wrappedImageBytes) {

			byte[] primitiveByteArrayImage = new byte[wrappedImageBytes.length];
			int i = 0;

			for (byte wrappedByte : wrappedImageBytes) {
				primitiveByteArrayImage[i++] = wrappedByte;
			}

			response.setContentType("image/jpeg");
			InputStream imageAsByteStream = new ByteArrayInputStream(primitiveByteArrayImage);
			IOUtils.copy(imageAsByteStream, response.getOutputStream());
		}
	}
}
