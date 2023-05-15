package com.example.wt_lab4;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "GetAttributesServlet", value = "/GetAttributesServlet")
public class GetAttributesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var sesssion = request.getSession();
        var welcome = WelcomeDto.builder()
                .browser("browser")
                .lastVisit("lastVisit")
                .build();
        sesssion.setAttribute("WelcomeObject", welcome);
        response.setContentType("text/html;charset=UTF-8");
        try (var writer = response.getWriter()) {
            var servletContext = getServletContext();
            var servletContextAttributeNames = servletContext.getAttributeNames();
            writer.write("<h3>Attributes ServletContext:</h3>");
            while (servletContextAttributeNames.hasMoreElements()) {
                String attributeName = servletContextAttributeNames.nextElement();
                Object attributeValue = servletContext.getAttribute(attributeName);
                writer.write("<p>" + attributeName + " = " + attributeValue + "</p>");
            }

            var session = request.getSession();
            var sessionAttributeNames = session.getAttributeNames();
            writer.write("<h3>Attributes HttpSession:</h3>");
            while (sessionAttributeNames.hasMoreElements()) {
                String attributeName = sessionAttributeNames.nextElement();
                Object attributeValue = session.getAttribute(attributeName);
                writer.write("<p>" + attributeName + " = " + attributeValue + "</p>");
            }

            var requestParameterNames = request.getParameterNames();
            writer.write("<h3>Attributes HttpServletRequest:</h3>");
            while (requestParameterNames.hasMoreElements()) {
                String parameterName = requestParameterNames.nextElement();
                String parameterValue = request.getParameter(parameterName);
                writer.write("<p>" + parameterName + " = " + parameterValue + "</p>");
            }
        }
    }
}
