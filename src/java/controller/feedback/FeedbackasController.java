/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.feedback;

import dal.CustomerDAO;
import dal.FeedbackDAO;

import dal.OrderDetailsDAO;
import dal.ProductDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;

import java.util.List;
import model.Customer;
import model.Feedback;
import model.OrderDetails;

/**
 *
 * @author DNC
 */
public class FeedbackasController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet FeedbackasController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FeedbackasController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("order_id"));

        OrderDetailsDAO orderdetailsDAO = new OrderDetailsDAO();
        List<OrderDetails> orderDetailsList = orderdetailsDAO.getOrderDetailsByOrderId(orderId);

        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        // Tạo HashMap để lưu trữ hasFeedback với key là ProductID
        HashMap<Integer, Boolean> feedbackMap = new HashMap<>();

        // Kiểm tra xem feedback có tồn tại cho từng chi tiết đơn hàng
        for (OrderDetails orderDetails : orderDetailsList) {
            boolean hasFeedback = feedbackDAO.hasFeedback(account.getCustomerId(), orderDetails.getProductId().getProductId());
            // Lưu vào HashMap
            feedbackMap.put(orderDetails.getProductId().getProductId(), hasFeedback);
        }

        // Đưa HashMap vào request để sử dụng trong JSP
        request.setAttribute("feedbackMap", feedbackMap);

        request.setAttribute("orderDetailsList", orderDetailsList);
        request.getRequestDispatcher("feedbackas.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");
        String orderIdParam = request.getParameter("order_id");
        String ratingParam = request.getParameter("rating");
        String productIdParam = request.getParameter("productId");

        if (ratingParam == null || productIdParam == null) {
            // Handle missing parameters

            return;
        }

        int rating = Integer.parseInt(ratingParam);
        String comment = request.getParameter("feedbackComment");

        int productId = Integer.parseInt(productIdParam);

        FeedbackDAO feedbackDAO = new FeedbackDAO();
        Feedback feedback = new Feedback();
        CustomerDAO customerDao = new CustomerDAO();
        ProductDAO productDao = new ProductDAO();

        feedback.setCustomerId(account);
        feedback.setProductId(productDao.getProductById(productId));
        feedback.setRating(rating);
        feedback.setComment(comment);

        feedbackDAO.addFeedback(feedback);

        // Optional: set success message
        response.sendRedirect("feedbackas?order_id=" + orderIdParam);

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
