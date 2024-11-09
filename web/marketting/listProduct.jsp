<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
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
                <jsp:include page="sidebar.jsp" />
                <main class="col-md-9 col-lg-10 main-content">
                    <header class="main-header d-flex justify-content-between align-items-center py-3">
                        <button id="sidebar-toggle" class="btn btn-outline-secondary">
                            <i class="fas fa-bars"></i>
                        </button>
                        <div class="user-info">
                            <span>Welcome, Marketting</span>
                            <a href="${pageContext.request.contextPath}/logoutstaff" class="btn btn-outline-danger btn-sm ml-2">
                                <i class="fas fa-sign-out-alt"></i> Logout
                            </a>
                        </div>

                    </header>
                    <div class="dashboard">
                        <h1 class="my-4">Product List</h1>
                        <form method="get" action="/SWP391Gr5/marketting/listproductmarketting">
                            <div class="form-inline my-2">
                                <input type="text" name="search" class="form-control mr-2" placeholder="Search by title or summary" value="${param.search}">

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

                                    <option value="title" ${param.sort == 'title' ? 'selected' : ''}>Sort by Title</option>
                                    <option value="productId"${param.sort == 'productId' ? 'selected' : ''}>Id</option> 
                                    <option value="listPrice" ${param.sort == 'listPrice' ? 'selected' : ''}>Sort by Price</option>
                                    <option value="salePrice" ${param.sort == 'salePrice' ? 'selected' : ''}>Sort by Sale</option>
                                </select>


                                <button type="submit" class="btn btn-primary filter-btn">Apply Filters</button>
                            </div>
                        </form>
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

                        <!-- Add Product Button -->
                        <div class="my-4">
                            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addProductModal">
                                <i class="fas fa-plus"></i> Add Product
                            </button>
                        </div>

                        <!-- Add Product Modal -->
                        <div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content" style="border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);">
                                    <div class="modal-header" style="background-color: #007bff; color: #fff; border-bottom: none;">
                                        <h5 class="modal-title" id="addProductModalLabel" style="font-weight: bold; font-size: 1.5rem;">Add New Product</h5>                                     
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
                                        <form action="/SWP391Gr5/marketting/createProductMarketting" method="POST" enctype="multipart/form-data">
                                            <div class="form-group">
                                                <label for="productName">Product Name</label>
                                                <input type="text" class="form-control" id="productName" name="productName" required>
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
                                                <label for="brand">Brand</label>
                                                <select class="form-control" id="brand" name="brand" required>
                                                    <c:forEach var="brand" items="${listBrand}">
                                                        <option value="${brand.brandId}">${brand.brandName}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                            <div class="form-group">

                                                <label for="description">Description</label>
                                                <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                                            </div>
                                            <!--                                            <div class="form-group">
                                                                                            <label for="imgPath">Image Path</label>
                                                                                            <input type="text" class="form-control" id="imgPath" name="imgPath" required>
                                                                                        </div>-->
                                            <div class="form-group">
                                                <label for="imgPath">Image Upload</label>
                                                <input type="file" class="form-control" id="imgPath" name="imgPath" accept="image/*" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="releaseYear">Release Year</label>
                                                <input type="number" class="form-control" id="releaseYear" name="releaseYear" required>
                                            </div>
                                            <div class="form-group">
                                                <label for="status">Status</label>
                                                <select class="form-control" id="status" name="status" required>
                                                 
                                                    <option value="0">Inactive</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">Add Product</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <!-- Product Table -->
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Thumbnail</th>
                                    <th>Title</th>
                                    <th>Category</th>
                                    <th>Price</th>
                                    <th>Import Price</th>
                                    <th>Sale</th>
                                    <th>Status</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach var="product" items="${filteredProductList}">
                                    <tr>
                                        <td>${product.productId}</td>
                                        <td><img src="../${product.imgPath}" alt="${product.productName}" width="100"></td>
                                        <td>${product.productName}</td>
                                        <td>${product.category.categoryName}</td>
                                        <td>
                                            
                                            <span style="color: red; margin-left: 10px;">
                                                <fmt:formatNumber value="${product.price - (product.price * product.sale / 100)}" type="number" groupingUsed="true" minFractionDigits="1" maxFractionDigits="2"/>$
                                            </span>


                                        </td>
                                          <td>
                                                ${product.importPrice}$
                                        </td>

                                        <td>${product.sale}%</td>
                                        <td>
                                            <button class="btn ${product.status == 1 ? 'btn-success' : 'btn-danger'}">
                                                ${product.status == 1 ? 'Active' : 'Inactive'}
                                            </button>
                                        </td>

                                        <td>
                                            <a href="viewProduct?id=${product.productId}" class="btn btn-info btn-sm"><i class="fas fa-eye"></i> View</a>

                                            <a href="editProductMarketting?id=${product.productId}" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Edit</a>

                                            <a href="changestatusproduct?id=${product.productId}&page=${currentPage}&search=${paramSearch}&category=${paramCategory}&status=${paramStatus}&sort=${paramSort}" 
                                               class="btn btn-secondary btn-sm" 
                                               onclick="return confirm('Are you sure you want to ${product.status == 1 ? 'hide' : 'show'} this product?');">
                                                <i class="fas ${product.status==1 ? 'fa-eye' : 'fa-eye-slash'}"></i> 
                                                ${product.status==1 ? 'Hide' : 'Show'}
                                            </a>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                        <!-- Product Details Modal -->
                        <div class="modal fade" id="productModal" tabindex="-1" role="dialog" aria-labelledby="productModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content" style="border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);">
                                    <div class="modal-header" style="background-color: #007bff; color: #fff; border-bottom: none;">
                                        <h5 class="modal-title" id="productModalLabel" style="font-weight: bold; font-size: 1.5rem;">Product Details</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: #fff; opacity: 1;">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body" style="padding: 20px;">
                                        <c:if test="${not empty productById}">
                                            <div class="product-details" style="text-align: left;">
                                                <div class="text-center">
                                                    <img src="../${productById.imgPath}" alt="${productById.productName}" class="img-fluid mb-3" style="max-width: 70%; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1); margin-bottom: 15px;">
                                                </div>
                                                <h4 style="font-size: 1.4rem; font-weight: bold; margin-bottom: 10px;">${productById.productName}</h4>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Brand:</strong> ${productById.brand.brandName}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Category:</strong> ${productById.category.categoryName}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Price:</strong> $${productById.price}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Sale Price:</strong> $${productById.sale}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Description:</strong> ${productById.description}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Status:</strong> ${productById.status==1 ? 'Active' : 'InActive'}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;"><strong style="color: #333;">Date Create</strong> ${productById.dateCreate}</p>
                                                <p style="font-size: 1rem; margin-bottom: 8px; line-height: 1.5;">
                                                    <strong style="color: #333;">Rate:</strong>
                                                    <!-- Loop for displaying filled stars -->
                                                    <c:forEach begin="1" end="${productById.getRate()}" var="i">
                                                        <i class="fa fa-star" style="color: #FFD700;"></i> <!-- Filled star -->
                                                    </c:forEach>

                                                </p>

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
                                                       $('#addProductModal').modal('show');

                                                       // Change the URL to the current page (if necessary)
                                                       const currentUrl = window.location.href;
                                                       const errorUrl = currentUrl.replace("/editProductMarketting", "/createProductMarketting"); // Change to appropriate URL
                                                       history.pushState(null, null, errorUrl);

                                                       // Listen for modal close event
                                                       $('#addProductModal').on('hidden.bs.modal', function () {
                                                           // Change the URL back to the /marketting/home path
                                                           history.pushState(null, null, '/SWP391Gr5/marketting/listproductmarketting');
                                                       });
            <% } %>
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
        <script>
            $(document).ready(function () {
                // Check if the productById object exists
            <c:if test="${not empty productById}">
                // Show the modal automatically when productById is present
                $('#productModal').modal('show');

                // Change the URL to the product view URL
                const currentUrl = window.location.href;
                const productUrl = currentUrl.replace("/home", `/viewProduct?id=${productById.productId}`);
                history.pushState(null, null, productUrl);

                // Listen for modal close event
                $('#productModal').on('hidden.bs.modal', function () {
                    // Change the URL back to the /marketting/home path
                    history.pushState(null, null, '/SWP391Gr5/marketting/listproductmarketting');
                });
            </c:if>
            });
        </script>


    </body>
</html>
