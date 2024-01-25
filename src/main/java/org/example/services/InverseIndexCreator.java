package org.example.services;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface InverseIndexCreator {

    void createInverseIndex() throws IOException, GeneralSecurityException, TikaException, SAXException;
}
