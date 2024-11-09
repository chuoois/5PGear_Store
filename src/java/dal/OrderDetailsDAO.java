/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Order;
import model.Product;

/**
 *
 * @author DNC
 */
public class OrderDetailsDAO extends DBContext {

    private final Connection connection;
    private OrderDAO orderDao;
    private ProductDAO productDAO;

    public OrderDetailsDAO() {
        connection = DBContext.getConnection();
        this.orderDao = new OrderDAO();
        this.productDAO = new ProductDAO();
    }

    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails WHERE order_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, orderId); // Set the order_id parameter
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int product_id = rs.getInt("product_id");

                    Order order = orderDao.getOrderByID(orderId);
                    Product product = productDAO.getProductById(product_id); // Retrieve product details

                    OrderDetails orderDetails = new OrderDetails(
                            rs.getInt("order_details_id"),
                            order,
                            product,
                            rs.getInt("quantity"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getBigDecimal("unit_price"),
                            rs.getString("note")
                    );
                    orderDetailsList.add(orderDetails);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return orderDetailsList;
    }

    public List<OrderDetails> getOrderDetails() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetails";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {

                    int order_id = rs.getInt("order_id");
                    int product_id = rs.getInt("product_id");

                    Order order = orderDao.getOrderByID(order_id);
                    Product product = productDAO.getProductById(product_id);

                    OrderDetails orderDetails = new OrderDetails(
                            rs.getInt("order_details_id"),
                            order,
                            product,
                            rs.getInt("quantity"),
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("address"),
                            rs.getBigDecimal("unit_price"),
                            rs.getString("note")
                    );
                    orderDetailsList.add(orderDetails);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return orderDetailsList;
    }

}
