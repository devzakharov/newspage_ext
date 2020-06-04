package com.zrv.newspage;

import com.zrv.newspage.domain.News;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class NewsPageApplication {

    public static void main(String[] args) throws InterruptedException {

        //SpringApplication.run(NewsPageApplication.class, args);
        System.out.println("Hello Github");

        News news = new News(1, "", "", "", "", new Date());

        System.out.println(news.getDatePublished().toString());
        Thread.sleep(1000);
        System.out.println(news.getDateParsed().toString());
    }

}

