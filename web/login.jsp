<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="layout/head.jsp"/>

    <body>
        <div class="back-to-home rounded d-none d-sm-block">
            <a href="home" class="btn btn-icon btn-primary"><i data-feather="home" class="icons"></i></a>
        </div>

        <!-- Hero Start -->
        <section class="bg-half-150 d-table w-100 bg-light">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-lg-5 col-md-8">
                        <div class="text-center mb-4">
                            <img src="assets/images/bg/fpt.png" alt="FPT Logo" class="img-fluid" style="max-width: 200px;">
                        </div>
                        <div class="card login-page bg-white shadow mt-4 rounded border-0">
                            <div class="card-body">
                                <h4 class="text-center">Login</h4>

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
                                <!-- Form đăng nhập --> 
                                <form action="login" class="login-form mt-4" method="post">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Email <span class="text-danger">*</span></label>
                                                <input type="email" class="form-control" placeholder="Email" name="email" id="email" 
                                                       value="${cookie.cemail.value}" required>
                                                <div id="emailError" class="text-danger">
                                                    <%= request.getAttribute("emailError") != null ? request.getAttribute("emailError") : "" %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Password <span class="text-danger">*</span></label>
                                                <input type="password" class="form-control" placeholder="Password" name="password" id="password" 
                                                       value="${cookie.cpass.value}" required>
                                                <div id="passwordError" class="text-danger">
                                                    <%= request.getAttribute("passwordError") != null ? request.getAttribute("passwordError") : "" %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-12">
                                            <div class="d-flex justify-content-between">
                                                <div class="mb-3">
                                                    <div class="form-check">
                                                        <input class="form-check-input align-middle" type="checkbox" 
                                                               ${(cookie.crem != null ? 'checked' : '')}
                                                               name="remember" value="ON"
                                                               id="remember-check" >
                                                        <label class="form-check-label" for="remember-check">Remember me</label>
                                                    </div>
                                                </div>
                                                <a href="forgotpass" class="text-dark h6 mb-0">Forgot Password ?</a>
                                            </div>
                                        </div>
                                        <!-- Nút đăng ký login -->                 
                                        <div class="col-md-12 mb-3">
                                            <div class="d-grid">
                                                <button class="btn btn-primary">Login</button>
                                            </div>
                                        </div>
                                        <!-- Nút đăng ký google --> 
                                        <div class="col-md-12 mb-3">
                                            <div class="d-grid">
                                                <a href="https://accounts.google.com/o/oauth2/auth?scope=email%20profile%20openid&redirect_uri=http://localhost:8080/SWP391Gr5/login&response_type=code&client_id=747621299770-5o32uddebj2520537cpb9aplufl15q1s.apps.googleusercontent.com&access_type=offline" class="btn btn-soft-primary">
                                                    <i class="uil uil-google"></i> Google
                                                </a>
                                            </div>
                                        </div>
                                        <!-- Chuyển sang trang đăng ký -->                
                                        <div class="mx-auto mt-3">
                                            <span class="mb-0 mt-3"><small class="text-dark me-2">Don't have an account?</small> <a href="register" class="ms-auto">Sign up</a></span>
                                        </div>

                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <!-- Hero End -->

        <!-- JavaScript -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <!-- Icons -->
        <script src="assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="assets/js/app.js"></script>
    </body>
</html>
