package com.zrv.newspage;

import com.fasterxml.jackson.databind.JsonNode;
import com.zrv.newspage.controller.UserLoginController;
import com.zrv.newspage.controller.UserRegistrationController;
import com.zrv.newspage.service.ArticlesUriParser;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

public class ArticlesPageApplication {


    public static void main(String[] args) throws Exception {

        ArticlesUriParser.getInstance();

        // System.out.println(ArticlesUriParser.getInstance().getRequestUrl());
        System.out.println(ArticlesUriParser.getInstance().getJsonObject());

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(5656);
        server.setConnectors(new Connector[]{connector});
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(UserRegistrationController.class, "/register");
        servletHandler.addServletWithMapping(UserLoginController.class, "/login");
        server.setHandler(servletHandler);
        server.start();
        System.out.println("Server started!");
        server.join();
    }

}
