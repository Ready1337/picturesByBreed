package com.dogs.picturesByBreed.business.validationServices;

import com.dogs.picturesByBreed.business.exceptions.dogServiceExceptions.NoPictureForBreedDogsApiResponseException;
import com.dogs.picturesByBreed.business.exceptions.dogServiceExceptions.NoSuchBreedDogsApiResponseException;
import com.dogs.picturesByBreed.business.services.DogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DogsValidationService {
    private final DogsService dogsService;

    public DogsValidationService(DogsService dogsService) {
        this.dogsService = dogsService;
    }

    public void validateBreed(String breed) throws NoSuchBreedDogsApiResponseException, NoPictureForBreedDogsApiResponseException {
        if (!dogsService.getAllBreeds().getMessage().containsKey(breed)) {
            throw new NoSuchBreedDogsApiResponseException(String.format("Dogs API doesn't contain breed %s", breed));
        }

        if (dogsService.getBreedImages(breed).getMessage().isEmpty()) {
            throw new NoPictureForBreedDogsApiResponseException(String.format(
                    "There is no pictures for breed %s",
                    breed
            ));
        }
    }
}
