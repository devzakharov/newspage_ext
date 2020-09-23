package com.zrv.newspage.service;

import com.zrv.newspage.dao.ArticleDao;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class ArticlesParseScheduler extends TimerTask {

    // TODO настроить адекватные события для логера
    final static Logger logger = Logger.getLogger(ArticlesParseScheduler.class);

    @Override
    public void run() {
        try {

            logger.info("Scheduled task is started");

            ArticlesJsonDataParser.getInstance().fillDataObject();

            ArticlesHtmlDataParser htmlDataParser = new ArticlesHtmlDataParser(
                    ArticlesJsonDataParser.getInstance().getDataObject()
            );

            htmlDataParser.fillDataObject();
            htmlDataParser.writeArticlesToDb();

            logger.info("Scheduled task is finished");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
