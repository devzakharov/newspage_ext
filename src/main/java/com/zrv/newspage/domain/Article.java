package com.zrv.newspage.domain;

import java.sql.Timestamp;
import java.util.Base64;

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
    private Timestamp publishDate;
    private final Timestamp parsedDate = new java.sql.Timestamp(new java.util.Date().getTime());

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

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public Timestamp getParsedDate() {
        return parsedDate;
    }

    public String toQueryString() {
        return  "('" + id + "', " +
                "'" + Base64.getEncoder().encodeToString(description.getBytes()) + "', " +
                "'" + newsKeywords + "', " +
                "'" + image + "', " +
                "'" + Base64.getEncoder().encodeToString(articleHtml.getBytes()) + "', " +
                "'" + frontUrl + "', " +
                "'" + Base64.getEncoder().encodeToString(title.getBytes()) + "', " +
                "'" + photo + "', " +
                "'" + project + "', " +
                "'" + category + "', " +
                "'" + opinionAuthors + "', " +
                "'" + Base64.getEncoder().encodeToString(anons.getBytes()) + "', " +
                "'" + publishDate + "', " +
                "'" + parsedDate + "')";
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", newsKeywords='" + newsKeywords + '\'' +
                ", image='" + image + '\'' +
                ", articleHtml='" + articleHtml + '\'' +
                ", frontUrl='" + frontUrl + '\'' +
                ", title='" + title + '\'' +
                ", photo=" + photo +
                ", project='" + project + '\'' +
                ", category='" + category + '\'' +
                ", opinionAuthors='" + opinionAuthors + '\'' +
                ", anons='" + anons + '\'' +
                ", publishDate=" + publishDate +
                ", parsedDate=" + parsedDate +
                '}';
    }
}
