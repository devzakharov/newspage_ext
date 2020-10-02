package com.zrv.newspage.service;

import com.zrv.newspage.dao.ArticleDao;
import com.zrv.newspage.domain.Article;

import java.sql.SQLException;
import java.util.List;


public class ArticlesServiceImpl implements ArticlesService {

    private static ArticlesServiceImpl instance;
    private static final ArticleDao articleDao = ArticleDao.getInstance();

    private ArticlesServiceImpl() {

    }

    public static ArticlesServiceImpl getInstance() {

        if (instance == null) {
            instance = new ArticlesServiceImpl();
        }
        return instance;
    }

    @Override
    public boolean delete(int id) {
        return true;
    }

    @Override
    public void show(int id) {

    }

    public List<Article> getArticlesList(
            Integer limit, Integer offset, String tags, String fromDate, String toDate
    ) throws SQLException {

        String[] tagsArray = tags.split(",");
        return articleDao.getAllFiltered(limit, offset, tagsArray, fromDate, toDate);
    }

    public List<Article> getSearchResults(String searchQuery) throws SQLException {

        return articleDao.getSearchResult(searchQuery);
    }
}