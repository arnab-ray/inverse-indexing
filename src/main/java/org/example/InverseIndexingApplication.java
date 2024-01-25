package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.example.auth.DriveService;
import org.example.repository.IndexRepository;
import org.example.repository.IndexSQLRepository;
import org.example.resources.SearchResource;
import org.example.services.FileIndexer;
import org.example.services.FileParsingServiceFactory;
import org.example.services.InverseIndexCreator;
import org.example.services.SearchFileService;
import org.example.services.impl.FileIndexerImpl;
import org.example.services.impl.InverseIndexCreatorImpl;
import org.example.services.impl.TermSearchServiceImpl;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class InverseIndexingApplication extends Application<InverseIndexingConfiguration> {
    public static void main(String[] args) throws Exception {
        new InverseIndexingApplication().run(args);
    }

    @Override
    public String getName() {
        return "search-indexing";
    }

    @Override
    public void initialize(Bootstrap<InverseIndexingConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(InverseIndexingConfiguration inverseIndexingConfiguration, Environment environment) throws Exception {
        // nothing to do
        environment
                .jersey()
                .register(
                        new SearchResource(
                                getInverseIndexCreator(),
                                new TermSearchServiceImpl(IndexSQLRepository.getInstance())
                        )
                );
    }

    private InverseIndexCreator getInverseIndexCreator() throws GeneralSecurityException, IOException {
        SearchFileService searchFileService = new SearchFileService(DriveService.getDrive());
        IndexRepository indexRepository = IndexSQLRepository.getInstance();
        FileIndexer fileIndexer = new FileIndexerImpl(indexRepository);
        FileParsingServiceFactory fileParsingServiceFactory = new FileParsingServiceFactory();
        return new InverseIndexCreatorImpl(searchFileService, fileIndexer, fileParsingServiceFactory);
    }
}
