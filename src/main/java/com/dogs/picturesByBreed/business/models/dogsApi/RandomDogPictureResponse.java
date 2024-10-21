package com.dogs.picturesByBreed.business.models.dogsApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RandomDogPictureResponse {
    @JsonProperty
    private final String message;
    @JsonProperty
    private final String status;
}
