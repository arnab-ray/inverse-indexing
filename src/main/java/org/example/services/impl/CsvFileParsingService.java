package org.example.services.impl;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.example.auth.DriveService;
import org.example.services.FileParsingService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvFileParsingService implements FileParsingService {
    private CsvFileParsingService() {

    }

    private static class SingletonHelper {
        private static final CsvFileParsingService INSTANCE = new CsvFileParsingService();
    }

    public static CsvFileParsingService getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public List<String> getContents(File file) throws GeneralSecurityException, IOException {
        Drive drive = DriveService.getDrive();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
        InputStream in = new ByteArrayInputStream(outputStream.toByteArray());

        String content = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));

        List<String> result = new LinkedList<>();
        String regex = "[a-zA-Z]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            result.add(matcher.group());
        }

        return result;
    }
}
