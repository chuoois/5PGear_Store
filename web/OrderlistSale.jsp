<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Order List - Sale</title>
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

                <!-- Sidebar -->
                <nav class="col-md-2 d-none d-md-block sidebar">
                    <div class="sidebar-sticky">

                        <h6 class="sidebar-heading d-flex justify-content-between align-items-center px-3 mt-4 mb-1 text-muted">
                            <span>Manage</span>
                        </h6>
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link" href="#">
                                    <i class="fas fa-users"></i> Order List
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
                            <span>Welcome, ${sales.getSaleName()}</span>
                            <a href="logoutstaff" class="btn btn-outline-danger btn-sm ml-2">
                                <i class="fas fa-sign-out-alt"></i> Logout
                            </a>
                        </div>

                    </header>

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

                    <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                        <h1 class="h2">Order List</h1>
                    </div>

                    <!-- Filter and Search Form -->
                    <form action="orderlist" method="get" class="mb-4">
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
                                <input type="text" name="searchQuery" class="form-control" value="${param.searchQuery}" placeholder="Search customer ">
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
                                    <th>Order Date</th>
                                    <th>Status</th>
                                    <th>Payment</th>
                                    <th>Total Price</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${orderList}" var="order">
                                    <tr>
                                        <td>${order.orderId}</td>
                                        <td>${order.customerId.customerName}</td>
                                       

                                        <td><fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd"/></td>
                                        <td>${order.ostatusId.ostatusName}</td>
                                        <td>${order.payment}</td>
                                        <td><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="$"/></td>
                                        <td>
                                            <button type="button" class="btn btn-info btn-sm" onclick="loadOrderDetails('${order.orderId}')">
                                                <i class="fas fa-eye"></i> View
                                            </button>
                                            <c:if test="${order.ostatusId.ostatusName eq 'Pending'}">
                                                <a href="#" class="btn btn-sm btn-success" onclick="updateStatus(${order.orderId}, 'Approve')">
                                                    <i class="fas fa-check"></i> Approve
                                                </a>
                                                <a href="#" class="btn btn-sm btn-danger" onclick="updateStatus(${order.orderId}, 'Cancel')">
                                                    <i class="fas fa-times"></i> Cancel
                                                </a>

                                            </c:if>
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
                                    <a class="page-link" href="orderlist?pageNumber=${currentPage - 1}&size=${pageSize}${searchParams}" tabindex="-1">Previous</a>
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
                                            <a class="page-link" href="orderlist?pageNumber=${i}&size=${pageSize}${searchParams}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${currentPage lt totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="orderlist?pageNumber=${currentPage + 1}&size=${pageSize}${searchParams}">Next</a>
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

                                                    function updateStatus(orderId, newStatus) {
                                                        if (confirm('Are you sure you want to ' + newStatus + ' this order?')) {
                                                            window.location.href = 'orderlist?orderId=' + orderId + '&newStatus=' + newStatus;
                                                        }
                                                    }

                                                    function closeAlert(button) {
                                                        const alert = button.closest('[show-alert]');
                                                        alert.classList.add('alert-hidden');
                                                        setTimeout(() => {
                                                            alert.parentElement.remove();
                                                        }, 500);
                                                    }

                                                    setTimeout(() => {
                                                        const alert = document.querySelector('[show-alert]');
                                                        if (alert) {
                                                            closeAlert(alert.querySelector('button'));
                                                        }
                                                    }, 5000);
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
                        <!-- Shipping Information -->
                        <h5>Customer Information</h5>
                        <table class="table table-bordered">
                            <tbody>

                            </tbody>
                        </table>

                        <h5>Order Details</h5>
                        <table class="table table-bordered">
                            <thead>
                                <tr>
                                    <th>Product Image</th>
                                    <th>Product Name</th>
                                    <th>Quantity</th>
                                    <th>Price</th>                                    
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody id="orderDetailsList">
                                <!-- Order details will be dynamically loaded here -->
                            </tbody>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html> 