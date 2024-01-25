package org.example.auth;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.example.auth.CredentialService.JSON_FACTORY;
import static org.example.auth.CredentialService.getCredentials;
import static org.glassfish.jersey.server.ServerProperties.APPLICATION_NAME;

public class DriveService {

    public static Drive getDrive() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
