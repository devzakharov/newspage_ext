package com.zrv.newspage;

import com.zrv.newspage.domain.Article;
import org.eclipse.jetty.server.Server;

import java.sql.SQLOutput;
import java.util.Date;


public class NewsPageApplication {

    public static void main(String[] args) throws Exception {

        Server server = new Server(8080);

        server.start();
        System.out.println("Server started!");
        server.join();

        Article article = new Article(1, "", "", "", "", new Date());
        System.out.println(article);
    }

}

