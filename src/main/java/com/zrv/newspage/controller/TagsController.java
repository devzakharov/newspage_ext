package com.zrv.newspage.controller;

import com.zrv.newspage.service.TagsService;
import com.zrv.newspage.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TagsController extends HttpServlet {

    private static final TagsService tagsService = TagsService.getInstance();
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

        ServletUtils.setUsualHeaders(resp);
        ServletUtils.setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();
        String json;

        if (req.getParameter("getalltags").equals("1")) {
            json = tagsService.getFilteredTagsJson();
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json = "{\"status\":\"Неудача при загрузке тегов!\"}";
        }

        output.write(json);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {

        ServletUtils.setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
