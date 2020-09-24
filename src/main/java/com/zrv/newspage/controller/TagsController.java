package com.zrv.newspage.controller;

import com.zrv.newspage.service.TagsService;
import com.zrv.newspage.util.ServletUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TagsController extends HttpServlet {

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

        String json;

        TagsService tagsService = new TagsService();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ServletUtils.setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();
        if (req.getParameter("getalltags").equals("1")) {
            json = tagsService.getFilteredTagsJson();
            output.write(json);
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            json = "{\"status\":\"Неудача при загрузке тегов!\"}";
            output.write(json);
        }
    }

    //for Preflight
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        ServletUtils.setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
