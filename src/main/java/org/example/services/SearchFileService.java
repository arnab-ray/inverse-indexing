package org.example.services;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchFileService {

    private final Drive service;

    public SearchFileService(Drive service) {
        this.service = service;
    }

    /**
     * Search for specific set of files.
     *
     * @return search result list.
     * @throws IOException if service account credentials file not found.
     */
    public List<File> searchFile() throws IOException {

        List<File> files = new ArrayList<>();

        String pageToken = null;
        do {
            System.out.println("Trying to fetch file");
            FileList result = this.service.files().list()
                    .setQ("name contains 'indexingtest' and mimeType != 'application/vnd.google-apps.folder'")
                    .setSpaces("drive")
                    //.setFields("nextPageToken, items(id, title)")
                    .setPageToken(pageToken)
                    .execute();
            System.out.println(result.getFiles().size());
            for (File file : result.getFiles()) {
                System.out.printf("Found file: %s (%s) (%s)\n", file.getName(), file.getId(), file.getFileExtension());
            }

            files.addAll(result.getFiles());

            pageToken = result.getNextPageToken();
        } while (pageToken != null);

        return files;
    }
}
