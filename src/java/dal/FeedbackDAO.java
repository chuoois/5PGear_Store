package dal;

import dal.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Category;
import model.Customer;
import model.Feedback;
import model.Product;

public class FeedbackDAO extends DBContext {

    private final Connection connection;
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;

    public FeedbackDAO() {

        this.connection = DBContext.getConnection();
        this.productDAO = new ProductDAO();
        this.customerDAO = new CustomerDAO();
    }

    public float getAverageStarRating(LocalDate startDate, LocalDate endDate) {
        float averageRating = 0;
        String sql = "SELECT AVG(rating) AS avgRating "
                + "FROM Feedback "
                + "WHERE feedback_date BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(startDate));
            ps.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                averageRating = rs.getFloat("avgRating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averageRating;
    }

    public Map<Category, Float> getAverageStarRatingByCategory(LocalDate startDate, LocalDate endDate) {
        Map<Category, Float> categoryRatings = new HashMap<>();
        String sql = "SELECT c.category_id, c.category_name, AVG(f.rating) AS avgRating "
                + "FROM Feedback f "
                + "JOIN Product p ON f.product_id = p.product_id "
                + "JOIN Category c ON p.category_id = c.category_id "
                + "WHERE f.feedback_date BETWEEN ? AND ? "
                + "GROUP BY c.category_id, c.category_name";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(startDate));
            ps.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt("category_id");
                String categoryName = rs.getString("category_name");
                float avgRating = rs.getFloat("avgRating");
                Category category = new Category(categoryId, categoryName, null);
                categoryRatings.put(category, avgRating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryRatings;
    }


 
    // Lấy phản hồi theo product_id với sắp xếp theo rating
    public List<Feedback> getFeedbackByProductId(int productId, String sort) {
        List<Feedback> feedbackList = new ArrayList<>();

        // Kiểm tra giá trị của biến sort, nếu là "low" thì sắp xếp tăng dần, ngược lại sắp xếp giảm dần
        String sortOrder = "DESC"; // mặc định sắp xếp giảm dần (từ cao đến thấp)
        if ("low".equalsIgnoreCase(sort)) {
            sortOrder = "ASC"; // sắp xếp tăng dần (từ thấp đến cao)
        }

        String query = "SELECT * FROM Feedback WHERE product_id = ? ORDER BY rating " + sortOrder;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product product = productDAO.getProductById(rs.getInt("product_id"));
                    Customer customer = customerDAO.getCustomerById(rs.getInt("customer_id"));
                    Feedback feedback = new Feedback(
                            rs.getInt("feedback_id"),
                            customer,
                            product,
                            rs.getString("comment"),
                            rs.getString("status"),
                            rs.getInt("rating"),
                            rs.getDate("feedback_date")
                    );
                    feedbackList.add(feedback);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbackList;
    }

    public void addFeedback(Feedback feedback) {
        String sql = "INSERT INTO Feedback (customer_id, product_id, rating, comment, status, feedback_date) "
                + "VALUES (?, ?, ?, ?, ?, GETDATE())";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, feedback.getCustomerId().getCustomerId());
            ps.setInt(2, feedback.getProductId().getProductId());
            ps.setInt(3, feedback.getRating());
            ps.setString(4, feedback.getComment());
            feedback.setStatus("Active");
            ps.setString(5, feedback.getStatus());

            // Execute the SQL insert statement
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();  // Handle SQL exception
        }
    }

    //update 
    public void updateFeedback(int feedbackId, String updatedComment, int updatedRating) throws SQLException {
        String sql = "UPDATE feedback SET comment = ?, rating = ? WHERE feedback_id = ?";
        try (Connection conn = this.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, updatedComment);
            pstmt.setInt(2, updatedRating);
            pstmt.setInt(3, feedbackId);
            pstmt.executeUpdate();
        }
    }

    public int getRatingCount(int productId) {
        int count = 0;
        String sql = "SELECT COUNT(*) AS totalCount FROM Feedback WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("totalCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public float getAverageRating(int productId) {
        float averageRating = 0;
        String sql = "SELECT AVG(rating) AS avgRating FROM Feedback WHERE product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                averageRating = rs.getFloat("avgRating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return averageRating;
    }

    public void updateProductRate(int productId, float averageRating) {
        String sql = "UPDATE Product SET rate = ? WHERE product_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setFloat(1, averageRating);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Integer> getRatingCounts(int productId) {
        Map<Integer, Integer> ratingCounts = new HashMap<>();
        String sql = "SELECT rating, COUNT(*) AS count "
                + "FROM Feedback "
                + "WHERE product_id = ? "
                + "GROUP BY rating";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, productId); // Thiết lập productId vào câu truy vấn
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int rating = rs.getInt("rating");
                int count = rs.getInt("count");
                ratingCounts.put(rating, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratingCounts;
    }

    public boolean hasPurchasedProduct(int customerId, int productId) {
        String query = "SELECT COUNT(*) FROM Orders WHERE customer_id = ? AND ostatus_id = 7 AND order_id IN ("
                + "SELECT order_id FROM OrderDetails WHERE product_id = ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Trả về true nếu có đơn hàng với ostatus_id = 7
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasFeedback(int customerId, int productId) {
        String query = "SELECT COUNT(*) FROM Feedback WHERE customer_id = ? AND product_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;  // Trả về true nếu có phản hồi
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ SQL
        }
        return false; // Trả về false nếu không có phản hồi
    }
    
    public List<Feedback> getFeedbackTop2(int productId) {
    List<Feedback> feedbackList = new ArrayList<>();

    
   
    // Query with LIMIT to fetch the top 2 newest comments
    String query = "SELECT top 2* FROM Feedback WHERE product_id = ?  ORDER BY feedback_date DESC";

    try (PreparedStatement ps = connection.prepareStatement(query)) {
        ps.setInt(1, productId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Product product = productDAO.getProductById(rs.getInt("product_id"));
                Customer customer = customerDAO.getCustomerById(rs.getInt("customer_id"));
                Feedback feedback = new Feedback(
                        rs.getInt("feedback_id"),
                        customer,
                        product,
                        rs.getString("comment"),
                        rs.getString("status"),
                        rs.getInt("rating"),
                        rs.getDate("feedback_date")
                );
                feedbackList.add(feedback);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return feedbackList;
}


    public static void main(String[] args) {
          FeedbackDAO feedbackDAO = new FeedbackDAO();

        // Example product ID and sort order
        int productId = 1;
        // Or "low" for ascending order

        // Call the method to test
        List<Feedback> feedbackList = feedbackDAO.getFeedbackTop2(productId);

        // Print out the feedback to check if the method is working correctly
        System.out.println("Top 2 Feedbacks for Product ID " + productId + ":");
        for (Feedback feedback : feedbackList) {
            System.out.println("Feedback ID: " + feedback.getFeedbackId());
            System.out.println("Customer Name: " + feedback.getCustomerId().getCustomerName());
      
            System.out.println("Comment: " + feedback.getComment());
            System.out.println("Rating: " + feedback.getRating());
            System.out.println("Feedback Date: " + feedback.getFeedbackDate());
            System.out.println("Status: " + feedback.getStatus());
            System.out.println("----------------------------------");
        
        }
    }
}
