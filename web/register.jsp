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
                                <h4 class="text-center">Sign Up</h4>

                                <!-- Display success message if exists -->
                                <%
                                    String successMessage = (String) request.getAttribute("successMessage");
                                    if (successMessage != null) {
                                %>
                                <div class="alert alert-success alert-fixed" role="alert" id="successMessage">
                                    <%= successMessage %>
                                </div>

                                <script>
                                    document.addEventListener("DOMContentLoaded", function () {
                                        var successMessage = document.getElementById('successMessage');
                                        successMessage.classList.add('alert-show');
                                        setTimeout(function () {
                                            successMessage.classList.remove('alert-show');
                                            window.location.href = 'login.jsp';
                                        }, 5000); // 2 seconds delay
                                    });
                                </script>
                                <%
                                    }
                                %>
                                <!-- Form đăng ký -->
                                <form action="register" class="login-form mt-4" method="post">
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Full Name <span class="text-danger">*</span></label>
                                                <input type="text" class="form-control" placeholder="Full Name" name="fullname" id="fullName" 
                                                       value="<%= request.getAttribute("fullname") != null ? request.getAttribute("fullname") : "" %>" required>
                                                <div id="fullnameError" class="text-danger">
                                                    <%= request.getAttribute("fullnameError") != null ? request.getAttribute("fullnameError") : "" %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Your Email <span class="text-danger">*</span></label>
                                                <input type="email" class="form-control" placeholder="Email" name="email" id="email" 
                                                       value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required>
                                                <div id="emailError" class="text-danger">
                                                    <%= request.getAttribute("emailError") != null ? request.getAttribute("emailError") : "" %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Address</label>
                                                <input type="text" class="form-control" placeholder="Address" name="address" id="address"
                                                       value="<%= request.getAttribute("address") != null ? request.getAttribute("address") : "" %>">
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Phone </label>
                                                <input type="text" class="form-control" placeholder="Phone" name="phone" id="phone" 
                                                       value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>">
                                                <div id="phoneError" class="text-danger">
                                                    <%= request.getAttribute("phoneError") != null ? request.getAttribute("phoneError") : "" %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Date Of Birth <span class="text-danger">*</span></label>
                                                <input type="date" class="form-control" placeholder="Date Of Birth" name="dob" id="dob" 
                                                       value="<%= request.getAttribute("dob") != null ? request.getAttribute("dob") : "" %>" required>
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="gender" class="form-label">Gender  <span class="text-danger">*</span></label>
                                            <div class="d-flex">
                                                <div class="form-check me-3">
                                                    <input class="form-check-input" type="radio" name="gender" id="genderMale" 
                                                           value="Male" <%= "Male".equals(request.getAttribute("gender")) ? "checked" : "" %>>
                                                    <label class="form-check-label" for="genderMale">Male</label>
                                                </div>
                                                <div class="form-check me-3">
                                                    <input class="form-check-input" type="radio" name="gender" id="genderFemale" 
                                                           value="Female" <%= "Female".equals(request.getAttribute("gender")) ? "checked" : "" %>>
                                                    <label class="form-check-label" for="genderFemale">Female</label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="gender" id="genderOther" 
                                                           value="Other" <%= "Other".equals(request.getAttribute("gender")) ? "checked" : "" %> checked>
                                                    <label class="form-check-label" for="genderOther">Other</label>
                                                </div>
                                            </div>
                                        </div>  
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Password <span class="text-danger">*</span></label>
                                                <input type="password" oninput="CheckPassword(this);" class="form-control" name="password" id="password" placeholder="Password" 
                                                       pattern="^(?=.*[A-Z])(?=.*[!@#$%^&*(),.?:{}|<>]).{8,}"
                                                       value = "<%= request.getAttribute("password") != null ? request.getAttribute("password") : "" %>">
                                                <div id="passwordError" class="text-danger">
                                                    <%= request.getAttribute("passwordError") != null ? request.getAttribute("passwordError") : "" %>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-12">
                                            <div class="mb-3">
                                                <label class="form-label">Confirm Password <span class="text-danger">*</span></label>
                                                <input type="password" class="form-control" placeholder="Confirm Password" name="confirmPassword" id="confirmPassword"
                                                       value = "<%= request.getAttribute("confirmPassword") != null ? request.getAttribute("confirmPassword") : "" %>">
                                                <div id="confirmPasswordError" class="text-danger">
                                                    <%= request.getAttribute("confirmPasswordError") != null ? request.getAttribute("confirmPasswordError") : "" %>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Nút đăng ký -->        
                                        <div class="col-md-12">
                                            <div class="d-grid">
                                                <button class="btn btn-primary">Register</button>
                                            </div>
                                        </div>
                                        <!-- Link chuyển sang tran login -->
                                        <div class="mx-auto mt-3">
                                            <span class="mb-0 mt-3"><small class="text-dark me-2">Already have an account?</small> <a href="login" class="ms-auto">Sign in</a> </span>
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
