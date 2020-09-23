package com.zrv.newspage.dao;
import com.zrv.newspage.domain.Article;
import com.zrv.newspage.domain.RawPhoto;
import com.zrv.newspage.service.DatabaseConnectionService;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.*;


public class ArticleDao implements Dao<Article> {

    // TODO настроить адекватные события для логера
    final static Logger logger = Logger.getLogger(ArticleDao.class);

    @Override
    public Optional<Article> get(String id) throws SQLException {
        DatabaseConnectionService db = new DatabaseConnectionService();

        // TODO переписать на PreparedStatement https://metanit.com/java/database/2.6.php
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM articles ");
        query.append("WHERE id LIKE '").append(id).append("'");
        query.append(" LIMIT 1");
        ResultSet rs = db.getConnection().createStatement().executeQuery(query.toString());
        db.closeConnection();
        if (!rs.next()) {
            return Optional.empty();
        } else {

            // TODO Вынести в метод заполнение объекта
            Article article = new Article();
            RawPhoto currentRawPhoto = new RawPhoto();

            currentRawPhoto.setUrl(rs.getString("photo"));

            article.setId(rs.getString("id"));
            article.setDescription(rs.getString("description"));
            article.setNewsKeywords(rs.getString("news_keywords"));
            article.setImage(rs.getString("image"));
            article.setArticleHtml(rs.getString("article_html"));
            article.setFrontUrl(rs.getString("front_url"));
            article.setTitle(rs.getString("title"));
            article.setPhoto(currentRawPhoto);
            article.setProject(rs.getString("project"));
            article.setCategory(rs.getString("category"));
            article.setOpinionAuthors(rs.getString("opinion_authors"));
            article.setAnons(rs.getString("anons"));
            article.setPublishDate(rs.getTimestamp("publish_date"));
            article.setParsedDate(rs.getTimestamp("parsed_date"));

            return Optional.of(article);

        }

    }

    @Override
    public List<Article> getAll() throws SQLException {
        return null;
    }

    public List<Article> getAllFiltered(
            Integer limit, Integer offset, String[] tagsArray, String from, String to
    ) throws SQLException {

        List<Article> filteredArticleList = new LinkedList<>();
        // TODO переписать на PreparedStatement https://metanit.com/java/database/2.6.php
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM articles ");
        if (!tagsArray[0].equals("")) {
            query.append("WHERE news_keywords @> '{");
            query.append(String.join(",", tagsArray));
            query.append("}'");
            if(!from.equals("undefined")||!to.equals("undefined")) {
                query.append(" AND ");
            }
        } else if(!from.equals("undefined")&&!to.equals("undefined")) {
            query.append(" WHERE ");
        }
        if (!from.equals("undefined")&&!to.equals("undefined")) {
            query.append(" publish_date BETWEEN '").append(Timestamp.valueOf(from + " 00:00:00")).append("'::timestamp");
            query.append(" AND '").append(Timestamp.valueOf(to+ " 00:00:00")).append("'::timestamp");
        }
        query.append(" ORDER BY publish_date DESC ");
        query.append(" LIMIT ").append(limit);
        query.append(" OFFSET ").append(offset).append(";");

        System.out.println(query);

        DatabaseConnectionService db = new DatabaseConnectionService();

        ResultSet rs = db.getConnection().createStatement().executeQuery(query.toString());
        db.closeConnection();

        // TODO Вынести в метод заполнение объекта
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

    // TODO разделить на методы составление запроса, составление листа запросов
    public void save(Set<Article> articleSet) throws SQLException {

        Iterator<Article> iterator = articleSet.iterator();
        List<String> queryList = new LinkedList<>();

        // TODO переписать на PreparedStatement https://metanit.com/java/database/2.6.php
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
        DatabaseConnectionService db = new DatabaseConnectionService();

        queryList.forEach(queryListItem -> {
            try {
                db.getConnection().prepareStatement(queryListItem).executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
                logger.warn("Проблема с: " + queryListItem);
                logger.warn(e.getMessage());
            }
        });
        db.closeConnection();

    }

    @Override
    public void update(Article article, String[] params) {

    }

    @Override
    public void delete(Article a) {

    }

    public List<Article> getSearchResult(String searchQuery) throws SQLException {
        String[] searchQueryArray = searchQuery.split(" ");
        String[] searchQueryArrayPrepared = new String[searchQueryArray.length];
        List<Article> articleList = new LinkedList<>();

        for (int i = 0; i < searchQueryArray.length; i++) {
            searchQueryArray[i] = "%" + searchQueryArray[i] + "%";
        }

//        String query = "SELECT *, t FROM ( SELECT *, convert_from(decode(article_html, 'base64'), 'UTF-8') as t " +
//                " FROM articles) foo " +
//                " WHERE t LIKE ALL (array['%Наваль%', '%Владимир%']);";
        String query = "SELECT *, t FROM ( SELECT *, convert_from(decode(article_html, 'base64'), 'UTF-8') as t " +
                " FROM articles) foo " +
                " WHERE t LIKE ALL (?)";

        DatabaseConnectionService db = new DatabaseConnectionService();

        PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);

        Array array = db.getConnection().createArrayOf("VARCHAR", searchQueryArray);

        preparedStatement.setArray(1, array);

        ResultSet rs = preparedStatement.executeQuery();

        db.closeConnection();

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

            articleList.add(currentArticle);
        }

        return articleList;
    }
}
