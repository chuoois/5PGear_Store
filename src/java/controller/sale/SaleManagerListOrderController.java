package controller.sale;

import dal.OrderDAO;
import dal.SaleDAO;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;
import model.Order;
import model.Sale;

public class SaleManagerListOrderController extends HttpServlet {

    private OrderDAO orderdao = new OrderDAO();
    private SaleDAO saledao = new SaleDAO();
    private static final int RECORDS_PER_PAGE = 5;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * RECORDS_PER_PAGE;

        // Retrieve filter parameters
        String fromDateParam = request.getParameter("fromDate");
        String toDateParam = request.getParameter("toDate");
        String status = request.getParameter("status");
        String search = request.getParameter("searchQuery");

        // Retrieve sort parameters
        String sortField = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        if (sortOrder == null) {
            sortOrder = "ASC"; // Default sort order
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = null;
        Date toDate = null;

        // Parse dates
        try {
            if (fromDateParam != null && !fromDateParam.isEmpty()) {
                fromDate = dateFormat.parse(fromDateParam);
            }
            if (toDateParam != null && !toDateParam.isEmpty()) {
                toDate = dateFormat.parse(toDateParam);
            }
        } catch (ParseException e) {
            System.out.println("Date parsing error: " + e.getMessage());
        }

        // Convert to SQL Date
        java.sql.Date sqlFromDate = fromDate != null ? new java.sql.Date(fromDate.getTime()) : null;
        java.sql.Date sqlToDate = toDate != null ? new java.sql.Date(toDate.getTime()) : null;

        // Get filtered order list
        List<Order> orderList = orderdao.getFilteredOrders(sqlFromDate, sqlToDate, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);

        // Get total orders count for pagination
        int totalOrders = orderdao.getOrderCount(); // You'll need to implement this method
        int totalPages = (int) Math.ceil((double) totalOrders / RECORDS_PER_PAGE);

        // Get all sales for assigning orders
        List<Sale> salesList = saledao.getAllSales();
        Map<Integer, Integer> pendingOrdersCount = saledao.getPendingOrdersCountForSales();
        
        // Set attributes for JSP
        request.setAttribute("pendingOrdersCount", pendingOrdersCount);
        request.setAttribute("orderList", orderList);
        request.setAttribute("salesList", salesList); // Pass sales list to JSP
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Maintain search parameters in pagination links
        StringBuilder searchParamsBuilder = new StringBuilder();
        if (fromDateParam != null && !fromDateParam.isEmpty()) {
            searchParamsBuilder.append("&fromDate=").append(fromDateParam);
        }
        if (toDateParam != null && !toDateParam.isEmpty()) {
            searchParamsBuilder.append("&toDate=").append(toDateParam);
        }
        if (status != null && !status.isEmpty()) {
            searchParamsBuilder.append("&status=").append(status);
        }
        if (search != null && !search.isEmpty()) {
            searchParamsBuilder.append("&searchQuery=").append(search);
        }
        request.setAttribute("searchParams", searchParamsBuilder.toString());

        // Forward to JSP
        request.getRequestDispatcher("OrderListSaleManager.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý việc gán sale cho đơn hàng
        String orderIdParam = request.getParameter("orderId");
        String saleIdParam = request.getParameter("saleId");

        if (orderIdParam != null && saleIdParam != null) {
            try {
                int orderId = Integer.parseInt(orderIdParam);
                int saleId = Integer.parseInt(saleIdParam);

                // Cập nhật saleId cho đơn hàng
                boolean assignSuccess = orderdao.assignSaleToOrder(orderId, saleId);

                if (assignSuccess) {
                    request.getSession().setAttribute("successMessage", "Assigned order to sale successfully.");
                } else {
                    request.getSession().setAttribute("errorMessage", "Failed to assign order to sale.");
                }

            } catch (NumberFormatException e) {
                request.getSession().setAttribute("errorMessage", "Invalid order or sale ID.");
            }
        }

        // Redirect lại trang danh sách đơn hàng sau khi gán sale
        response.sendRedirect("salemanagerlist");
    }

    @Override
    public String getServletInfo() {
        return "Controller for Sale Manager List of Orders";
    }
}
