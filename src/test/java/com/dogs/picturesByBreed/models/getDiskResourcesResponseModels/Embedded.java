package com.dogs.picturesByBreed.models.getDiskResourcesResponseModels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class Embedded {
    public String sort;
    public ArrayList<Item> items;
    public int limit;
    public int offset;
    public String path;
    public int total;
}
