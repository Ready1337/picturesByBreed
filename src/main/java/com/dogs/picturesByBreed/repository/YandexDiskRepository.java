package com.dogs.picturesByBreed.repository;

import org.springframework.stereotype.Repository;

@Repository
public class YandexDiskRepository {
    private final String ROOT_FOLDER = "disk:/tech_intreview_full_task";
    private String token;

    public String getRootFolder() {
        return ROOT_FOLDER;
    }

    public String getToken() {
        return token;
    }

    public void saveToken(String token) {
        this.token = token;
    }
}
