/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketting;

import dal.BlogDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Blog;

@WebServlet(name = "changeStatusBlog", urlPatterns = {"/marketting/changestatusblog"})
public class changeStatusBlog extends HttpServlet {

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
            out.println("<title>Servlet changeStatusBlog</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet changeStatusBlog at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParam = request.getParameter("id");
        BlogDAO blogDAO = new BlogDAO();

        // Capture the original query string
        String queryString = request.getQueryString();

        if (productIdParam != null) {
            int blogId = Integer.parseInt(productIdParam);
            Blog blog = blogDAO.getBlogById(blogId);

            if (blog != null) {
                String currentStatus = blog.getStatus();
                String newStatus = currentStatus.equals("1") ? "0" : "1";
                blog.setStatus(newStatus);

                boolean success = blogDAO.updateBlogStatus(blogId, newStatus);

                if (success) {
                    // Redirect back to home with the original query string
                    if (queryString != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("successMessage", "Change status successfully!");
                        response.sendRedirect("listblogmarketting?" + queryString);
                    } else {
                        response.sendRedirect("listblogmarketting");
                    }
                } else {
                    response.getWriter().write("Failed to update product status.");
                }
            } else {
                response.getWriter().write("Product not found.");
            }
        } else {
            response.getWriter().write("Product ID is missing.");
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
