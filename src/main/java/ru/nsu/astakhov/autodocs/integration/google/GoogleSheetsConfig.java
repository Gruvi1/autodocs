package ru.nsu.astakhov.autodocs.integration.google;


import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class GoogleSheetsConfig {
    private final ResourceLoader resourceLoader;

    @Value("${google.sheets.credential.path}")
    private String credentialsPath;

    @Value("${google.sheets.application.name}")
    private String applicationName;

    @Bean
    public Sheets googleSheets() throws GeneralSecurityException, IOException {
        Resource resource = resourceLoader.getResource(credentialsPath);
        try (InputStream inputStream = resource.getInputStream()) {
            GoogleCredentials credentials = ServiceAccountCredentials
                    .fromStream(inputStream)
                    .createScoped(List.of(SheetsScopes.SPREADSHEETS));

            HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

            return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                    GsonFactory.getDefaultInstance(),
                    requestInitializer)
                    .setApplicationName(applicationName)
                    .build();
        }
    }
}
