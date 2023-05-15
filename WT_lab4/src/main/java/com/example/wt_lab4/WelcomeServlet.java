package com.example.wt_lab4;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.FileWriter;

@WebServlet(name = "WelcomeServlet", value = "/WelcomeServlet")
public class WelcomeServlet extends HttpServlet {
    private static final String WelcomeObject = "WelcomeObject";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();
        var welcome = (WelcomeDto) session.getAttribute(WelcomeObject);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        var currentVisit = formatter.format(date);
        var browser = request.getHeader("User-Agent");
        var ipAddr = request.getRemoteAddr();
        if (welcome == null){
            response.setContentType("text/html");
            try (var writer = response.getWriter()) {
                writer.write("<h1>Welcome to the server!!!</h1>");
            }
            try (var writer = new FileWriter("D:\\JetBrains Projects\\IntelIj Projects\\WT_lab4\\src\\main\\java\\com\\example\\wt_lab4\\file.txt", true)) {
                writer.write(ipAddr + " ---- ");
                writer.write(browser + " ---- ");
                writer.write(currentVisit + "\n");
            }
        } else {
            try (var writer = response.getWriter()) {
                writer.write("<h1>User info</h1>");
                writer.write("<p>Last visit: " + welcome.getLastVisit() + "</p>");
                writer.write("<p>Used browser: " + welcome.getBrowser() + "</p>");
            }
        }
        var newWelcome = WelcomeDto.builder()
                .browser(browser)
                .lastVisit(currentVisit)
                .build();
        session.setAttribute(WelcomeObject, newWelcome);
    }
}
