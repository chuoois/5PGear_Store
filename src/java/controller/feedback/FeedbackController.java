package controller.feedback;

import dal.CustomerDAO;
import dal.FeedbackDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Feedback;
import model.Product;
import dal.ProductDAO;
import jakarta.servlet.http.HttpSession;

import java.util.Map;
import model.Customer;

public class FeedbackController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");

        // Kiểm tra xem người dùng đã đăng nhập chưa
        if (account == null) {
            response.sendRedirect("login");  // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
            return;
        }
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        ProductDAO productDAO = new ProductDAO();

        // Lấy productId từ request
        String productIdParam = request.getParameter("productid");
        int productId = Integer.parseInt(productIdParam);

        // Kiểm tra xem người dùng đã mua sản phẩm chưa
        boolean hasPurchased = feedbackDAO.hasPurchasedProduct(account.getCustomerId(), productId);

        // Lấy dữ liệu về sản phẩm và feedbacks
        Product product = productDAO.getProductById(productId);
        String sortRating = request.getParameter("sortRating");

        List<Feedback> feedbacks = feedbackDAO.getFeedbackByProductId(productId, sortRating);

        // Xử lý sắp xếp
        // Lấy tổng số bình luận và đánh giá trung bình
        int ratingCount = feedbackDAO.getRatingCount(productId);
        float averageRating = feedbackDAO.getAverageRating(productId);
        feedbackDAO.updateProductRate(productId, averageRating);
        // Lấy số lượng bình luận cho từng mức sao
        Map<Integer, Integer> ratingCounts = feedbackDAO.getRatingCounts(productId);

        // Tính toán tỷ lệ phần trăm cho từng mức sao (5 sao đến 1 sao)
        int fiveStarRaw = (int) Math.round(ratingCounts.getOrDefault(5, 0) * 100.0 / ratingCount);
        int fourStarRaw = (int) Math.round(ratingCounts.getOrDefault(4, 0) * 100.0 / ratingCount);
        int threeStarRaw = (int) Math.round(ratingCounts.getOrDefault(3, 0) * 100.0 / ratingCount);
        int twoStarRaw = (int) Math.round(ratingCounts.getOrDefault(2, 0) * 100.0 / ratingCount);
        int oneStarRaw = (int) Math.round(ratingCounts.getOrDefault(1, 0) * 100.0 / ratingCount);

// Tính tổng sau khi làm tròn
        int totalPercentage = fiveStarRaw + fourStarRaw + threeStarRaw + twoStarRaw + oneStarRaw;

// Nếu tổng khác 100, điều chỉnh phần trăm
        int difference = 100 - totalPercentage;

        if (difference != 0) {
            // Điều chỉnh dựa trên giá trị lớn nhất (hoặc giá trị nào bạn muốn tăng/giảm)
            if (difference > 0) {
                fiveStarRaw += difference; // Thêm vào một trong các mức đánh giá, chẳng hạn 5 sao
            } else {
                fiveStarRaw += difference; // Giảm ở mức đánh giá tương ứng nếu cần
            }
        }

// Sau khi điều chỉnh, kết quả sẽ có tổng bằng 100
        int fiveStarPercentage = fiveStarRaw;
        int fourStarPercentage = fourStarRaw;
        int threeStarPercentage = threeStarRaw;
        int twoStarPercentage = twoStarRaw;
        int oneStarPercentage = oneStarRaw;

        // Đặt các thuộc tính cho JSP
        request.setAttribute("sortRating", sortRating);
        request.setAttribute("product", product);
        request.setAttribute("sortRating", sortRating);
        request.setAttribute("feedbackList", feedbacks);
        request.setAttribute("account", account);
        request.setAttribute("ratingCount", ratingCount);
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("ratingCounts", ratingCounts);
        request.setAttribute("fiveStarPercentage", fiveStarPercentage);
        request.setAttribute("fourStarPercentage", fourStarPercentage);
        request.setAttribute("threeStarPercentage", threeStarPercentage);
        request.setAttribute("twoStarPercentage", twoStarPercentage);
        request.setAttribute("oneStarPercentage", oneStarPercentage);
        request.setAttribute("hasPurchased", hasPurchased);
        // Chuyển tiếp tới JSP để hiển thị feedbacks
        request.getRequestDispatcher("feedback.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        FeedbackDAO feedbackDAO = new FeedbackDAO();

        // Retrieve feedback details from the request
        int feedbackId = Integer.parseInt(request.getParameter("feedbackId")); // Ensure feedbackId is sent from the modal
        String updatedComment = request.getParameter("feedbackComment"); // Updated comment
        int updatedRating = Integer.parseInt(request.getParameter("rating")); // Updated rating
        int productId = Integer.parseInt(request.getParameter("productid"));
        try {
            // Update feedback in the database
            feedbackDAO.updateFeedback(feedbackId, updatedComment, updatedRating);
            response.sendRedirect("feedback?productid=" + productId); // Redirect to the feedback list page
        } catch (Exception e) {

            request.setAttribute("errorMessage", "Cập nhật phản hồi thất bại."); // Error message
            request.getRequestDispatcher("error.jsp").forward(request, response); // Redirect to the error page
        }
    }

    @Override
    public String getServletInfo() {
        return "FeedbackController handles displaying customer feedback.";
    }
}
