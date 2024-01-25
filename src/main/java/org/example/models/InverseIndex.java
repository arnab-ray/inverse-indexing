package org.example.models;

import java.util.HashSet;
import java.util.Set;

public class InverseIndex {
    private final String term;
    private final Set<String> fileIds = new HashSet<>();

    public InverseIndex(String term) {
        this.term = term;
    }

    public InverseIndex(String term, Set<String> fileIds) {
        this.term = term;
        addFileId(fileIds);
    }

    public synchronized void addFileId(String fileId) {
        fileIds.add(fileId);
    }

    public synchronized void addFileId(Set<String> fileIds) {
        fileIds.addAll(fileIds);
    }

    public Set<String> getFileIds() {
        return this.fileIds;
    }
}
