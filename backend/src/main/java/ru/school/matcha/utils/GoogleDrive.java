package ru.school.matcha.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import ru.school.matcha.domain.Image;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class GoogleDrive {

    private static final String APPLICATION_NAME = "Matcha";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "backend/tokens";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String CREDENTIALS = "credentials.json";
    private static Drive service;

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) {
        try {
            InputStream in = Resources.getResourceAsStream(CREDENTIALS);
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + CREDENTIALS);
            }
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        } catch (Exception ex) {
            log.error("Failed to get credentials in running GoogleDrive", ex);
        }
        return null;
    }

    public static Image createFile(String fileName) {
        log.info("Start create file with name: {} in GoogleDrive", fileName);
        try {
            File fileMetadata = new File();
            fileMetadata.setName(fileName);
            java.io.File filePath = new java.io.File("backend/images/" + fileName);
            FileContent mediaContent = new FileContent("image/jpeg", filePath);
            File file = service.files().create(fileMetadata, mediaContent)
                    .setFields("id, name, webViewLink")
                    .execute();
            service.permissions().create(file.getId(),
                    new Permission()
                            .setType("anyone")
                            .setRole("reader")
            ).execute();
            Image image = new Image();
            image.setName(file.getName());
            image.setLink(file.getWebViewLink());
            image.setExternalId(file.getId());
            return image;
        } catch (Exception ex) {
            log.error("Failed to create file with name: {} in GoogleDrive", fileName, ex);
        }
        log.error("An unexpected error occurred while trying to create file with name: {} in GoogleDrive", fileName);
        return null;
    }

    public static void uploadFile(String externalId) {
        log.info("Start upload file with externalId: {} from GoogleDrive", externalId);
        String fileId;
        try {
            fileId = externalId;
            OutputStream outputStream = new ByteArrayOutputStream();
            service.files().get(fileId)
                    .executeMediaAndDownloadTo(outputStream);
        } catch (Exception ex) {
            log.error("Failed upload file with externalId: {} from GoogleDrive", externalId, ex);
        }
    }

    public static Image searchFile(String externalId) {
        log.info("Start searching file with externalId: {} in GoogleDrive", externalId);
        try {
            String pageToken = null;
            do {
                FileList result = service.files().list()
                        .setQ("mimeType='image/jpeg'")
                        .setSpaces("drive")
                        .setFields("nextPageToken, files(id, name, webViewLink)")
                        .setPageToken(pageToken)
                        .execute();
                Optional<File> optionalFile = result.getFiles()
                        .stream()
                        .filter(file -> file.getId().equals(externalId))
                        .findFirst();
                if (!optionalFile.isPresent()) {
                    pageToken = result.getNextPageToken();
                } else {
                    log.info("Image with externalId {} is found", externalId);
                    File file = optionalFile.get();
                    Image image = new Image();
                    image.setName(file.getName());
                    image.setLink(file.getWebViewLink());
                    image.setExternalId(file.getId());
                    return image;
                }
            } while (pageToken != null);
        } catch (Exception ex) {
            log.error("Failed to search file with externalId {} from GoogleDrive", externalId, ex);
        }
        log.info("Image with externalId {} doesn't found", externalId);
        return null;
    }

    public static List<Image> getListImages(Integer count) {
        log.info("Get list with {} images from GoogleDrive", count);
        try {
            FileList result = service.files().list()
                    .setPageSize(count)
                    .setFields("nextPageToken, files(id, name, webViewLink)")
                    .execute();
            List<File> files = result.getFiles();
            if (files == null || files.isEmpty()) {
                log.info("No files found in GoogleDrive");
                return null;
            } else {
                log.info("{} files found in GoogleDrive", files.size());
                return files.stream()
                        .map(file -> {
                            Image image = new Image();
                            image.setName(file.getName());
                            image.setLink(file.getWebViewLink());
                            image.setExternalId(file.getId());
                            return image;
                        }).collect(Collectors.toList());
            }
        } catch (Exception ex) {
            log.error("Failed to get list images with count {} from GoogleDrive", count, ex);
        }
        return null;
    }

    public static void run() {
        try {
            NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME).build();
            log.info("GoogleDrive is run");
        } catch (Exception ex) {
            log.error("GoogleDrive doesn't run", ex);
        }
    }

}