///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
// */
//
//package controller.feedback;
//
//import dal.FeedbackDAO;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//public class UpdateFeedbackServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String feedbackIdParam = request.getParameter("feedbackId");
//        String updatedComment = request.getParameter("updatedComment");
//
//        // Validate inputs
//        if (feedbackIdParam != null && updatedComment != null && !updatedComment.trim().isEmpty()) {
//            try {
//                // Convert ID to integer
//                int feedbackId = Integer.parseInt(feedbackIdParam);
//                FeedbackDAO feedbackDAO = new FeedbackDAO();
//
//                // Update the feedback in the database
//                feedbackDAO.updateFeedback(feedbackId, updatedComment);
//
//                // Redirect to feedback listing page after successful update
//                response.sendRedirect("feedback");
//            } catch (NumberFormatException e) {
//                request.setAttribute("errorMessage", "Invalid feedback ID.");
//                request.getRequestDispatcher("feedback.jsp").forward(request, response);
//            } catch (SQLException e) {
//                request.setAttribute("errorMessage", "Error updating feedback. Please try again.");
//                request.getRequestDispatcher("feedback.jsp").forward(request, response);
//            }
//        } else {
//            request.setAttribute("errorMessage", "Feedback ID or comment cannot be null or empty.");
//            request.getRequestDispatcher("feedback.jsp").forward(request, response);
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        doPost(request, response); // Handle GET as a POST
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "UpdateFeedbackServlet handles updating feedback comments.";
//    }
//}
