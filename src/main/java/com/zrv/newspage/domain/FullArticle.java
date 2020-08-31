package com.zrv.newspage.domain;

public class FullArticle {
    String id;
    String description;
    String newsKeywords;
    String image;
    String articleHtml;

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
}
