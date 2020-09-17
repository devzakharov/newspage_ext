package com.zrv.newspage.service;


import com.zrv.newspage.dao.ArticleDao;
import com.zrv.newspage.domain.Article;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class ArticlesServiceImpl implements ArticlesService {

    @Override
    public boolean delete(int id) {
        return true;
    }

    @Override
    public void show(int id) {

    }

    public List<Article> getArticlesList(Integer limit, Integer offset, String tags, String fromDate, String toDate)
            throws SQLException, ParseException {
        ArticleDao articleDao = new ArticleDao();
        String[] tagsArray = tags.split(",");
//        Timestamp from = Timestamp.valueOf(fromDate);
//        Timestamp to = Timestamp.valueOf(toDate);
        return articleDao.getAllFiltered(limit, offset, tagsArray, fromDate, toDate);
    }
}