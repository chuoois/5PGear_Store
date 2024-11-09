<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Customer"%> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <jsp:include page="layout/head.jsp"/>
    <body>
        <div class="back-to-home rounded d-none d-sm-block">
            <a href="home" class="btn btn-icon btn-primary"><i data-feather="home" class="icons"></i></a>
        </div>
        <!-- Hero Start -->
        <section class="bg-home d-flex bg-light align-items-center">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 col-md-8">
                        <div class="text-center mb-4">
                            <img src="assets/images/bg/fpt.png" alt="FPT Logo" class="img-fluid" style="max-width: 200px;">
                        </div>
                        
                        <div class="card login-page bg-white shadow mt-4 rounded border-0">
                            <div class="card-body">
                                <h4 class="text-center">Change Password</h4>
                                <% 
                                 String successMessage = (String) session.getAttribute("successfull");
                                if (successMessage != null) { 
                                %>
                                <div class="alert alert-success alert-dismissible fade show" role="alert" id="success-alert">
                                    <%= successMessage %>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                                <script>
                                    document.addEventListener("DOMContentLoaded", function () {
                                        setTimeout(function () {
                                            window.location.href = 'login.jsp'; // Redirect after 5 seconds
                                        }, 2000); // 2 seconds delay
                                    });
                                </script>
                                <% 
                                    session.removeAttribute("successfull"); // Remove the message from session after displaying
                                } 
                                %>


                                <!-- Hiển thị thông báo lỗi -->
                                <% 
                                String error = (String) request.getAttribute("error");
                                if (error != null) { 
                                %>
                                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                    <%= error %>
                                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                                </div>
                                <% } %>
                                <form action="changepass" method="POST" class="login-form mt-4" onSubmit="document.getElementById('submit').disabled = true;">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <p class="text-muted">Please enter your new password.</p>
                                            <div class="mb-3">
                                                <label class="form-label">New Password : <span class="text-danger">*</span></label>
                                                <input value="${password}" oninvalid="CheckPassword(this);" oninput="CheckPassword(this);" type="password" id="password" class="form-control" name="password" required="">
                                            </div>
                                            <div class="mb-3">
                                                <label class="form-label">Confirm New Password : <span class="text-danger">*</span></label>
                                                <input value="${repassword}" oninvalid="CheckRePassword(this);" oninput="CheckRePassword(this);" type="password" class="form-control" name="repassword" required="">
                                            </div>
                                                <div id="confirmPasswordError" class="text-danger">
                                                    <%= request.getAttribute("confirmPasswordError") != null ? request.getAttribute("confirmPasswordError") : "" %>
                                                </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="d-grid">
                                                <button id="submit" class="btn btn-primary">Change Password</button>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
    </body>
</html>
