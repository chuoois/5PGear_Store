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
        <jsp:include page="layout/head.jsp"/>
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

            .pagination {
                justify-content: center;
            }

            .warehouse-container {
                border: 1px solid #ddd;
                border-radius: 8px;
                margin: 20px auto;
                padding: 20px;
                max-width: 90%;
            }
            .status-button-container {
                display: flex;
                justify-content: space-around;
                gap: 10px;
                flex-wrap: wrap;
            }

            .status-button-container {
                display: flex;
                justify-content: space-around;
                border-bottom: 1px solid #ddd;
                padding-bottom: 8px;
                margin-bottom: 16px;
            }

            /* Button styles to look like tabs */
            .status-link {
                background: none;
                border: none;
                color: #333;
                font-size: 16px;
                padding: 8px 12px;
                cursor: pointer;
                transition: color 0.3s, border-bottom 0.3s;
                text-decoration: none;
            }

            /* Hover effect */
            .status-link:hover {
                color: #007bff;
            }

            /* Active tab styling */
            .status-link.active {
                color: red;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <jsp:include page="layout/menu.jsp"/>
        <div class="container-fluid" style="padding-top: 100px">
            <div class="warehouse-container">
                <div class="pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">My Order List</h1>
                </div>
                <form action="myoderlist" method="get" class="mb-4">


                    <div class="row">
                        <div class="col-md-3 mb-2">
                            <input type="date" name="fromDate" class="form-control" value="${param.fromDate}" >
                        </div>
                        <div class="col-md-3 mb-2">
                            <input type="date" name="toDate" class="form-control" value="${param.toDate}" >
                        </div>
                        <div class="col-md-3 mb-2">
                            <select name="searchQuery"  class="form-control">
                                <option value="">All Payment</option>
                                <option value="COD" ${param.searchQuery == 'COD' ? 'selected' : ''}>COD</option>
                                <option value="VNPAY" ${param.searchQuery == 'VNPAY' ? 'selected' : ''}>VNPAY</option>
                            </select>
                        </div>
                        <div class="col-md-2 mb-2">
                            <button type="submit" class="btn-primary form-control ">Apply Filters</button>
                        </div>
                    </div>
                    <div class="d-flex justify-content-between mb-3 ml-5 mr-5" style="padding-top: 20px">
                        <button type="submit" class="status-link ${empty param.status ? 'active' : ''}" onclick="setStatus('')">
                            All 
                        </button>
                        <button type="submit" class="status-link ${param.status == 'Pending' ? 'active' : ''}" onclick="setStatus('Pending')">
                            Pending 
                        </button>
                        <button type="submit" class="status-link ${param.status == 'Approved' ? 'active' : ''}" onclick="setStatus('Approved')">
                            Approved 
                        </button>
                        <button type="submit" class="status-link ${param.status == 'Processing' ? 'active' : ''}" onclick="setStatus('Processing')">
                            Processing 
                        </button>
                        <button type="submit" class="status-link ${param.status == 'Packed' ? 'active' : ''}" onclick="setStatus('Packed')">
                            Packed 
                        </button>
                        <button type="submit" class="status-link ${param.status == 'Shipping' ? 'active' : ''}" onclick="setStatus('Shipping')">
                            Shipping 
                        </button>
                        <button type="submit" class="status-link ${param.status == 'Cancel' ? 'active' : ''}" onclick="setStatus('Cancel')">
                            Cancel 
                        </button>
                        <button type="submit" class="status-link ${param.status == 'Success' ? 'active' : ''}" onclick="setStatus('Success')">
                            Success 
                        </button>
                    </div>

                    <!-- Hidden status input -->
                    <input type="hidden" name="status" id="statusInput" value="${param.status}">

                </form>

                <div class="table-responsive">
                    <table class="table table-striped table-sm text-center">
                        <thead>
                            <tr> 
                                <th>Order ID</th>
                                <th>Order Date</th>
                                <th>Total Price</th>
                                <th>Payment</th>
                                <th>Status</th>                             
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orderlist}" var="orderlist">                         
                                <tr>  
                                    <td>${orderlist.getOrderId()}</td>                                    
                                    <td>${orderlist.getOrderDate()}</td>
                                    <td>${orderlist.getTotalPrice()}</td>
                                    <td>${orderlist.getPayment()}</td>
                                    <td>${orderlist. getOstatusId().getOstatusName()}</td>                                     
                                    <td>
                                        <div class="btn-group">
                                            <button class="btn btn-sm btn-success" onclick="loadOrderDetails('${orderlist.getOrderId()}')">
                                                View Detail
                                            </button>
                                        </div>
                                        <c:if test="${orderlist.getOstatusId().getOstatusName() == 'Success'}">
                                            <button class="btn btn-sm btn-success">
                                                 <a href="feedbackas?order_id=${orderlist.getOrderId()}" style="color: white">Feedback</a>
                                            </button>
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
                                <a class="page-link" href="myoderlist?pageNumber=${currentPage - 1}&size=${pageSize}${searchParams}" tabindex="-1">Previous</a>
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
                                        <a class="page-link" href="myoderlist?pageNumber=${i}&size=${pageSize}${searchParams}">${i}</a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        <c:if test="${currentPage lt totalPages}">
                            <li class="page-item">
                                <a class="page-link" href="myoderlist?pageNumber=${currentPage + 1}&size=${pageSize}${searchParams}">Next</a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
        <jsp:include page="layout/footer.jsp"/>
        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!--        <script src="assets/js/bootstrap.bundle.min.js"></script>
        -->        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
        <script>
                                function setStatus(value) {
                                    document.getElementById('statusInput').value = value;
                                }
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