<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Feedbacks List</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.0.3/css/font-awesome.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>

        <script src="https://kit.fontawesome.com/a076d05399.js"></script> <!-- For star icons -->
        <link href="assets/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
        <jsp:include page="layout/head.jsp"/>
    </head>

    <body>
        <jsp:include page="layout/menu.jsp"/>
        <div style="padding-top:50px  " class="container mt-5">
            <div class="row">
                <!-- customer reviews -->
                <div class="col-lg-4 col-md-5 col-12 mb-4 mb-lg-0 pr-lg-6">
                    <div class="mb-6">
                        <h4 class="mb-3">Customer reviews</h4>
                        <span class="font-14">
                            <i class="fa fa-star <c:if test="${averageRating >= 1}">star-active</c:if><c:if test="${averageRating < 1}">star-inactive</c:if>"></i>
                            <i class="fa fa-star <c:if test="${averageRating >= 2}">star-active</c:if><c:if test="${averageRating < 2}">star-inactive</c:if>"></i>
                            <i class="fa fa-star <c:if test="${averageRating >= 3}">star-active</c:if><c:if test="${averageRating < 3}">star-inactive</c:if>"></i>
                            <i class="fa fa-star <c:if test="${averageRating >= 4}">star-active</c:if><c:if test="${averageRating < 4}">star-inactive</c:if>"></i>
                            <i class="fa fa-star <c:if test="${averageRating >= 5}">star-active</c:if><c:if test="${averageRating < 5}">star-inactive</c:if>"></i>
                            </span>
                                <span class="h5">${averageRating} </span>
                        <p class="font-14">${ratingCount}  comments</p>

                        <!-- Rating Progress Bars -->
                        <div class="row align-items-center mb-1">
                            <div class="col-2 pr-0">
                                <div class="font-12 text-dark">5 Star</div>
                            </div>
                            <div class="col-8 pr-2">
                                <div class="progress" style="height: 8px;">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: ${fiveStarPercentage}%" aria-valuenow="${fiveStarPercentage}%" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>

                            <div class="col-2">
                                <div class="font-12 text-primary">${fiveStarPercentage}% </div>
                            </div>
                        </div>

                        <div class="row align-items-center mb-1">
                            <div class="col-2 pr-0">
                                <div class="font-12 text-dark">4 Star</div>
                            </div>
                            <div class="col-8 pr-2">
                                <div class="progress" style="height: 8px;">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: ${fourStarPercentage}%" aria-valuenow="fourStarComments" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="font-12 text-primary">${fourStarPercentage}%</div>
                            </div>
                        </div>

                        <div class="row align-items-center mb-1">
                            <div class="col-2 pr-0">
                                <div class="font-12 text-dark">3 Star</div>
                            </div>
                            <div class="col-8 pr-2">
                                <div class="progress" style="height: 8px;">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: ${threeStarPercentage}%" aria-valuenow="${threeStarPercentage}%" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="font-12 text-primary">${threeStarPercentage}%</div>
                            </div>
                        </div>

                        <div class="row align-items-center mb-1">
                            <div class="col-2 pr-0">
                                <div class="font-12 text-dark">2 Star</div>
                            </div>
                            <div class="col-8 pr-2">
                                <div class="progress" style="height: 8px;">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: ${twoStarPercentage}%" aria-valuenow="${twoStarPercentage}%" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="font-12 text-primary">${twoStarPercentage}%</div>
                            </div>
                        </div>

                        <div class="row align-items-center mb-2">
                            <div class="col-2 pr-0">
                                <div class="font-12 text-dark">1 Star</div>
                            </div>
                            <div class="col-8 pr-2">
                                <div class="progress" style="height: 8px;">
                                    <div class="progress-bar bg-warning" role="progressbar" style="width: ${oneStarPercentage}%" aria-valuenow="${oneStarPercentage}%" aria-valuemin="0" aria-valuemax="100"></div>
                                </div>
                            </div>
                            <div class="col-2">
                                <div class="font-12 text-primary">${oneStarPercentage}%</div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <h4 class="mb-1">${product. getProductName()}</h4>
                        <img  style=" width: 350px; border: 1px solid gainsboro ; border-radius:12px " src="${product.getImgPath()}" alt="Image">


                    </div>
                </div>
                <!-- customer reviews list -->
                <div class="col-lg-8 col-md-7 col-12">
                    <div class="d-flex align-items-center justify-content-between mb-4">
                        <div>
                            <h4 class="mb-0">Ratings & Reviews</h4>
                        </div>
                        <form method="GET" action="feedback">
                            <input type="hidden" value="${product.getProductId()}" name="productid">
                            <label for="sortOptions">Sort by:</label>
                            <select id="sortOptions" name="sortRating" class="custom-select" onchange="this.form.submit()">

                                <option value="DESC" ${param.sortRating == 'DESC' ? 'selected' : ''}>Rating: High to Low</option>
                                <option value="low" ${param.sortRating == 'low' ? 'selected' : ''}>Rating: Low to High</option>
                            </select>
                        </form>


                    </div>

                    <!-- Reviews -->
                    <c:forEach var="feedback" items="${feedbackList}">
                        <div class="mb-4 pb-4 border-bottom">
                            <div class="d-flex mb-3 align-items-center">
                                <img src="path_to_images/avatar-1.png" alt="" class="rounded-circle avatar-lg">
                                <div class="ml-2">
                                    <h5 class="mb-1">
                                        ${feedback.getCustomerId().getCustomerName()}
                                        <img src="path_to_images/verified.svg" alt="">
                                    </h5>
                                    <p class="font-12 mb-0">
                                        <span>${feedback.getDobString()}</span>
                                    </p>
                                </div>
                            </div>
                            <div class="mb-2 star-rating">
                                <div>
                                    <span class="font-14 mr-2">
                                        <c:forEach begin="1" end="${feedback.rating}" var="i">
                                            <i class="fa fa-star star-active mx-1" title="Rated ${i}"></i>
                                        </c:forEach>
                                        <c:forEach begin="${feedback.rating + 1}" end="5" var="j">
                                            <i class="fa fa-star star-inactive mx-1" title="Rated ${j}"></i>
                                        </c:forEach>
                                    </span>
                                </div>
                            </div>
                            <p>${feedback.getComment()}</p>
                            <c:if test="${hasPurchased}">
                                <button style="margin-top: 20px" class="btn btn-primary btn-block" 
                                        data-toggle="modal" 
                                        data-target="#reviewModal" 
                                        data-feedback-id="${feedback.getFeedbackId()}" 
                                        data-feedback-comment="${feedback.getComment()}" 
                                        data-feedback-rating="${feedback.rating}">
                                    Edit
                                </button>
                            </c:if>
                        </div>
                    </c:forEach>

                    <!-- Review Modal -->
                    <div class="modal fade" id="reviewModal" tabindex="-1" role="dialog" aria-labelledby="reviewModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="reviewModalLabel">Edit Your Review</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body">
                                    <form id="feedbackForm" action="feedback" method="POST">
                                        <input type="hidden" id="feedbackId" name="feedbackId">
                                        <input type="hidden" value="${product.getProductId()}" name="productid">
                                        <div class="form-group">
                                            <label for="customerName">Your Name</label>
                                            <input value="${account.getCustomerName()}" type="text" class="form-control" id="customerName" required readonly>
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
                                            <button type="submit" class="btn btn-primary">Submit</button>
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>







                    <!-- Another Review -->


                    <!-- Read More Reviews -->

                </div>
            </div>
            <!-- Read More Reviews -->
            <!--      
                    </div>
            
            -->        <style>

                .star-active {
                    color: #FBC02D; /* Color for active stars */
                    margin-top: 10px;
                    margin-bottom: 10px;
                }

                .star-active:hover {
                    color: #F9A825; /* Color on hover for active stars */
                    cursor: pointer;
                }

                .star-inactive {
                    color: #CFD8DC; /* Color for inactive stars */
                    margin-top: 10px;
                    margin-bottom: 10px;
                }
            </style><!--
            <!-- Bootstrap JS -->

            <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
            <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
            <!-- Modal for editing feedback -->
            <script>
                                $('#reviewModal').on('show.bs.modal', function (event) {
                                    var button = $(event.relatedTarget); // Button that triggered the modal
                                    var feedbackId = button.data('feedback-id'); // Extract info from data-* attributes
                                    var feedbackComment = button.data('feedback-comment');
                                    var feedbackRating = button.data('feedback-rating');

                                    // Update the modal's content
                                    var modal = $(this);
                                    modal.find('#feedbackId').val(feedbackId);
                                    modal.find('#feedbackComment').val(feedbackComment);
                                    modal.find('#rating').val(feedbackRating);
                                });
            </script>
            <!--        <script>
                                            // JavaScript for submitting feedback
                                            document.getElementById('submitFeedbackBtn').addEventListener('click', function () {
                                                const name = document.getElementById('customerName').value;
                                                const rating = document.getElementById('rating').value;
                                                const comment = document.getElementById('feedbackComment').value;
            
                                                // TODO: Add logic to submit feedback to the server (e.g., via AJAX)
                                                console.log('Feedback submitted:', {name, rating, comment});
            
                                                // Close the modal
                                                $('#reviewModal').modal('hide');
            
                                                // Optionally, reset the form
                                                document.getElementById('feedbackForm').reset();
                                            });
                    </script>-->
            <!-- Modal for editing feedback -->
            <div class="modal fade" id="editFeedbackModal" tabindex="-1" role="dialog" aria-labelledby="editFeedbackModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editFeedbackModalLabel">Edit Feedback</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <textarea class="form-control" id="feedbackComment" rows="4"></textarea>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                            <button type="button" class="btn btn-primary" id="saveFeedbackBtn">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>
        <!-- Script files -->
        <script src="assets/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/feather.min.js"></script>
        <script src="assets/js/app.js"></script>
        <jsp:include page="layout/footer.jsp"/>
    </body>
</html>
