package com.dogs.picturesByBreed.business.models.dogsApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class BreedImagesResponse {
    @JsonProperty("message")
    private final List<String> message;
    @JsonProperty("status")
    private final String status;
}
