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
            .status-link {
                text-decoration: none;
                color: #333;
            }

            .status-link.active {
                color: red;
                font-weight: bold;
            }

            .status-link:hover {
                text-decoration: none;
            }
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

        </style>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <!-- Sidebar -->
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
                        </ul>
                    </div>
                </nav>


                <!-- Main content -->
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                    <header class="main-header d-flex justify-content-between align-items-center py-3">
                        <div></div>
                        <div class="user-info">
                            <span>Welcome, ${waleStaff.warehousestaffName}</span>
                            <a id="logout-link" href="logoutstaff" class="btn btn-outline-danger btn-sm ml-2">
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
                        <h1 class="h2">Warehouse Order List</h1>
                    </div>

                    <div class="d-flex justify-content-between mb-3 ml-5 mr-5">
                        <a href="warehouse" class="status-link ${empty param.type ? 'active' : ''}">
                            All (${allorder1})
                        </a>
                        <a href="warehouse?type=4" class="status-link ${param.type == '4' ? 'active' : ''}">
                            Processing (${allorder4})
                        </a>
                        <a href="warehouse?type=5" class="status-link ${param.type == '5' ? 'active' : ''}">
                            Packed (${allorder5})
                        </a>
                        <a href="warehouse?type=6" class="status-link ${param.type == '6' ? 'active' : ''}">
                            Shipping (${allorder6})
                        </a>
                        <a href="warehouse?type=7" class="status-link ${param.type == '7' ? 'active' : ''}">
                            Success (${allorder7})
                        </a>
                        <a href="warehouse?type=8" class="status-link ${param.type == '8' ? 'active' : ''}">
                            Failed (${allorder8})
                        </a>
                        <a href="warehouse?type=1" class="status-link ${param.type == '1' ? 'active' : ''}">
                            Reject (${allorder2})
                        </a>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped table-sm text-center">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Customer Account Name</th>
                                    <th>Total Price</th>
                                    <th>Status</th>
                                    <th>View Details</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${orderDetailsList}" var="order">
                                    <c:choose>
                                        <c:when test="${empty param.type}">
                                            <c:if test="${order.ostatusId.ostatusId >= 3 or order.ostatusId.ostatusId == 1}">
                                                <tr>
                                                    <td>${order.orderId}</td>
                                                    <td>${order.customerId.customerName}</td>
                                                    <c:set var="formattedPrice" value="${order.totalPrice}" />
                                                    <td>${formattedPrice}$</td>
                                                    <td>${order.ostatusId.ostatusName}</td>
                                                    <td>
                                                        <button type="button" class="btn btn-info btn-sm" onclick="loadOrderDetails('${order.orderId}')">
                                                            <i class="fas fa-eye"></i> View Details
                                                        </button>
                                                    </td>
                                                    <td>
                                                        <div class="btn-group">
                                                            <c:choose>
                                                                <c:when test="${order.ostatusId.ostatusName eq 'Approved'}">
                                                                    <a href="#" class="btn btn-sm btn-primary btn-action" onclick="updateStatus(${order.orderId}, 'Processing')">
                                                                        <i class="fas fa-check"></i> Processing
                                                                    </a>
                                                                    <a href="#" class="btn btn-sm btn-danger btn-action" onclick="updateStatus(${order.orderId}, 'Cancel')">
                                                                        <i class="fas fas fa-times"></i> Reject
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${order.ostatusId.ostatusName eq 'Processing'}">
                                                                    <a href="#" class="btn btn-sm btn-warning btn-action" onclick="updateStatus(${order.orderId}, 'Packed')">
                                                                        <i class="fas fa-box"></i> Packed
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${order.ostatusId.ostatusName eq 'Packed'}">
                                                                    <a href="#" class="btn btn-sm btn-warning btn-action" onclick="updateStatus(${order.orderId}, 'Shipping')">
                                                                        <i class="fas fa-truck"></i> Shipping
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${order.ostatusId.ostatusName eq 'Shipping'}">
                                                                    <a href="#" class="btn btn-sm btn-success btn-action" onclick="updateStatus(${order.orderId}, 'Success')">
                                                                        <i class="fas fa-check-double"></i> Success
                                                                    </a>
                                                                    <a href="#" class="btn btn-sm btn-danger btn-action" onclick="updateStatus(${order.orderId}, 'Failed')">
                                                                        <i class="fas fa-exclamation"></i> Failed
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a style="color: green">
                                                                        <i class="fas fa-circle-check"></i> Complete
                                                                    </a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${order.ostatusId.ostatusId == param.type}">
                                                <tr>
                                                    <td>${order.orderId}</td>
                                                    <td>${order.customerId.customerName}</td>
                                                    <c:set var="formattedPrice" value="${order.totalPrice}" />
                                                    <td>${formattedPrice}$</td>
                                                    <td>${order.ostatusId.ostatusName}</td>
                                                    <td>
                                                        <button type="button" class="btn btn-info btn-sm" onclick="loadOrderDetails('${order.orderId}')">
                                                            <i class="fas fa-eye"></i> View Details
                                                        </button>
                                                    </td>
                                                    <td>
                                                        <div class="btn-group">
                                                            <c:choose>
                                                                <c:when test="${order.ostatusId.ostatusName eq 'Approved'}">
                                                                    <a href="#" class="btn btn-sm btn-primary btn-action" onclick="updateStatus(${order.orderId}, 'Processing')">
                                                                        <i class="fas fa-check"></i> Processing
                                                                    </a>
                                                                    <a href="#" class="btn btn-sm btn-danger btn-action" onclick="updateStatus(${order.orderId}, 'Cancel')">
                                                                        <i class="fas fas fa-times"></i> Cancel
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${order.ostatusId.ostatusName eq 'Processing'}">
                                                                    <a href="#" class="btn btn-sm btn-warning btn-action" onclick="updateStatus(${order.orderId}, 'Packed')">
                                                                        <i class="fas fa-box"></i> Packed
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${order.ostatusId.ostatusName eq 'Packed'}">
                                                                    <a href="#" class="btn btn-sm btn-warning btn-action" onclick="updateStatus(${order.orderId}, 'Shipping')">
                                                                        <i class="fas fa-truck"></i> Shipping
                                                                    </a>
                                                                </c:when>
                                                                <c:when test="${order.ostatusId.ostatusName eq 'Shipping'}">
                                                                    <a href="#" class="btn btn-sm btn-success btn-action" onclick="updateStatus(${order.orderId}, 'Success')">
                                                                        <i class="fas fa-check-double"></i> Success
                                                                    </a>
                                                                    <a href="#" class="btn btn-sm btn-danger btn-action" onclick="updateStatus(${order.orderId}, 'Failed')">
                                                                        <i class="fas fa-exclamation"></i> Failed
                                                                    </a>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <a style="color: green">
                                                                        <i class="fas fa-circle-check"></i> Complete
                                                                    </a>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <!-- Pagination -->
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage > 1}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage - 1}${searchParams}">Previous</a>
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
                                            <a class="page-link" href="?page=${i}${searchParams}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${currentPage lt totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="?page=${currentPage + 1}${searchParams}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </main>
            </div>
        </div>

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
                        <!-- Content will be loaded here -->
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
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
                                                                            if (confirm('Are you sure you want to change the status to ' + newStatus + '?')) {
                                                                                window.location.href = 'warehouse?orderId=' + orderId + '&newStatus=' + newStatus;
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
    </body>
</html>