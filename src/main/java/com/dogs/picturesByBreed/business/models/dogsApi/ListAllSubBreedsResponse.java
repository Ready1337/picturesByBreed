package com.dogs.picturesByBreed.business.models.dogsApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ListAllSubBreedsResponse {
    @JsonProperty("message")
    private final ArrayList<String> message;
    @JsonProperty("status")
    private final String status;
}
