package com.zrv.newspage;

import com.zrv.newspage.controller.*;
import com.zrv.newspage.service.ArticlesParseScheduler;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import java.util.Timer;


public class ArticlesPageApplication {

    public static void main(String[] args) throws Exception {

        BasicConfigurator.configure();

        Timer time = new Timer();
        ArticlesParseScheduler scheduler = new ArticlesParseScheduler();
        time.schedule(scheduler, 0, 3600000);

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(5656);
        server.setConnectors(new Connector[]{connector});

        ServletHandler servletHandler = new ServletHandler();

        servletHandler.addServletWithMapping(UserRegistrationController.class, "/register");
        servletHandler.addServletWithMapping(UserLoginController.class, "/login");
        servletHandler.addServletWithMapping(ArticlesController.class, "/articles");
        servletHandler.addServletWithMapping(ArticleController.class, "/article");
        servletHandler.addServletWithMapping(TagsController.class, "/tags");
        servletHandler.addServletWithMapping(SuggestionsController.class, "/suggestions");

        server.setHandler(servletHandler);
        server.start();
        System.out.println("Server started!");
        server.join();
    }

}
