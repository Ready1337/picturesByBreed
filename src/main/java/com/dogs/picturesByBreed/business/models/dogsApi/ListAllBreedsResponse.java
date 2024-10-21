package com.dogs.picturesByBreed.business.models.dogsApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Map;

@Data
@AllArgsConstructor
public class ListAllBreedsResponse {
    @JsonProperty("message")
    private final Map<String, ArrayList<String>> message;
    @JsonProperty("status")
    private final String status;
}
