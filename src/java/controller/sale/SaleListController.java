/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.sale;

import dal.RoleDAO;
import dal.SaleDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Sale;

/**
 *
 * @author Bùi Khánh Linh 
 */
public class SaleListController extends HttpServlet {
    private static final int RECORDS_PER_PAGE = 5;
    
    private SaleDAO saleDao = new SaleDAO();

    private RoleDAO roleDao = new RoleDAO();
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet SaleListController</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleListController at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * RECORDS_PER_PAGE;

        // Retrieve filter parameters
        String gender = request.getParameter("gender");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String search = request.getParameter("search");

        // Retrieve sort parameters
        String sortField = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        if (sortOrder == null) {
            sortOrder = "ASC"; // Default sort order
        }

        // Query with filter and sorting parameters
        List<Sale> saleList = saleDao.getFilteredSale(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);

        // Get total user count for pagination
        int userCount = saleDao.getSaleCount();
        int totalPages = (int) Math.ceil((double) userCount / RECORDS_PER_PAGE);

        // Set lists and attributes in request
        request.setAttribute("saleList", saleList);

        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Create search params string for maintaining state in pagination and sorting links
        StringBuilder searchParamsBuilder = new StringBuilder();
        if (gender != null && !gender.isEmpty()) {
            searchParamsBuilder.append("&gender=").append(gender);
        }
        if (role != null && !role.isEmpty()) {
            searchParamsBuilder.append("&role=").append(role);
        }
        if (status != null && !status.isEmpty()) {
            searchParamsBuilder.append("&status=").append(status);
        }
        if (search != null && !search.isEmpty()) {
            searchParamsBuilder.append("&search=").append(search);
        }
        request.setAttribute("searchParams", searchParamsBuilder.toString());

        // Forward to JSP
        request.getRequestDispatcher("SaleList.jsp").forward(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
