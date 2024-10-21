package com.dogs.picturesByBreed.business.exceptions.dogServiceExceptions;

public class NoPictureForBreedDogsApiResponseException extends Exception {
    public NoPictureForBreedDogsApiResponseException(String message) {
        super(message);
    }
}
