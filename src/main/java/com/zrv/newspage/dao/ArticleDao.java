package com.zrv.newspage.dao;

import com.zrv.newspage.ArticlesPageApplication;
import com.zrv.newspage.domain.Article;
import com.zrv.newspage.service.DatabaseQueryService;
import org.apache.log4j.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ArticleDao implements Dao<Article> {

    final static Logger logger = Logger.getLogger(ArticleDao.class);

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
//            if (iterator.hasNext()) {
//                query.append(article.toQueryString());
//                query.append(",");
//            }

//            if (!iterator.hasNext()) {
//                query.append(article.toQueryString());
//                query.append(";");
//            }

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

//        logger.info(query.toString());

//        System.out.println(query.toString());
//        db.getStatement().executeUpdate(query.toString());
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

    public String getTagMap () throws SQLException {
        String query = "with elements (element) as (" +
                "select unnest(news_keywords) " +
                "from articles" +
                "select *, count(*)" +
                "from elements" +
                "group by element";
        ResultSet rs = db.getConnection().createStatement().executeQuery(query);
        rs.next();
        String tagList = rs.getString(1);
        return tagList;
    }
}
