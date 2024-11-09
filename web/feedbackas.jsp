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
                    <h1 class="h2">Feedback</h1>
                </div>





                <div class="table-responsive">
                    <table class="table  table-sm text-center">
                        <thead>
                            <tr> 
                                <th>Product</th>                              
                                <th>Name</th>
                                <th>Price</th>                                                   
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orderDetailsList}" var="orderlist">                         
                                <tr>  
                                    <td>  <img src="${orderlist.getProductId().getImgPath()}" class="img-fluid me-5" style="width: 100px; height: 100px;" alt=""></td>                                    
                                    <td>${orderlist.getProductId().getProductName()}</td>
                                    <td>${orderlist.getProductId().getPrice()}</td>


                                    <td>

                                        <div class="btn-group">
                                            <c:if test="${!feedbackMap[orderlist.getProductId().getProductId()]}">
                                                <button class="btn btn-sm btn-success" onclick="openFeedbackModal('${orderlist.getProductId().getProductId()}')">
                                                    Feedback
                                                </button>
                                            </c:if>

                                            <div class="btn-group">

                                                <button class="btn btn-sm btn-info" >
                                                    <a href="feedback?productid=${orderlist.getProductId().getProductId()}"> View</a>
                                                </button>
                                            </div>
                                            <!-- Feedback Modal -->
                                            <div class="modal fade" id="feedbackModal" tabindex="-1" role="dialog" aria-labelledby="feedbackModalLabel" aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <h5 class="modal-title" id="feedbackModalLabel">Provide Feedback</h5>
                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                <span aria-hidden="true">&times;</span>
                                                            </button>
                                                        </div>
                                                        <div class="modal-body">
                                                            <form action="feedbackas" method="POST">
                                                                <input type="hidden" name="order_id" value="${orderlist.getOrderId().getOrderId()}">
                                                                <input type="hidden" id="productId" name="productId" value="${orderlist.getProductId().getProductId()}">
                                                                <div class="form-group">
                                                                    <label  for="customerName">Your Name</label>
                                                                    <input value="${account.getCustomerName()}" type="text" class="form-control" id="customerName" required readonly="">
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="rating">Rate:</label>
                                                                    <select class="form-control" id="rating" name="rating" required>
                                                                        <option value="">Select Rating</option>
                                                                        <option value="1">1 Star</option>
                                                                        <option value="2">2 Stars</option>
                                                                        <option value="3">3 Stars</option>
                                                                        <option value="4">4 Stars</option>
                                                                        <option value="5">5 Stars</option>
                                                                    </select>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="feedbackComment">Comment</label>
                                                                    <textarea class="form-control" id="feedbackComment" name="feedbackComment" rows="4" required></textarea>
                                                                </div>

                                                                <div class="modal-footer">
                                                                    <button type="submit" class="btn btn-primary" >Submit</button>
                                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>

                                                                </div>
                                                            </form>

                                                        </div>

                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <!-- JavaScript to open modal and set product ID -->
                                        <script>
                                            function openFeedbackModal(productId) {
                                                document.getElementById('productId').value = productId;
                                                $('#feedbackModal').modal('show');
                                            }

                                        </script>


                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

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
                                            function                updateStatus(orderId, newStatus) {
                                                if (confirm('Are you sure you want to ' + newStatus + ' this order?')) {
                                                    window.location.href = 'warehouse?orderId=' + orderId + '&newStatus=' + newStatus;
                                                }
                                            }

                                            funct                ion closeAlert(button) {
                                                const alert = button.closest('[show-alert]');
                                                alert.classList.add('alert-hidden');
                                                setTimeout(() => {
                                                    alert.parentElement.remove();
                                                }, 500);
                                            }

                                            setTimeout(() => {
                                                const alert = doc
                                                ument.querySelector('[show-alert]');
                                                if (alert) {
                                                    closeAlert(alert.querySelector('button'));
                                                }
                                            }, 5000);
        </script>
        <div class="modal fade" id="orderDetailsModal" tabin                dex="-1" role="dialog" aria-labelledby="orderDetailsModalLabel" aria-hidden="true">
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