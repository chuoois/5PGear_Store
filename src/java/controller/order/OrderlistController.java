package controller.order;

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

public class OrderlistController extends HttpServlet {

    private OrderDAO orderdao = new OrderDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Sale sale = (Sale) session.getAttribute("sales");
        String pageNumberParam = request.getParameter("pageNumber");
        String pageSizeParam = request.getParameter("size");
        int pageNumber = (pageNumberParam != null && !pageNumberParam.isEmpty()) ? Integer.parseInt(pageNumberParam) : 1;
        int pageSize = (pageSizeParam != null && !pageSizeParam.isEmpty()) ? Integer.parseInt(pageSizeParam) : 5;

        // Sorting and filtering parameters
        String sortColumn = "order_date";  // Default sorting column
        String sortOrder = "DESC";  // Default sorting order
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

        // Check if orderId and newStatus are being sent for status update
        String orderIdRaw = request.getParameter("orderId");
        String newStatus = request.getParameter("newStatus");
        if (orderIdRaw != null && newStatus != null) {
            try {
                int orderId = Integer.parseInt(orderIdRaw);
                int statusId;
                switch (newStatus) {
                    case "Approve":
                        statusId = 3;
                        break;
                    case "Cancel":
                        statusId = 1;
                        orderdao.decreaseProductHold(orderId);
                        break;
                    default:
                        statusId = 2;
                }
                // Update the order status in the database
                boolean updateSuccess = orderdao.updateStatus(orderId, statusId);
                if (updateSuccess) {
                    session.setAttribute("successMessage", "Order status updated successfully.");
                } else {
                    session.setAttribute("errorMessage", "Failed to update order status.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid orderId: " + e.getMessage());
                session.setAttribute("errorMessage", "Invalid order ID.");
            }
        }

        // Get paginated list of orders from the DAO
        List<Order> orderList = orderdao.getAllBySaleID(sale.getSaleId(), pageNumber, pageSize, sortColumn, sortOrder, sqlFromDate, sqlToDate, status, searchQuery);

        // Calculate total pages
        int totalOrder = orderdao.getTotalOrder();
        int totalPages = (int) Math.ceil((double) totalOrder / pageSize);

        // Set attributes for JSP
        request.setAttribute("orderList", orderList);
        request.setAttribute("currentPage", pageNumber);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("fromDate", fromDateParam);
        request.setAttribute("toDate", toDateParam);
        request.setAttribute("saleName", saleName);
        request.setAttribute("status", status);
        request.setAttribute("searchQuery", searchQuery);

        // Forward the request to JSP
        request.getRequestDispatcher("OrderlistSale.jsp").forward(request, response);
    }

    // ... other methods ...
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    public String getServletInfo() {
        return "Controller for listing orders";
    }
}
