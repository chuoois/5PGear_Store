

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Marketting Dashboard</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
        <style>
            .message.info {
                position: fixed;
                top: 20px;
                right: 20px;
                z-index: 9999;
                max-width: 300px;
                width: 100%;
            }

            .message.info [show-alert] {
                background-color: #28a745;
                color: white;
                padding: 15px 20px;
                border-radius: 8px;
                box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
                display: flex;
                justify-content: space-between;
                align-items: center;
                animation: slideIn 0.5s ease-out;
            }

            .message.info [show-alert] button {
                background: none;
                border: none;
                color: white;
                font-size: 20px;
                cursor: pointer;
                padding: 0;
                margin-left: 10px;
            }

            @keyframes slideIn {
                from {
                    transform: translateX(100%);
                    opacity: 0;
                }
                to {
                    transform: translateX(0);
                    opacity: 1;
                }
            }

            @keyframes fadeOut {
                from {
                    opacity: 1;
                }
                to {
                    opacity: 0;
                }
            }

            .message.info [show-alert].alert-hidden {
                animation: fadeOut 0.5s ease-out forwards;
            }
            .Page navigation{
                display: flex
            }
            .pagination{
                justify-content: center;

            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Gọi sidebar ở đây -->
                <jsp:include page="sidebar.jsp" />
                <main class="col-md-9 col-lg-10 main-content">
                    <header class="main-header d-flex justify-content-between align-items-center py-3">
                        <button id="sidebar-toggle" class="btn btn-outline-secondary">
                            <i class="fas fa-bars"></i>
                        </button>
                        <div class="user-info">
                            <span>Welcome, Marketing</span>
                            <a href="${pageContext.request.contextPath}/logoutstaff" class="btn btn-outline-danger btn-sm ml-2">
                                <i class="fas fa-sign-out-alt"></i> Logout
                            </a>
                        </div>
                    </header>

                    <div class="dashboard">
                        <h1 class="my-4">Blog List</h1>
                        <!-- Filter Form -->
                        <form method="get" action="/SWP391Gr5/marketting/listblogmarketting">

                            <div class="form-inline my-2">
                                <input type="text" name="search" class="form-control mr-2" placeholder="Search by title" value="${param.search}">
                                <select name="category" class="form-control mr-2">
                                    <option value="">All Categories</option>
                                    <c:forEach var="category" items="${listCategories}">
                                        <option value="${category.categoryId}" ${category.categoryId == param.category ? 'selected' : ''}>${category.categoryName}</option>
                                    </c:forEach>
                                </select>
                                <select name="status" class="form-control mr-2">
                                    <option value="">All Statuses</option>
                                    <option value="1" ${param.status == '1' ? 'selected' : ''}>Active</option>
                                    <option value="0" ${param.status == '0' ? 'selected' : ''}>Inactive</option>
                                </select>
                                <select name="sort" class="form-control mr-2">

                                    <option value="titleAZ" ${param.sort == 'titleAZ' ? 'selected' : ''}>Sort by Title A-Z</option>
                                    <option value="titleZA" ${param.sort == 'titleZA' ? 'selected' : ''}>Sort by Title Z-A</option>
                                    <option value="blogId"${param.sort == 'blogId' ? 'selected' : ''}>blogId</option> 
                                    <option value="dateCreated" ${param.sort == 'dateCreated' ? 'selected' : ''}>Sort by date</option>

                                </select>
                                <button type="submit" class="btn btn-primary filter-btn">Apply Filters</button>
                            </div>
                        </form>

                        <!-- Add Blog Button -->
                        <div class="my-4">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addBlogModal">
                                <i class="fas fa-plus"></i> Add Blog
                            </button>
                        </div>
                        <!-- Success Message Alert -->
                        <c:if test="${not empty sessionScope.successMessage}">
                            <div class="message info">
                                <div show-alert>
                                    ${sessionScope.successMessage}
                                    <button type="button" onclick="closeAlert(this)" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                            </div>
                            <c:remove var="successMessage" scope="session"/>
                        </c:if>

                        <!-- Add Blog Modal -->
                        <div class="modal fade" id="addBlogModal" tabindex="-1" role="dialog" aria-labelledby="addBlogModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content" style="border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);">
                                    <div class="modal-header" style="background-color: #007bff; color: #fff; border-bottom: none;">
                                        <h5 class="modal-title" id="addBlogModalLabel" style="font-weight: bold; font-size: 1.5rem;">Add New Blog</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: #fff; opacity: 1;">
                                            <span aria-hidden="true">&times;</span>
                                        </button>                                      
                                    </div>
                                    <div>
                                        <% if (request.getAttribute("errors") != null) { %>
                                        <div class="alert alert-danger">
                                            <ul>
                                                <c:forEach var="error" items="${errors}">
                                                    <li>${error}</li>
                                                    </c:forEach>
                                            </ul>
                                        </div>
                                        <% } %>
                                    </div>
                                    <div class="modal-body">
                                        <form action="/SWP391Gr5/marketting/createBlogMarketting" method="POST" enctype="multipart/form-data">
                                            <div class="form-group">
                                                <label for="blogTitle">Blog Title</label>
                                                <input type="text" class="form-control" id="blogTitle" name="blogTitle" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="category">Category</label>
                                                <select class="form-control" id="category" name="category" required>
                                                    <c:forEach var="category" items="${listCategories}">
                                                        <option value="${category.categoryId}">${category.categoryName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">
                                                <label for="description">Description:</label>
                                                <textarea class="form-control" id="description" name="description" rows="5" required></textarea>
                                            </div>
                                            <div class="form-group">
                                                <label for="briefInfo">Brief Info</label>
                                                <textarea class="form-control" id="briefInfo" name="briefInfo" rows="3" required></textarea>
                                            </div>

                                            <div class="form-group">
                                                <label for="imgPath">Image Upload</label>
                                                <input type="file" class="form-control" id="imgPath" name="imgPath" accept="image/*" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="tags">Tags</label>
                                                <input type="text" class="form-control" id="tags" name="tags" placeholder="Comma-separated tags">
                                            </div>
                                            <div class="form-group" style="display: none" >
                                                <label for="marketerId">Marketer ID</label>
                                                <input type="number" class="form-control" id="marketerId" name="marketerId" value="${sessionScope.marketer.marketingId}" readonly>
                                            </div>

                                            <div class="form-group">
                                                <label for="status">Status</label>
                                                <select class="form-control" id="status" name="status" required>
                                                    <option value="1">Active</option>
                                                    <option value="0">Inactive</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Add Blog</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <!-- Blog Table -->
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Title</th>
                                    <th>Category</th>
                                    <th>Images</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach var="blog" items="${filteredBlogList}">
                                    <tr>
                                        <td>${blog.blogId}</td>
                                        <td>${blog.title}</td>
                                        <td>
                                            <c:forEach var="category" items="${listCategories}">
                                                <c:if test="${category.categoryId == blog.categoryId}">
                                                    ${category.categoryName}
                                                </c:if>
                                            </c:forEach>
                                        </td>

                                        <td>
                                            <img src="../${blog.imgPath}" alt="${blog.title}" width="100">

                                        </td>

                                        <td>
                                            <button class="btn ${blog.status.equals("1") ? 'btn-success' : 'btn-danger'}">
                                                ${blog.status.equals("1") ? 'Active' : 'Inactive'}
                                            </button>
                                        </td>
                                        <td>
                                            <a href="viewBlog?id=${blog.blogId}" class="btn btn-info btn-sm">
                                                <i class="fas fa-eye"></i> View
                                            </a>

                                            <a href="editBlogMarketting?id=${blog.blogId}" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Edit</a>
                                            <a href="changestatusblog?id=${blog.blogId}&page=${currentPage}&search=${paramSearch}&category=${paramCategory}&status=${paramStatus}" 
                                               class="btn btn-secondary btn-sm" 
                                               onclick="return confirm('Are you sure you want to ${blog.status.equals("Active") ? 'Inactive' : 'Active'} this blog?');">
                                                <i class="fas ${blog.status.equals("1") ? 'fa-eye'  :'fa-eye-slash' }"></i> 
                                                ${blog.status.equals("1") ? 'Hide' : 'Show'}
                                            </a>

                                        </td>


                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>



                        <!-- Blog Details Modal -->
                        <div class="modal fade" id="blogModal" tabindex="-1" role="dialog" aria-labelledby="blogModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content" style="border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);">
                                    <div class="modal-header" style="background-color: #007bff; color: #fff; border-bottom: none;">
                                        <h5 class="modal-title" id="blogModalLabel" style="font-weight: bold; font-size: 1.5rem;">Blog Details</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: #fff; opacity: 1;">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body" style="padding: 20px;">
                                        <c:if test="${not empty blogById}">
                                            <div class="product-details" style="text-align: left;">
                                                <div class="text-center">
                                                    <img src="${pageContext.request.contextPath}/${blogById.imgPath}" alt="${blogById.title}" class="img-fluid mb-3" style="max-width: 70%; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-bottom: 15px;">
                                                </div>
                                                <h4 style="font-size: 1.4rem; font-weight: bold; margin-bottom: 10px;">${blogById.title}</h4>

                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Category:</strong>                               
                                                    <c:forEach var="category" items="${listCategories}">
                                                        <c:if test="${category.categoryId == blogById.categoryId}">
                                                            ${category.categoryName}
                                                        </c:if>
                                                    </c:forEach>
                                                </p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Marketing Name: </strong> ${sessionScope.marketer.marketingName}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Description:</strong> ${blogById.description}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Tags:</strong> ${blogById.tags}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Brief Info:</strong> ${blogById.brief_info}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Status:</strong> ${blogById.status==1 ? 'Active' : 'InActive'}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Date Create:</strong> ${blogById.dateCreated}</p>


                                            </div>
                                        </c:if>
                                    </div>
                                    <div class="modal-footer" style="border-top: none; padding: 15px;">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal" style="padding: 8px 16px; font-size: 1rem;">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>                       
                    </div>
                </main>
            </div>
        </div>


        <!-- Pagination -->
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <!-- Previous Button -->
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage - 1}&search=${paramSearch}&category=${paramCategory}&status=${paramStatus}&sort=${paramSort}">Previous</a>
                    </li>
                </c:if>

                <!-- Page Number Links -->
                <c:forEach begin="1" end="${totalPages}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                        <a class="page-link" href="?page=${i}&search=${paramSearch}&category=${paramCategory}&status=${paramStatus}&sort=${paramSort}">${i}</a>
                    </li>
                </c:forEach>

                <!-- Next Button -->
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${currentPage + 1}&search=${paramSearch}&category=${paramCategory}&status=${paramStatus}&sort=${paramSort}">Next</a>
                    </li>
                </c:if>
            </ul>
        </nav>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="script.js"></script>

        <script>
                                                   $(document).ready(function () {
                                                       // Check if there are validation errors
            <% if (request.getAttribute("errors") != null) { %>
                                                       // Open the modal if there are errors
                                                       $('#addBlogModal').modal('show');

                                                       // Change the URL to the current page (if necessary)
                                                       const currentUrl = window.location.href;
                                                       const errorUrl = currentUrl.replace("/editBlogMarketting", "/createBlogMarketting"); // Change to appropriate URL
                                                       history.pushState(null, null, errorUrl);

                                                       // Listen for modal close event
                                                       $('#addBlogModal').on('hidden.bs.modal', function () {
                                                           // Change the URL back to the /marketing/listBlog path
                                                           history.pushState(null, null, '/SWP391Gr5/marketing/listblogmarketting');
                                                       });
            <% } %>
                                                   });
        </script>

        <script>
            $(document).ready(function () {
                // Check if the blogById object exists
            <c:if test="${not empty blogById}">
                // Show the modal automatically when blogById is present
                $('#blogModal').modal('show');

                // Change the URL to the blog view URL
                const currentUrl = window.location.href;
                const blogUrl = currentUrl.replace("/listBlogMarketting", `/marketting/viewBlog?id=${blogById.blogId}`);
                history.pushState(null, null, blogUrl);

                // Listen for modal close event
                $('#blogModal').on('hidden.bs.modal', function () {
                    // Change the URL back to the /marketing/listBlogMarketting path
                    history.pushState(null, null, '/SWP391Gr5/marketting/listblogmarketting');
                });
            </c:if>
            });

        </script>

        <script>
            function closeAlert(button) {
                const alert = button.closest('[show-alert]');
                alert.classList.add('alert-hidden');
                setTimeout(() => {
                    alert.parentElement.remove();
                }, 500);
            }

            // Auto-hide the alert after 5 seconds
            setTimeout(() => {
                const alert = document.querySelector('[show-alert]');
                if (alert) {
                    closeAlert(alert.querySelector('button'));
                }
            }, 5000);
        </script>






    </body>
</html>
