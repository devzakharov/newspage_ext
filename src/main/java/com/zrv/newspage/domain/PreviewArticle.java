package com.zrv.newspage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class PreviewArticle {

    @JsonProperty("id")
    private String id;

    @JsonProperty("fronturl")
    private String frontUrl;

    @JsonProperty("publish_date")
    private Timestamp publishDate;

    @JsonProperty("title")
    private String title;

    @JsonProperty("photo")
    private RawPhoto photo;

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

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
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

    public RawPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(RawPhoto photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "PreviewArticle{" +
                "id='" + id + '\'' +
                ", frontUrl='" + frontUrl + '\'' +
                ", publishDate=" + publishDate +
                ", title='" + title + '\'' +
                ", photo=" + photo +
                ", project='" + project + '\'' +
                ", category='" + category + '\'' +
                ", opinionAuthors='" + opinionAuthors + '\'' +
                ", anons='" + anons + '\'' +
                '}';
    }
}