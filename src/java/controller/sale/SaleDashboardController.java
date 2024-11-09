/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.sale;

import dal.CustomerDAO;
import dal.FeedbackDAO;
import dal.OrderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import model.Category;

/**
 *
 * @author Bùi Khánh Linh
 */
public class SaleDashboardController extends HttpServlet {

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
            out.println("<title>Servlet SaleDashboardController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SaleDashboardController at " + request.getContextPath() + "</h1>");
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
        String startDateParam = request.getParameter("start-date");
        String endDateParam = request.getParameter("end-date");

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(7);
        boolean hasError = false;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        if (startDateParam != null && !startDateParam.isEmpty()) {
            try {
                startDate = LocalDate.parse(startDateParam, formatter);
            } catch (DateTimeParseException e) {
                request.setAttribute("startDateError", "Invalid start date format. Please use YYYY-MM-DD.");
                hasError = true;
            }
        }

        if (endDateParam != null && !endDateParam.isEmpty()) {
            try {
                endDate = LocalDate.parse(endDateParam, formatter);
            } catch (DateTimeParseException e) {
                request.setAttribute("endDateError", "Invalid end date format. Please use YYYY-MM-DD.");
                hasError = true;
            }
        }

        if (hasError) {
            request.getRequestDispatcher("SaleDashboard.jsp").forward(request, response);
            return;
        }

        if (!hasError) {
            Map<Category, Float> revenueByCategory = calculateRevenueByCategory(startDate, endDate);
            float totalRevenue = getTotalRevenue(startDate, endDate);

            int approvedCount = getOrderCountByStatus(startDate, endDate, "Approved");
            int cancelCount = getOrderCountByStatus(startDate, endDate, "Cancel");
            int pendingCount = getOrderCountByStatus(startDate, endDate, "Pending");
            int newlyRegisteredCustomers = getNewlyRegisteredCustomerCount(startDate, endDate);
            int newlyPurchasedCustomers = getNewlyPurchasedCustomerCount(startDate, endDate);
            float averageStarRating = getAverageStarRating(startDate, endDate);
            Map<Category, Float> averageStarRatingByCategory = getAverageStarRatingByCategory(startDate, endDate);

            Map<LocalDate, Integer> orderTrendData = getOrderCountByDate(startDate, endDate, "success");
            Map<LocalDate, Integer> totalOrderCountByDay = getOrderCountByDate(startDate, endDate, null);

            request.setAttribute("successCount", approvedCount);
            request.setAttribute("cancelledCount", cancelCount);
            request.setAttribute("submittedCount", pendingCount);
            request.setAttribute("revenueByCategory", revenueByCategory);
            request.setAttribute("totalRevenue", totalRevenue);
            request.setAttribute("newlyRegisteredCustomers", newlyRegisteredCustomers);
            request.setAttribute("newlyPurchasedCustomers", newlyPurchasedCustomers);
            request.setAttribute("averageStarRating", averageStarRating);
            request.setAttribute("averageStarRatingByCategory", averageStarRatingByCategory);
            request.setAttribute("orderTrendData", orderTrendData);
            request.setAttribute("totalOrderCountByDay", totalOrderCountByDay);

            request.setAttribute("startDate", startDate.format(formatter));
            request.setAttribute("endDate", endDate.format(formatter));
            request.getRequestDispatcher("SaleDashboard.jsp").forward(request, response);
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

    private int getOrderCountByStatus(LocalDate startDate, LocalDate endDate, String status) {
        String startDateStr = startDate.format(DateTimeFormatter.ISO_DATE);
        String endDateStr = endDate.format(DateTimeFormatter.ISO_DATE);
        OrderDAO orderDao = new OrderDAO();
        return orderDao.getOrderCountByStatus(startDateStr, endDateStr, status);
    }

    private Map<Category, Float> calculateRevenueByCategory(LocalDate startDate, LocalDate endDate) {
        OrderDAO orderDao = new OrderDAO();
        return orderDao.getRevenueByCategory(startDate, endDate);
    }

    private float getTotalRevenue(LocalDate startDate, LocalDate endDate) {
        String startDateStr = startDate.format(DateTimeFormatter.ISO_DATE);
        String endDateStr = endDate.format(DateTimeFormatter.ISO_DATE);
        OrderDAO orderDao = new OrderDAO();
        return orderDao.getTotalRevenue(startDateStr, endDateStr);
    }

    private int getNewlyRegisteredCustomerCount(LocalDate startDate, LocalDate endDate) {
        CustomerDAO customerDao = new CustomerDAO();
        return customerDao.getNewlyRegisteredCustomerCount(startDate, endDate, 2);
    }

    private int getNewlyPurchasedCustomerCount(LocalDate startDate, LocalDate endDate) {
        OrderDAO orderDao = new OrderDAO();
        return orderDao.getNewlyPurchasedCustomerCount(startDate, endDate);
    }

    private float getAverageStarRating(LocalDate startDate, LocalDate endDate) {
        FeedbackDAO feedbackDao = new FeedbackDAO();
        return feedbackDao.getAverageStarRating(startDate, endDate);
    }

    private Map<Category, Float> getAverageStarRatingByCategory(LocalDate startDate, LocalDate endDate) {
        FeedbackDAO feedbackDao = new FeedbackDAO();
        return feedbackDao.getAverageStarRatingByCategory(startDate, endDate);
    }

    private Map<LocalDate, Integer> getOrderCountByDate(LocalDate startDate, LocalDate endDate, String status) {
        OrderDAO orderDao = new OrderDAO();
        return orderDao.getOrderCountByDate(startDate, endDate, status);
    }
}
