package org.example.resources;

import com.codahale.metrics.annotation.Timed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.apache.tika.exception.TikaException;
import org.example.models.SearchResponse;
import org.example.services.InverseIndexCreator;
import org.example.services.TermSearchService;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Path("/search")
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {

    private final InverseIndexCreator inverseIndexCreator;
    private final TermSearchService termSearchService;

    public SearchResource(InverseIndexCreator inverseIndexCreator, TermSearchService termSearchService) {
        this.inverseIndexCreator = inverseIndexCreator;
        this.termSearchService = termSearchService;
    }

    @GET
    @Timed
    public SearchResponse getDocuments(@QueryParam("q") String query) {
        List<String> fileIds = termSearchService.searchFiles(query);
        return new SearchResponse(query, fileIds);
    }

    @POST
    @Timed
    @Path("/index")
    public void buildIndex() {
        try {
            inverseIndexCreator.createInverseIndex();
        } catch (IOException | GeneralSecurityException | TikaException | SAXException ioException) {
            System.out.println("Failed to create inverse index " + ioException.getMessage());
        }
    }
}
