package com.wenjiajun.JDBC;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
@WebListener
public class JDBCServletContextListener implements ServletContextListener {
    Connection con=null;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context=sce.getServletContext();
        String driver=context.getInitParameter("driver");//<param-name>driver</param-name>
        String url=context.getInitParameter("url");
        String username=context.getInitParameter("username");
        String password=context.getInitParameter("password");

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
            System.out.println("init()--> " + con);// ok(java code)- ok(use config-in web.xml) -- ok -use( @webservlet )--> (use context) ok-- use mysql ( no need to change in java code )
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        context.setAttribute("con",con);
//            if(con!=null) System.out.println("success!!!");
//        else System.out.println("err!!!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
