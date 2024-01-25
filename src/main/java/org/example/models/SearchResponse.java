package org.example.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResponse {
    private final String searchQuery;
    private final List<String> documents;

    public SearchResponse(String searchQuery, List<String> documents) {
        this.searchQuery = searchQuery;
        this.documents = documents;
    }
}
