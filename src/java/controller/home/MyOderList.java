/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import java.io.PrintWriter;
import dal.OrderDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import model.Sale;
import dal.SaleDAO;
import model.Customer;

/**
 *
 * @author acer
 */
public class MyOderList extends HttpServlet {

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
            out.println("<title>Servlet MyOderList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MyOderList at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pageNumberParam = request.getParameter("pageNumber");
        String pageSizeParam = request.getParameter("size");
        int pageNumber = (pageNumberParam != null && !pageNumberParam.isEmpty()) ? Integer.parseInt(pageNumberParam) : 1;
        int pageSize = (pageSizeParam != null && !pageSizeParam.isEmpty()) ? Integer.parseInt(pageSizeParam) : 5;
        String sortColumn = "order_date";
        String sortOrder = "DESC";
        String fromDateParam = request.getParameter("fromDate");
        String toDateParam = request.getParameter("toDate");
        String saleName = request.getParameter("saleName");
        String status = request.getParameter("status");
        String searchQuery = request.getParameter("searchQuery");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date fromDate = null;
        java.util.Date toDate = null;

        // Parse the date
        try {
            if (fromDateParam != null && !fromDateParam.isEmpty()) {
                fromDate = dateFormat.parse(fromDateParam);
            }
            if (toDateParam != null && !toDateParam.isEmpty()) {
                toDate = dateFormat.parse(toDateParam);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }

        // Convert dates to java.sql.Date
        java.sql.Date sqlFromDate = fromDate != null ? new java.sql.Date(fromDate.getTime()) : null;
        java.sql.Date sqlToDate = toDate != null ? new java.sql.Date(toDate.getTime()) : null;

        OrderDAO orderDao = new OrderDAO();
        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");
        if (account != null) {
            int customerid = account.getCustomerId();
            List<Order> orders = orderDao.getAllByCustomerId(customerid, pageNumber, pageSize, sortColumn, sortOrder, sqlFromDate, sqlToDate, status, searchQuery);         
            int totalOrder = orderDao.getTotalOrderByCustomerId(customerid, sqlFromDate, sqlToDate, status, searchQuery);
            int totalPages = (int) Math.ceil((double) totalOrder / pageSize);
            request.setAttribute("orderlist", orders);
            request.setAttribute("currentPage", pageNumber);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("pageSize", pageSize);
            request.setAttribute("fromDate", fromDateParam);
            request.setAttribute("toDate", toDateParam);
            request.setAttribute("saleName", saleName);
            request.setAttribute("status", status);
            request.setAttribute("searchQuery", searchQuery);
            request.getRequestDispatcher("myorderlist.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
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
