
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

    <body>

        <jsp:include page="layout/menu.jsp"/>
        <div class="container-fluid fruite py-5">
            <div class="container py-5">               
                <h1 class="mb-4"><img src="assets/images/fpt.png" height="50" class="l-light" alt="">5PGEAR SHOP</h1>
                <div class="row g-4">
                    <div class="col-lg-12">
                        <div class="row g-4">
                            <div class="col-xl-3">
                                <form action="product" method="GET" class="w-100 mx-auto d-flex">
                                    <div class="input-group">
                                        <input type="search" name="search" class="form-control p-3" placeholder="Product's name" aria-describedby="search-icon-1">
                                        <button type="submit" id="search-icon-1" class="input-group-text p-3">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </form>
                            </div>
                            <div class="col-6"></div>
                            <div class="col-xl-3">
                                <div class="bg-light ps-3 py-3 rounded d-flex justify-content-between mb-4">
                                    <label for="sortOptions">Sorted by updated date:</label>
                                    <form id="sortForm" action="product">
                                        <select style="display: flex" id="sortOptions" name="sort" class="border-0 form-select-sm bg-light me-3" onchange="document.getElementById('sortForm').submit();">                                 
                                            <option value="" disabled selected style="">Select</option>
                                            <option value="latest">Latest</option>
                                            <option value="oldest">Oldest</option>
                                        </select>
                                    </form>
                                </div>

                            </div>
                        </div>
                        <div class="row g-4">
                            <div class="col-lg-3">
                                <div class="row g-4">
                                    <div class="col-lg-12">
                                        <div class="mb-3">
                                            <h4>Category</h4>
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
                                        <div class="mb-3">
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
                                        <h4 class="mb-3">Latest products</h4>
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
                                    <div class="col-lg-12">
                                        <div class="position-relative">
                                            <a href="https://www.facebook.com/profile.php?id=61566276511480" style="display: block; transition: transform 0.3s ease, box-shadow 0.3s ease;">
                                                <img src="iproduct/contact_link.png" class="img-fluid w-100 rounded" alt="" style="width: 100%; border-radius: 5px;">
                                            </a>
                                            <div class="position-absolute" style="top: 50%; right: 10px; transform: translateY(-50%);">

                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-9">
                                <div class="row g-4 justify-content-center">

                                    <c:forEach var="product" items="${plist}">
                                        <div class="col-md-6 col-lg-6 col-xl-4">
                                            <!-- Wrap the card content in an anchor tag -->
                                            <a href="productdetail_h?productId=${product.getProductId()}" class="text-decoration-none text-dark">
                                                <div class="rounded position-relative fruite-item">
                                                    <div class="fruite-img">
                                                        <img src="<c:out value="${product.getImgPath()}"/>" 
                                                             width="261" height="261" 
                                                             class="rounded-top" 
                                                             alt="" style="object-fit: cover;">
                                                    </div>
                                                    <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">
                                                        ${product.getCategory().getCategoryName()}
                                                    </div>
                                                    <div class="p-4 border border-secondary border-top-0 rounded-bottom" style="width: 261px; height: 305px; display: flex; flex-direction: column;">
                                                        <h5>${product.getProductName()}</h5>
                                                        <p>${product.getDescription()}</p>
                                                        <div class="d-flex mb-2">
                                                            <c:forEach begin="1" end="${product.getRate()}" var="i">
                                                                <i class="fa fa-star text-danger"></i> 
                                                            </c:forEach>
                                                            <c:forEach begin="1" end="${5 - product.getRate()}" var="i">
                                                                <i class="fa fa-star"></i> 
                                                            </c:forEach>
                                                        </div>
                                                        <div class="d-flex justify-content-between flex-lg-wrap" style="margin-top: auto;">
                                                            <c:set var="price" value="${product.getPrice()}" />
                                                            <c:set var="sale" value="${product.getSale()}" />
                                                            <c:set var="discountAmount" value="${price * (sale / 100)}" />
                                                            <c:set var="finalPrice" value="${price - discountAmount}" />

                                                            <c:if test="${sale > 0}">
                                                                <p class="text-dark fs-5 fw-bold mb-0">
                                                                    <fmt:formatNumber value="${finalPrice}" type="currency" currencySymbol="$" pattern="###0.00" />$
                                                                </p>
                                                                <p class="text-danger fs-5 fw-bold mb-0 text-decoration-line-through">
                                                                    ${product.getPrice()}$
                                                                </p>
                                                            </c:if>

                                                            <c:if test="${sale <= 0}">
                                                                <p class="text-dark fs-5 fw-bold mb-0">${product.getPrice()}$</p>
                                                            </c:if>

                                                            <c:choose>
                                                                <c:when test="${product.getQuantity()-product.getHold() < 1}">
                                                                    <p class="text-danger">Product is out of stock.</p>
                                                                </c:when>
                                                                <c:when test="${not empty sessionScope.account}">
                                                                    <form action="cart" method="post">
                                                                        <input type="hidden" name="productId" value="${product.getProductId()}">
                                                                        <button type="submit" class="btn border border-secondary rounded-pill px-3 text-primary">
                                                                            <i class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart
                                                                        </button>
                                                                    </form>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <p class="text-danger">Please log in to add items to your cart.</p>
                                                                </c:otherwise>
                                                            </c:choose>


                                                            <!-- Display success message -->


                                                        </div>
                                                    </div>
                                                </div>
                                            </a>
                                        </div>
                                    </c:forEach>

                                    <div class="col-12">
                                        <div class="pagination d-flex justify-content-center mt-5">    
                                            <c:forEach begin="1" end="${totalPages}" var="i">
                                                <a href="product?page=${i}" class="rounded">${i}</a>
                                            </c:forEach>                                  
                                        </div>
                                    </div>
                                </div>
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
        <script src="assets/js/main.js"></script>
    </body>
</html>




