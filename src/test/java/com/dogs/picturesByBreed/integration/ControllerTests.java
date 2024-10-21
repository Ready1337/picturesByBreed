package com.dogs.picturesByBreed.integration;

import com.dogs.picturesByBreed.PicturesByBreedApplication;
import com.dogs.picturesByBreed.business.services.DogsService;
import com.dogs.picturesByBreed.business.services.YandexDiskService;
import com.dogs.picturesByBreed.business.validationServices.DogsValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.dogs.picturesByBreed.constants.Authorization.YANDEX_DISK_TEST_TOKEN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PicturesByBreedApplication.class)
@AutoConfigureMockMvc
public class ControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private YandexDiskService yandexDiskService;
    @Autowired
    private DogsValidationService dogsValidationService;
    @Autowired
    private DogsService dogsService;

    @Test
    public void givenInvalidBreedReturnsErrorMessage() throws Exception {

        var request = post("/dogs/uploadPictureToYandexDiskByBreed/invalidBreed")
                .header("Token", "fakeToken");

        mockMvc.perform(request)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.TEXT_PLAIN+";charset=UTF-8"),
                        content().string("Dogs API doesn't contain breed invalidBreed")
                );
    }

    @Test
    public void givenValidBreedReturnsSuccessMessage() throws Exception {
        var request = post("/dogs/uploadPictureToYandexDiskByBreed/hound")
                .header("Token", YANDEX_DISK_TEST_TOKEN);

        mockMvc.perform(request)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.TEXT_PLAIN+";charset=UTF-8"),
                        content().string("Image(s) successfully uploaded to {disk:/tech_intreview_full_task}")
                );
    }
}
