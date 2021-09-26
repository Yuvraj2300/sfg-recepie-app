package org.sfg.recipeapp.service;

import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImageServiceImpl implements ImageService {

	@Override
	public void saveImageFile(Long long1, MultipartFile file) {
		log.trace("Received a file");

	}

}
