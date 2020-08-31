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

public class ArticlesUriParser {

    private static ArticlesUriParser instance;
    final static int limitPerRequest = 100; // Ограничение api
    int offset = 0; //стартовый сдвиг
    URL requestUrl = new URL(String.format("https://www.rbc.ru/v10/search/ajax/?offset=%d&limit=%d", offset, limitPerRequest));
    int articlesCountLimit = 1000;
    Set<RawArticle> rawArticleSet = new HashSet<>();

    private ArticlesUriParser() throws IOException {

    }

    public static ArticlesUriParser getInstance() throws IOException {
        if (instance == null) {
            instance = new ArticlesUriParser();
        }
        return instance;
    }

    public void fillDataObject() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        while (rawArticleSet.size() < articlesCountLimit) {
            RawArticles addedRawArticles = mapper.readValue(requestUrl, RawArticles.class);
            rawArticleSet.addAll(addedRawArticles.getRawArticleSet());
            // Данила так советовал:
            // rawArticleSet.addAll(mapper.readValue(requestUrl, RawArticles.class).getRawArticleSet());
            offset += limitPerRequest;
            updateRequestUrl();
        }

    }

    public Set<RawArticle> getDataObject() {
        return rawArticleSet;
    }

    private void updateRequestUrl() throws MalformedURLException {
        requestUrl = new URL(String.format("https://www.rbc.ru/v10/search/ajax/?offset=%d&limit=%d", offset, limitPerRequest));
        System.out.println(requestUrl);
    }
}