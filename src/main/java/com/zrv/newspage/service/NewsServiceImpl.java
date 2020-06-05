package com.zrv.newspage.service;

interface NewsService {
    boolean delete(int id);
    void show(int id);
}

public class NewsServiceImpl implements NewsService {

    @Override
    public boolean delete(int id) {
        return true;
    }

    @Override
    public void show(int id) {

    }
}