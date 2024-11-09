<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <!-- Include head and preloader -->
    <jsp:include page="layout/head.jsp"/>
    <body>
        <jsp:include page="layout/preloader.jsp"/>
        <jsp:include page="layout/menu.jsp"/>

        <!-- Main Blog Content -->
        <div class="container blog-container py-5">
            <div class="row">
                <!-- Left Sidebar: Search Box, Categories, Latest Posts -->
                <div class="col-md-4">
                    <!-- Search Box -->
                    <form action="blogcontroller" method="get" class="mb-4 search-box">
                        <input type="text" name="query" class="form-control" placeholder="Search for posts...">
                        <button type="submit" class="btn btn-primary mt-2">Search</button>
                    </form>

                    <!-- Categories List from Database -->
                    <h5 class="mb-3">Post Categories</h5>
                    <ul class="list-group mb-4">
                        <!-- Loop through the category list -->
                        <c:forEach var="c" items="${categoryList}">
                            <li class="list-group-item">
                                <a href="?categoryId=${c.categoryId}">${c.categoryName}</a>
                            </li>
                        </c:forEach>
                    </ul>
                    
                    <!-- Static Links (Contact/Other) -->
                    <h5 class="mt-4">Contact & Links</h5>
                    <ul class="list-group">
                        <li class="list-group-item"><a href="contact">Contact Us</a></li>
                        <li class="list-group-item"><a href="https://www.facebook.com/profile.php?id=61566276511480">About Us</a></li>
                    </ul>
                </div>

                <!-- Blog Posts Section -->
                <div class="col-md-8">
                    <h3 class="text-center blog-heading">
                        Discover our blog for useful tips and the latest tech trends!
                    </h3>

                    <div class="row">
                        <!-- Blog Post Card (Dynamically Generated) -->
                        <c:forEach var="p" items="${blogList}">
                            <div class="col-md-12 mb-4">
                                <div class="blog-post-card shadow-sm">
                                    <a href="blogdetail?blogId=${p.blogId}" class="d-flex align-items-center text-decoration-none">
                                        <img src="${p.imgPath}" alt="${p.title}" class="img-fluid rounded-start">
                                        <div class="post-info px-4 py-3">
                                            <h3 class="post-title">${p.title}</h3>
                                            <p class="post-summary">${p.brief_info}</p>
                                        </div>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <!-- Pagination -->
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <!-- Hiển thị các nút phân trang từ 1 đến totalPages -->
                            <c:forEach var="i" begin="1" end="${totalPages}">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="?page=${i}">${i}</a>
                                </li>
                            </c:forEach>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>

        <jsp:include page="layout/footer.jsp"/>

        <a href="#" onclick="topFunction()" id="back-to-top" class="btn btn-icon btn-pills btn-primary back-to-top">
            <i data-feather="arrow-up" class="icons"></i>
        </a>

        <!-- Script files -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
    </body>

    <style>
        /* Adjusted spacing for the blog content */
        .blog-container {
            margin-top: 50px;
            padding-bottom: 50px; /* Adds padding to avoid footer overlapping */
        }

        /* Blog Post Cards */
        .blog-post-card {
            display: flex;
            align-items: center;
            border-radius: 10px;
            background-color: #fff;
            overflow: hidden;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .blog-post-card:hover {
            transform: scale(1.03);
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }

        .blog-post-card img {
            width: 200px;
            height: auto;
            object-fit: cover;
            border-right: 1px solid #ddd;
        }

        .post-info {
            padding: 20px;
        }

        .post-title {
            font-size: 22px;
            color: #007bff;
            font-weight: bold;
            margin-bottom: 10px;
        }

        .post-title:hover {
            text-decoration: underline;
        }

        .post-summary {
            font-size: 16px;
            color: #555;
        }

        /* Pagination Styling */
        .pagination .page-item .page-link {
            color: #007bff;
        }

        .pagination .page-item.active .page-link {
            background-color: #007bff;
            border-color: #007bff;
            color: white;
        }

        /* Footer adjustment */
        footer {
            position: relative;
            width: 100%;
            clear: both;
            padding: 20px 0;
            background-color: #f1f1f1;
            bottom: 0;
            text-align: center;
        }

        /* Ensure footer doesn't overlap */
        html, body {
            height: 100%;
        }

        body {
            display: flex;
            flex-direction: column;
        }

        .container.blog-container {
            flex: 1 0 auto; /* Ensures the container takes up remaining space */
        }

        footer {
            flex-shrink: 0; /* Prevents footer from shrinking or overlapping content */
        }
    </style>
</html>
