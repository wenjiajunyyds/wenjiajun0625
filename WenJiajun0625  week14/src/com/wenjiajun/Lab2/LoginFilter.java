package com.wenjiajun.Lab2;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.http.HttpRequest;

@WebFilter(filterName = "LoginFilter" , urlPatterns = {"/lab2/validate.jsp","/lab2/welcome.jsp"})
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
        System.out.println("i am in LoginFilter--init()");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("I am in LoginFilter-->doFilter()-- request before chain");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session=req.getSession(false);
        if(session.getAttribute("user")==null) {
            req.getRequestDispatcher("login.jsp").forward(req,res);
        }
        chain.doFilter(request, response);
        System.out.println("I am in LoginFilter-->destroy()-- response after chain");
    }
}
