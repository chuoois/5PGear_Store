<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Marketting Dashboard</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="styles.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
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
                

                   <jsp:include page="sidebar.jsp" />
                <main class="col-md-9 col-lg-10 main-content">
                    <header class="main-header d-flex justify-content-between align-items-center py-3">
                        <button id="sidebar-toggle" class="btn btn-outline-secondary">
                            <i class="fas fa-bars"></i>
                        </button>
                        <div class="user-info">
                            <span>Welcome, Marketting</span>
                            <a href="${pageContext.request.contextPath}/logoutstaff" class="btn btn-outline-danger btn-sm ml-2">
                                <i class="fas fa-sign-out-alt"></i> Logout
                            </a>
                        </div>

                    </header>
                    <div class="dashboard">
                        <h1 class="my-4">Dashboard Marketting</h1>
                        <div class="chart-container my-4">
                            <form method="get" action="/SWP391Gr5/marketting/home">
                                <div class="form-inline my-2">
                                    <label for="start-date" class="mr-2">Start Date:</label>
                                    <input type="date" id="start-date" name="startDate" class="form-control mr-3" value="${sqlStartDate}" required>
                                    <label for="end-date" class="mr-2">End Date:</label>
                                    <input type="date" id="end-date" name="endDate" class="form-control mr-3" value="${sqlEndDate}" required>
                                    <button type="submit" class="btn btn-primary filter-btn">Filter</button>
                                </div>
                            </form>

                        </div>
                        <div class="row stats-container">
                            <div class="col-sm-6 col-md-6">
                                <div class="card text-center stat-card">
                                    <div class="card-body">
                                        <i class="fas fa-users fa-3x"></i>
                                        <h3 class="card-title">Customers </h3>
                                        <p class="card-text">${totalCustomers}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-md-6">
                                <div class="card text-center stat-card">
                                    <div class="card-body">
                                        <i class="fas fa-box fa-3x"></i>
                                        <h3 class="card-title">Products</h3>
                                        <p class="card-text">${totalProducts}</p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row stats-container mt-4">
                            <div class="col-sm-6 col-md-6">
                                <div class="card text-center stat-card">
                                    <div class="card-body">
                                        <i class="fas fa-comments fa-3x"></i>
                                        <h3 class="card-title">Feedbacks:</h3>
                                        <p class="card-text">${totalFeedbacks}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-6 col-md-6">
                                <div class="card text-center stat-card">
                                    <div class="card-body">
                                        <i class="fas fa-blog fa-3x"></i>
                                        <h3 class="card-title">Blogs:</h3>
                                        <p class="card-text">${totalBlogs}</p>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </main>
            </div>
        </div>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="script.js"></script>


    </body>
</html>
