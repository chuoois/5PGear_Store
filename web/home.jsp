<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="layout/head.jsp"/>
    <body>
        <jsp:include page="layout/preloader.jsp"/>
        <jsp:include page="layout/menu.jsp"/>

        <!-- Slider Section -->
        <div id="carouselExampleCaptions" class="carousel slide">
            <!-- Carousel indicators -->
            <div class="carousel-indicators">
                <c:forEach var="s" items="${slider}" varStatus="status">
                    <button type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide-to="${status.index}" class="${status.first ? 'active' : ''}" aria-current="true" aria-label="Slide ${status.index + 1}"></button>
                </c:forEach>
            </div>

            <div class="carousel-inner">
                <!-- Loop through the slider items -->
                <c:forEach var="s" items="${slider}" varStatus="status">
                    <div class="carousel-item ${status.first ? 'active' : ''}">
                        <a href="productdetail_h?productId=${s.productId}">
                            <img src="${s.imgPath}" class="d-block w-100" alt="${s.title}">
                        </a>
                        <div class="carousel-caption d-none d-md-block">
                            <h5>${s.title}</h5>
                            <p>${sp.description}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>     
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleCaptions" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>

        <!-- Product Listing Section -->
        <h1>Lastest Products</h1>
        <div class="container">
            <div class="row">
                <!-- Loop through the products -->
                <c:forEach var="product" items="${listProductH}">
                    <div class="col-md-4">
                        <div class="card mb-4">
                            <!-- Wrap the image in a link to the product detail page -->
                            <a href="productdetail_h?productId=${product.productId}">
                                <img src="${product.imgPath}" class="card-img-top" alt="${product.productName}">
                            </a>
                            <div class="card-body">   
                                <!-- Wrap the product name in a link to the product detail page -->
                                <a href="productdetail_h?productId=${product.productId}">
                                    <h5 class="card-title">${product.productName}</h5>
                                </a>
                                <p class="card-text">Price: $${product.price}</p>

                                <!-- Add to cart button logic based on stock and login status -->
                                <c:choose>
                                   
                                    <c:when test="${product.quantity - product.hold < 1}">
                                        <p class="text-danger">Product is out of stock.</p>
                                    </c:when>

                                    
                                    <c:when test="${not empty sessionScope.account}">
                                        <form action="cart" method="post">
                                            <input type="hidden" name="productId" value="${product.productId}">
                                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                                        </form>
                                    </c:when>

                                    
                                    <c:otherwise>
                                        <p class="text-danger">Please log in to add items to your cart.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>


        <h1>Sale Products</h1>
        <div class="container">
            <div class="row">
                <!-- Loop through the products -->
                <c:forEach var="product" items="${listProductTopSale}">
                    <div class="col-md-4">
                        <div class="card mb-4">
                            <!-- Wrap the image in a link to the product detail page -->
                            <a href="productdetail_h?productId=${product.productId}">
                                <img src="${product.imgPath}" class="card-img-top" alt="${product.productName}">
                            </a>
                            <div class="card-body">   
                                <!-- Wrap the product name in a link to the product detail page -->
                                <a href="productdetail_h?productId=${product.productId}">
                                    <h5 class="card-title">${product.productName}</h5>
                                </a>
                                <p class="card-text">Price: $${product.price}</p>

                                <!-- Add to cart button logic based on stock and login status -->
                                <c:choose>
                                   
                                    <c:when test="${product.quantity - product.hold < 1}">
                                        <p class="text-danger">Product is out of stock.</p>
                                    </c:when>

                                    
                                    <c:when test="${not empty sessionScope.account}">
                                        <form action="cart" method="post">
                                            <input type="hidden" name="productId" value="${product.productId}">
                                            <button type="submit" class="btn btn-primary">Add to Cart</button>
                                        </form>
                                    </c:when>

                                    
                                    <c:otherwise>
                                        <p class="text-danger">Please log in to add items to your cart.</p>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <!-- Latest Blog Section -->
        <h2 class="text-center">Latest Blog Posts</h2>
        <div class="container">
            <div class="row">
                <!-- Loop through the latest blog posts -->
                <c:forEach var="blog" items="${blogH}">
                    <div class="col-md-4">
                        <div class="post-card">
                            <a href="blogdetail?blogId=${blog.blogId}"> <!-- Link to blog detail -->
                                <img src="${blog.imgPath}" alt="${blog.title}" class="img-fluid">
                                <p>${blog.dateCreated} - ${blog.brief_info}</p>
                                <h3>${blog.title}</h3>
                            </a>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

        <jsp:include page="layout/footer.jsp"/>

        <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top">
            <i data-feather="arrow-up" class="icons"></i>
        </a>

        <jsp:include page="layout/search.jsp"/>

        <div class="modal fade" id="watchvideomodal" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg">
                <div class="modal-content video-modal rounded overflow-hidden">
                    <div class="ratio ratio-16x9">
                        <iframe src="https://www.youtube.com/embed/QIvIN8M91x4" title="YouTube video" allowfullscreen></iframe>
                    </div>
                </div>
            </div>
        </div>

        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
    </body>

    <style>
        .carousel-inner img {
            width: 100%;
            max-width: 1920px;
            height: 700px; /* Tăng chiều cao ảnh slider */
            object-fit: cover;
        }

        .carousel-caption h5 {
            color: white;
            text-shadow: 4px 4px 8px rgba(0, 0, 0, 0.9); /* Tăng độ mạnh của bóng */
            background-color: rgba(0, 0, 0, 0.5); /* Nền tối bán trong suốt */
            padding: 10px;
            border-radius: 5px; /* Bo góc cho nền chữ */
            display: inline-block;
            font-size: 24px; /* Tăng kích thước chữ */
        }

        .carousel-caption p {
            color: white;
            text-shadow: 2px 2px 6px rgba(0, 0, 0, 0.8);
            background-color: rgba(0, 0, 0, 0.5); /* Nền tối bán trong suốt cho mô tả */
            padding: 5px;
            border-radius: 5px;
            display: inline-block;
            font-size: 16px; /* Tăng kích thước mô tả */
        }

        .card {
            width: 100%;
            max-width: 350px;
            margin: auto;
            border: 1px solid #ddd;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        .card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .card-body {
            padding: 16px;
            text-align: center;
        }

        .btn-primary {
            background-color: #007bff;
            border: none;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        h1, h2 {
            text-align: center;
            margin-top: 20px;
        }

        .latest-posts {
            padding: 50px 0;
        }

        .post-card {
            text-align: center;
            margin-bottom: 30px;
        }

        .post-card img {
            width: 100%;
            height: 200px;
            object-fit: cover;
            border-radius: 8px;
        }

        .post-card h3 {
            margin-top: 15px;
            font-size: 18px;
        }

        .post-card p {
            font-size: 14px;
            color: #555;
        }
    </style>
</html>
