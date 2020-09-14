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

    TagsDao tagsDao = new TagsDao();

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

        Map<String, Integer> tagsMap = getTagsMap();
//        Map<String, Integer> filteredTagsMap = new LinkedHashMap<>();
//
//        int maxTagValue = getMaxTagValue(tagsMap);
//
//        tagsMap.forEach((k,v) -> {
//            if (v == maxTagValue) {
//                filteredTagsMap.put(k + v, 5);
//            } else if (v > maxTagValue / 2) {
//                filteredTagsMap.put(k + v, 4);
//            } else if (v == maxTagValue / 2) {
//                filteredTagsMap.put(k + v, 3);
//            } else if (v < maxTagValue / 2) {
//                filteredTagsMap.put(k + v, 3);
//            }
//        });


        return tagsMap;
    }

    public <K, V extends Comparable<V>> V getMaxTagValue(Map<K, V> map) {
        Optional<Map.Entry<K, V>> maxEntry = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue());

        return maxEntry.get().getValue();
    }
}
