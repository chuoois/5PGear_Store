/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.home;

import dal.BlogDAO;
import dal.CustomerDAO;
import dal.ProductDAO;
import dal.SliderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Blog;
import model.Customer;
import model.Product;
import model.Slider;

public class HomeController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String token = request.getParameter("token");

        if (token != null && !token.isEmpty()) {
            CustomerDAO customerDAO = new CustomerDAO();
            Customer customer = customerDAO.getCustomerByToken(token);
            

            if (customer != null && customer.getStatus().equals("Inactive")) {
                customerDAO.activateCustomer(customer.getCustomerId());
                request.setAttribute("successMessage", "Your account has been successfully activated. You can now log in.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Invalid or expired activation token.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } else {
            // Nếu không có token, điều hướng đến trang home bình thường
            ProductDAO dao = new ProductDAO();
            List<Product> list = dao.getProductLastest();
            List<Product> list2 = dao.getProductTopSale();
            SliderDAO sliderDAO = new SliderDAO();
            List<Slider> slider =  sliderDAO.getSliderHomepage();
            BlogDAO blogDAO = new BlogDAO();
            List<Blog> blog = blogDAO.getBlogHomepage();
            request.setAttribute("blogH", blog);
            request.setAttribute("slider", slider);
            request.setAttribute("listProductH", list);
            request.setAttribute("listProductTopSale", list2);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}





