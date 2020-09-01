package com.zrv.newspage.dao;

import com.zrv.newspage.domain.Article;
import com.zrv.newspage.domain.PreviewArticle;
import com.zrv.newspage.service.DatabaseQueryService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ArticleDao implements Dao<Article> {
    DatabaseQueryService db = new DatabaseQueryService();

    @Override
    public Optional<Article> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Article> getAll() throws SQLException {
        return null;
    }

    @Override
    public void save(Article article) throws SQLException {

    }

    public void save(Set<Article> articleSet) throws SQLException {

        StringBuilder query = new StringBuilder();
        articleSet.forEach(article -> {
            query.append(
                    "INSERT INTO articles (" +
                            "id, " +
                            "description, " +
                            "news_keyword, " +
                            "image, " +
                            "article_html, " +
                            "front_url, " +
                            "title, " +
                            "photo, " +
                            "project, " +
                            "category, " +
                            "opinion_authors, " +
                            "anons, " +
                            "publish_date, " +
                            "parsed_date) VALUES ("
            );
            query.append(article.toQueryString());
            query.append("); \n");
        });

        System.out.println(query.toString());
    }

    @Override
    public void update(Article article, String[] params) {

    }

    @Override
    public void delete(Article a) {

    }
}
