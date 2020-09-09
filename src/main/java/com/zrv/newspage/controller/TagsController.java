package com.zrv.newspage.controller;

import com.zrv.newspage.service.TagsService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TagsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        handleRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        handleRequest(req, resp);
    }

    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String json = "{\"status\":\"Ничего не произошло!\"}";

        TagsService tagsService = new TagsService();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(resp);
        PrintWriter output = resp.getWriter();
        if (req.getParameter("getalltags").equals("1")) {
            json = tagsService.getTagsJson();
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
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        resp.setHeader("Access-Control-Max-Age", "1000");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with, Content-Type, origin, authorization, accept, x-access-token");
    }
}
