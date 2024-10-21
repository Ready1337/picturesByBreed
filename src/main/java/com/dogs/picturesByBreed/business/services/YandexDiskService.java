package com.dogs.picturesByBreed.business.services;

import com.dogs.picturesByBreed.business.models.yandexDiskApi.GetDiskResourcesResponse;
import com.dogs.picturesByBreed.repository.YandexDiskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class YandexDiskService {
    private YandexDiskRepository yandexDiskRepository;

    public YandexDiskService(YandexDiskRepository yandexDiskRepository) {
        this.yandexDiskRepository = yandexDiskRepository;
    }

    public void upLoadByPathAndUrl(String path, String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", yandexDiskRepository.getToken());

        log.info(String.format("GET https://cloud-api.yandex.net/v1/disk/resources/upload?path=%s&url=%s", path, url));
        ResponseEntity<String> response = new RestTemplate().exchange(
                String.format("https://cloud-api.yandex.net/v1/disk/resources/upload?path=%s&url=%s", path, url),
                HttpMethod.POST,
                new HttpEntity<>(headers),
                String.class
        );
        log.info(response.toString());
    }

    public void createRootFolderIfNotExists() {
        String rootFolderPath = yandexDiskRepository.getRootFolder();
        if (folderNotExists(rootFolderPath)) {
            createDiskFolder(rootFolderPath);
        }
    }

    private boolean folderNotExists(String folderPath) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", yandexDiskRepository.getToken());

        log.info(String.format("Request GET https://cloud-api.yandex.net/v1/disk/resources?path=%s", folderPath));
        try {
            ResponseEntity<GetDiskResourcesResponse> response = new RestTemplate().exchange(
                    String.format("https://cloud-api.yandex.net/v1/disk/resources?path=%s", folderPath),
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    GetDiskResourcesResponse.class
            );
        } catch (HttpClientErrorException e) {
            log.info(String.format(
                    "Response from GET https://cloud-api.yandex.net/v1/disk/resources?path=%s:\n%s",
                    folderPath,
                    e.getResponseBodyAsString()
            ));
            GetDiskResourcesResponse response = e.getResponseBodyAs(GetDiskResourcesResponse.class);
            return e.getStatusCode().equals(HttpStatusCode.valueOf(404)) &&
                    response.getMessage().equals("Не удалось найти запрошенный ресурс.") &&
                    response.getDescription().equals("Resource not found.") &&
                    response.getError().equals("DiskNotFoundError");
        }

        return false;
    }

    public void createDiskFolder(String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", yandexDiskRepository.getToken());

        log.info(String.format("Request GET https://cloud-api.yandex.net/v1/disk/resources?path=%s", path));
        try {
            new RestTemplate().exchange(
                    String.format("https://cloud-api.yandex.net/v1/disk/resources?path=%s", path),
                    HttpMethod.PUT,
                    new HttpEntity<String>(headers),
                    String.class
            );
        } catch (HttpClientErrorException e) {
            log.info(String.format(
                    "Response from GET https://cloud-api.yandex.net/v1/disk/resources?path=%s:\n%s",
                    path,
                    e.getResponseBodyAsString()
            ));
        }
    }

    public String getRootUploadPath() {
        return yandexDiskRepository.getRootFolder();
    }

    public String createSubFolderPath(String folderName) {
        return yandexDiskRepository.getRootFolder() + "/" + folderName;
    }

    public void saveToken(String token) {
        yandexDiskRepository.saveToken(token);
    }
}
