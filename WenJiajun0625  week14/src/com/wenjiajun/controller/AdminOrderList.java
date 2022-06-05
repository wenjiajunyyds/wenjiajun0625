package com.wenjiajun.controller;

import com.wenjiajun.dao.OrderDao;
import com.wenjiajun.model.Order;
import com.wenjiajun.model.Payment;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet(name = "AdminOrderList", value = "/admin/orderList")
public class AdminOrderList extends HttpServlet {
    private Connection con=null;

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        con=(Connection) getServletContext().getAttribute("con");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Payment> paymentTypeList=Payment.findAllPayment(con);

        request.setAttribute( "paymentTypeList",paymentTypeList);
        OrderDao orderDao=new OrderDao();
        List<Order> orderList=orderDao.findAll(con);
        request.setAttribute("orderList", orderList);
        String path="/WEB-INF/views/admin/orderList.jsp";

        request.getRequestDispatcher(path).forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
