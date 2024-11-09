<%-- 
    Document   : cart
    Created on : Sep 27, 2024, 9:16:28 PM
    Author     : acer
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <jsp:include page="layout/head.jsp"/>
    <body>
        <jsp:include page="layout/menu.jsp"/>
        <!-- Add margin to create space between menu and breadcrumb -->
        <nav class="breadcrumb-nav" style="margin-top: 100px;">
            <div class="container">
                <ol class="breadcrumb" style="display: flex; justify-content: center;">
                    <li class="breadcrumb-item active" aria-current="page" style="font-size: 20px;">Shopping Cart</li>
                </ol>
            </div>
        </nav>

        <!-- Cart Page Start -->
        <div class="container-fluid py-4" style="margin-bottom: 100px;">
            <div class="container">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th scope="col">Products</th>
                                <th scope="col">Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Total</th>
                                <th scope="col">Handle</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="cart" items="${cart}">
                                <tr>
                                    <th scope="row">
                                        <div class="d-flex align-items-center">
                                            <img src="${cart.getProduct().getImgPath()}" class="img-fluid me-5" style="width: 100px; height: 100px;" alt="">
                                        </div>
                                    </th>
                                    <td>
                                        <p class="mb-0 mt-4 productname">${cart.getProduct().getProductName()}</p>
                                    </td>
                                    <td>
                                        <c:set var="price" value="${cart.getProduct().getPrice()}" />
                                        <c:set var="sale" value="${cart.getProduct().getSale()}" />
                                        <c:set var="discountAmount" value="${price * (sale / 100)}" />
                                        <c:set var="finalPrice" value="${price - discountAmount}" />
                                        <c:if test="${sale > 0}">
                                            <p class="mb-0 mt-4 "><fmt:formatNumber value="${finalPrice}" type="currency" currencySymbol="$" pattern="###0.00" />$</p>
                                        </c:if>
                                        <c:if test="${sale <= 0}">
                                            <p class="mb-0 mt-4">${cart.getProduct().getPrice()} $</p>
                                        </c:if>

                                    </td>
                                    <td> 
                                        <input type="hidden" class="product-stock" value="${cart.getProduct().getQuantity()-cart.getProduct().getHold()}" />
                                        <form action="cart" method="get" >

                                            <input type="hidden" name="cartquantityId" value="${cart.cartId}" />
                                            <div class="input-group quantity mt-4 bg-light border" style="width: 120px;">
                                                <div class="input-group-btn">
                                                    <button  class="btn btn-sm btn-minus" >
                                                        <i class="fa fa-minus"></i>
                                                    </button>
                                                </div>

                                                <input type="text" class="form-control form-control-sm text-center border-0" value="${cart.getQuantity()}" name="quantitypro" readonly="">
                                                <div class="input-group-btn">
                                                    <button  class="btn btn-sm btn-plus">
                                                        <i class="fa fa-plus"></i>
                                                    </button>
                                                </div>
                                            </div>
                                        </form>
                                    </td>
                                    <td>
                                        <p class="mb-0 mt-4"><fmt:formatNumber value="${cart.getQuantity()*finalPrice}" type="currency" currencySymbol="$" pattern="###0.00" /> $</p>
                                    </td>
                                    <td>
                                        <form action="cart" method="get" onsubmit="return confirmDelete();">
                                            <input type="hidden" name="cartId" value="${cart.cartId}" />
                                            <button type="submit" class="btn btn-md mt-4">
                                                <i class="fa fa-trash text-danger"></i>
                                            </button>
                                        </form>
                                    </td>

                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="d-flex align-items-center justify-content-between">
                    <div class="mt-5">  
                        <form action="cart" method="get" onsubmit="return confirmDeleteAll();">
                            <input type="hidden" name="cartdeleteall" value="deleteall" />
                            <button type="submit" class="btn btn-dark btn-block" type="button">CLEAR ALL ITEM</button>
                        </form>
                    </div>
                    <div class="mt-5">          
                        <a class="btn btn-dark btn-block" href="product" type="button">CHOOSE MORE PRODUCT</a>
                    </div>
                    <div class="mt-5">          
                        <a class="btn btn-dark btn-block" href="javascript:void(0)" onclick="checkStock()" type="button">ORDER INFORMATION</a>
                    </div>
                </div>
            </div>
        </div>  
        <jsp:include page="layout/footer.jsp"/> 
        <script>
            function confirmDelete() {
                return confirm("Are you sure you want to delete this item from your cart?");
            }
            function confirmDeleteAll() {
                return confirm("Are you sure you want to delete all item from your cart?");
            }
            function checkStock() {
                let isValid = true;
                let errorMessage = '';

                $('.table tbody tr').each(function () {
                    let cartQuantity = parseFloat($(this).find('input[name="quantitypro"]').val());
                    let maxStock = parseFloat($(this).find('.product-stock').val());
                    let productName = $(this).find('td p.mb-0.mt-4.productname').text(); // Get the product name correctly

                    // If the quantity in the cart exceeds the available stock
                    if (cartQuantity > maxStock) {
                        isValid = false;
                        errorMessage += `"The product "` + productName + `" exceeds the available stock. Maximum available: ` + maxStock + `.\n`;
                    }
                });

                // If there are errors, show the alert with details
                if (!isValid) {
                    alert(errorMessage);
                } else {
                    // If all quantities are valid, redirect to the order information page
                    window.location.href = 'orderinfor';
                }
            }


        </script>
        <script>
            (function ($) {
                "use strict";

                // Product Quantity
                $('.quantity button').on('click', function () {
                    var button = $(this);
                    var oldValue = button.parent().parent().find('input').val();
                    var maxStock = button.closest('tr').find('.product-stock').val(); // Lấy số lượng tồn kho từ thẻ ẩn

                    if (button.hasClass('btn-plus')) {
                        // Chỉ tăng nếu số lượng hiện tại nhỏ hơn số lượng tồn kho
                        if (parseFloat(oldValue) < parseFloat(maxStock)) {
                            var newVal = parseFloat(oldValue) + 1;
                        } else {
                            newVal = oldValue; // Không thay đổi nếu đạt giới hạn tồn kho
                            alert('Số lượng sản phẩm không thể vượt quá tồn kho');
                        }
                    } else {
                        // Không giảm dưới 1
                        if (oldValue > 1) {
                            var newVal = parseFloat(oldValue) - 1;
                        } else {
                            newVal = 1;
                        }
                    }
                    button.parent().parent().find('input').val(newVal);
                });

            })(jQuery);
        </script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/lib/easing/easing.min.js"></script>
        <script src="assets/js/lib/waypoints/waypoints.min.js"></script>
        <script src="assets/js/lib/lightbox/js/lightbox.min.js"></script>
        <script src="assets/js/lib/owlcarousel/owl.carousel.min.js"></script>

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet"> 
        <!-- Icon Font Stylesheet -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
    </body>
</html>
