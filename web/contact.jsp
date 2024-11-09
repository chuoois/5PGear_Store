<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="layout/head.jsp"/>
    <body>
        <jsp:include page="layout/menu.jsp"/>

        <!-- Start Hero -->
        <section class="bg-half-170 d-table w-100" style="background: url('assets/images/bg/fpt.png');">
            <div class="bg-overlay bg-overlay-dark"></div>
            <div class="container">
                <div class="row mt-5 justify-content-center">
                    <div class="col-12">
                        <div class="section-title text-center">
                            <h3 class="sub-title mb-4 text-white title-dark">5P Gear</h3>
                            <nav aria-label="breadcrumb" class="d-inline-block mt-3">
                                <ul class="breadcrumb bg-light rounded mb-0 py-1 px-2">
                                    <li class="breadcrumb-item"><a href="home">Home</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Contact Us</li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Shape Divider -->
        <div class="position-relative">
            <div class="shape overflow-hidden text-white">
                <svg viewBox="0 0 2880 48" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M0 48H1437.5H2880V0H2160C1442.5 52 720 0 720 0H0V48Z" fill="currentColor"></path>
                </svg>
            </div>
        </div>

        <!-- Contact Info Section -->
        <section class="mt-100 mt-60">
            <div class="container">
                <div class="row">
                    <!-- Contact Information Cards -->
                    <div class="col-lg-4 col-md-6">
                        <div class="card features feature-primary text-center border-0">
                            <div class="icon text-center rounded-md mx-auto">
                                <i class="uil uil-phone h3 mb-0"></i>
                            </div>
                            <div class="card-body p-0 mt-3">
                                <h5>HotLine</h5>
                                <a href="tel:888195313" class="link">+84 378757198</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6 mt-4 mt-sm-0 pt-2 pt-sm-0">
                        <div class="card features feature-primary text-center border-0">
                            <div class="icon text-center rounded-md mx-auto">
                                <i class="uil uil-envelope h3 mb-0"></i>
                            </div>
                            <div class="card-body p-0 mt-3">
                                <h5>Email</h5>
                                <a href="mailto:support@doctriscare.ml" class="link">Group5@fpt.edu.vn</a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4 col-md-6 mt-4 mt-lg-0 pt-2 pt-lg-0">
                        <div class="card features feature-primary text-center border-0">
                            <div class="icon text-center rounded-md mx-auto">
                                <i class="uil uil-map-marker h3 mb-0"></i>
                            </div>
                            <div class="card-body p-0 mt-3">
                                <h5>Address</h5>
                                <a href="https://maps.app.goo.gl/4kutyvsn1ZDfi6AL8" class="link">FPT University</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Contact Form Section -->
            <div class="container mt-100 mt-60">
                <div class="row align-items-center">
                    <div class="col-lg-5 col-md-6">
                        <div class="me-lg-5">
                            <img src="assets/images/bg/fpt.png" class="img-fluid" alt="">
                        </div>
                    </div>
                    <div class="col-lg-7 col-md-6 mt-4 pt-2 mt-sm-0 pt-sm-0">
                        <div class="custom-form rounded shadow p-4">
                            <h5 class="mb-4">Get in touch!</h5>

                            <!-- Display success/failure message if exists -->
                            <% if (request.getAttribute("message") != null) { %>
                            <div class="alert alert-info">
                                <%= request.getAttribute("message") %>
                            </div>
                            <% } %>

                            <form method="post" action="contact" name="myForm" onsubmit="return validateForm()">
                                <p id="error-msg"></p>
                                <div id="simple-msg"></div>
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">Your Name <span class="text-danger">*</span></label>
                                            <input name="name" id="name" type="text" class="form-control border rounded">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="mb-3">
                                            <label class="form-label">Email <span class="text-danger">*</span></label>
                                            <input name="email" id="email" type="email" class="form-control border rounded">
                                        </div> 
                                    </div>
                                    <div class="col-md-12">
                                        <div class="mb-3">
                                            <label class="form-label">Titles</label>
                                            <input name="subject" id="subject" type="text" class="form-control border rounded">
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="mb-3">
                                            <label class="form-label">Comment <span class="text-danger">*</span></label>
                                            <textarea name="comments" id="comments" rows="4" class="form-control border rounded"></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-12">
                                        <button type="submit" id="submit" name="send" class="btn btn-primary">Send message</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Map Section -->
            <div class="container-fluid mt-100 mt-60">
                <div class="row">
                    <div class="col-12 p-0">
                        <div class="card map border-0">
                            <div class="card-body p-0">
                                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.506341458941!2d105.52528919999999!3d21.012416699999996!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135abc60e7d3f19%3A0x2be9d7d0b5abcbf4!2zVHLGsOG7nW5nIMSQ4bqhaSBo4buNYyBGUFQgSMOgIE7hu5lp!5e0!3m2!1svi!2s!4v1716286607018!5m2!1svi!2s" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <jsp:include page="layout/footer.jsp"/>

        <!-- Back to Top Button -->
        <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top"><i data-feather="arrow-up" class="icons"></i></a>

        <!-- Additional Scripts -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
    </body>
</html>
