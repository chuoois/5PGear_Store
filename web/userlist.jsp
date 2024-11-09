<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>User List - Admin</title>
        <!-- Bootstrap CSS -->
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
                            <span>Manager</span>
                        </h6>
                        <ul class="nav flex-column">
                            <li class="nav-item">
                                <a class="nav-link" href="userlist">
                                    <i class="fas fa-users"></i> Users List
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
                            <span>Welcome, ${account_a.getAdminName()}</span>
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
                        <h1 class="h2">User List</h1>
                        <a href="#" class="btn btn-primary" data-toggle="modal" data-target="#addUserModal"><i class="fas fa-plus"></i> Add New User</a>
                    </div>

                    <!-- Filter and Search Form -->
                    <form action="userlist" method="get" class="mb-4">
                        <div class="form-row align-items-center">
                            <div class="col-md-3 mb-2">
                                <select name="gender" class="custom-select">
                                    <option value="">All Genders</option>
                                    <option value="male" ${param.gender == 'male' ? 'selected' : ''}>Male</option>
                                    <option value="female" ${param.gender == 'female' ? 'selected' : ''}>Female</option>
                                    <option value="other" ${param.gender == 'other' ? 'selected' : ''}>Other</option>
                                </select>
                            </div>
                            <div class="col-md-3 mb-2">
                                <select name="status" class="custom-select">
                                    <option value="">All Statuses</option>
                                    <option value="active" ${param.status == 'active' ? 'selected' : ''}>Active</option>
                                    <option value="inactive" ${param.status == 'inactive' ? 'selected' : ''}>Inactive</option>
                                </select>
                            </div>
                            <div class="col-md-3 mb-2">
                                <input type="text" name="search" class="form-control" placeholder="Search name, email, mobile" value="${param.search}">
                            </div>
                            <div class="col-md-3 mb-2">
                                <button type="submit" class="btn btn-primary">Apply Filters</button>
                            </div>
                        </div>
                        <!-- Add hidden input for type -->
                        <input type="hidden" name="type" value="${param.type}">
                    </form>

                    <div class="d-flex justify-content-between mb-3 ml-5 mr-5">
                        <a href="userlist" class="status-link ${empty param.type ? 'active' : ''}">
                            All (${totalUsers})
                        </a>    
                        <a href="userlist?type=2${not empty param.gender ? '&gender='.concat(param.gender) : ''}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.search ? '&search='.concat(param.search) : ''}" 
                           class="status-link ${param.type == '2' ? 'active' : ''}">
                            Customer (${userCount2})
                        </a>
                        <a href="userlist?type=3${not empty param.gender ? '&gender='.concat(param.gender) : ''}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.search ? '&search='.concat(param.search) : ''}" 
                           class="status-link ${param.type == '3' ? 'active' : ''}">
                            Sale (${userCount3})
                        </a>
                        <a href="userlist?type=4${not empty param.gender ? '&gender='.concat(param.gender) : ''}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.search ? '&search='.concat(param.search) : ''}" 
                           class="status-link ${param.type == '4' ? 'active' : ''}">
                            Marketing (${userCount5})
                        </a>
                        <a href="userlist?type=5${not empty param.gender ? '&gender='.concat(param.gender) : ''}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.search ? '&search='.concat(param.search) : ''}" 
                           class="status-link ${param.type == '5' ? 'active' : ''}">
                            Sale Manager (${userCount4})
                        </a>
                        <a href="userlist?type=6${not empty param.gender ? '&gender='.concat(param.gender) : ''}${not empty param.status ? '&status='.concat(param.status) : ''}${not empty param.search ? '&search='.concat(param.search) : ''}" 
                           class="status-link ${param.type == '6' ? 'active' : ''}">
                            Warehouse Staff (${userCount6})
                        </a>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped table-sm text-center">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Full Name</th>
                                    <th>Gender <a href="userlist?sort=gender&order=ASC${searchParams}">▲</a><a href="userlist?sort=gender&order=DESC${searchParams}">▼</a></th>
                                    <th>Email <a href="userlist?sort=email&order=ASC${searchParams}">▲</a><a href="userlist?sort=email&order=DESC${searchParams}">▼</a></th>
                                    <th>Mobile <a href="userlist?sort=phone&order=ASC${searchParams}">▲</a><a href="userlist?sort=phone&order=DESC${searchParams}">▼</a></th>
                                    <th>Status <a href="userlist?sort=status&order=ASC${searchParams}">▲</a><a href="userlist?sort=status&order=DESC${searchParams}">▼</a></th>
                                    <th>Role</th>
                                    <th>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                            <tbody>
                                <c:choose>
                                    <c:when test="${param.type == '2'}">
                                        <c:forEach items="${customerList}" var="customer">
                                            <tr>
                                                <td>${customer.customerId}</td>
                                                <td>${customer.customerName}</td>
                                                <td>${customer.gender}</td>
                                                <td>${customer.email}</td>
                                                <td>${customer.phone}</td>
                                                <td>${customer.status}</td>
                                                <td>${customer.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${customer.customerId}" 
                                                       data-name="${customer.customerName}" 
                                                       data-gender="${customer.gender}" 
                                                       data-email="${customer.email}" 
                                                       data-phone="${customer.phone}" 
                                                       data-role="${customer.role.roleName}" 
                                                       data-status="${customer.status}" 
                                                       data-dob="${customer.dob}"
                                                       data-avatar="${customer.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${customer.customerId}&type=Customer" 
                                                       class="btn btn-sm ${customer.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${customer.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </c:when>
                                    <c:when test="${param.type == '3'}">
                                        <c:forEach items="${saleList}" var="sale">
                                            <tr>
                                                <td>${sale.saleId}</td>
                                                <td>${sale.saleName}</td>
                                                <td>${sale.gender}</td>
                                                <td>${sale.email}</td>
                                                <td>${sale.phone}</td>
                                                <td>${sale.status}</td>
                                                <td>${sale.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${sale.saleId}" 
                                                       data-name="${sale.saleName}" 
                                                       data-gender="${sale.gender}" 
                                                       data-email="${sale.email}" 
                                                       data-phone="${sale.phone}" 
                                                       data-role="${sale.role.roleName}" 
                                                       data-status="${sale.status}" 
                                                       data-dob="${sale.dob}"
                                                       data-avatar="${sale.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${sale.saleId}&type=Sale" 
                                                       class="btn btn-sm ${sale.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${sale.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>

                                                </td>
                                            </tr>
                                        </c:forEach>    
                                    </c:when>
                                    <c:when test="${param.type == '4'}">
                                        <c:forEach items="${marketingList}" var="marketing">
                                            <tr>
                                                <td>${marketing.marketingId}</td>
                                                <td>${marketing.marketingName}</td>
                                                <td>${marketing.gender}</td>
                                                <td>${marketing.email}</td>
                                                <td>${marketing.phone}</td>
                                                <td>${marketing.status}</td>
                                                <td>${marketing.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${marketing.marketingId}" 
                                                       data-name="${marketing.marketingName}" 
                                                       data-gender="${marketing.gender}" 
                                                       data-email="${marketing.email}" 
                                                       data-phone="${marketing.phone}" 
                                                       data-role="${marketing.role.roleName}" 
                                                       data-status="${marketing.status}" 
                                                       data-dob="${marketing.dob}"
                                                       data-avatar="${marketing.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${marketing.marketingId}&type=Marketing" 
                                                       class="btn btn-sm ${marketing.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${marketing.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${param.type == '5'}">
                                        <c:forEach items="${saleManagerList}" var="saleManager">
                                            <tr>
                                                <td>${saleManager.salemanagerId}</td>
                                                <td>${saleManager.salemanagerName}</td>
                                                <td>${saleManager.gender}</td>
                                                <td>${saleManager.email}</td>
                                                <td>${saleManager.phone}</td>
                                                <td>${saleManager.status}</td>
                                                <td>${saleManager.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${saleManager.salemanagerId}" 
                                                       data-name="${saleManager.salemanagerName}" 
                                                       data-gender="${saleManager.gender}" 
                                                       data-email="${saleManager.email}" 
                                                       data-phone="${saleManager.phone}" 
                                                       data-role="${saleManager.role.roleName}" 
                                                       data-status="${saleManager.status}" 
                                                       data-dob="${saleManager.dob}"
                                                       data-avatar="${saleManager.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${saleManager.salemanagerId}&type=SaleManager" 
                                                       class="btn btn-sm ${saleManager.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${saleManager.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${param.type == '6'}">
                                        <c:forEach items="${warehousestaffList}" var="saleManager">
                                            <tr>
                                                <td>${saleManager.warehousestaffId}</td>
                                                <td>${saleManager.warehousestaffName}</td>
                                                <td>${saleManager.gender}</td>
                                                <td>${saleManager.email}</td>
                                                <td>${saleManager.phone}</td>
                                                <td>${saleManager.status}</td>
                                                <td>${saleManager.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${saleManager.warehousestaffId}" 
                                                       data-name="${saleManager.warehousestaffName}" 
                                                       data-gender="${saleManager.gender}" 
                                                       data-email="${saleManager.email}" 
                                                       data-phone="${saleManager.phone}" 
                                                       data-role="${saleManager.role.roleName}" 
                                                       data-status="${saleManager.status}" 
                                                       data-dob="${saleManager.dob}"
                                                       data-avatar="${saleManager.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${saleManager.warehousestaffId}&type=Warehouse" 
                                                       class="btn btn-sm ${saleManager.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${saleManager.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>    

                                    <c:when test="${empty param.type}">
                                        <c:forEach items="${customerList}" var="customer">
                                            <tr>
                                                <td>${customer.customerId}</td>
                                                <td>${customer.customerName}</td>
                                                <td>${customer.gender}</td>
                                                <td>${customer.email}</td>
                                                <td>${customer.phone}</td>
                                                <td>${customer.status}</td>
                                                <td>${customer.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${customer.customerId}" 
                                                       data-name="${customer.customerName}" 
                                                       data-gender="${customer.gender}" 
                                                       data-email="${customer.email}" 
                                                       data-phone="${customer.phone}" 
                                                       data-role="${customer.role.roleName}" 
                                                       data-status="${customer.status}" 
                                                       data-dob="${customer.dob}"
                                                       data-avatar="${customer.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${customer.customerId}&type=Customer" 
                                                       class="btn btn-sm ${customer.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${customer.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:forEach items="${saleList}" var="sale">
                                            <tr>
                                                <td>${sale.saleId}</td>
                                                <td>${sale.saleName}</td>
                                                <td>${sale.gender}</td>
                                                <td>${sale.email}</td>
                                                <td>${sale.phone}</td>
                                                <td>${sale.status}</td>
                                                <td>${sale.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${sale.saleId}" 
                                                       data-name="${sale.saleName}" 
                                                       data-gender="${sale.gender}" 
                                                       data-email="${sale.email}" 
                                                       data-phone="${sale.phone}" 
                                                       data-role="${sale.role.roleName}" 
                                                       data-status="${sale.status}" 
                                                       data-dob="${sale.dob}"
                                                       data-avatar="${sale.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${sale.saleId}&type=Sale" 
                                                       class="btn btn-sm ${sale.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${sale.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>

                                                </td>
                                            </tr>
                                        </c:forEach>    
                                        <c:forEach items="${saleManagerList}" var="saleManager">
                                            <tr>
                                                <td>${saleManager.salemanagerId}</td>
                                                <td>${saleManager.salemanagerName}</td>
                                                <td>${saleManager.gender}</td>
                                                <td>${saleManager.email}</td>
                                                <td>${saleManager.phone}</td>
                                                <td>${saleManager.status}</td>
                                                <td>${saleManager.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${saleManager.salemanagerId}" 
                                                       data-name="${saleManager.salemanagerName}" 
                                                       data-gender="${saleManager.gender}" 
                                                       data-email="${saleManager.email}" 
                                                       data-phone="${saleManager.phone}" 
                                                       data-role="${saleManager.role.roleName}" 
                                                       data-status="${saleManager.status}" 
                                                       data-dob="${saleManager.dob}"
                                                       data-avatar="${saleManager.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${saleManager.salemanagerId}&type=SaleManager" 
                                                       class="btn btn-sm ${saleManager.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${saleManager.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:forEach items="${marketingList}" var="marketing">
                                            <tr>
                                                <td>${marketing.marketingId}</td>
                                                <td>${marketing.marketingName}</td>
                                                <td>${marketing.gender}</td>
                                                <td>${marketing.email}</td>
                                                <td>${marketing.phone}</td>
                                                <td>${marketing.status}</td>
                                                <td>${marketing.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${marketing.marketingId}" 
                                                       data-name="${marketing.marketingName}" 
                                                       data-gender="${marketing.gender}" 
                                                       data-email="${marketing.email}" 
                                                       data-phone="${marketing.phone}" 
                                                       data-role="${marketing.role.roleName}" 
                                                       data-status="${marketing.status}" 
                                                       data-dob="${marketing.dob}"
                                                       data-avatar="${marketing.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${marketing.marketingId}&type=Marketing" 
                                                       class="btn btn-sm ${marketing.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${marketing.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        <c:forEach items="${warehousestaffList}" var="saleManager">
                                            <tr>
                                                <td>${saleManager.warehousestaffId}</td>
                                                <td>${saleManager.warehousestaffName}</td>
                                                <td>${saleManager.gender}</td>
                                                <td>${saleManager.email}</td>
                                                <td>${saleManager.phone}</td>
                                                <td>${saleManager.status}</td>
                                                <td>${saleManager.role.roleName}</td>
                                                <td>
                                                    <a href="#" class="btn btn-sm btn-info" data-toggle="modal" data-target="#userDetailModal" 
                                                       data-id="${saleManager.warehousestaffId}" 
                                                       data-name="${saleManager.warehousestaffName}" 
                                                       data-gender="${saleManager.gender}" 
                                                       data-email="${saleManager.email}" 
                                                       data-phone="${saleManager.phone}" 
                                                       data-role="${saleManager.role.roleName}" 
                                                       data-status="${saleManager.status}" 
                                                       data-dob="${saleManager.dob}"
                                                       data-avatar="${saleManager.avatar_url}"><i class="fas fa-eye"></i> View</a>

                                                    <a href="updateStatus?action=updateStatus&id=${saleManager.warehousestaffId}&type=Warehouse" 
                                                       class="btn btn-sm ${saleManager.status == 'Active' ? 'btn-success' : 'btn-danger'}"
                                                       onclick="return confirm('Do you want to change this status?');">
                                                        <i class="fas fa-toggle-on"></i> ${saleManager.status == 'Active' ? 'Active' : 'Inactive'}
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                </c:choose>
                            </tbody>
                        </table>
                    </div>

                    <!-- Pagination -->
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <c:if test="${currentPage != 1}">
                                <li class="page-item">
                                    <a class="page-link" href="userlist?page=${currentPage - 1}${searchParams}" tabindex="-1">Previous</a>
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
                                            <a class="page-link" href="userlist?page=${i}${searchParams}">${i}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            <c:if test="${currentPage lt totalPages}">
                                <li class="page-item">
                                    <a class="page-link" href="userlist?page=${currentPage + 1}${searchParams}">Next</a>
                                </li>
                            </c:if>
                        </ul>
                    </nav>
                </main>
            </div>
        </div>
        <!-- Add New User Modal -->
        <div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="addUserModalLabel">Add New User</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form action="adduser" method="post">
                            <div class="form-group">
                                <label for="fullName">Full Name</label>
                                <input type="text" class="form-control" id="fullName" name="fullName" required>
                            </div>
                            <div class="form-group">
                                <label for="gender">Gender</label>
                                <select class="form-control" id="gender" name="gender" required>
                                    <option value="Male">Male</option>
                                    <option value="Female">Female</option>
                                    <option value="Other">Other</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="email">Email</label>
                                <input type="email" class="form-control" id="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label for="phone">Mobile</label>
                                <input type="tel" class="form-control" id="phone" name="phone" required>
                            </div>
                            <div class="form-group">
                                <label for="role">Role</label>
                                <select class="form-control" id="role" name="role" required>
                                    <option value="Customer">Customer</option>
                                    <option value="Sale">Sale</option>
                                    <option value="SaleManager">Sale Manager</option>
                                    <option value="Marketing">Marketing</option>
                                    <option value="Admin">Admin</option>
                                    <option value="WarehouseStaff">Warehouse Staff</option>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="status">Status</label>
                                <select class="form-control" id="status" name="status" required>
                                    <option value="Active">Active</option>
                                    <option value="Inactive">Inactive</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary">Add User</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <!-- User Detail Modal -->
        <div class="modal fade" id="userDetailModal" tabindex="-1" aria-labelledby="userDetailModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="userDetailModalLabel">User Details</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <img id="userAvatar" src="assets/images/avatar.png" alt="User Avatar" class="img-fluid rounded-circle mb-3" style="width: 120px; height: 120px;" />
                        <h5 class="mb-3"><strong id="userName"></strong></h5>
                        <div class="list-group">
                            <p class="list-group-item"><strong>Gender:</strong> <span id="userGender"></span></p>
                            <p class="list-group-item"><strong>Email:</strong> <span id="userEmail"></span></p>
                            <p class="list-group-item"><strong>Mobile:</strong> <span id="userPhone"></span></p>
                            <p class="list-group-item"><strong>Role:</strong> <span id="userRole"></span></p>
                            <p class="list-group-item"><strong>Status:</strong> <span id="userStatus"></span></p>
                            <p class="list-group-item"><strong>Date Of Birth:</strong> <span id="userDob"></span></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
                                                           $('#userDetailModal').on('show.bs.modal', function (event) {
                                                               var button = $(event.relatedTarget); // Button that triggered the modal
                                                               var id = button.data('id');
                                                               var name = button.data('name');
                                                               var gender = button.data('gender');
                                                               var email = button.data('email');
                                                               var phone = button.data('phone');
                                                               var role = button.data('role');
                                                               var status = button.data('status');
                                                               var dob = button.data('dob');
                                                               var avatar = button.data('avatar');

                                                               // Kiá»m tra vÃ  gÃ¡n giÃ¡ trá» cho avatar
                                                               if (!avatar || avatar === '' || avatar === '0') {
                                                                   avatar = 'assets/images/avata.png';
                                                               }

                                                               // Cáº­p nháº­t ná»i dung modal
                                                               var modal = $(this);
                                                               modal.find('#userName').text(name);
                                                               modal.find('#userGender').text(gender);
                                                               modal.find('#userEmail').text(email);
                                                               modal.find('#userPhone').text(phone);
                                                               modal.find('#userRole').text(role);
                                                               modal.find('#userStatus').text(status);
                                                               modal.find('#userDob').text(dob);
                                                               modal.find('#userAvatar').attr('src', avatar);
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
    </body>
</html>