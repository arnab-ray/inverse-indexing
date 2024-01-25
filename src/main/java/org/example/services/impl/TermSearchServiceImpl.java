package org.example.services.impl;

import org.example.repository.IndexRepository;
import org.example.services.TermSearchService;

import java.util.ArrayList;
import java.util.List;

public class TermSearchServiceImpl implements TermSearchService {

    private final IndexRepository indexRepository;

    public TermSearchServiceImpl(IndexRepository indexRepository) {
        this.indexRepository = indexRepository;
    }

    @Override
    public List<String> searchFiles(String term) {
        return new ArrayList<>(indexRepository.getFiles(term.toLowerCase()));
    }
}
