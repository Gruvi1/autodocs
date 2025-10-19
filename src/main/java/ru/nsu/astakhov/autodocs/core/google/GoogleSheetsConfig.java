package ru.nsu.astakhov.autodocs.core.google;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class GoogleSheetsConfig {
    @Value("${google.sheets.credential.path}")
    private String credentialsPath;

    @Value("${google.sheets.application.name}")
    private String applicationName;

    @Bean
    public Sheets googleSheets() throws GeneralSecurityException, IOException {
        GoogleCredentials credentials = ServiceAccountCredentials
                .fromStream(new FileInputStream(credentialsPath))
                .createScoped(List.of(SheetsScopes.SPREADSHEETS));

        HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                                GsonFactory.getDefaultInstance(),
                                requestInitializer)
                .setApplicationName(applicationName)
                .build();
    }
}
