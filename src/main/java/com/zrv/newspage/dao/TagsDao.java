package com.zrv.newspage.dao;

import com.zrv.newspage.domain.Tags;
import com.zrv.newspage.service.DatabaseConnectionService;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class TagsDao implements Dao<Tags> {

    private static final DatabaseConnectionService dbcs = DatabaseConnectionService.getInstance();
    private static TagsDao instance;
    // TODO настроить адекватные события для логера

    private TagsDao() {

    }

    public static TagsDao getInstance() {

        if (instance == null) {
            instance = new TagsDao();
        }
        return instance;
    }

    @Override
    public Optional<Tags> get(String id) {
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
    // TODO переписать на препейред стейтмент
    public Map<String, Integer> getTagsMap() throws SQLException {

        String query = "with elements (element) as (" +
                "select unnest(news_keywords) " +
                "from articles) " +
                "select *, count(*) " +
                "from elements " +
                "group by element " +
                "order by count(*) desc " +
                "limit 40";
        ResultSet rs = dbcs.getConnection().prepareStatement(query).executeQuery();

        Map<String, Integer> tagsMap = new LinkedHashMap<>();

        while (rs.next()) {
            tagsMap.put(rs.getString("element"), rs.getInt("count"));
        }

        return tagsMap;
    }

    public Set<String> getSuggestions(String string) throws SQLException {

        Set<String> suggestions = new HashSet<>();

        String query = "SELECT unnest FROM (SELECT unnest(news_keywords) FROM articles" +
                " GROUP BY id) foo WHERE lower(unnest) LIKE lower(?) LIMIT 10";

        PreparedStatement preparedStatement = dbcs.getConnection().prepareStatement(query);
        preparedStatement.setString(1, string + "%");
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()) {
            suggestions.add(resultSet.getString(1));
        }

        return suggestions;
    }
}
