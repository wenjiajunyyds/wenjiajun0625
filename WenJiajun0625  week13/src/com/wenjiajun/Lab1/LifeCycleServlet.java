package com.wenjiajun.Lab1;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LifeCycleServlet", value = "/life")
public class LifeCycleServlet extends HttpServlet {

    private int times=0;
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init");
        System.out.println("I Am from default constructor");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)  throws  ServletException, IOException {
        System.out.println("service");
        times++;
        request.setAttribute("times",times);
        request.getRequestDispatcher("lab1/lifeCycle.jsp").forward(request,response);
    }

    public void destroy() {
        System.out.println("destroy");
    }
}