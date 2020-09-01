package com.zrv.newspage.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.PreviewArticle;
import com.zrv.newspage.domain.PreviewArticles;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticlesJsonDataParser {

    private static ArticlesJsonDataParser instance;
    final static int limitPerRequest = 10; // Ограничение api - 100
    int offset = 0; //стартовый сдвиг
    int articlesCountLimit = 10;
    Set<PreviewArticle> previewArticleSet = new HashSet<>();

    private ArticlesJsonDataParser() throws IOException {

    }

    public static ArticlesJsonDataParser getInstance() throws IOException {

        if (instance == null) {
            instance = new ArticlesJsonDataParser();
        }
        return instance;
    }

    public void fillDataObject() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        while (previewArticleSet.size() < articlesCountLimit) {
            PreviewArticles addedPreviewArticles = mapper.readValue(getRequestUrl(), PreviewArticles.class);
            previewArticleSet.addAll(addedPreviewArticles.getRawArticleSet());
            offset += limitPerRequest;
        }

    }

    public Map<String, PreviewArticle> getDataObject() {
        Map<String, PreviewArticle> map = previewArticleSet.stream().collect(Collectors.toMap(PreviewArticle::getId, e -> e));
        return map;
    }

    private URL getRequestUrl() throws MalformedURLException {

        return new URL(String.format("https://www.rbc.ru/v10/search/ajax/?offset=%d&limit=%d", offset, limitPerRequest));
    }
}