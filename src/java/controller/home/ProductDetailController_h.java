/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.FeedbackDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.ProductDAO;

import model.Product;
import model.Brand;
import model.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Feedback;

/**
 *
 * @author acer
 */
public class ProductDetailController_h extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductDetailController_h</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailController_h at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        String productId = request.getParameter("productId");

        ProductDAO productDAO = new ProductDAO();
        BrandDAO brand = new BrandDAO();
        CategoryDAO category = new CategoryDAO();
        List<Feedback> feedbacklist = feedbackDAO.getFeedbackTop2(Integer.parseInt(productId));
        List<Product> productlist = productDAO.getAllProducts();
        List<Product> productlatest = productDAO.getLatestProducts(); // Danh sách sản phẩm mới nhất
        List<Brand> brandlist = brand.getAllBrands(); // Danh sách tất cả các thương hiệu
        List<Category> categorylist = category.getAllCategories();
        Map<Integer, Integer> brandProductCount = new HashMap<>();
        Map<Integer, Integer> categoryProductCount = new HashMap<>();
        for (Product product1 : productlist) {
            int brandId = product1.getBrand().getBrandId();
            int categoryId = product1.getCategory().getCategoryId();
            brandProductCount.put(brandId, brandProductCount.getOrDefault(brandId, 0) + 1);
            categoryProductCount.put(categoryId, categoryProductCount.getOrDefault(categoryId, 0) + 1);
        }
        int pId = Integer.parseInt(productId);
        Product productdetail = productDAO.getProductById(pId);
        request.setAttribute("feedbacklist", feedbacklist);
        request.setAttribute("pllist", productlatest);
        request.setAttribute("brandProductCount", brandProductCount);
        request.setAttribute("blist", brandlist);
        request.setAttribute("categoryProductCount", categoryProductCount);
        request.setAttribute("clist", categorylist);
        request.setAttribute("productdetail", productdetail);
        request.getRequestDispatcher("productdetail_h.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
