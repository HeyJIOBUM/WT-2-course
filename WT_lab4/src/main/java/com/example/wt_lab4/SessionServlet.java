package com.example.wt_lab4;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "SessionServlet", value = "/SessionServlet")
public class SessionServlet extends HttpServlet {
    private static final String USER = "user";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var session = request.getSession();
        var user = (UserDto) session.getAttribute(USER);
        if (user == null){
            user = UserDto.builder()
                    .id(25L)
                    .mail("test@gmail.com")
                    .build();
            session.setAttribute(USER, user);
        }
    }
}
