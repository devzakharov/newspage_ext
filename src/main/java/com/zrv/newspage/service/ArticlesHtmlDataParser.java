package com.zrv.newspage.service;

import com.zrv.newspage.domain.FullArticle;
import com.zrv.newspage.domain.RawArticle;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ArticlesHtmlDataParser {
    Set<RawArticle> rawArticleSet;
    Set<FullArticle> fullArticleSet = new HashSet<>();

    public ArticlesHtmlDataParser(Set<RawArticle> rawArticleSet) throws IOException {

        this.rawArticleSet = rawArticleSet;
    }

    public void fillDataObject() {

        rawArticleSet.forEach(rawArticle -> {
            //System.out.println(rawArticle.getFrontUrl());
            try {
                linksHandler(rawArticle.getFrontUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void linksHandler(String link) throws IOException {
        FullArticle article = new FullArticle();
        Document doc = Jsoup.connect(link).get();
        System.out.println("Data id: " + doc.select(".js-rbcslider-slide").attr("data-id"));
        article.setId(doc.select(".js-rbcslider-slide").attr("data-id"));
        this.fullArticleSet.add(article);
    }
}
