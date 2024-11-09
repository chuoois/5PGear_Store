package controller.order;

import dal.OrderDetailsDAO; // Ensure this class handles database access
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import model.OrderDetails; // Import your OrderDetails model

public class OrderDetailsController extends HttpServlet {

    private OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the order ID from the request
        String orderIdRaw = request.getParameter("orderID");
        int orderID = 0;

        try {
            orderID = Integer.parseInt(orderIdRaw);
        } catch (NumberFormatException e) {
            System.out.println("Invalid order ID: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
            return; // Exit early on error
        }

        // Fetch order details
        List<OrderDetails> orderDetailsList = orderDetailsDAO.getOrderDetailsByOrderId(orderID);

        // Set the content type for the response
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Check if the list is not empty
        if (orderDetailsList.isEmpty()) {
            out.println("<h5>No order details found for this order ID.</h5>");
            return;
        }

        // Assuming the first item in the list contains the shipping info
        OrderDetails firstDetail = orderDetailsList.get(0);
        String detailaddress = firstDetail.getAddress();
        String detailphone = firstDetail.getPhone();
        String detailname = firstDetail.getName();
        // Construct the HTML for shipping information
        out.println("<h5>Customer Information</h5>");
        out.println("<table class='table table-bordered'>");
        out.println("<tbody>");
        out.println("<tr><td>Order ID</td><td>" + firstDetail.getOrderDetailsId() + "</td></tr>");

        out.println("<tr><td>Name</td><td>" + (detailname != null && !detailname.isEmpty() ? detailname : firstDetail.getOrderId().getCustomerId().getCustomerName()) + "</td></tr>");
        out.println("<tr><td>Phone</td><td>" + (detailphone != null && !detailphone.isEmpty() ? detailphone : firstDetail.getOrderId().getCustomerId().getPhone()) + "</td></tr>");
        out.println("<tr><td>Address</td><td>" + (detailaddress != null && !detailaddress.isEmpty() ? detailaddress : firstDetail.getOrderId().getCustomerId().getAddress()) + "</td></tr>");
        out.println("</tbody>");
        out.println("</table>");

        // Construct the HTML for order details
        out.println("<h5>Product Details</h5>");
        out.println("<table class='table table-bordered'>");
        out.println("<thead>");
        out.println("<tr><th>Product Image</th><th>Product Name</th><th>Quantity</th><th>Price</th></tr>");

        out.println("</thead>");
        out.println("<tbody>");

        // Loop through each order detail and display it
        for (OrderDetails detail : orderDetailsList) {
            BigDecimal total = detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity()));
            out.println("<tr>");
            out.println("<td><img src='" + detail.getProductId().getImgPath() + "' width='50' alt='Product Image'/></td>");
            out.println("<td>" + detail.getProductId().getProductName() + "</td>");
            out.println("<td>" + detail.getQuantity() + "</td>");

            out.println("<td>" + total + "$</td>");

            out.println("</tr>");
        }

        out.println("</tbody>");
        out.println("</table>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Order Details Controller";
    }
}
