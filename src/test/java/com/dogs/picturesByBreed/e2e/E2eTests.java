package com.dogs.picturesByBreed.e2e;

import com.dogs.picturesByBreed.PicturesByBreedApplication;
import com.dogs.picturesByBreed.business.services.DogsService;
import com.dogs.picturesByBreed.business.services.YandexDiskService;
import com.dogs.picturesByBreed.business.validationServices.DogsValidationService;
import com.dogs.picturesByBreed.models.getDiskResourcesResponseModels.GetDiskResourcesResponse;
import com.dogs.picturesByBreed.models.getDiskResourcesResponseModels.Item;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.dogs.picturesByBreed.constants.Authorization.YANDEX_DISK_TEST_TOKEN;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = PicturesByBreedApplication.class)
@AutoConfigureMockMvc
public class E2eTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private YandexDiskService yandexDiskService;
    @Autowired
    private DogsValidationService dogsValidationService;
    @Autowired
    private DogsService dogsService;

    @Test
    public void breedWithNoSubBreedShouldUploadOneImageTest() throws Exception {
        String breed = "kelpie";
        Integer subBreedsCount = dogsService.getSubBreeds(breed).size();
        assertThat("Breed should not contain sub-breeds", subBreedsCount.equals(0));
        Long oldImagesCount = getFolderGeneralImagesCount(List.of(breed));

        var request = post(String.format("/dogs/uploadPictureToYandexDiskByBreed/%s", breed))
                .header("Token", YANDEX_DISK_TEST_TOKEN);

        mockMvc.perform(request)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.TEXT_PLAIN+";charset=UTF-8"),
                        content().string("Image(s) successfully uploaded to {disk:/tech_intreview_full_task}")
                );

        //TODO Yandex API wait(можно сделать явное)
        Thread.sleep(10000);
        Long newImagesCount = getFolderGeneralImagesCount(List.of(breed));

        assertThat(
                String.format("Only 1 image should be uploaded, actual: %s", newImagesCount - oldImagesCount),
                newImagesCount - oldImagesCount == 1
        );
    }

    @Test
    public void breedWithSubBreedsShouldUploadSubBreedCountImagesTest() throws Exception {
        String breed = "hound";
        List<String> subBreeds = dogsService.getSubBreeds(breed);
        Long oldImagesCount = getFolderGeneralImagesCount(subBreeds);
        Integer subBreedsCount = subBreeds.size();
        assertThat("Breed should contain sub-breeds", subBreedsCount > 0);

        var request = post(String.format("/dogs/uploadPictureToYandexDiskByBreed/%s", breed))
                .header("Token", YANDEX_DISK_TEST_TOKEN);

        mockMvc.perform(request)
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.TEXT_PLAIN+";charset=UTF-8"),
                        content().string("Image(s) successfully uploaded to {disk:/tech_intreview_full_task}")
                );

        //TODO Yandex API wait(можно сделать явное)
        Thread.sleep(10000);
        Long newImagesCount = getFolderGeneralImagesCount(subBreeds);

        assertThat(
                String.format("SubBreed count images should be uploaded, actual: %s", newImagesCount - oldImagesCount),
                newImagesCount - oldImagesCount == subBreedsCount
        );
    }

    private long getFolderGeneralImagesCount(List<String> subBreeds) {
        long generalImageCount = 0;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", YANDEX_DISK_TEST_TOKEN);

        for (String subBreed : subBreeds) {
            String subFolderPath = String.format("disk:/tech_intreview_full_task/%s", subBreed);
            log.info(String.format("Request GET to https://cloud-api.yandex.net/v1/disk/resources/?path=%s", subFolderPath));
            try {
                ResponseEntity<GetDiskResourcesResponse> response = new RestTemplate().exchange(
                        String.format("https://cloud-api.yandex.net/v1/disk/resources/?path=%s", subFolderPath),
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        GetDiskResourcesResponse.class
                );
                log.info(String.format("Response:\n%s", response));

                generalImageCount += response.getBody().get_embedded().getItems().stream()
                        .map(Item::getMedia_type)
                        .filter(type -> type.equals("image"))
                        .count();
            } catch (HttpClientErrorException e) {
                generalImageCount += 0;
            }
        }

        return generalImageCount;
    }
}
