package com.zrv.newspage.domain;

import java.util.Date;

public class Article {

    private String id;
    private String description;
    private String newsKeywords;
    private String image;
    private String articleHtml; //
    private String frontUrl;
    private String title;
    private RawPhoto photo;
    private String project;
    private String category;
    private String opinionAuthors;
    private String anons;
    private Date publishDate;
    private final Date parsedDate = new Date();

    public Article() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNewsKeywords() {
        return newsKeywords;
    }

    public void setNewsKeywords(String newsKeywords) {
        this.newsKeywords = newsKeywords;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getArticleHtml() {
        return articleHtml;
    }

    public void setArticleHtml(String articleHtml) {
        this.articleHtml = articleHtml;
    }

    public String getFrontUrl() {
        return frontUrl;
    }

    public void setFrontUrl(String frontUrl) {
        this.frontUrl = frontUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RawPhoto getPhoto() {
        return photo;
    }

    public void setPhoto(RawPhoto photo) {
        this.photo = photo;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Date getParsedDate() {
        return parsedDate;
    }

    public String toQueryString() {
        return  "\"" + id + "\", " +
                "\"" + description + "\", " +
                "\"" + newsKeywords + "\", " +
                "\"" + image + "\", " +
                "\"" + articleHtml + "\", " +
                "\"" + frontUrl + "\", " +
                "\"" + title + "\", " +
                "\"" + photo + "\", " +
                "\"" + project + "\", " +
                "\"" + category + "\", " +
                "\"" + opinionAuthors + "\", " +
                "\"" + anons + "\", " +
                "\"" + publishDate + "\", " +
                "\"" + parsedDate + "\"";
    }
}
