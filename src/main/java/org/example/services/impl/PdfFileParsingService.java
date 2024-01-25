package org.example.services.impl;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.example.auth.DriveService;
import org.example.services.FileParsingService;
import org.xml.sax.SAXException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PdfFileParsingService implements FileParsingService {
    private PdfFileParsingService() {}

    private static class SingletonHelper {
        private static final PdfFileParsingService INSTANCE = new PdfFileParsingService();
    }

    public static PdfFileParsingService getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public List<String> getContents(File file) throws GeneralSecurityException, IOException, TikaException, SAXException {
        Drive drive = DriveService.getDrive();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
        InputStream input = new ByteArrayInputStream(outputStream.toByteArray());

        BodyContentHandler handler = new BodyContentHandler(-1);
        Metadata metadata = new Metadata();
        Parser parser = new AutoDetectParser();
        parser.parse(input, handler, metadata, new ParseContext());

        String content = handler.toString();

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
