package com.zrv.newspage.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zrv.newspage.dao.TagsDao;
import com.zrv.newspage.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;


public class SuggestionsController extends HttpServlet {

    private final TagsDao tagsDao = TagsDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {

            handleRequest(req, resp);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ServletUtils.setUsualHeaders(resp);
        ServletUtils.setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();
        String inputValue = req.getParameter("inputvalue");
        String json;

        ObjectMapper mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(tagsDao.getSuggestions(inputValue));
        } catch (SQLException e) {
            e.printStackTrace();
            json = e.getMessage();
        }

        output.write(json);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {

        ServletUtils.setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
