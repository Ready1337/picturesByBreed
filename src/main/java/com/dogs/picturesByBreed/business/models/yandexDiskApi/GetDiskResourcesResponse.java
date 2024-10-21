package com.dogs.picturesByBreed.business.models.yandexDiskApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetDiskResourcesResponse {
    @JsonProperty
    private final String message;
    @JsonProperty
    private final String description;
    @JsonProperty
    private final String error;
}
