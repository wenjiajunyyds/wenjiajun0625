package com.wenjiajun.Lab2;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebFilter(filterName = "wenJiajunFilter")
public class YourNameFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        System.out.println("wenJiajunFilter-->before chain");
        chain.doFilter(request, response);
        System.out.println("wenJiajunFilter-->after chain");
    }
}
