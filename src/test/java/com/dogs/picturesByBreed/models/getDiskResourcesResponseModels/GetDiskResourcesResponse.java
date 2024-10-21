package com.dogs.picturesByBreed.models.getDiskResourcesResponseModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Date;

@Data
@AllArgsConstructor
public class GetDiskResourcesResponse {
    @JsonProperty("_embedded")
    public Embedded _embedded;
    @JsonProperty("name")
    public String name;
    @JsonProperty("exif")
    public Exif exif;
    @JsonProperty("resource_id")
    public String resource_id;
    @JsonProperty("created")
    public Date created;
    @JsonProperty("modified")
    public Date modified;
    @JsonProperty("path")
    public String path;
    @JsonProperty("comment_ids")
    public CommentIds comment_ids;
    @JsonProperty("type")
    public String type;
    @JsonProperty("revision")
    public long revision;
}


