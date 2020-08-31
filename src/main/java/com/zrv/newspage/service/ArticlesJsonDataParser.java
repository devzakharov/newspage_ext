package com.zrv.newspage.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.RawArticle;
import com.zrv.newspage.domain.RawArticles;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class ArticlesJsonDataParser {

    private static ArticlesJsonDataParser instance;
    final static int limitPerRequest = 100; // Ограничение api
    int offset = 0; //стартовый сдвиг
    int articlesCountLimit = 100;
    Set<RawArticle> rawArticleSet = new HashSet<>();

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

        while (rawArticleSet.size() < articlesCountLimit) {
            RawArticles addedRawArticles = mapper.readValue(getRequestUrl(), RawArticles.class);
            rawArticleSet.addAll(addedRawArticles.getRawArticleSet());
            offset += limitPerRequest;
        }

    }

    public Set<RawArticle> getDataObject() {

        return rawArticleSet;
    }

    private URL getRequestUrl() throws MalformedURLException {

        return new URL(String.format("https://www.rbc.ru/v10/search/ajax/?offset=%d&limit=%d", offset, limitPerRequest));
    }
}