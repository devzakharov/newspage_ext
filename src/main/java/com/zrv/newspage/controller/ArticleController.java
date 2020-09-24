package com.zrv.newspage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.domain.Article;
import com.zrv.newspage.service.ArticleService;
import com.zrv.newspage.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class ArticleController extends HttpServlet {

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

        ArticleService articleService = new ArticleService();
        PrintWriter output = resp.getWriter();
        ObjectMapper mapper = new ObjectMapper();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ServletUtils.setAccessControlHeaders(resp);

        try {
            Article article = articleService.getArticle(req.getParameter("id"));
            output.write(mapper.writeValueAsString(article));
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            output.write("{\"message\":\"" + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {

        ServletUtils.setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
