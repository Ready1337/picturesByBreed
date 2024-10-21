package com.dogs.picturesByBreed.business.exceptions.dogServiceExceptions;

public class NoSuchBreedDogsApiResponseException extends Exception {
    public NoSuchBreedDogsApiResponseException(String message) {
        super(message);
    }
}
