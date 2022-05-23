package com.wenjiajun.Lab1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ParametersServlet", value = "/parameters")
public class ParametersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("lab1/MyJsp.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name= request.getParameter("cname");
        String clas= request.getParameter("clas");
        String cid=  request.getParameter("cid");
        request.setAttribute("name",name);
        request.setAttribute("clas",clas);
        request.setAttribute("cid",cid);
        request.getRequestDispatcher("lab1/MyDearJsp.jsp").forward(request,response);

    }
}
