package com.zrv.newspage;

import com.zrv.newspage.controller.ArticlesController;
import com.zrv.newspage.controller.TagsController;
import com.zrv.newspage.controller.UserLoginController;
import com.zrv.newspage.controller.UserRegistrationController;
import com.zrv.newspage.dao.ArticleDao;
import org.apache.log4j.BasicConfigurator;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;


public class ArticlesPageApplication {

    public static void main(String[] args) throws Exception {

        BasicConfigurator.configure();

//        ArticlesJsonDataParser.getInstance().fillDataObject();
//
//        ArticlesHtmlDataParser htmlDataParser = new ArticlesHtmlDataParser(
//                ArticlesJsonDataParser.getInstance().getDataObject()
//        );
//
//        htmlDataParser.fillDataObject();
//        htmlDataParser.writeArticlesToDb();

//        ArticleDao ad = new ArticleDao();
//        System.out.println(ad.getTagMap().toString());

//        String[] tagArray = ad.getTagList().split(", ");
//        for (int i = 0; i <= tagArray.length; i++) {
//
//        }

//        Timer time = new Timer();
//        ArticlesParseScheduler s = new ArticlesParseScheduler();
//        time.schedule(s, 0, 3600000);

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(5656);
        server.setConnectors(new Connector[]{connector});
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(UserRegistrationController.class, "/register");
        servletHandler.addServletWithMapping(UserLoginController.class, "/login");
        servletHandler.addServletWithMapping(ArticlesController.class, "/articles");
        servletHandler.addServletWithMapping(TagsController.class, "/tags");
        server.setHandler(servletHandler);
        server.start();
        System.out.println("Server started!");
        server.join();
    }

}
