package ru.nsu.astakhov.autodocs.integration.google;


import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GoogleSheetsService {
    private final Sheets googleSheets;

    @Value("${google.sheets.spreadsheet.id}")
    private String spreadsheetId;

    public GoogleSheetsService(Sheets googleSheets) {
        this.googleSheets = googleSheets;
    }

    public List<List<Object>> readRange(String range) throws IOException {
        ValueRange response = googleSheets.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        return response.getValues();
    }
}