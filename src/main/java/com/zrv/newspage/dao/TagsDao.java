package com.zrv.newspage.dao;

import com.zrv.newspage.domain.Tags;
import com.zrv.newspage.service.DatabaseConnectionService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TagsDao implements Dao<Tags> {

    DatabaseConnectionService db = new DatabaseConnectionService();

    @Override
    public Optional<Tags> get(long id) {
        // not a usable method
        return Optional.empty();
    }

    @Override
    public List<Tags> getAll() throws SQLException {
        // not a usable method
        return null;
    }

    @Override
    public void save(Tags tags) throws SQLException {
        // not a usable method
    }

    @Override
    public void update(Tags tags, String[] params) {
        // not a usable method
    }

    @Override
    public void delete(Tags tags) {
        // not a usable method
    }

    public Map<String, Integer> getTagsMap() throws SQLException {

        String query = "with elements (element) as (" +
                "select unnest(news_keywords) " +
                "from articles) " +
                "select *, count(*) " +
                "from elements " +
                "group by element " +
                "order by count(*) desc " +
                "limit 40";
        ResultSet rs = db.getConnection().createStatement().executeQuery(query);
        Map<String, Integer> tagMap = new HashMap<>();
        while (rs.next()) {
            tagMap.put(rs.getString("element"), rs.getInt("count"));
        }
        return tagMap;
    }
}
