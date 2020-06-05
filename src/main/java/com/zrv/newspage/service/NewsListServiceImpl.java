package com.zrv.newspage.service;

import com.zrv.newspage.domain.NewsList;

interface NewsListService {
    NewsList sort(NewsList newsList);
}

public class NewsListServiceImpl implements NewsListService {

    @Override
    public NewsList sort(NewsList newsList) {
        return newsList;
    }
}

enum Sort {
    ID,
    DATE_CREATED,
    DATE_PUBLISHED,
    AUTHOR
}


