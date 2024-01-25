package org.example.repository;

import java.util.Set;

public interface IndexRepository {
    void updateIndex(String term, String fileId);

    Set<String> getFiles(String term);
}
