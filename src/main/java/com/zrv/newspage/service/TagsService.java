package com.zrv.newspage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.dao.TagsDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

public class TagsService {

    private static final TagsDao tagsDao = TagsDao.getInstance();
    private static TagsService instance;

    private TagsService() {

    }

    public static TagsService getInstance() {

        if (instance == null) {
            instance = new TagsService();
        }
        return instance;
    }

    public Map<String, Integer> getTagsMap() {

        Map<String, Integer> tagsMap = new LinkedHashMap<>();

        try {
            tagsMap = tagsDao.getTagsMap();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tagsMap;
    }

    public String getFilteredTagsJson () throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getFilteredTagsMap());
    }

    public Map<String, Integer> getFilteredTagsMap() {

        return getTagsMap();
    }

    public <K, V extends Comparable<V>> V getMaxTagValue(Map<K, V> map) {

        Optional<Map.Entry<K, V>> maxEntry = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        return maxEntry.get().getValue();
    }
}
