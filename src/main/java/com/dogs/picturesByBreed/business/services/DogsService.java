package com.dogs.picturesByBreed.business.services;

import com.dogs.picturesByBreed.business.models.dogsApi.BreedImagesResponse;
import com.dogs.picturesByBreed.business.models.dogsApi.ListAllBreedsResponse;
import com.dogs.picturesByBreed.business.models.dogsApi.ListAllSubBreedsResponse;
import com.dogs.picturesByBreed.business.models.dogsApi.RandomDogPictureResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class DogsService {
    //TODO happypath done
    public List<String> getSubBreeds(String breed) {
        log.info(String.format("GET https://dog.ceo/api/breed/%s/list", breed));
        ListAllSubBreedsResponse response = new RestTemplate().getForEntity(
                String.format("https://dog.ceo/api/breed/%s/list", breed),
                ListAllSubBreedsResponse.class
        ).getBody();
        log.info(String.format("Response from GET https://dog.ceo/api/breed/%s/list:\n", breed) + response);

        return Optional.ofNullable(response.getMessage()).orElse(new ArrayList<>());
    }

    public String getRandomDogPictureUrlByBreed(String path) {
        log.info(String.format("Request GET https://dog.ceo/api/breed/%s/images/random", path));
        ResponseEntity<RandomDogPictureResponse> dogPictureResponse = new RestTemplate().getForEntity(
                String.format("https://dog.ceo/api/breed/%s/images/random", path),
                RandomDogPictureResponse.class
        );
        log.info(String.format(
                "Response from GET https://dog.ceo/api/breed/%s/images/random:\n%s",
                path,
                dogPictureResponse
        ));

        return dogPictureResponse.getBody().getMessage();
    }

    public ListAllBreedsResponse getAllBreeds() {
        log.info("Request GET https://dog.ceo/api/breeds/list/all");
        ResponseEntity<ListAllBreedsResponse> response = new RestTemplate().getForEntity(
                "https://dog.ceo/api/breeds/list/all",
                ListAllBreedsResponse.class
        );
        log.info(String.format(
                "Response from GET https://dog.ceo/api/breeds/list/all:\n%s",
                response
        ));

        return response.getBody();
    }

    public BreedImagesResponse getBreedImages(String breed) {
        log.info(String.format("Request GET https://dog.ceo/api/breed/%s/images", breed));
        ResponseEntity<BreedImagesResponse> response = new RestTemplate().getForEntity(
                String.format("https://dog.ceo/api/breed/%s/images", breed),
                BreedImagesResponse.class
        );
        log.info(String.format(
                "Response from GET https://dog.ceo/api/breed/%s/images:\n%s",
                breed,
                response
        ));

        return response.getBody();
    }
}
