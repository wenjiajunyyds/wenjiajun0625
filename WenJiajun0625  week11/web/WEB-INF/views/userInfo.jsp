<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 4/5/2021
  Time: 5:47 PM
  To change this template use File | Settings | File Templates.
--%>

<%@include file="header.jsp"%>
<%@page import="com.wenjiajun.model.User" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.text.DateFormat" %>

<h1> User Info</h1>
<%
    Cookie[] allCookies=request.getCookies();
    for (Cookie c:allCookies){
        out.println("<br/>"+c.getName()+" --- "+c.getValue());
    }
%>
<%
    User u =(User) session.getAttribute("user");
%>
<table>
    <tr>
        <td>Username:</td><td><%=u.getUsername()%></td>
    </tr><tr>
        <td>Password:</td><td><%=u.getPassword()%></td>
</tr><tr>
        <td>Email:</td><td><%=u.getEmail()%></td>
</tr><tr>
    <td>Gender:</td><td><%=u.getGender()%></td>
</tr><tr>]
    <% DateFormat df= new SimpleDateFormat("yyyy-MM-dd");
        String birth=df.format(u.getBirthDate());%>
    <td>Birth Date:</td><td><%=birth %></td>
</tr>
    <tr>
        <td><a href="updateUser">UpDate</a></td>
    </tr>

</table>

<%@include file="footer.jsp"%>
