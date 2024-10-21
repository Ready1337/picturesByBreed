package com.dogs.picturesByBreed.business;

import com.dogs.picturesByBreed.business.exceptions.dogServiceExceptions.NoPictureForBreedDogsApiResponseException;
import com.dogs.picturesByBreed.business.exceptions.dogServiceExceptions.NoSuchBreedDogsApiResponseException;
import com.dogs.picturesByBreed.business.services.DogsService;
import com.dogs.picturesByBreed.business.services.YandexDiskService;
import com.dogs.picturesByBreed.business.validationServices.DogsValidationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    private final DogsService dogsService;
    private final DogsValidationService dogsValidationService;
    private final YandexDiskService yandexDiskService;

    public Controller(DogsService dogsService, DogsValidationService dogsValidationService, YandexDiskService yandexDiskService) {
        this.dogsService = dogsService;
        this.dogsValidationService = dogsValidationService;
        this.yandexDiskService = yandexDiskService;
    }

    //TODO сделать нормальное нозвание
    @PostMapping("/dogs/uploadPictureToYandexDiskByBreed/{breed}")
    public String uploadPictureToYandexDiskByBreed(@PathVariable String breed, @RequestHeader("Token") String token) {
        try {
            dogsValidationService.validateBreed(breed);
            yandexDiskService.saveToken(token);

            yandexDiskService.createRootFolderIfNotExists();

            List<String> subBreeds = dogsService.getSubBreeds(breed);
            if (subBreeds.isEmpty()) {
                String breedFolderPath = yandexDiskService.createSubFolderPath(breed);
                yandexDiskService.createDiskFolder(breedFolderPath);
                String dogPictureUrl = dogsService.getRandomDogPictureUrlByBreed(breed);
                yandexDiskService.upLoadByPathAndUrl(String.format("%s/%s", breedFolderPath, breed), dogPictureUrl);
            }

            for (String subBreed: subBreeds) {
                String subBreedFolderPath = yandexDiskService.createSubFolderPath(subBreed);
                yandexDiskService.createDiskFolder(subBreedFolderPath);
                String subBreedPicturePath = String.format("%s/%s", breed, subBreed);
                String dogPictureUrl = dogsService.getRandomDogPictureUrlByBreed(subBreedPicturePath);
                yandexDiskService.upLoadByPathAndUrl(
                        String.format("%s/%s", subBreedFolderPath, subBreed),
                        dogPictureUrl
                );
            }
            //TODO Вынести в ExceptionHandler-ы
        } catch (NoSuchBreedDogsApiResponseException | NoPictureForBreedDogsApiResponseException e) {
            return e.getMessage();
        }

        return String.format("Image(s) successfully uploaded to {%s}", yandexDiskService.getRootUploadPath());
    }
}
