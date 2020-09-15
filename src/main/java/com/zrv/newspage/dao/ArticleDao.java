package com.zrv.newspage.dao;

import com.zrv.newspage.domain.Article;
import com.zrv.newspage.domain.RawPhoto;
import com.zrv.newspage.service.DatabaseConnectionService;
import org.apache.log4j.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class ArticleDao implements Dao<Article> {

    final static Logger logger = Logger.getLogger(ArticleDao.class);

    DatabaseConnectionService db = new DatabaseConnectionService();

    @Override
    public Optional<Article> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Article> getAll() throws SQLException {
        return null;
    }

    public List<Article> getAllFiltered(Integer limit, Integer offset, String[] tagsArray) throws SQLException {

        List<Article> filteredArticleList = new LinkedList<>();

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM articles ");
        if (!tagsArray[0].equals("")) {
            query.append("WHERE news_keywords @> '{");
            query.append(String.join(",", tagsArray));
            query.append("}'");
        }
        query.append(" ORDER BY publish_date DESC ");
        query.append(" LIMIT ").append(limit);
        query.append(" OFFSET ").append(offset).append(";");

        System.out.println(query);

        ResultSet rs = db.getConnection().createStatement().executeQuery(query.toString());

        while (rs.next()) {

            Article currentArticle = new Article();
            RawPhoto currentRawPhoto = new RawPhoto();

            currentRawPhoto.setUrl(rs.getString("photo"));

            currentArticle.setId(rs.getString("id"));
            currentArticle.setDescription(rs.getString("description"));
            currentArticle.setNewsKeywords(rs.getString("news_keywords"));
            currentArticle.setImage(rs.getString("image"));
            currentArticle.setArticleHtml(rs.getString("article_html"));
            currentArticle.setFrontUrl(rs.getString("front_url"));
            currentArticle.setTitle(rs.getString("title"));
            currentArticle.setPhoto(currentRawPhoto);
            currentArticle.setProject(rs.getString("project"));
            currentArticle.setCategory(rs.getString("category"));
            currentArticle.setOpinionAuthors(rs.getString("opinion_authors"));
            currentArticle.setAnons(rs.getString("anons"));
            currentArticle.setPublishDate(rs.getTimestamp("publish_date"));
            currentArticle.setParsedDate(rs.getTimestamp("parsed_date"));

            filteredArticleList.add(currentArticle);
        }

        System.out.println(filteredArticleList.size());
        return filteredArticleList;

    }

    @Override
    public void save(Article article) throws SQLException {

    }

    public void save(Set<Article> articleSet) throws SQLException {

        Iterator<Article> iterator = articleSet.iterator();
        List<String> queryList = new LinkedList<>();

        StringBuilder query = new StringBuilder();
        query.append(
                "INSERT INTO articles (" +
                        "id, " +
                        "description, " +
                        "news_keywords, " +
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
                        "parsed_date) VALUES "
        );


        int queryStringCounter = 0;
        while (iterator.hasNext()) {

            Article article = iterator.next();

            if (queryStringCounter < 4) {
                query.append(article.toQueryString());
                query.append(",");
                queryStringCounter++;
           } else {
                query.append(article.toQueryString());
                query.append(" ON CONFLICT DO NOTHING;");
                queryList.add(query.toString());
                queryStringCounter = 0;
                query = new StringBuilder();
                query.append(
                        "INSERT INTO articles (" +
                                "id, " +
                                "description, " +
                                "news_keywords, " +
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
                                "parsed_date) VALUES "
                );
            }

        }

        queryList.forEach(queryListItem -> {
            try {
                db.getConnection().prepareStatement(queryListItem).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.warn("Проблема с: " + queryListItem);
                logger.warn(e.getMessage());
            }
        });

    }

    @Override
    public void update(Article article, String[] params) {

    }

    @Override
    public void delete(Article a) {

    }
}
