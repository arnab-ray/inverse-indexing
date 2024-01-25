package org.example.services.impl;

import org.example.repository.IndexRepository;
import org.example.services.FileIndexer;

import java.util.List;

public class FileIndexerImpl implements FileIndexer {
    private final IndexRepository indexRepository;

    public FileIndexerImpl(IndexRepository indexRepository) {
        this.indexRepository = indexRepository;
    }

    @Override
    public void createIndex(String fileId, List<String> contents) {
        for (String term : contents) {
            indexRepository.updateIndex(term.toLowerCase(), fileId);
        }
    }
}
