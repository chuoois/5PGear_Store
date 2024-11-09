<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="layout/head.jsp"/>
    <body>
        <jsp:include page="layout/preloader.jsp"/>
        <jsp:include page="layout/menu.jsp"/>

        <!-- Main Blog Detail Content -->
        <div class="container blog-container py-5">
            <div class="row">
                <!-- Left Sidebar: Search Box, Categories -->
                <div class="col-md-4">
                    <!-- Search Box -->
                    <form action="blogcontroller" method="get" class="mb-4 search-box">
                        <input type="text" name="query" class="form-control" placeholder="Search for posts...">
                        <button type="submit" class="btn btn-primary mt-2">Search</button>
                    </form>

                    <!-- Categories List from Database -->
                    <h5 class="mb-3">Post Categories</h5>
                    <ul class="list-group mb-4">
                        <c:forEach var="c" items="${categoryList}">
                            <li class="list-group-item">
                                <a href="blogcontroller?categoryId=${c.categoryId}">${c.categoryName}</a>
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

                <!-- Blog Post Details Section -->
                <div class="col-md-8">
                    <div class="blog-detail">
                        <!-- Blog Title -->
                        <h2 class="blog-title">${blog.title}</h2>

                        <!-- Blog Image -->
                        <img src="${blog.imgPath}" class="img-fluid mb-4 blog-img" alt="${blog.title}">

                        <!-- Blog Meta Information -->
                        <div class="blog-meta mb-3">
                            <span><strong>Date Created:</strong> ${blog.dateCreated}</span>
                            <span><strong>Tags:</strong> ${blog.tags}</span>
                            <span><strong>Category:</strong> ${category}</span>
                        </div>
                        
                        <!-- Blog Content -->
                         <div class="brief-info">
                            <p><strong>Description:</strong> </p>
                        </div>
                        <div class="blog-content mb-5">
                            <p>${blog.description}</p>
                        </div>

                        <!-- Brief Info (Optional) -->
                        <div class="brief-info">
                            <p><strong>Brief Info:</strong> ${blog.brief_info}</p>
                        </div>
                        <div class="brief-info">
                            <p><strong>Author:</strong> ${author}</p>
                        </div>
                    </div>
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

        /* Blog Title Styling */
        .blog-title {
            font-size: 32px;
            font-weight: bold;
            margin-bottom: 20px;
        }

        /* Blog Meta Information Styling */
        .blog-meta {
            font-size: 14px;
            color: #555;
        }

        .blog-meta span {
            display: block;
            margin-bottom: 10px;
        }

        /* Blog Image Styling */
        .blog-img {
            width: 100%;
            height: auto;
            border-radius: 8px;
            object-fit: cover;
        }

        /* Blog Content Styling */
        .blog-content p {
            font-size: 18px;
            line-height: 1.8;
            color: #333;
        }

        /* Sidebar Styling */
        .search-box {
            margin-bottom: 30px;
        }

        .list-group-item a {
            text-decoration: none;
            color: #007bff;
        }

        .list-group-item a:hover {
            text-decoration: underline;
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
