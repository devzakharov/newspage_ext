package com.zrv.newspage.service;


import com.zrv.newspage.dao.ArticleDao;
import com.zrv.newspage.domain.Article;

import java.util.List;

public class ArticlesServiceImpl implements ArticlesService {

    @Override
    public boolean delete(int id) {
        return true;
    }

    @Override
    public void show(int id) {

    }

    public List<Article> getArticlesList(Integer limit, Integer offset) {
        ArticleDao articleDao = new ArticleDao();
        return articleDao.getAllFiltered(limit, offset);
    }
}