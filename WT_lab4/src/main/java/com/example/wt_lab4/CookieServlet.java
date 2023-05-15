package com.example.wt_lab4;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(name = "CookieServlet", value = "/CookieServlet")
public class CookieServlet extends HttpServlet {
    private static final AtomicInteger counter = new AtomicInteger();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var cookies = request.getCookies();
        if (cookies == null || Arrays.stream(cookies)
                .filter(cookie -> "visited".equals(cookie.getName()))
                .findFirst()
                .isEmpty()){
            var cookie = new Cookie("visited", "true");
//            cookie.setPath("/CookieServlet");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            counter.incrementAndGet();
        }
        response.setContentType("text/html");
        try (var writer = response.getWriter()) {
            writer.write("<h1> Number of unique visits = " + counter.get() + "</h1>");
        }
    }
}
