package com.wenjiajun.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.wenjiajun.dao.UserDao;
import com.wenjiajun.model.User;

@WebServlet(name = "UpdateUserServlet", value = "/updateUser")
public class  UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/updateUser.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id=Integer.parseInt(request.getParameter("id"));
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String gender=request.getParameter("gender");
        String email=request.getParameter("email");
        String birthDate=request.getParameter("birthDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = sdf.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        User u=new User(id,username,password,email,gender,date);
        UserDao d=new UserDao();
        Connection con=(Connection) request.getServletContext().getAttribute("con");
        if(con!=null) System.out.println("success!!!");
        else System.out.println("err!!!");
        int res=0;
        try {
            res= d.updateUser(con,u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(res==1){
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(10);
            session.setAttribute("user",u);
            request.getRequestDispatcher("WEB-INF/views/userInfo.jsp").forward(request,response);
        }else{
            System.out.println("err!!!");
        }
    }
}
