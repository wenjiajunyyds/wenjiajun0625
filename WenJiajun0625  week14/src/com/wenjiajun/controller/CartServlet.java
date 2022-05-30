package com.wenjiajun.controller;

import com.wenjiajun.dao.ProductDao;
import com.wenjiajun.model.Item;
import com.wenjiajun.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "CartServlet", value = "/cart")
public class CartServlet extends HttpServlet {
    private Connection con = null;

    @Override
    public void init() throws ServletException {
        super.destroy();
        con = (Connection) getServletContext().getAttribute("con");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null){
            if (request.getParameter("action") == null){
                displayCart(request,response);
            }else if (request.getParameter("action").equals("add")){
                try {
                    buy(request,response);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }else if (request.getParameter("action").equals("remove")){
                remove(request,response);
            }
        }else {
            response.sendRedirect("login");
        }
    }

    private void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("remove----------");
        List<Item> cart =(List<Item>) request.getSession().getAttribute("cart");
        int id = 0;
        if (request.getParameter("productId") != null){
            id = Integer.parseInt(request.getParameter("productId"));
        }
        int index = isExisting(id,cart);
        cart.remove(index);
        request.getSession().setAttribute("cart",cart);
        String path = request.getContextPath()+"/cart";
        response.sendRedirect(path);

    }

    private void buy(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        System.out.println("buy----------");
        HttpSession session = request.getSession();
        int id = request.getParameter("productId") != null ? Integer.parseInt(request.getParameter("productId")) : 0;
        int quantity = request.getParameter("quantity") != null ? Integer.parseInt(request.getParameter("quantity")) : 1;
        if (id == 0 || quantity == 0){
            return;
        }
        ProductDao productDao = new ProductDao();
        if (session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<Item>();
            Product p = productDao.findById(id,con);
            cart.add(new Item(p,quantity));
            session.setAttribute("cart",cart);
        }else {
            List<Item> cart = (ArrayList<Item>) session.getAttribute("cart");
            int index = isExisting(id,cart);
            if (index == -1){
                Product p = productDao.findById(id,con);
                cart.add(new Item(p,1));
            }else {
                int newQuantity = cart.get(index).getQuantity()+1;
                cart.get(index).setQuantity(newQuantity);
            }
            session.setAttribute("cart",cart);
        }
        try {
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int isExisting(int id, List<Item> cart) {
        for (int i = 0;i<cart.size();i++){
            if (cart.get(i).getProduct().getProductId() == id){
                return i;
            }
        }
        return -1;
    }

    private void displayCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("display---------");
        request.setAttribute("message","Your Cart");
        String path = "/WEB-INF/views/cart.jsp";
        request.getRequestDispatcher(path).forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
