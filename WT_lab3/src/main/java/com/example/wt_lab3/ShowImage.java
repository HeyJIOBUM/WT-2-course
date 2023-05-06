package com.example.wt_lab3;

import java.io.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "ShowImage", value = "/show-image")
public class ShowImage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setHeader("Content-Disposition", "attachment; filename=\"myfile.txt\"");
//        response.setContentType("text/plain");
//        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//        try (PrintWriter writer = response.getWriter()) {
//            writer.write("Servlet, hello from txt file!");
//        }
        response.setContentType("image/jpeg");
        ServletOutputStream out;
        out = response.getOutputStream();
        FileInputStream fin  = new FileInputStream("D:\\QT\\Tools\\QtDesignStudio\\share\\qtcreator\\examples\\SideMenu\\content\\assets\\depths.jpeg");
        BufferedInputStream bin  = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        int ch=0;
        while ((ch=bin.read()) != -1) {
            bout.write(ch);
        }
        bin.close();
        fin.close();
        bout.close();
        out.close();
    }
}