package com.wenjiajun.controller;

import com.wenjiajun.dao.ProductDao;
import com.wenjiajun.model.Category;
import com.wenjiajun.model.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.xml.stream.events.Comment;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AddProductServlet", value = "/admin/addProduct")
@MultipartConfig(maxFileSize = 16177215)
public class AddProductServlet extends HttpServlet {
    Connection con = null;

    public void init() {
        con = (Connection) getServletContext().getAttribute("con");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> categoryList= Category.findAllCategory(con);
            request.setAttribute("categoryList",categoryList);
            String path = "../WEB-INF/views/admin/addProduct.jsp";
            request.getRequestDispatcher(path).forward(request,response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String prosuctName = request.getParameter("productName");
        double price = request.getParameter("price") != null ? Double.parseDouble(request.getParameter("price")) : 0.0;
        int categoryId = request.getParameter("categoryId") != null ? (int) Double.parseDouble(request.getParameter("categoryId")) : 0;
        String productDescription = request.getParameter("productDescription");

        InputStream inputStream = null;
        Part fileParts =  request.getPart("picture");
        if (fileParts != null){
            inputStream = fileParts.getInputStream();
        }

        Product product = new Product();
        product.setProductName(prosuctName);
        product.setProductDescription(productDescription);
        product.setPicture(inputStream);
        product.setPrice(price);
        product.setCategoryId(categoryId);

        ProductDao productDao = new ProductDao();
        try {
            int n = productDao.save(product,con);
            if (n>0)
                response.sendRedirect("productList");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
