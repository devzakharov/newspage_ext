package com.zrv.newspage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;

public class RawArticles {

    @JsonProperty("items")
    private HashSet<RawArticle> rawArticles;

    public HashSet<RawArticle> getRawArticleSet() {
        return rawArticles;
    }

    public void setRawArticles(HashSet<RawArticle> rawArticles) {
        this.rawArticles = rawArticles;
    }
}