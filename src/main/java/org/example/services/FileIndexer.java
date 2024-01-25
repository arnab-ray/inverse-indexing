package org.example.services;

import java.util.List;

public interface FileIndexer {
    void createIndex(String fileId, List<String> contents);
}
