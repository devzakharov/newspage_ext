package com.zrv.newspage;

import com.zrv.newspage.controller.UserLoginController;
import com.zrv.newspage.controller.UserRegistrationController;
import com.zrv.newspage.service.ArticlesHtmlDataParser;
import com.zrv.newspage.service.ArticlesJsonDataParser;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

public class ArticlesPageApplication {


    public static void main(String[] args) throws Exception {

        ArticlesJsonDataParser.getInstance();

        ArticlesJsonDataParser.getInstance().fillDataObject();
        System.out.println(ArticlesJsonDataParser.getInstance().getDataObject().toString());

        ArticlesJsonDataParser.getInstance().getDataObject().forEach(data -> System.out.println(data.getFrontUrl()));

        ArticlesHtmlDataParser htmlDataParser = new ArticlesHtmlDataParser(ArticlesJsonDataParser.getInstance().getDataObject());
        htmlDataParser.fillDataObject();

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
