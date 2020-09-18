package com.zrv.newspage.service;

import com.zrv.newspage.dao.ArticleDao;
import com.zrv.newspage.domain.Article;

import java.sql.SQLException;

public class ArticleService {

    ArticleDao articleDao = new ArticleDao();

    public Article getArticle(String id) throws SQLException {
        if(articleDao.get(id).isPresent()) {
            return articleDao.get(id).get();
        } else {
            throw new RuntimeException();
        }
    }
}
