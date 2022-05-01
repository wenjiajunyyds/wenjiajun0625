package com.wenjiajun.controller;

import com.wenjiajun.dao.UserDao;
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
//            UserDao userDao=new UserDao();
//            try {
//                user = userDao.findByUsernamePassword(con,username,password);
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
            if (rs.next()){
                //week 5 code
                //out.print("Login Successful!!!");
                //out.print("Welcome"+username);
                //get from rs and set into resquest attribute



                user=new User(rs.getInt("id"),rs.getString("username"),rs.getString("password"),rs.getString("email"),rs.getString("gender"),rs.getDate("birthdate"));

                Cookie c = new Cookie("sessionid",""+user.getId());//week7
                c.setMaxAge(10*60);
                response.addCookie(c);

                String rememberMe = request.getParameter("rememberMe");//week8
                if (rememberMe != null && rememberMe.equals("1")){
                    Cookie usernameCookie = new Cookie("cUsername",user.getUsername());
                    Cookie  passwordCookie= new Cookie("cPassword",user.getPassword());
                    Cookie rememberMeCookie = new Cookie("cRememberMe",rememberMe);
                    usernameCookie.setMaxAge(5);
                    passwordCookie.setMaxAge(5);
                    rememberMeCookie.setMaxAge(5);
                    response.addCookie(usernameCookie);
                    response.addCookie(passwordCookie);
                    response.addCookie(rememberMeCookie);
                }

                HttpSession session = request.getSession();//week8
                System.out.println("<session id--->"+session.getId());
                session.setMaxInactiveInterval(10*60);

                session.setAttribute("user",user);
                request.getRequestDispatcher("WEB-INF/views/userInfo.jsp").forward(request,response);

            }else{
                //out.print("Username or password Error!!!");
                request.setAttribute("message","Username or Password Error!!!");
                request.getRequestDispatcher("WEB-INF/views/login.jsp").forward(request,response);

            }//end of else


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
