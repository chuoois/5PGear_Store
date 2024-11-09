package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Role;

public class StatisticsDAO extends DBContext {

    private final Connection connection;
    public StatisticsDAO() {
        this.connection = DBContext.getConnection();
    }

    public int getTotalProducts() throws SQLException {
        String query = "SELECT COUNT(*) AS total_products FROM Product";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt("total_products");
        }
    }

    public int getTotalProductsByDate(Date startDate, Date endDate) throws SQLException {
        String query = "SELECT COUNT(*) AS total_products FROM Product WHERE date_create >= ? AND date_create <= ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("total_products");
        }
    }

    public int getTotalFeedbacksByDate(Date startDate, Date endDate) throws SQLException {
        String query = "SELECT COUNT(*) AS total_feedbacks FROM Feedback WHERE feedback_date >= ? AND feedback_date <= ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("total_feedbacks");
        }
    }

    public int getTotalBlogsByDate(Date startDate, Date endDate) throws SQLException {
        String query = "SELECT COUNT(*) AS total_blogs FROM Blog WHERE [date_created] >= ? AND date_created <= ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("total_blogs");
        }
    }

    public int getTotalCustomersByDate(Date startDate, Date endDate) throws SQLException {
        String query = "SELECT COUNT(*) AS total_customers FROM Customer WHERE date_create >= ? AND date_create <= ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(startDate.getTime()));
            statement.setDate(2, new java.sql.Date(endDate.getTime()));
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getInt("total_customers");
        }
    }

    public static void main(String[] args) {
        try {
            StatisticsDAO s = new StatisticsDAO();
             

            // Tạo ngày bắt đầu và kết thúc
            java.sql.Date startDate = java.sql.Date.valueOf("2000-09-10");
            java.sql.Date endDate = java.sql.Date.valueOf("2024-09-29");
            int a = s.getTotalFeedbacksByDate(startDate, endDate);
            int b = s.getTotalBlogsByDate(startDate, endDate);
            int c = s.getTotalProductsByDate(startDate, endDate);
            int d = s.getTotalCustomersByDate(startDate, endDate);
            System.out.println(a+" "+b+" "+c+" "+d);

            // Lấy danh sách khách hàng mới
//            List<Customer> newCustomers = s.getNewCustomersByDay(startDate, endDate);
            // In ra thông tin khách hàng mới
//            for (Customer customer : newCustomers) {
//                System.out.println(customer);
//            }
        } catch (Exception e) {
            e.printStackTrace(); // Xử lý ngoại lệ nếu cần
        }
       
        

    }

}
