<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet"> 
        <!-- Icon Font Stylesheet -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
        <!-- Libraries Stylesheet -->
        <link href="assets/css/dist/css/lightbox.min.css" rel="stylesheet">
        <link href="assets/css/dist/css/owl.carousel.min.css" rel="stylesheet">
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/style.min.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/dist/css/style.css" rel="stylesheet" type="text/css" />
        <link href="assets/css/style.min.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <jsp:include page="layout/menu.jsp"/>
        <div class="container-fluid py-5 mt-5">
            <div class="container py-5">
                <div class="row g-4 mb-5">
                    <div class="col-lg-8 col-xl-9">
                        <div class="row g-4">
                            <div class="col-lg-6">
                                <div class="border rounded">
                                    <a href="#">
                                        <img src="${productdetail.getImgPath()}" class="img-fluid rounded" alt="Image">
                                    </a>
                                </div>
                            </div>
                            <div class="col-lg-6">
                                <h4 class="fw-bold mb-3">${productdetail.getProductName()}</h4>
                                <p class="mb-3">Category: ${productdetail.getCategory().getCategoryName()}</p>
                                <div class="d-flex mb-4">
                                    <c:set var="price" value="${productdetail.getPrice()}" />
                                    <c:set var="sale" value="${productdetail.getSale()}" />
                                    <c:set var="discountAmount" value="${price * (sale / 100)}" />
                                    <c:set var="finalPrice" value="${price - discountAmount}" />
                                    <c:if test="${sale > 0}">
                                        <h5 class="fw-bold mb-3 text-danger" style="padding-right: 30px ">
                                            <fmt:formatNumber value="${finalPrice}" type="currency" currencySymbol="$" pattern="###0.00" />$
                                        </h5>
                                        <h5 class="fw-bold mb-3 text-decoration-line-through">
                                            ${productdetail.getPrice()}$
                                        </h5>
                                    </c:if>
                                    <c:if test="${sale <= 0}">
                                        <h5 class="text-dark fs-5 fw-bold mb-0">${productdetail.getPrice()}$</h5>
                                    </c:if>
                                </div>
                                <div class="d-flex mb-4">
                                    <c:forEach begin="1" end="${productdetail.getRate()}" var="i">
                                        <i class="fa fa-star text-danger"></i> 
                                    </c:forEach>
                                    <c:forEach begin="1" end="${5 - productdetail.getRate()}" var="i">
                                        <i class="fa fa-star"></i> 
                                    </c:forEach>
                                </div>
                                <p class="mb-4">${productdetail.getCategory().getDescription()}</p>
                                <p class="mb-4">${productdetail.getDescription()}</p>
                                <p class="mb-4 text-danger">Quantity available: ${productdetail.getQuantity()-productdetail.getHold()}</p>
                                <input type="hidden" class="product-stock" value="${productdetail.getQuantity()-productdetail.getHold()}" />
                                <form action="cart" method="post">
                                    <div class="input-group quantity mb-5" style="width: 120px">
                                        <div class="input-group-btn">
                                            <button class="btn btn-sm btn-minus rounded-circle bg-light border" type="button" >
                                                <i class="fa fa-minus"></i>
                                            </button>
                                        </div>
                                        <input type="text" class="form-control form-control-sm text-center border-0" value="1" name="productQuantity" readonly="">
                                        <div class="input-group-btn">
                                            <button class="btn btn-sm btn-plus rounded-circle bg-light border" type="button">
                                                <i class="fa fa-plus"></i>
                                            </button>
                                        </div>
                                    </div>

                                    <c:choose>
                                        <c:when test="${productdetail.getQuantity()-productdetail.getHold() < 1}">
                                            <p class="text-danger">Product is out of stock.</p>
                                        </c:when>
                                        <c:when test="${not empty sessionScope.account}">

                                            <input type="hidden" name="productId" value="${productdetail.getProductId()}">
                                            <button type="submit" class="btn border border-secondary rounded-pill px-4 py-2 mb-4 text-primary ">
                                                <i class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart
                                            </button>
                                        </form>
                                    </c:when>

                                    <c:otherwise>
                                        <p class="text-danger">Please log in to add items to your cart.</p>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                            <div class="col-lg-12">
                                <nav>
                                    <div class="nav nav-tabs mb-3">
                                        <button class="nav-link active border-white border-bottom-0" type="button" role="tab"
                                                id="nav-about-tab" data-bs-toggle="tab" data-bs-target="#nav-about"
                                                aria-controls="nav-about" aria-selected="true">Description</button>
                                        <button class="nav-link border-white border-bottom-0" type="button" role="tab"
                                                id="nav-mission-tab" data-bs-toggle="tab" data-bs-target="#nav-mission"
                                                aria-controls="nav-mission" aria-selected="false">Reviews</button>
                                    </div>
                                </nav>
                                <div class="tab-content mb-5">
                                    <div class="tab-pane active" id="nav-about" role="tabpanel" aria-labelledby="nav-about-tab">
                                        <p>${productdetail.getCategory().getDescription()}</p>
                                        <p>${productdetail.getBrand().getDescription()}</p>
                                        <p>${productdetail.getDescription()}</p>
                                        <div class="px-2">
                                            <div class="row g-4">
                                                <div class="col-6">
                                                    <div class="row bg-light align-items-center text-center justify-content-center py-2">
                                                        <div class="col-6">
                                                            <p class="mb-0">Category</p>
                                                        </div>
                                                        <div class="col-6">
                                                            <p class="mb-0">${productdetail.getCategory().getCategoryName()}</p>
                                                        </div>
                                                    </div>
                                                    <div class="row text-center align-items-center justify-content-center py-2">
                                                        <div class="col-6">
                                                            <p class="mb-0">Brand</p>
                                                        </div>
                                                        <div class="col-6">
                                                            <p class="mb-0">${productdetail.getBrand().getBrandName()}</p>
                                                        </div>
                                                    </div>
                                                    <div class="row bg-light text-center align-items-center justify-content-center py-2">
                                                        <div class="col-6">
                                                            <p class="mb-0">Quantity</p>
                                                        </div>
                                                        <div class="col-6">
                                                            <p class="mb-0">${productdetail.getQuantity()}</p>
                                                        </div>
                                                    </div>
                                                    <div class="row text-center align-items-center justify-content-center py-2">
                                                        <div class="col-6">
                                                            <p class="mb-0">Year of release</p>
                                                        </div>
                                                        <div class="col-6">
                                                            <p class="mb-0">${productdetail.getReleaseYear()}</p>
                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="tab-pane" id="nav-mission" role="tabpanel" aria-labelledby="nav-mission-tab">
                                        <c:forEach var="feedback" items="${feedbacklist}">
                                            <div class="d-flex">
                                                <img src="${feedback.getCustomerId().getAvatar_url()}" class="img-fluid rounded-circle p-3" style="width: 100px; height: 100px;" alt="Avatar">
                                                <div class="">                                                  
                                                     <h5>${feedback.getCustomerId().getCustomerName()}</h5>
                                                     <div class="d-flex mb-3">
                                                          
                                                            
                                                                <c:forEach begin="1" end="${feedback.getRating()}" var="i">
                                                                    <i class="fa fa-star text-danger"></i> 
                                                                </c:forEach>
                                                                <c:forEach begin="1" end="${5 - feedback.getRating()}" var="i">
                                                                    <i class="fa fa-star"></i> 
                                                                </c:forEach>
                                                            
                                                        </div>
                                                    <div class="d-flex justify-content-between">                                     
                                                         <p class="mb-2" style="font-size: 14px;">${feedback.getDobString() }</p>
                                                       
                                                    </div>
                                                    <p>${feedback.getComment()}</p>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </div>

                                </div>
                            </div>

                        </div>
                        <a href="feedback?productid=${productdetail.getProductId()}" class="btn border border-secondary text-primary rounded-pill px-4 py-3"> View Comment</a>
                    </div>
                    <div class="col-lg-4 col-xl-3">
                        <div class="row g-4 fruite">
                            <div class="col-lg-12">
                                <form action="action" method="get">
                                    <div class="input-group w-100 mx-auto d-flex mb-4">
                                        <input type="search" name="search" class="form-control p-3" placeholder="Product's name" aria-describedby="search-icon-1">
                                        <span id="search-icon-1" class="input-group-text p-3"><i class="fa fa-search"></i></span>
                                    </div>
                                </form>
                                <div class="mb-4">
                                    <h4>Categories</h4>
                                    <ul class="list-unstyled fruite-categorie">
                                        <c:forEach var="category" items="${clist}">
                                            <li>
                                                <div class="d-flex justify-content-between fruite-name">
                                                    <a href="product?categoryId=${category.getCategoryId()}"><i class="fas fa-laptop me-2"></i><c:out value="${category.getCategoryName()}"/></a>
                                                    <span>(<c:out value="${categoryProductCount[category.categoryId] != null ? categoryProductCount[category.categoryId] : 0}"/>)</span>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                                <div class="mb-4">
                                    <h4>Brand</h4>
                                    <ul class="list-unstyled fruite-categorie">                                             
                                        <c:forEach var="brand" items="${blist}">
                                            <li>
                                                <div class="d-flex justify-content-between fruite-name">
                                                    <a href="product?brandId=${brand.getBrandId()}""><i class="fas fa-laptop me-2"></i><c:out value="${brand.getBrandName()}"/></a>
                                                    <span>(<c:out value="${brandProductCount[brand.brandId] != null ? brandProductCount[brand.brandId] : 0}"/>)</span>
                                                </div>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-lg-12">
                                <h4 class="mb-4">Latest products</h4>
                                <c:forEach var="platest" items="${pllist}">
                                    <a href="productdetail_h?productId=${platest.getProductId()}" 
                                       style="text-decoration: none; color: black;" 
                                       onmouseover="this.style.color = '#007bff';" 
                                       onmouseout="this.style.color = 'black';">
                                        <div class="d-flex align-items-center justify-content-start">
                                            <div class="rounded me-4" style="width: 100px; height: 100px;">
                                                <img src="${platest.getImgPath()}" class="img-fluid rounded" alt="">
                                            </div>
                                            <div>
                                                <h6 class="mb-2">${platest.getProductName()}</h6>
                                                <div class="d-flex mb-2">
                                                    <c:forEach begin="1" end="${platest.getRate()}" var="i">
                                                        <i class="fa fa-star text-danger"></i> 
                                                    </c:forEach>
                                                    <c:forEach begin="1" end="${5 - platest.getRate()}" var="i">
                                                        <i class="fa fa-star"></i> 
                                                    </c:forEach>
                                                </div>
                                                <div class="d-flex mb-2">
                                                    <c:set var="price" value="${platest.getPrice()}" />
                                                    <c:set var="sale" value="${platest.getSale()}" />
                                                    <c:set var="discountAmount" value="${price * (sale / 100)}" />
                                                    <c:set var="finalPrice" value="${price - discountAmount}" />
                                                    <h5 class="fw-bold me-2">
                                                        <fmt:formatNumber value="${finalPrice}" type="currency" currencySymbol="$" pattern="###0.00" />$
                                                    </h5>                                                      
                                                    <h5 class="text-danger text-decoration-line-through">${platest.getPrice()}$</h5>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>               
            </div>
        </div>
        <jsp:include page="layout/footer.jsp"/> 
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/lib/easing/easing.min.js"></script>
        <script src="assets/js/lib/waypoints/waypoints.min.js"></script>
        <script src="assets/js/lib/lightbox/js/lightbox.min.js"></script>
        <script src="assets/js/lib/owlcarousel/owl.carousel.min.js"></script>
        <script>
                                           (function ($) {
                                               "use strict";

                                               // Product Quantity
                                               $('.quantity button').on('click', function () {
                                                   var button = $(this);
                                                   var input = button.parent().parent().find('input');
                                                   var oldValue = input.val();
                                                   var stockQuantity = $('.product-stock').val(); // Lấy số lượng sản phẩm trong kho

                                                   if (button.hasClass('btn-plus')) {
                                                       // Kiểm tra nếu số lượng hiện tại nhỏ hơn số lượng trong kho thì mới cho phép cộng
                                                       if (parseFloat(oldValue) < parseFloat(stockQuantity)) {
                                                           var newVal = parseFloat(oldValue) + 1;
                                                       } else {
                                                           newVal = oldValue; // Không tăng giá trị nếu đã đạt giới hạn
                                                       }
                                                   } else {
                                                       // Giới hạn giá trị nhỏ nhất là 1 thay vì 0
                                                       if (oldValue > 1) {
                                                           var newVal = parseFloat(oldValue) - 1;
                                                       } else {
                                                           newVal = 1; // Giữ nguyên giá trị nếu đã là 1
                                                       }
                                                   }

                                                   // Cập nhật giá trị mới vào trường nhập liệu
                                                   input.val(newVal);

                                                   // Kiểm tra và khóa hoặc mở khóa nút cộng tùy thuộc vào số lượng
                                                   if (parseFloat(newVal) >= parseFloat(stockQuantity)) {
                                                       button.closest('.quantity').find('.btn-plus').prop('disabled', true); // Khóa nút cộng nếu đạt giới hạn
                                                   } else {
                                                       button.closest('.quantity').find('.btn-plus').prop('disabled', false); // Mở khóa nút cộng nếu chưa đạt giới hạn
                                                   }
                                               });
                                           })(jQuery);
        </script>

    </body>
</html>
