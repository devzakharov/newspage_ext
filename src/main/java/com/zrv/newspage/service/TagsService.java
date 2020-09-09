package com.zrv.newspage.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.dao.TagsDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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

    public String getTagsJson () throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(getTagsMap());
    }
}
