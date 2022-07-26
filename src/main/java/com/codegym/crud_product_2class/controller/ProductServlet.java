package com.codegym.crud_product_2class.controller;

import com.codegym.crud_product_2class.model.Category;
import com.codegym.crud_product_2class.model.Product;
import com.codegym.crud_product_2class.service.impl.CategoryService;
import com.codegym.crud_product_2class.service.impl.ProductService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends HttpServlet {
    private final ProductService productService = new ProductService();
    private final CategoryService categoryService = new CategoryService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action =  request.getParameter("action");
        switch (action) {
            case "create":
                createProductGet(request, response);
                break;
            case "delete":
                deleteProduct(request, response);
                break;
            case "update":
                updateProductGet(request, response);
                break;
            case "detail":
                detailProduct(request, response);
                break;
            default:
                displayAllProduct(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action =  request.getParameter("action");
        switch (action) {
            case "create":
                createProductPost(request, response);
                break;
            case "update":
                updateProductPost(request, response);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        productService.deleteById(id);
        response.sendRedirect("/product?action=");
    }

    private void detailProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product/detail.jsp");
        Product product = productService.findById(id);
        request.setAttribute("p", product);
        requestDispatcher.forward(request, response);
    }

    private void updateProductGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.findById(id);
        ArrayList<Category> categories = categoryService.findAll();
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product/edit.jsp");
        request.setAttribute("p", product);
        request.setAttribute("categories", categories);
        requestDispatcher.forward(request, response);
    }

    private void updateProductPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        Category category = categoryService.findById(categoryId);
        Product product = new Product(id,name, price, quantity, description, category);
        productService.update(product);
        response.sendRedirect("/product?action=");
    }

    private void createProductGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product/create.jsp");
        ArrayList<Category> categories = categoryService.findAll();
        request.setAttribute("categories", categories);
        requestDispatcher.forward(request, response);
    }

    private void createProductPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        String description = request.getParameter("description");
        int categoryId = Integer.parseInt(request.getParameter("category"));
        Category category = categoryService.findById(categoryId);
        Product product = new Product(name, price, quantity, description, category);
        productService.create(product);
        response.sendRedirect("/product?action=");
    }

    private void displayAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("product/display.jsp");
        ArrayList<Product> products = productService.findAll();
        request.setAttribute("products", products);
        requestDispatcher.forward(request, response);
    }
}
