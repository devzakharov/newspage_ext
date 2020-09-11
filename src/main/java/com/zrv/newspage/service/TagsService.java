package com.zrv.newspage.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.dao.TagsDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TagsService {

    TagsDao tagsDao = new TagsDao();

    public Map<String, Integer> getTagsMap() {
        Map<String, Integer> tagsMap = new HashMap<>();
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

        Map<String, Integer> tagsMap = getTagsMap();
        Map<String, Integer> filteredTagsMap = new HashMap<>();

        int maxTagValue = getMaxTagValue(tagsMap);

        tagsMap.forEach((k,v) -> {
            if (v == maxTagValue) {
                filteredTagsMap.put(k, 5);
            } else if (v > maxTagValue / 2) {
                filteredTagsMap.put(k, 4);
            } else if (v == maxTagValue / 2) {
                filteredTagsMap.put(k, 3);
            } else if (v < maxTagValue / 2) {
                filteredTagsMap.put(k, 3);
            }
        });

        return filteredTagsMap;
    }

    public <K, V extends Comparable<V>> V getMaxTagValue(Map<K, V> map) {
        Optional<Map.Entry<K, V>> maxEntry = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        return maxEntry.get().getValue();
    }
}
