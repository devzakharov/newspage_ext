package com.zrv.newspage.dao;

import com.zrv.newspage.ArticlesPageApplication;
import com.zrv.newspage.domain.Article;
import com.zrv.newspage.service.DatabaseQueryService;
import org.apache.log4j.Logger;


import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

        StringBuilder query = new StringBuilder();
        query.append(
                "REPLACE INTO articles (" +
                        "`id`, " +
                        "`description`, " +
                        "`news_keywords`, " +
                        "`image`, " +
                        "`article_html`, " +
                        "`front_url`, " +
                        "`title`, " +
                        "`photo`, " +
                        "`project`, " +
                        "`category`, " +
                        "`opinion_authors`, " +
                        "`anons`, " +
                        "`publish_date`, " +
                        "`parsed_date`) VALUES "
        );

        articleSet.toString();

        Iterator<Article> iterator = articleSet.iterator();

        while (iterator.hasNext()) {

            Article article = iterator.next();
            if (iterator.hasNext()) {
                query.append(article.toQueryString());
                query.append(",");
            }

            if (!iterator.hasNext()) {
                query.append(article.toQueryString());
                query.append(";");
            }

        }

        logger.info(query.toString());

//        System.out.println(query.toString());
//        db.getStatement().executeUpdate(query.toString());
        try {
            db.getConnection().prepareStatement(query.toString()).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(query);

        }
    }

    @Override
    public void update(Article article, String[] params) {

    }

    @Override
    public void delete(Article a) {

    }
}
