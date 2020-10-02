package com.zrv.newspage.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TimerTask;


public class ArticlesParseScheduler extends TimerTask {

    private static final ArticlesJsonDataParser articlesJsonDataParser =
            ArticlesJsonDataParser.getInstance();
    private static final ArticlesHtmlDataParser articlesHtmlDataParser =
            ArticlesHtmlDataParser.getInstance();

    // TODO настроить адекватные события для логера
    final static Logger logger = Logger.getLogger(ArticlesParseScheduler.class);

    @Override
    public void run() {
        try {
            logger.info("Scheduled task is started");

            articlesJsonDataParser.fillDataObject();
            logger.info("Json data object filled");
            articlesHtmlDataParser.fillDataObject(articlesJsonDataParser.getDataObject());
            logger.info("Html data object filled");
            articlesHtmlDataParser.writeArticlesToDb();

            logger.info("Scheduled task is finished");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
