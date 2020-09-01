package com.zrv.newspage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;

public class PreviewArticles {

    @JsonProperty("items")
    private HashSet<PreviewArticle> previewArticles;

    public HashSet<PreviewArticle> getRawArticleSet() {
        return previewArticles;
    }

    public void setPreviewArticles(HashSet<PreviewArticle> previewArticles) {
        this.previewArticles = previewArticles;
    }
}