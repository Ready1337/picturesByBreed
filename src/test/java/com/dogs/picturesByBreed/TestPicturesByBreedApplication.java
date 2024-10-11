package com.dogs.picturesByBreed;

import org.springframework.boot.SpringApplication;

public class TestPicturesByBreedApplication {

	public static void main(String[] args) {
		SpringApplication.from(PicturesByBreedApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
