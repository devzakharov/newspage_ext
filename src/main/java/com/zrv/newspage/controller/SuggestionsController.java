package com.zrv.newspage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.dao.TagsDao;
import com.zrv.newspage.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


public class SuggestionsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            handleRequest(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            handleRequest(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ServletUtils.setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();

        System.out.println(req.getParameter("inputvalue"));

        TagsDao dao = new TagsDao();
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(dao.getSuggestions(req.getParameter("inputvalue")));

        output.write(json);

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
