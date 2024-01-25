package org.example.repository;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IndexSQLRepository implements IndexRepository {
    private IndexSQLRepository() {}

    private static class SingletonHelper {
        private static final IndexSQLRepository INSTANCE = new IndexSQLRepository();
    }

    public static IndexSQLRepository getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public void updateIndex(String term, String fileId) {
        try  {
            Jdbi dbi = Jdbi.create("jdbc:postgresql://localhost:5432/mydb", "arnabray", "");
            dbi.installPlugin(new SqlObjectPlugin());
            dbi.registerRowMapper(new InverseIndexMapper());
            Handle handle = dbi.open();
            IndexDao dao = handle.attach(IndexDao.class);
            List<String> files = dao.getFiles(term, fileId);

            if (files == null || files.isEmpty()) {
                dao.insertFileId(term, fileId);
            }
            handle.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Set<String> getFiles(String term) {
        try  {
            Jdbi dbi = Jdbi.create("jdbc:postgresql://localhost:5432/mydb", "arnabray", "");
            dbi.installPlugin(new SqlObjectPlugin());
            dbi.registerRowMapper(new InverseIndexMapper());
            Handle handle = dbi.open();
            IndexDao dao = handle.attach(IndexDao.class);
            List<String> files = dao.getFiles(term);
            handle.close();
            return new HashSet<>(files);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
