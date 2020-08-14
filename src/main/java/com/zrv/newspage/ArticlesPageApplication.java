package com.zrv.newspage;

import com.zrv.newspage.controller.UserRegistrationController;
import com.zrv.newspage.domain.User;
import com.zrv.newspage.service.DatabaseQueryService;
import com.zrv.newspage.service.UserServiceImpl;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import java.sql.ResultSet;
import java.sql.SQLOutput;

public class ArticlesPageApplication {

    public static void main(String[] args) throws Exception {

        User usr1 = new User("123", "123", "123");
        UserServiceImpl userService = new UserServiceImpl(usr1);
        userService.addUser();

        DatabaseQueryService dbService = new DatabaseQueryService();
        ResultSet resultSet = dbService.getResultSet("SELECT * FROM users");

        if (resultSet.next()) {
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getInt(2));
                System.out.println(resultSet.getInt(3));
            }
        } else {
            System.out.println("Empty response!");
        }
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(5656);
        server.setConnectors(new Connector[]{connector});
        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(UserRegistrationController.class, "/register");
        server.setHandler(servletHandler);
        server.start();
        System.out.println("Server started!");
        server.join();
    }

}
