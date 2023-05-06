package com.example.wt_lab3;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.*;
import java.util.*;
import java.io.IOException;

@WebServlet(name = "ShowHeaders", value = "/show-headers")
public class ShowHeaders extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        Enumeration<String> e = request.getHeaderNames();
        while (e.hasMoreElements()) {

            String name = e.nextElement();
            String value = request.getHeader(name);
            out.println(name + " = " + value + "</br>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        String resp = "";
        Map<String, String[]> params = request.getParameterMap();
        for (String key : params.keySet()){
            resp += (key + " = " + Arrays.toString(params.get(key)) + "</br>");
        }
        if (!resp.isEmpty()){
            writer.write(resp);
        }
    }
}
