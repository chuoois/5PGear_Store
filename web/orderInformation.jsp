<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
    <jsp:include page="layout/head.jsp"/>
    <body>
        <jsp:include page="layout/menu.jsp"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <style>
            #addressSelectionModal .modal-dialog {
                max-width: 80%;
                width: 600px;
            }
            .payment-button {
                position: relative;
                cursor: pointer;
            }

            .payment-button input[type="radio"] {
                display: none;
            }

            .payment-button span {
                padding: 10px 20px;
                border: 1px solid #ccc;
                border-radius: 4px;
                transition: background-color 0.3s, color 0.3s;
            }

            .payment-button input[type="radio"]:checked + span {
                background-color: #343a40;
                color: white;
                border-color: #343a40;
            }

        </style>
        <!-- Breadcrumb -->
        <nav class="breadcrumb-nav" style="margin-top: 100px;">
            <div class="container">
                <ol class="breadcrumb" style="display: flex; justify-content: center;">
                    <li class="breadcrumb-item" style="font-size: 20px;"><a href="cart">Shopping Cart</a></li>
                    <li class="breadcrumb-item active" aria-current="page" style="font-size: 20px;">Payment</li>
                </ol>
            </div>
        </nav>

        <!-- Checkout Form -->
        <main class="main checkout my-5">
            <div class="container-fluid px-0">
                <div class="row justify-content-center">
                    <!-- Payment Details Section -->
                    <div class="col-md-8">
                        <div class="card border-1 shadow-sm">
                            <div class="card-body p-4">
                                <h5 class="font-weight-bold mb-4">YOUR ORDER</h5>

                                <!-- Cart Items List -->
                                <c:forEach var="cartItem" items="${cart1}">
                                    <div class="row align-items-center mb-3 border-bottom pb-3">
                                        <div class="col-4 col-md-3 text-center">
                                            <img src="${cartItem.getProduct().getImgPath()}" alt="product" class="img-fluid rounded" style="max-height: 100px;">
                                        </div>
                                        <div class="col-8 col-md-6">
                                            <p class="mb-0"><b>${cartItem.getProduct().getProductName()}</b></p>
                                            <p class="mb-0">Quantity: ${cartItem.quantity}</p>
                                        </div>
                                        <div class="col-12 col-md-3 text-right">
                                            <c:set var="finalPrice1" value="${(cartItem.getProduct().getPrice() - (cartItem.getProduct().getPrice() * (cartItem.getProduct().getSale() / 100))) * cartItem.quantity}" />
                                            <p class="mb-0"><b>Price: </b> <fmt:formatNumber value="${finalPrice1}" type="currency" currencySymbol="$" pattern="###0.00" />$</p>
                                        </div>
                                    </div>
                                </c:forEach>

                                <div class="row mt-4">
                                    <div class="col-8 text-left">
                                        <h5><b>Total</b></h5>
                                    </div>
                                    <div class="col-4 text-right">
                                        <h5 style="text-align: center"><b><fmt:formatNumber value="${subtotal1}" type="currency" currencySymbol="$" pattern="###0.00" />$</b></h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Customer Information Section -->
                    <div class="col-md-8 mb-4 mt-4">
                        <div class="card border-1 shadow-sm">
                            <div class="card-body p-4">
                                <h5 class="font-weight-bold mb-4">DELIVERY ADDRESS</h5>
                                <hr>
                                <div id="defaultAddressDisplay" class="d-flex align-items-center">
                                    <div class="mb-3 flex-grow-1">
                                        <div class="card border-0">
                                            <div class="card-body">
                                                <p class="mb-0">
                                                    <span id="defaultName">${account.customerName}</span> - 
                                                    <span id="defaultPhone">${account.phone}</span> - 
                                                    <span id="defaultAddress">${account.address}</span>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                    <a href="#" data-toggle="modal" data-target="#addressSelectionModal" class="btn btn-outline-dark btn-sm ml-3">
                                        <i class="fas fa-edit"></i> Update
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Payment method -->
                    <div class="col-md-8 mb-4">
                        <div class="card border-1 shadow-sm">
                            <div class="card-body p-4">
                                <!-- Wrap everything inside the form -->
                                <form action="ordercheckout" method="POST">
                                    <div class="d-flex justify-content-between align-items-center mb-4">
                                        <h5 class="font-weight-bold mb-0">PAYMENT METHOD</h5>
                                        <div class="d-flex">
                                            <!-- Payment method options -->
                                            <label class="payment-button me-2">
                                                <input type="radio" name="payment" value="COD" aria-label="Cash on Delivery" required />
                                                <span class="btn btn-outline-dark"><i class="fas fa-truck"></i> COD</span>
                                            </label>
                                            <label class="payment-button">
                                                <input type="radio" name="payment" value="VNPAY" aria-label="VNPAY" required />
                                                <span class="btn btn-outline-dark"><i class="fas fa-credit-card"></i> VNPAY</span>
                                            </label>
                                        </div>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-center mb-4" style="border: 1px solid #ccc; padding: 10px;">
                                        <span class="font-weight-bold">Total:</span>
                                        <span class="font-weight-bold">Price: <span id="price"><fmt:formatNumber value="${subtotal1}" type="currency" currencySymbol="$" pattern="###0.00" />$</span></span>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <p class="text-dark"> Clicking "Place Order" means you agree to the Store Terms</p>

                                        <!-- Hidden input for the selected address -->
                                        <input type="hidden" name="selectedAddress" id="selectedAddress" value="default" />

                                        <!-- Submit button -->
                                        <button type="submit" class="btn btn-dark btn-block">PLACE ORDER</button>
                                    </div>
                                </form> 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>

        <jsp:include page="layout/footer.jsp"/>

        <!-- Address Selection Modal -->
        <div class="modal fade" id="addressSelectionModal" tabindex="-1" role="dialog" aria-labelledby="addressSelectionModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addressSelectionModalLabel">Select or Add Address</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <!-- Default Address -->
                        <div class="mb-3">
                            <div class="card-body">
                                <div class="form-check">
                                    <input type="radio" class="form-check-input" name="selectedAddresses" id="defaultAddress" value="default" checked />
                                    <label class="form-check-label" for="defaultAddress">
                                        <strong>${account.customerName}</strong> - ${account.phone} - ${account.address}
                                    </label>
                                    <small class="text-danger border border-danger p-1" style="font-size: 12px; margin-right: 10px;">Default Address</small>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <!-- Saved Addresses -->
                        <c:set var="addressManager" value="${sessionScope.addressManager}" />
                        <c:if test="${not empty addressManager}">
                            <c:forEach items="${addressManager.getAddresses(account.email)}" var="address" varStatus="status">
                                <div class="mb-3">
                                    <div class="card-body d-flex justify-content-between align-items-center">
                                        <div class="form-check">
                                            <input type="radio" class="form-check-input" name="selectedAddresses" id="address${status.index}" 
                                                   value="${status.index}" 
                                                   data-name="${address.name}" 
                                                   data-phone="${address.phone}" 
                                                   data-detail="${address.detail}"
                                                   onclick="updateAddressDisplay(this)" />
                                            <label class="form-check-label" for="address${status.index}">
                                                <strong>${address.name}</strong> - ${address.phone} - ${address.detail}
                                            </label>
                                        </div>
                                        <form action="orderinfor" method="GET">
                                            <input type="hidden" name="action" value="delete" />
                                            <input type="hidden" name="index" value="${status.index}" />
                                            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                                        </form>
                                    </div>
                                    <hr>
                                </div>
                            </c:forEach>
                        </c:if>

                        <!-- Add new address button -->
                        <div class="text-center mt-4">
                            <button class="btn btn-block" data-toggle="modal" data-target="#newAddressModal">
                                <i class="fas fa-plus"></i>Add new address
                            </button>
                        </div>
                    </div>
                    <div class="modal-footer d-flex justify-content-between">
                        <button type="button" class="btn btn-dark" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-dark" data-dismiss="modal" id="confirmAddressSelection">Confirm</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal for adding new address -->
        <div class="modal fade" id="newAddressModal" tabindex="-1" role="dialog" aria-labelledby="newAddressModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="newAddressModalLabel">Add New Address</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="orderinfor" method="POST">
                            <input type="hidden" name="action" value="add" />
                            <div class="form-group">
                                <label for="newAddressName">Receiver:</label>
                                <input type="text" class="form-control" id="newAddressName" name="name" placeholder="Recipient name" required />
                            </div>
                            <div class="form-group mt-4">
                                <label for="newAddressPhone">Phone Number:</label>
                                <input type="tel" class="form-control" id="newAddressPhone" name="phone" placeholder="Phone number" pattern="^[0-9]{10,11}$" required />
                                <small id="phoneHelp" class="form-text text-muted">Enter a valid phone number (10-11 digits).</small>
                            </div>
                            <div class="form-group mt-4">
                                <label for="newAddressDetail">Address:</label>
                                <textarea class="form-control" id="newAddressDetail" name="detail" placeholder="Specific address" required></textarea>
                            </div>
                            <div class="form-group text-center mt-4">
                                <button type="submit" class="btn btn-dark btn-block">Save Address</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Script files -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                                                       function updateAddressDisplay(radio) {
                                                           // Get the selected address details
                                                           const name = radio.getAttribute('data-name');
                                                           const phone = radio.getAttribute('data-phone');
                                                           const detail = radio.getAttribute('data-detail');

                                                           // Update the display with the selected address
                                                           document.getElementById('defaultName').innerText = name;
                                                           document.getElementById('defaultPhone').innerText = phone;
                                                           document.getElementById('defaultAddress').innerText = detail;

                                                           // Update the hidden input for the selected address
                                                           document.getElementById('selectedAddress').value = radio.value;
                                                       }

                                                       // Kiểm tra nếu URL chứa tham số "updateaddress"
                                                       window.onload = function () {
                                                           const urlParams = new URLSearchParams(window.location.search);
                                                           if (urlParams.has('updateaddress')) {
                                                               // Kích hoạt sự kiện click vào nút "Update"
                                                               document.querySelector('[data-target="#addressSelectionModal"]').click();
                                                           }
                                                       };
        </script>
    </body>
</html>