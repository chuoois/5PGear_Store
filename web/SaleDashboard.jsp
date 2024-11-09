<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>Dashboard</title>
        <!-- Bootstrap CSS -->
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
        <!-- Chart.js -->
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="assets/js/jsAdmindashboard.js" type="text/javascript"></script>
        <link href="assets/css/styleAdmindashboard.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <div class="container-fluid">
            <div class="row">
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
                                    <i class="fas fa-users"></i> Orders List
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

                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4"> 
                    <%
                    String startDateValue = request.getParameter("start-date");
                    String endDateValue = request.getParameter("end-date");
                    %>
                    <h2 style="margin-top: 20px; margin-bottom: 20px">Sale Manager Dashboard</h2>

                    <form action="saledashboard" method="GET">
                        <div class="input-time">
                            From <input type="date" id="start-date" name="start-date" value="${startDate}">
                            To <input type="date" id="end-date" name="end-date" value="${endDate}">
                            <c:if test="${not empty startDateError}">
                                <span class="error">${startDateError}</span>
                            </c:if>
                            <c:if test="${not empty endDateError}">
                                <span class="error">${endDateError}</span>
                            </c:if>
                            <c:if test="${not empty dateRangeError}">
                                <span class="error">${dateRangeError}</span>
                            </c:if>
                            <button type="submit">Filter</button>
                        </div>
                    </form>

                    <div class="row">
                        <div class="col-md-4">
                            <div class="card mb-4">
                                <div class="card-header">
                                    New Orders
                                </div>
                                <div class="card-body">
                                    <p>Approved: <span id="order-success">${successCount}</span></p>
                                    <p>Cancel: <span id="order-cancelled">${cancelledCount}</span></p>
                                    <p>Pending: <span id="order-submitted">${submittedCount}</span></p>
                                    <canvas id="orderChart"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card mb-4">
                                <div class="card-header">
                                    Revenues
                                </div>
                                <div class="card-body">
                                    <p>Total: $<span id="revenue-total">${totalRevenue}</span></p>
                                    <p>By Category:</p>
                                    <ul class="list-unstyled">
                                        <c:forEach var="entry" items="${revenueByCategory}">
                                            <li class="revenue-category" data-category="${entry.key.categoryName}" data-revenue="${entry.value}">
                                                ${entry.key.categoryName}: $<span>${entry.value}</span>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <canvas id="revenueChart"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="card mb-4">
                                <div class="card-header">
                                    Customers
                                </div>
                                <div class="card-body">
                                    <p>Newly Registered: <span id="customers-new">${newlyRegisteredCustomers}</span></p>
                                    <p>Newly Bought: <span id="customers-bought">${newlyPurchasedCustomers}</span></p>
                                    <canvas id="customerPieChart"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card mb-4">
                                <div class="card-header">
                                    Feedbacks
                                </div>
                                <div class="card-body">
                                    <p>Average Star: <span id="feedback-average">${averageStarRating}</span></p>
                                    <p>By Category:</p>
                                    <ul class="list-unstyled">
                                        <c:forEach var="entry" items="${averageStarRatingByCategory}">
                                            <li class="feedback-category" data-category="${entry.key.categoryName}" data-rating="${entry.value}">
                                                ${entry.key.categoryName}: <span>${entry.value}</span>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                    <canvas id="feedbackChart"></canvas>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-6">
                            <div class="card mb-4">
                                <div class="card-header">
                                    Order Trends
                                </div>
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th>Date</th>
                                                <th>Total Orders</th>
                                                <th>Successful Orders</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="entry" items="${totalOrderCountByDay}">
                                                <tr>
                                                    <td>${entry.key}</td>
                                                    <td>${entry.value}</td>
                                                    <c:choose>
                                                        <c:when test="${orderTrendData[entry.key] != null}">
                                                            <td>${orderTrendData[entry.key]}</td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td>0</td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>

        <!-- Bootstrap JS -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </body>

</html>
