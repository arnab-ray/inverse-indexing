package org.example.services.impl;

import com.google.api.services.drive.model.File;
import org.apache.tika.exception.TikaException;
import org.example.services.*;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class InverseIndexCreatorImpl implements InverseIndexCreator {

    private final SearchFileService searchFileService;
    private final FileIndexer fileIndexer;
    private final FileParsingServiceFactory fileParsingServiceFactory;

    public InverseIndexCreatorImpl(
            SearchFileService searchFileService,
            FileIndexer fileIndexer,
            FileParsingServiceFactory fileParsingServiceFactory
    ) {
        this.searchFileService = searchFileService;
        this.fileIndexer = fileIndexer;
        this.fileParsingServiceFactory = fileParsingServiceFactory;
    }

    @Override
    public void createInverseIndex() throws IOException, GeneralSecurityException, TikaException, SAXException {
        List<File> files = searchFileService.searchFile();
        for (File file : files) {
            List<String> info = fileParsingServiceFactory.getFileParsingService(file).getContents(file);
            for (String str : info) {
                System.out.println(str);
            }
            fileIndexer.createIndex(file.getName(), info);
        }
    }
}
