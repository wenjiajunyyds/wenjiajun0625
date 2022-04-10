package com.wenjiajun.controller;

import com.wenjiajun.model.User;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.wenjiajun.model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
            Connection con=null;
    @Override
    public void init() throws ServletException {
        super.init();
        con=(Connection)getServletContext().getAttribute("con");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out= response.getWriter();
        // TODO 3: GET REQUEST PARAMETER - USERNAME AND PASSWORD
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        try {

            //lets change code to make MVC

            //TODO 4: VALIDATE USER - SELEECT * FROM USERTABLE WHERE USERNAME='XXX'
            // AND PASSWORD='YYY'
            String sql="select id,username,password,email,gender,birthdate from usertable where username='"+username+"' and password='"+password+"'";

            ResultSet rs =con.createStatement().executeQuery(sql);
            User user=null;
            if (rs.next()){
                //week 5 code
                //out.print("Login Successful!!!");
                //out.print("Welcome"+username);
                //get from rs and set into resquest attribute
                user=new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getString("email"),rs.getString("gender"),rs.getDate("birthdate"));
                request.setAttribute("user",user);
                request.getRequestDispatcher("WEB-INF/views/userInfo.jsp").forward(request,response);

            }else{
                //out.print("Username or password Error!!!");
                request.setAttribute("message","Username or Password Error!!!");
                request.getRequestDispatcher("login.jsp").forward(request,response);

            }//end of else


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
