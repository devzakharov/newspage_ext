package com.zrv.newspage.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArticlesUriParser {
    private static ArticlesUriParser instance;

    List<String> uriList = new ArrayList();
    final static int limitPerRequest = 100; // Ограничение api
    int offset = 0; //стартовый сдвиг
    // URL requestUrl = new URL(String.format("https://www.rbc.ru/v10/ajax/get-news-by-filters/?offset=%d&limit=%d", offset, limitPerRequest));
    URL requestUrl = new URL(String.format("https://www.rbc.ru/v10/search/ajax/?offset=%d&limit=%d", offset, limitPerRequest));

    private ArticlesUriParser() throws URISyntaxException, MalformedURLException {

    }

    public static ArticlesUriParser getInstance() throws URISyntaxException, MalformedURLException {
        if (instance == null) {
            instance = new ArticlesUriParser();
        }
        return instance;
    }

    public URL getRequestUrl() {
        return requestUrl;
    }

    public JsonNode getJsonObject() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode data;
        data = mapper.readValue(requestUrl, JsonNode.class);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        JsonNode nodeItems = data.get("items");


        Set<Dataset> datasets = new HashSet<>();

        System.out.println(datasets.size());

        while (datasets.size() < 100) {
            Datasets items = mapper.readValue(requestUrl, Datasets.class);
            datasets.addAll(items.getDatasets());
            this.offset = this.offset + limitPerRequest;
            System.out.println(this.offset);
        }

        //System.out.println(datasets);


        // datasets.forEach((article) -> System.out.println(article.getId() + " " + article.getFrontUrl()));

        return nodeItems;
    }

    private void packToList() {

    }
}

class Datasets {

    private HashSet<Dataset> datasets;

    @JsonProperty("items")
    public HashSet<Dataset> getDatasets() {
        return datasets;
    }
}

class Photo {

    @JsonProperty("url")
    private String url;


    public Object getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

class Dataset {

    @JsonProperty("id")
    private String id;

    @JsonProperty("fronturl")
    private String frontUrl;

    @JsonProperty("publish_date")
    private String publishDate;

    @JsonProperty("title")
    private String title;

    @JsonProperty("photo")
    private Photo photo;

    @JsonProperty("project")
    private String project;

    @JsonProperty("category")
    private String category;

    @JsonProperty("opinion_authors")
    private String opinionAuthors;

    @JsonProperty("anons")
    private String anons;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOpinionAuthors() {
        return opinionAuthors;
    }

    public void setOpinionAuthors(String opinionAuthors) {
        this.opinionAuthors = opinionAuthors;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}