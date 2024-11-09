<%@page import="model.Customer"%> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="layout/head.jsp"/>
    <body>
        <!-- Back to Home Button -->
        <div class="back-to-home rounded d-none d-sm-block">
            <a href="home" class="btn btn-icon btn-primary"><i data-feather="home" class="icons"></i></a>
        </div>
        <!-- User Profile Section -->
        <section class="bg-light">
            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-lg-10 col-md-12">
                        <div class="card shadow rounded border-0">
                            <div class="card-body">
                                <div class="text-center mb-4">
                                    <img src="assets/images/bg/fpt.png" alt="FPT Logo" class="img-fluid" style="max-width: 200px;">
                                </div>

                                <ul class="nav nav-tabs" id="profileTabs">
                                    <li class="nav-item">
                                        <a class="nav-link active" data-bs-toggle="tab" href="#userprofile">User Profile</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" data-bs-toggle="tab" href="#changepassword">Change Password</a>
                                    </li>
                                </ul>
                                <div class="tab-content mt-3">
                                    <!-- User Profile Tab -->
                                    <div class="tab-pane fade show active" id="userprofile">
                                        <form class="form" action="updatep" method="post" id="profileForm" enctype="multipart/form-data">
                                            <h4 class="text-center mb-4">Your Profile</h4>
                                            <div class="row">
                                                <div class="col-md-3 text-center">
                                                    <img src="${account.avatar_url != null ? account.avatar_url : 'http://ssl.gstatic.com/accounts/ui/avatar_2x.png'}" class="avatar img-thumbnail" alt="avatar">
                                                    <h6></h6>
                                                    <input type="file" class="text-center center-block file-upload" id="fileUpload" name="avatar" >
                                                    <c:if test="${param.error == 'invalid_file_type'}">
                                                        <div class="alert alert-danger">Only image files are allowed!</div>
                                                    </c:if>
                                                </div>
                                                <div class="col-md-9">
                                                    <div class="mb-3">
                                                        <label for="full_name" class="form-label">Full Name</label>
                                                        <input type="text" class="form-control" name="full_name" id="full_name" placeholder="Full name" 
                                                               value="${account.customerName}">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="email" class="form-label">Email</label>
                                                        <input type="email" class="form-control" name="email" id="email" placeholder="you@email.com" readonly value="${account.email}">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="address" class="form-label">Address</label>
                                                        <input type="text" class="form-control" name="address" id="address" placeholder="Your address" value="${account.address}">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="date_of_birth" class="form-label">Date of Birth</label>
                                                        <input type="date" class="form-control" name="date_of_birth" id="date_of_birth" value="${account.dobString}">
                                                    </div>
                                                    <div class="mb-3 ">
                                                        <label class="form-label">Gender</label>
                                                        <div class="form-check">
                                                            <input type="radio" class="form-check-input" name="gender" id="male" value="male" 
                                                                   <c:if test="${account.gender == 'male'}">checked</c:if>>
                                                                   <label class="form-check-label" for="male">Male</label>
                                                            </div>
                                                            <div class="form-check">
                                                                <input type="radio" class="form-check-input" name="gender" id="female" value="female" 
                                                                <c:if test="${account.gender == 'female'}">checked</c:if>>
                                                                <label class="form-check-label" for="female">Female</label>
                                                            </div>
                                                            <div class="form-check">
                                                                <input type="radio" class="form-check-input" name="gender" id="other" value="other" 
                                                                <c:if test="${account.gender == 'other'}">checked</c:if>>
                                                                <label class="form-check-label" for="other">Other</label>
                                                            </div>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="phone" class="form-label">Phone</label>
                                                            <input type="text" class="form-control" name="phone" id="phone" 
                                                                   value="${requestScope.phone != null ? requestScope.phone : account.phone}" />
                                                        <div id="ErroPhone" class="text-danger">
                                                            <%= request.getAttribute("ErroPhone") != null ? request.getAttribute("ErroPhone") : "" %> 
                                                        </div>
                                                    </div>
                                                    <div class="d-flex">
                                                        <button class="btn btn-primary me-2" type="submit">Save</button>

                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>

                                    <!-- Change Password Tab -->
                                    <div class="tab-pane fade" id="changepassword">
                                        <h4 class="text-center mb-4">Change Password</h4>
                                        <c:if test="${not empty passsuccess}">
                                            <div class="alert alert-success text-center" role="alert">
                                                ${passsuccess}
                                            </div>
                                        </c:if>
                                        <form class="form" action="profile" method="POST" id="changePasswordForm">
                                            <div class="mb-3">
                                                <label for="current_password" class="form-label">Current Password</label>
                                                <input type="password" class="form-control" name="current_password" id="current_password" placeholder="Current password">
                                            </div>
                                            <div class="mb-3">
                                                <label for="new_password" class="form-label">New Password</label>
                                                <input type="password" class="form-control" name="new_password" id="new_password" placeholder="New password">
                                                <div id="passwordError" class="text-danger">
                                                    <%= request.getAttribute("passwordError") != null ? request.getAttribute("passwordError") : "" %>
                                                </div>
                                            </div>
                                            <div class="mb-3">
                                                <label for="confirm_new_password" class="form-label">Confirm New Password</label>
                                                <input type="password" class="form-control" name="confirm_new_password" id="confirm_new_password" placeholder="Confirm new password">
                                                <div id="confirmPasswordError" class="text-danger">
                                                    <%= request.getAttribute("confirmPasswordError") != null ? request.getAttribute("confirmPasswordError") : "" %>
                                                </div>
                                            </div>
                                            <div class="d-flex">

                                                <button class="btn btn-primary me-2" type="submit">Save</button>

                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <!-- Icons -->
        <script src="assets/js/feather.min.js"></script>
        <!-- Main Js -->
        <script src="assets/js/app.js"></script>
        <script>
            $(document).ready(function () {
                $('#fileUpload').on('change', function () {
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $('.avatar').attr('src', e.target.result);
                    }
                    reader.readAsDataURL(this.files[0]);
                });
            });
            document.addEventListener('DOMContentLoaded', function () {
                // Function to activate tab based on hash
                function activateTabFromHash() {
                    var hash = window.location.hash;
                    if (hash) {
                        var tabLink = document.querySelector('.nav-link[href="' + hash + '"]');
                        if (tabLink) {
                            var tab = new bootstrap.Tab(tabLink);
                            tab.show();
                        }
                    }
                }

                // Activate tab on page load
                activateTabFromHash();

                // Activate tab when hash changes
                window.addEventListener('hashchange', activateTabFromHash);

                // Update hash when tab is shown
                var tabLinks = document.querySelectorAll('.nav-link');
                tabLinks.forEach(function (tabLink) {
                    tabLink.addEventListener('shown.bs.tab', function (event) {
                        window.location.hash = event.target.hash;
                    });
                });
            });
        </script>
    </body>
</html>


