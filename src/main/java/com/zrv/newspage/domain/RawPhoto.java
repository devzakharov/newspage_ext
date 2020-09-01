package com.zrv.newspage.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

class RawPhoto {

    @JsonProperty("url")
    private String url;

    public Object getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{" + "url='" + url + '\'' + '}';
    }
}