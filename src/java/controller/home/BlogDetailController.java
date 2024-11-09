/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.BlogDAO;
import dal.CategoryDAO;
import dal.MarketingDAO;
import dal.SaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Blog;
import model.Category;
import model.Marketing;
import model.Sale;

/**
 *
 * @author Bùi Khánh Linh
 */
public class BlogDetailController extends HttpServlet {

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
            out.println("<title>Servlet BlogDetail</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet BlogDetail at " + request.getContextPath() + "</h1>");
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
        String blogIdParam = request.getParameter("blogId");
        if (blogIdParam != null && !blogIdParam.isEmpty()) {
            int blogId = Integer.parseInt(blogIdParam);

            // Lấy thông tin chi tiết bài blog từ BlogDAO
            BlogDAO blogDAO = new BlogDAO();
            Blog blog = blogDAO.getBlogById(blogId);

            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categoryL = categoryDAO.getAllCategories();
            request.setAttribute("categoryList", categoryL);
            Category category = categoryDAO.getCategoryById(blog.getCategoryId());
            String categoryName = category.getCategoryName();
            
            MarketingDAO marketingDAO = new MarketingDAO();
            Marketing marketing = marketingDAO.getMarketerById(blog.getMarketerId());
            String marketingName = marketing.getMarketingName();
            // Gửi thông tin blog sang blogdetail.jsp
            request.setAttribute("author", marketingName);
            request.setAttribute("category", categoryName);
            request.setAttribute("blog", blog);
            request.getRequestDispatcher("blogdetail.jsp").forward(request, response);
        } else {
            // Nếu không có blogId, chuyển hướng về trang danh sách blog
            response.sendRedirect("BlogController");
        }
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
