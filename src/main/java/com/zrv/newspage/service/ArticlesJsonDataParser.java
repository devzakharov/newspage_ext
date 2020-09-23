package com.zrv.newspage.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.PreviewArticle;
import com.zrv.newspage.domain.PreviewArticles;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ArticlesJsonDataParser {

    final static Logger logger = Logger.getLogger(ArticlesJsonDataParser.class);

    private static ArticlesJsonDataParser instance;
    final static int limitPerRequest = 100; // Ограничение api - 100
    int offset = 0; //стартовый сдвиг
    int articlesCountLimit = 400;
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

        // https://www.rbc.ru/v10/search/ajax/?offset=100&limit=100&dateFrom=06.09.2020&dateTo=07.09.2020 парсинг по дате - апи дает рендж сутки
        URL link = new URL(String.format("https://www.rbc.ru/v10/search/ajax/?offset=%d&limit=%d", offset, limitPerRequest));
        logger.info("Created new link: " + link);
        return link;
    }
}