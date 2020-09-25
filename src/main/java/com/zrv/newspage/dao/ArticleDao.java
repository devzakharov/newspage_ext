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
        String query = "SELECT * FROM articles WHERE id LIKE ? LIMIT 1";
        PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
        preparedStatement.setString(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        if (!rs.next()) {
            db.closeConnection();
            return Optional.empty();
        } else {
            db.closeConnection();
            return Optional.of(createArticle(rs));
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

        while (rs.next()) {
            filteredArticleList.add(createArticle(rs));
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

        List<Article> articleList = new LinkedList<>();

        String query = "SELECT *, t FROM ( SELECT *, convert_from(decode(article_html, 'base64'), 'UTF-8') as t " +
                " FROM articles) foo " +
                " WHERE t LIKE ALL (?)";

        DatabaseConnectionService db = new DatabaseConnectionService();
        PreparedStatement preparedStatement = db.getConnection().prepareStatement(query);
        Array array = db.getConnection().createArrayOf("VARCHAR", convertSearchQueryStringToArray(searchQuery));
        preparedStatement.setArray(1, array);
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            articleList.add(createArticle(rs));
        }

        db.closeConnection();
        return articleList;
    }

    private Article createArticle(ResultSet rs) throws SQLException {

        Article article = new Article();
        RawPhoto currentRawPhoto = new RawPhoto();

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

        return article;
    }

    private String[] convertSearchQueryStringToArray(String searchQuery) {

        String[] searchQueryArray = searchQuery.split(" ");

        for (int i = 0; i < searchQueryArray.length; i++) {
            searchQueryArray[i] = "%" + searchQueryArray[i].toLowerCase().substring(1) + "%";
        }

        return searchQueryArray;
    }
}
