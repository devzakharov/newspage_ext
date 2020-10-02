package com.zrv.newspage.service;

import com.zrv.newspage.dao.ArticleDao;
import com.zrv.newspage.domain.Article;
import com.zrv.newspage.domain.PreviewArticle;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArticlesHtmlDataParser {

    private static ArticlesHtmlDataParser instance;

    private static final ArticleDao articleDao = ArticleDao.getInstance();
    private static final Logger logger = Logger.getLogger(ArticlesHtmlDataParser.class);

    // private final Map<String, PreviewArticle> previewArticleMap;
    private final Set<Article> articleSet = new HashSet<>();

    private ArticlesHtmlDataParser() {

    }

    public static ArticlesHtmlDataParser getInstance() {

        if (instance == null) {
            instance = new ArticlesHtmlDataParser();
        }
        return instance;
    }

    // TODO add concurrency multithreading
    public void fillDataObject(Map<String, PreviewArticle> previewArticleMap) {

        previewArticleMap.forEach((previewArticleId, previewArticle) -> {
            try {
                parseLink(previewArticle);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void parseLink(PreviewArticle previewArticle) throws IOException {

        Article article = new Article();
        Document doc = Jsoup.connect(previewArticle.getFrontUrl()).get();

        article.setId(doc.select(".js-rbcslider-slide").attr("data-id"));
        article.setDescription(doc.select("div.js-rbcslider > div > meta").attr("content"));
        article.setNewsKeywords(doc.select("div.js-rbcslider > div > meta:nth-child(2)").attr("content"));
        article.setImage(doc.select("div.js-rbcslider > div > link:nth-child(7)").attr("href"));
        article.setArticleHtml(doc.select(".article__text").html());

        article.setFrontUrl(previewArticle.getFrontUrl());
        article.setTitle(previewArticle.getTitle());
        article.setPhoto(previewArticle.getPhoto());
        article.setProject(previewArticle.getProject());
        article.setCategory(previewArticle.getCategory());
        article.setOpinionAuthors(previewArticle.getOpinionAuthors());
        article.setAnons(previewArticle.getAnons());
        article.setPublishDate(previewArticle.getPublishDate());

        this.articleSet.add(article);
    }

    public Set<Article> getArticleSet() {

        return articleSet;
    }

    public void writeArticlesToDb () throws SQLException {

        articleDao.save(this.articleSet);
    }
}

