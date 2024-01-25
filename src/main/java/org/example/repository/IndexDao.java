package org.example.repository;

import org.example.models.InverseIndex;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface IndexDao {

    @SqlQuery("SELECT file_id FROM term_index WHERE term = :term AND file_id = :fileId")
    List<String> getFiles(@Bind("term") String term, @Bind("fileId") String fileId);

    @SqlQuery("SELECT file_id FROM term_index WHERE term = :term")
    List<String> getFiles(@Bind("term") String term);

    @SqlUpdate("UPDATE term_index SET file_id = :fileId WHERE term = :term")
    void updateFileId(@Bind("term") String term, @Bind("fileId") String fileId);

    @SqlUpdate("INSERT INTO term_index (term, file_id) VALUES (:term, :fileId)")
    void insertFileId(@Bind("term") String term, @Bind("fileId") String fileId);

}

class InverseIndexMapper implements RowMapper<InverseIndex> {

    @Override
    public InverseIndex map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new InverseIndex(
                rs.getString("text"),
                Set.of((String[])rs.getArray("fileIds").getArray())
        );
    }
}
