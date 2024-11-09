
<%@page import="model.Customer"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <footer class="bg-footer">
        <div class="container">
            <div class="row">
                <div class="col-xl-5 col-lg-4 mb-0 mb-md-4 pb-0 pb-md-2">
                    <a href="home" class="logo-footer">
                        <img src="assets/images/bg/fpt.png" height="50" alt="">
                    </a>
                    <p class="mt-4 me-xl-5">5PGear – Your ultimate destination for premium gaming gear that powers up your play and takes performance to the next level</p>
                </div>

                <div class="col-xl-7 col-lg-8 col-md-12">
                    <div class="row">
                        <div class="col-md-6 col-12 mt-4 mt-sm-0 pt-2 pt-sm-0">
                            <h5 class="text-light title- footer-head">Product</h5>
                            <ul class="list-unstyled footer-list mt-4">
                                <li><a href="contact" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Contact Us</a></li>
                                <%
                                    Customer account = (Customer) session.getAttribute("account");
                                    if (account != null) {
                                %>
                                <li><a href="register" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Register</a></li>
                                <%
                                    } else {
                                %>
                                <li><a href="register" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Register</a></li>
                                <%
                                    }
                                %>
                                
                                <li><a href="blogcontroller" class="text-foot"><i class="mdi mdi-chevron-right me-1"></i> Blog</a></li>
                            </ul>
                        </div>

                        <div class="col-md-6 col-12 mt-4 mt-sm-0 pt-2 pt-sm-0">
                            <h5 class="text-light title- footer-head">Contact us</h5>
                            <ul class="list-unstyled footer-list mt-4">
                                <li class="d-flex align-items-center">
                                    <i data-feather="mail" class="fea icon-sm text-foot align-middle"></i>
                                    <a href="mailto:support@doctriscare.ml" class="text-foot ms-2">5PSho@fpt.edu.vn</a>
                                </li>

                                <li class="d-flex align-items-center">
                                    <i data-feather="phone" class="fea icon-sm text-foot align-middle"></i>
                                    <a href="tel:+152534-468-854" class="text-foot ms-2">+84 822926203</a>
                                </li>

                                <li class="d-flex align-items-center">
                                    <i data-feather="map-pin" class="fea icon-sm text-foot align-middle"></i>
                                    <a target="_blank" href="https://maps.app.goo.gl/Jv4srRXmpG8iZmdN7" class="video-play-icon text-foot ms-2">Find our address here</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="container mt-5">
            <div class="pt-4 footer-bar">
                <div class="align-items-center">
                    <div class="text-sm-start text-center">
                        <p class="mb-0">
                            <script>
                                document.write(new Date().getFullYear())
                            </script> © 5P Gear
                    </div>
                </div>
            </div>
        </div>
    </footer>
</html>
