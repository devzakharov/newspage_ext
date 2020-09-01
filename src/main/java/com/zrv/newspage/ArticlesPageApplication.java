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

        // Заполнение сета сырых статей из JSON РБК
        ArticlesJsonDataParser.getInstance().fillDataObject();

        ArticlesJsonDataParser.getInstance().getDataObject().forEach((key, value) -> {
            System.out.println("Key: " + key + " Value: " + value.toString());
        });

        // Заполнение сета полными статьями по результатам полученным из мапы сырых статей
        ArticlesHtmlDataParser htmlDataParser = new ArticlesHtmlDataParser(
                ArticlesJsonDataParser.getInstance().getDataObject()
        );
        htmlDataParser.fillDataObject();
        htmlDataParser.writeArticlesToDb();

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
