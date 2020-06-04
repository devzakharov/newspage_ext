package com.zrv.newspage.domain;

import java.util.Date;

public class News {

    private int id;
    private String url;
    private String header;
    private String content;
    private String author;
    private Date datePublished;
    private final Date dateParsed;

    public News(int id, String url, String header, String content, String author, Date datePublished) {
        this.id = id;
        this.url = url;
        this.header = header;
        this.content = content;
        this.author = author;
        this.datePublished = datePublished;
        this.dateParsed = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public Date getDateParsed() {
        return dateParsed;
    }
}
