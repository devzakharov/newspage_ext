package com.zrv.newspage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.Article;
import com.zrv.newspage.service.ArticlesServiceImpl;
import com.zrv.newspage.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class ArticlesController extends HttpServlet {

    private final ArticlesServiceImpl articlesService = ArticlesServiceImpl.getInstance();

    // TODO настроить адекватные события для логера

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        handleRequest(req, resp);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        PrintWriter output = resp.getWriter();

        ServletUtils.setUsualHeaders(resp);
        ServletUtils.setAccessControlHeaders(resp);

        try {
            List<Article> requestedList;
            if (req.getParameter("search") != null) {
                requestedList = articlesService.getSearchResults(req.getParameter("search"));
            } else {
                requestedList = articlesService.getArticlesList(
                        Integer.parseInt(req.getParameter("limit")),
                        Integer.parseInt(req.getParameter("offset")),
                        req.getParameter("tags"),
                        req.getParameter("fromDate"),
                        req.getParameter("toDate")
                );
            }
            output.write(mapper.writeValueAsString(requestedList));
        } catch (SQLException e) {
            e.printStackTrace();
            output.write("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
