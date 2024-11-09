<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                            <p class="mb-3" style="font-size: 18px;">Transaction Status: <strong>Success</strong></p>
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
