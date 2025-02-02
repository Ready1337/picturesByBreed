package com.dogs.picturesByBreed;

import com.dogs.picturesByBreed.business.Controller;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class PicturesByBreedApplicationTests {

	@Autowired
	private Controller controller;
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
