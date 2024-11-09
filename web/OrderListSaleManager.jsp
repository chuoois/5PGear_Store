<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Order List - Sale Manager</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <link href="assets/css/styleAdmindashboard.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">

                <!-- Sidebar -->
                <nav class="col-md-2 d-none d-md-block sidebar">
                    <div class="sidebar-sticky">
                        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                            <span>Core</span>
                        </h6>
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link active" href="saledashboard">
                                    <i class="fas fa-tachometer-alt"></i> Dashboard
                                </a>
                            </li>
                        </ul>
                        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                            <span>Manager</span>
                        </h6>
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link" href="salelist">
                                    <i class="fas fa-users"></i> Sales List
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="salemanagerlist">
                                    <i class="fas fa-shopping-cart"></i> Orders List
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <i class="fas fa-cogs"></i> Settings List
                                </a>
                            </li>
                        </ul>
                        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                            <span>Account</span>
                        </h6>
                        <ul class="nav flex-column mb-2">
                            <li class="nav-item">
                                <a class="nav-link" href="logout">
                                    <i class="fas fa-sign-out-alt"></i> Logout
                                </a>
                            </li>
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
                            <span>Welcome, Sale Manager</span>
                            <a href="logoutstaff" class="btn btn-outline-danger btn-sm ml-2">
                                <i class="fas fa-sign-out-alt"></i> Logout
                            </a>
                        </div>
                    </header>

                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">Order List - Assign to Sales</h1>
                    </div>

                    <!-- Filter and Search Form -->
                    <form action="salemanagerlist" method="get" class="mb-4">
                        <div class="row">
                            <div class="col-md-3 mb-2">
                                <input type="date" name="fromDate" class="form-control" value="${param.fromDate}" placeholder="From Date">
                            </div>
                            <div class="col-md-3 mb-2">
                                <input type="date" name="toDate" class="form-control" value="${param.toDate}" placeholder="To Date">
                            </div>
                            <div class="col-md-3 mb-2">
                                <select name="status" class="form-control">
                                    <option value="">All Statuses</option>
                                    <option value="Cancel" ${param.status == 'Cancel' ? 'selected' : ''}>Cancel</option>
                                    <option value="Pending" ${param.status == 'Pending' ? 'selected' : ''}>Pending</option>
                                    <option value="Approved" ${param.status == 'Approved' ? 'selected' : ''}>Approved</option>
                                    <option value="Processing" ${param.status == 'Processing' ? 'selected' : ''}>Processing</option>
                                    <option value="Packing" ${param.status == 'Packing' ? 'selected' : ''}>Packing</option>
                                    <option value="Shipping" ${param.status == 'Shipping' ? 'selected' : ''}>Shipping</option>
                                    <option value="Success" ${param.status == 'Success' ? 'selected' : ''}>Success</option>
                                </select>
                            </div>

                            <div class="col-md-3 mb-2">
                                <input type="text" name="searchQuery" class="form-control" value="${param.searchQuery}" placeholder="Search customer or sale name">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-primary mt-2">Apply Filters</button>
                    </form>

                    <div class="table-responsive">
                        <table class="table table-striped table-sm text-center">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Customer Name</th>
                                    <th>Sale Name</th>
                                    <th>Total Price</th>
                                    <th>Order Date</th>
                                    <th>Status</th>
                                    <th>Payment</th>
                                    <th>Assign to Sale</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${orderList}" var="order">
                                    <tr>
                                        <td>${order.orderId}</td>
                                        <td>${order.customerId.customerName}</td>
                                        <td>${order.saleId.saleName}</td>
                                        <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/></td>
                                        <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd"/></td>
                                        <td>${order.ostatusId.ostatusName}</td>
                                        <td>${order.payment}</td>
                                        <td>
                                            <!-- Dropdown list to assign sale staff -->
                                        <td>
                                            <!-- Dropdown list to assign sale staff -->
                                            <form action="salemanagerlist" method="post">
                                                <input type="hidden" name="orderId" value="${order.orderId}"/>
                                                <select name="saleId" class="form-control">
                                                    <c:forEach items="${salesList}" var="sale">
                                                        <c:set var="pendingCount" value="${pendingOrdersCount[sale.saleId] != null ? pendingOrdersCount[sale.saleId] : 0}" />
                                                        <option value="${sale.saleId}" ${order.saleId.saleId == sale.saleId ? 'selected' : ''}>
                                                            ${sale.saleName} (Pending: ${pendingCount})
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                                <button type="submit" class="btn btn-sm btn-success mt-2">
                                                    Assign
                                                </button>
                                            </form>
                                        </td>

                                        </td>
                                        <td>
                                            <button type="button" class="btn btn-info btn-sm" onclick="loadOrderDetails('${order.orderId}')">
                                                <i class="fas fa-eye"></i> View
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Pagination -->
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage != 1}">
                                <li class="page-item">
                                    <a class="page-link" href="salemanagerlist?page=${currentPage - 1}&size=${pageSize}${searchParams}" tabindex="-1">Previous</a>
                                </li>
                            </c:if>
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <c:choose>
                                    <c:when test="${currentPage eq i}">
                                        <li class="page-item active">
                                            <span class="page-link">${i}</span>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item">
                                            <a class="page-link" href="salemanagerlist?page=${i}&size=${pageSize}${searchParams}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${currentPage lt totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="salemanagerlist?page=${currentPage + 1}&size=${pageSize}${searchParams}">Next</a>
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
                                                function loadOrderDetails(orderId) {
                                                    fetch('orderdetails?orderID=' + encodeURIComponent(orderId))
                                                            .then(response => response.text())
                                                            .then(data => {
                                                                // Update the modal content with the fetched data
                                                                document.getElementById('orderDetailsContent').innerHTML = data;

                                                                // Show the modal
                                                                $('#orderDetailsModal').modal('show');
                                                            })
                                                            .catch(error => {
                                                                console.error('Error fetching order details:', error);
                                                            });
                                                }
        </script>
        <!-- Modal for Order Details -->
        <div class="modal fade" id="orderDetailsModal" tabindex="-1" role="dialog" aria-labelledby="orderDetailsModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="orderDetailsModalLabel">Order Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body" id="orderDetailsContent">
                        <!-- Order details will be dynamically loaded here -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
