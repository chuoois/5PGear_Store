<%@page import="model.Customer"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <header id="topnav" class="navigation sticky">
        <div class="container">

            <div>
                <a class="logo" href="home">
                    <img src="assets/images/bg/fpt.png" height="60" class="l-light" alt="">
                    <img src="assets/images/bg/fpt.png" class="l-dark" height="60" alt="">
                </a>
            </div>

            <div class="menu-extras">
                <div class="menu-item">
                    <a class="navbar-toggle" id="isToggle" onclick="toggleMenu()">
                        <div class="lines">
                            <span></span>
                            <span></span>
                            <span></span>
                        </div>
                    </a>
                </div>
            </div>

            <ul class="dropdowns list-inline mb-0">
                <li class="list-inline-item mb-0 ms-1">
                    <div class="dropdown dropdown-primary">
                        <%
                        Customer account = (Customer) session.getAttribute("account");
                        String avatarUrl = "assets/images/avata.png"; // default avatar
                        if (account != null) {
                            String accountAvatar = account.getAvatar_url();
                            if (accountAvatar != null && !accountAvatar.isEmpty()) {
                                avatarUrl = accountAvatar;
                            }
                        %>
                        <button type="button" class="btn btn-pills btn-soft-primary dropdown-toggle p-0" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <img src="<%= avatarUrl %>" class="avatar avatar-md-sm rounded-circle shadow" alt="Avatar">
                        </button>
                        <%
                        } else {
                        %>
                        <div class="d-flex justify-content-between">
                            <button class="btn btn-primary flex-grow-1 me-1 p-1" onclick="window.location.href = 'login'">Login</button>
                            <button class="btn btn-primary flex-grow-1 ms-1 p-1" onclick="window.location.href = 'register'">Sign Up</button>
                        </div>
                        <%
                        }
                        %>

                        <div class="dropdown-menu dd-menu dropdown-menu-end bg-white shadow border-0 mt-3 py-3" style="min-width: 200px;">
                            <%
                                if (account != null) {
                            %>
                            <a class="dropdown-item d-flex align-items-center text-" href="profile">
                                <img src="<%= avatarUrl %>" class="avatar avatar-md-sm rounded-circle shadow" alt="Avatar">
                                <div class="flex-1 ms-2">
                                    <span class="d-block mb-1"><%= account.getCustomerName() %></span> <!-- Changed getFullname() to getCustomerName() -->
                                </div>
                            </a>
                            <%
                                }
                            %>
                            <div class="dropdown-divider border-top"></div>
                            <%
                                if (account != null) {
                            %>
                            <a class="dropdown-item text-" href = "profile#userprofile"><span class="mb-0 d-inline-block me-1"><i class="uil uil-user align-middle h6"></i></span> Tài khoản của tôi</a>
                            <a class="dropdown-item text-" href="profile#changepassword"><span class="mb-0 d-inline-block me-1"><i class="uil uil-lock-alt align-middle h6"></i></span> Thay đổi mật khẩu</a>
                            <a class="dropdown-item text-" href="logout"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-out-alt align-middle h6"></i></span> Đăng xuất</a>
                                    <%
                                        } else {
                                    %>
                            <a class="dropdown-item text-" href="login"><span class="mb-0 d-inline-block me-1"><i class="uil uil-sign-in-alt align-middle h6"></i></span> Đăng Nhập</a>
                                    <%
                                        }
                                    %>
                        </div>
                    </div>
                </li>
            </ul>

            <div id="navigation" style="margin-left: 200px ; ">
                <ul class="navigation-menu nav-left nav-light">
                    <!-- Home -->
                    <li><a href="home" class="sub-menu-item">Home</a></li>

                    <!-- Products Dropdown -->
                    <li class="has-submenu">
                        <a href="product" class="sub-menu-item">Product <span class="dropdown-indicator"></span></a>
                    </li>

                    <!-- Blog -->
                    <li><a href="blogcontroller" class="sub-menu-item">Blog</a></li>

                    <!-- Conditional Support Link -->




                    <!-- Track Order -->
                    <li><a href="myoderlist" class="sub-menu-item">My Order</a></li>

                    <!-- Daily Deals -->
                     <li><a href="cart" class="sub-menu-item">Cart</a></li>
                </ul>
            </div>
        </div>
    </header>
</html>
