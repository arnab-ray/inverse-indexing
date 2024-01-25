package org.example.services;

import com.google.api.services.drive.model.File;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface FileParsingService {

    List<String> getContents(File file) throws GeneralSecurityException, IOException, TikaException, SAXException;
}
