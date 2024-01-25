package org.example.services;

import com.google.api.services.drive.model.File;
import jakarta.validation.constraints.NotNull;
import org.example.exceptions.InvalidFileTypeException;
import org.example.services.impl.CsvFileParsingService;
import org.example.services.impl.PdfFileParsingService;
import org.example.services.impl.TextFileParsingService;

public class FileParsingServiceFactory {

    public FileParsingService getFileParsingService(@NotNull File file) {
        System.out.println(file);
        if (file.getName().contains("txt")) {
            return TextFileParsingService.getInstance();
        } else if (file.getName().contains("csv")) {
            return CsvFileParsingService.getInstance();
        } else if (file.getName().contains("pdf")) {
            return PdfFileParsingService.getInstance();
        } else {
            throw new InvalidFileTypeException("Unknown file type");
        }
    }
}
