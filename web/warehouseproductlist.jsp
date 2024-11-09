<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>WareHouse</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link href="assets/css/styleAdmindashboard.css" rel="stylesheet" type="text/css"/>
        <style>
            .modal-body {
                padding: 20px;
                background-color: #f9f9f9;
                border-radius: 8px;
                box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            }

            #userAvatar {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                object-fit: cover;
                margin-bottom: 15px;
            }

            .modal-body p {
                margin: 10px 0;
                font-size: 16px;
                color: #333;
            }

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

            .pagination {
                justify-content: center;
            }

            .table th, .table td {
                vertical-align: middle;
            }

            .btn-action {
                margin: 0 5px;
            }

            .btn-success {
                background-color: #28a745;
            }

            .btn-danger {
                background-color: #dc3545;
            }

            .btn-warning {
                background-color: #ffc107;
            }

            .btn-primary {
                background-color: #007bff;
            }
        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
                <nav class="col-md-2 d-none d-md-block sidebar">
                    <div class="sidebar-sticky">
                        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                            <span>Warehouse Management</span>
                        </h6>
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link active" href="warehouse">
                                    <i class="fas fa-box"></i> Warehouse Order List
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link active" href="warehouseproduct">
                                    <i class="fas fa-laptop"></i> Warehouse Product List
                                </a>
                            </li>
                            <!-- Add more sidebar items as needed -->
                        </ul>
                    </div>
                </nav>

                <!-- Main content -->
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                    <header class="main-header d-flex justify-content-between align-items-center py-3">
                        <button id="sidebar-toggle" class="btn btn-outline-secondary">
                            <i class="fas fa-bars"></i>
                        </button>
                        <div class="user-info">
                            <span>Welcome, ${waleStaff.warehousestaffName}</span>
                            <a href="logoutstaff" class="btn btn-outline-danger btn-sm ml-2">
                                <i class="fas fa-sign-out-alt"></i> Logout
                            </a>
                        </div>
                    </header>


                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">Warehouse Product List</h1>
                    </div>

                    <!-- Filter and Search Form -->
                    <form method="get" action="warehouseproduct">
                        <div class="form-inline my-2">
                            <input type="text" name="search" class="form-control mr-2" placeholder="Search by title or summary" value="${param.search}">

                            <select name="category" class="form-control mr-2">
                                <option value="">All Categories</option>
                                <c:forEach var="category" items="${listCategories}">
                                    <option value="${category.categoryId}" ${category.categoryId == param.category ? 'selected' : ''}>${category.categoryName}</option>
                                </c:forEach>
                            </select>

                            <select name="brand" class="form-control mr-2">
                                <option value="">All Brands</option>
                                <c:forEach var="brand" items="${listBrand}">
                                    <option value="${brand.brandId}" ${brand.brandId == param.brand ? 'selected' : ''}>${brand.brandName}</option>
                                </c:forEach>
                            </select>

                            <select name="sort" class="form-control mr-2">
                                <option value="id" ${param.sort == 'id' ? 'selected' : ''}>Sort by Id</option>
                                <option value="quantity" ${param.sort == 'quantity' ? 'selected' : ''}>Sort by quantity</option>
                                <option value="hold"${param.sort == 'hold' ? 'selected' : ''}>Sort by hold</option> 
                                <option value="available"${param.sort == 'available' ? 'selected' : ''}>Sort by quantity available</option>
                            </select>


                            <button type="submit" class="btn btn-primary filter-btn">Apply Filters</button>
                        </div>
                    </form>
                    <!-- Modal for Importing New Product -->
                    <div class="modal fade" id="addNewProductModal" tabindex="-1" role="dialog" aria-labelledby="addNewProductModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content" style="border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);">
                                <div class="modal-header" style="background-color: #28a745; color: #fff; border-bottom: none;">
                                    <h5 class="modal-title" id="addNewProductModalLabel" style="font-weight: bold; font-size: 1.5rem;">Import New Product</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: #fff; opacity: 1;">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form action="warehouseproduct" method="POST">
                                        <input type="hidden" id="newProductId" name="productId">

                                        <div class="form-group">
                                            <label for="newQuantity">Quantity</label>
                                            <input type="number" class="form-control" id="newQuantity" name="quantity" min="1" required>
                                        </div>

                                        <div class="form-group">
                                            <label for="importPrice">Import Price</label>
                                            <input type="number" class="form-control" id="importPrice" name="importPrice" step="0.01"  min="1" required>
                                        </div>

                                        <button type="submit" class="btn btn-success">Import New Product</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Modal for Importing Existing Product -->
                    <div class="modal fade" id="addProductModal" tabindex="-1" role="dialog" aria-labelledby="addProductModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content" style="border-radius: 10px; box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);">
                                <div class="modal-header" style="background-color: #007bff; color: #fff; border-bottom: none;">
                                    <h5 class="modal-title" id="addProductModalLabel" style="font-weight: bold; font-size: 1.5rem;">Import Product</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close" style="color: #fff; opacity: 1;">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form action="warehouseproduct" method="POST">
                                        <input type="hidden" id="productId" name="productId">

                                        <div class="form-group">
                                            <label for="quantity">Quantity</label>
                                            <input type="number" class="form-control" id="quantity" name="quantity" min="1" required>
                                        </div>

                                        <button type="submit" class="btn btn-primary">Import Product</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped table-sm text-center">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Thumbnail</th>
                                    <th>Title</th>
                                    <th>Category</th>
                                    <th>Brand</th>
                                    <th>Import Price</th>
                                    <th>Quantity</th>
                                    <th>Hold</th>
                                    <th>Quantity Available</th>
                                    <th>Action</th>
                                </tr>
                            </thead>
                            <tbody>

                                <c:forEach var="product" items="${filteredProductList}">
                                    <tr>
                                        <td>${product.getProductId()}</td>
                                        <td><img src="${product.getImgPath()}" alt="${product.getProductName()}" width="100"></td>
                                        <td>${product.getProductName()}</td>
                                        <td>${product.getCategory().getCategoryName()}</td>
                                        <td>${product.getBrand().getBrandName()}</td>
                                        <td> <fmt:formatNumber value="${product.getImportPrice()}" type="currency" currencySymbol="$" pattern="###0.00" />$</td>
                                        <td>                                       
                                            <span style="color: green; margin-left: 10px;">
                                                ${product.getQuantity()}
                                            </span>
                                        </td>

                                        <td> 
                                            <span style="color: red; margin-left: 10px;">
                                                ${product.getHold()}
                                            </span></td>
                                        <td>

                                            ${product.getQuantity()-product.getHold()}

                                        </td>

                                        <td>
                                            <c:choose>
                                                <c:when test="${product.getImportPrice() < 1}">
                                                    <button type="button" class="btn btn-success increase-new-product" data-product-id="${product.getProductId()}">
                                                        <i class="fas fa-plus"></i> Import New Product
                                                    </button>
                                                </c:when>
                                                <c:otherwise>
                                                    <button type="button" class="btn btn-primary increase-quantity" data-product-id="${product.getProductId()}">
                                                        <i class="fas fa-plus"></i> Import Product
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>

                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
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
                </main>
            </div>
        </div>


        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
            $(document).ready(function () {
                // Khi người dùng bấm vào nút "Increase New Product"
                $(".increase-new-product").click(function () {
                    var productId = $(this).data("product-id");
                    $("#newProductId").val(productId);
                    $("#addNewProductModal").modal("show");
                });

                // Khi người dùng bấm vào nút "Import Product"
                $(".increase-quantity").click(function () {
                    var productId = $(this).data("product-id");
                    $("#productId").val(productId);
                    $("#addProductModal").modal("show");
                });
            });
        </script>

    </body>
</html>