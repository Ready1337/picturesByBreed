package com.dogs.picturesByBreed.models.getDiskResourcesResponseModels;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
@AllArgsConstructor
public class Item {
    public String antivirus_status;
    public int size;
    public CommentIds comment_ids;
    public String name;
    public Exif exif;
    public Date created;
    public String resource_id;
    public Date modified;
    public String mime_type;
    public ArrayList<Size> sizes;
    public String file;
    public String media_type;
    public String preview;
    public String path;
    public String sha256;
    public String type;
    public String md5;
    public Object revision;
}
