<%@page import="java.net.URLEncoder"%>
<%@page import="java.nio.charset.StandardCharsets"%>
<%@ page import="config.Config" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="java.util.Enumeration"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="dal.OrderDAO" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page="layout/head.jsp"/>
    <body>
        <jsp:include page="layout/menu.jsp"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">

        <!-- Breadcrumb -->
        <nav class="breadcrumb-nav" style="margin-top: 100px;">
            <div class="container">
                <ol class="breadcrumb" style="display: flex; justify-content: center;">
                    <li class="breadcrumb-item" style="font-size: 20px;"><a href="cart">Shopping Cart</a></li>
                    <li class="breadcrumb-item" style="font-size: 20px;"><a href="cart">Payment</a></li>
                    <li class="breadcrumb-item active" aria-current="page" style="font-size: 20px;">Thank You</li>
                </ol>
            </div>
        </nav>

        <!-- Checkout Form -->
        <main class="main checkout my-5">
            <div class="container-fluid px-0">
                <div class="row justify-content-center">
                    <!-- Thông tin trạng thái đơn hàng -->
                    <div class="col-md-6 text-center">
                        <div class="thank-you-message p-4 bg-light rounded">
                            <h2 class="mb-4" style="color: #28a745;">Thank You for Your Purchase!</h2>
                            <%
                                // Lấy trạng thái giao dịch
                                Map fields = new HashMap();
                                for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
                                    String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
                                    String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
                                    if ((fieldValue != null) && (fieldValue.length() > 0)) {
                                        fields.put(fieldName, fieldValue);
                                    }
                                }

                                String vnp_SecureHash = request.getParameter("vnp_SecureHash");
                                if (fields.containsKey("vnp_SecureHashType")) {
                                    fields.remove("vnp_SecureHashType");
                                }
                                if (fields.containsKey("vnp_SecureHash")) {
                                    fields.remove("vnp_SecureHash");
                                }
                                String signValue = Config.hashAllFields(fields);
                                String transactionStatus = "";

                                if (signValue.equals(vnp_SecureHash)) {
                                    if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                                        transactionStatus = "Success";
                                    } else {
                                        transactionStatus = "Failed";
                                        Integer orderId = (Integer) session.getAttribute("orderId");

                                    if (orderId != null) {
                                        OrderDAO orderDAO = new OrderDAO();
                                        orderDAO.decreaseProductHold(orderId);
                                        int status = 8;
                                        orderDAO.updateStatus(orderId, status);
                                    }
                                    }
                                } else {
                                    transactionStatus = "Invalid signature";
                                }
                            %>
                            <p class="mb-3" style="font-size: 18px;">Transaction Status: <strong><%= transactionStatus %></strong></p>
                            <p class="mb-3" style="font-size: 18px;">Order ID: <strong>${orderId}</strong></p>
                            <p class="mb-3" style="font-size: 18px;">You can track your order status in the <a href="myoderlist" style="text-decoration: underline;">My Order</a> section.</p>
                            <i class="fas fa-smile-beam fa-4x" style="color: #28a745;"></i>
                        </div>
                    </div>

                    <!-- Các button điều hướng -->
                    <div class="col-12 text-center mt-4">
                        <a href="home" class="btn btn-primary me-3">Home</a>
                        <a href="myoderlist" class="btn btn-secondary">My Order</a>
                    </div>
                </div>
            </div>
        </main>

        <jsp:include page="layout/footer.jsp"/>

        <!-- Script files -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
    </body>
</html>
