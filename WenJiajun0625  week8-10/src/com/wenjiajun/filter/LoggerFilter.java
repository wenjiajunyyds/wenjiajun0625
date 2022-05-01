package com.wenjiajun.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebFilter(filterName = "LoggerFilter")
public class LoggerFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("I am in LoggerFilter-->doFilter()- before servlet-(request come here )");
        chain.doFilter(request, response);
        System.out.println("I am in LoggerFilter-->doFilter()- AFTER servlet-(request come here )");
    }
}
