package controller.warehousestaff;

import Email.JavaMail;
import Email.JavaMailImpl;
import dal.OrderDAO;
import dal.OrderDetailsDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Order;
import model.OrderDetails;

public class WarehouseStaffController extends HttpServlet {

    private static final int RECORDS_PER_PAGE = 20;
    private OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();
    private OrderDAO orderDao = new OrderDAO();
    private JavaMail mailService = new JavaMailImpl();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    private void sendProcessingNotification(Order order) {
        if (order != null && order.getCustomerId().getEmail() != null) {
            String subject = "Order Status Update - Order ID: " + order.getOrderId();

            StringBuilder messageText = new StringBuilder();
            messageText.append("<div style='max-width: 600px; margin: auto; border: 1px solid #ddd; padding: 20px;'>");

            // Header
            messageText.append("<h2 style='text-align: center; color: #333;'>Thank you for your purchase!</h2>");
            messageText.append("<hr>");

            // Order information
            messageText.append("<h3>Order Details</h3>");
            messageText.append("<table style='width: 100%; border-collapse: collapse;'>");
            messageText.append("<tr><td style='padding: 8px; border: 1px solid #ddd;'><strong>Order ID:</strong></td><td style='padding: 8px; border: 1px solid #ddd;'>" + order.getOrderId() + "</td></tr>");
            messageText.append("<tr><td style='padding: 8px; border: 1px solid #ddd;'><strong>Order Date:</strong></td><td style='padding: 8px; border: 1px solid #ddd;'>" + order.getOrderDate() + "</td></tr>");
            messageText.append("<tr><td style='padding: 8px; border: 1px solid #ddd;'><strong>Total Amount:</strong></td><td style='padding: 8px; border: 1px solid #ddd;'>" + order.getTotalPrice() + " $</td></tr>");
            messageText.append("</table>");

            // Get order details for shipping information
            List<OrderDetails> orderItems = orderDetailsDAO.getOrderDetailsByOrderId(order.getOrderId());
            OrderDetails firstOrderDetail = orderItems.isEmpty() ? null : orderItems.get(0);

            // Determine shipping address and phone
            String shippingName = firstOrderDetail != null && firstOrderDetail.getName() != null
                    ? firstOrderDetail.getName()
                    : order.getCustomerId().getCustomerName();

            String shippingAddress = firstOrderDetail != null && firstOrderDetail.getAddress() != null && !firstOrderDetail.getAddress().trim().isEmpty()
                    ? firstOrderDetail.getAddress()
                    : order.getCustomerId().getAddress();

            String shippingPhone = firstOrderDetail != null && firstOrderDetail.getPhone() != null && !firstOrderDetail.getPhone().trim().isEmpty()
                    ? firstOrderDetail.getPhone()
                    : order.getCustomerId().getPhone();

            // Shipping address
            messageText.append("<h3>Shipping Address</h3>");
            messageText.append("<p style='color: #555;'>" + shippingName + "<br>"
                    + shippingAddress + "<br>"
                    + shippingPhone + "</p>");

            // Order items
            messageText.append("<h3>Order Items</h3>");
            messageText.append("<table style='width: 100%; border-collapse: collapse;'>");
            messageText.append("<thead><tr>");
            messageText.append("<th style='padding: 8px; border: 1px solid #ddd; background-color: #f8f8f8;'>Product</th>");
            messageText.append("<th style='padding: 8px; border: 1px solid #ddd; background-color: #f8f8f8;'>Quantity</th>");
            messageText.append("<th style='padding: 8px; border: 1px solid #ddd; background-color: #f8f8f8;'>Price</th>");
            messageText.append("</tr></thead>");
            messageText.append("<tbody>");

            for (OrderDetails item : orderItems) {
                messageText.append("<tr>");
                messageText.append("<td style='padding: 8px; border: 1px solid #ddd;'>" + item.getProductId().getProductName() + "</td>");
                messageText.append("<td style='padding: 8px; border: 1px solid #ddd; text-align: center;'>" + item.getQuantity() + "</td>");
                messageText.append("<td style='padding: 8px; border: 1px solid #ddd; text-align: right;'>" + item.getUnitPrice() + " $</td>");
                messageText.append("</tr>");
            }

            messageText.append("</tbody>");
            messageText.append("</table>");

            // Footer
            messageText.append("<hr>");
            messageText.append("<p style='text-align: center; color: #555;'><br>Thank you for shopping with us!</p>");
            messageText.append("</div>");

            mailService.send(order.getCustomerId().getEmail(), subject, messageText.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * RECORDS_PER_PAGE;
        List<Order> orders = orderDao.getAllOrdersWarehouse(offset, RECORDS_PER_PAGE);
        int totalRecords = orderDao.getOrderCount();
        int totalRecords1 = orderDao.getOrderCount1();
        int totalRecords4 = orderDao.getOrderCount4();
        int totalRecords5 = orderDao.getOrderCount5();
        int totalRecords6 = orderDao.getOrderCount6();
        int totalRecords7 = orderDao.getOrderCount7();
        int totalRecords8 = orderDao.getOrderCount8();
        int totalRecords2 = orderDao.getOrderCount2();

        int totalPages = (int) Math.ceil((double) totalRecords / RECORDS_PER_PAGE);
        request.setAttribute("orderDetailsList", orders);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("allorder", totalRecords);
        request.setAttribute("allorder1", totalRecords1);
        request.setAttribute("allorder4", totalRecords4);
        request.setAttribute("allorder5", totalRecords5);
        request.setAttribute("allorder6", totalRecords6);
        request.setAttribute("allorder7", totalRecords7);
        request.setAttribute("allorder8", totalRecords8);
        request.setAttribute("allorder2", totalRecords2);

        // Check if orderId and newStatus are being sent for status update
        String orderIdRaw = request.getParameter("orderId");
        String newStatus = request.getParameter("newStatus");
        if (orderIdRaw != null && newStatus != null) {
            try {
                int orderId = Integer.parseInt(orderIdRaw);
                int statusId;
                // Variable to store the redirect type
                int redirectType;

                switch (newStatus) {
                    case "Processing":
                        statusId = 4;
                        redirectType = 4;
                        break;
                    case "Packed":
                        statusId = 5;
                        redirectType = 5;
                        break;
                    case "Shipping":
                        statusId = 6;
                        redirectType = 6;
                        orderDao.updateProductOnDelivery(orderId);
                        break;
                    case "Success":
                        statusId = 7;
                        redirectType = 7;
                        break;
                    case "Failed":
                        statusId = 8;
                        redirectType = 8;
                        orderDao.returnProductToStore(orderId);
                        break;
                    case "Cancel":
                        statusId = 1;
                        redirectType = 1;
                        orderDao.decreaseProductHold(orderId);
                        break;
                    default:
                        statusId = 2;
                        redirectType = 2;
                }

                // Update the order status in the database
                boolean updateSuccess = orderDao.updateStatus(orderId, statusId);
                if (updateSuccess) {
                    // If status is updated to Processing (4), send email notification
                    if (statusId == 7) {
                        Order order = orderDao.getOrderByID(orderId);
                        sendProcessingNotification(order);
                    }
                    session.setAttribute("successMessage", "Order status updated successfully.");
                    // Redirect to the corresponding type page
                    response.sendRedirect("warehouse?type=" + redirectType);
                    return;
                } else {
                    session.setAttribute("errorMessage", "Failed to update order status.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid orderId: " + e.getMessage());
                session.setAttribute("errorMessage", "Invalid order ID.");
            }
        }
        request.getRequestDispatcher("warehousestaff.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "WarehouseStaffController handles order management for warehouse staff.";
    }
}
