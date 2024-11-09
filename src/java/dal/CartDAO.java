/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Category;
import model.Brand;
import model.Customer;
import model.Cart;

public class CartDAO extends DBContext {

    private final Connection connection;

    public CartDAO() {
        this.connection = DBContext.getConnection();

    }

    public List<Cart> getAllCartByCustomerId(int customerId) {
        CustomerDAO customerDao = new CustomerDAO();
        ProductDAO productDao = new ProductDAO();
        List<Cart> list = new ArrayList<>();  // Danh sách Cart
        String sql = "SELECT * FROM Cart WHERE customer_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            // Thiết lập giá trị cho tham số customer_id trong câu truy vấn
            st.setInt(1, customerId);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int productId = rs.getInt("product_id");

                    // Lấy thông tin khách hàng và sản phẩm
                    Customer customer = customerDao.getCustomerById(customerId);  // customerId được lấy từ tham số truyền vào
                    Product product = productDao.getProductById(productId);

                    // Tạo đối tượng Cart
                    Cart cart = new Cart(
                            rs.getInt("cart_id"),
                            customer,
                            product,
                            rs.getInt("quantity")
                    );

                    // Thêm đối tượng Cart vào danh sách
                    list.add(cart);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        // Trả về danh sách Cart theo customerId
        return list;
    }

    public void updateCart(int customerId, int productId, int newQuantity) {
        String selectSql = "SELECT quantity FROM Cart WHERE customer_id = ? AND product_id = ?";
        String updateSql = "UPDATE Cart SET quantity = ? WHERE customer_id = ? AND product_id = ?";
        String insertSql = "INSERT INTO Cart (customer_id, product_id, quantity) VALUES (?, ?, ?)";
        ProductDAO productDao = new ProductDAO();
        Product productdetail = productDao.getProductById(productId);
        try (PreparedStatement selectSt = connection.prepareStatement(selectSql)) {
            // Check if the cart with the same customer_id and product_id exists
            selectSt.setInt(1, customerId);
            selectSt.setInt(2, productId);

            try (ResultSet rs = selectSt.executeQuery()) {
                if (rs.next()) {
                    // If the cart entry exists, update the quantity
                    int existingQuantity = rs.getInt("quantity");
                    int updatedQuantity = existingQuantity + newQuantity;
                    if(updatedQuantity >=productdetail.getQuantity()-productdetail.getHold()){
                        updatedQuantity=productdetail.getQuantity()-productdetail.getHold();
                    }
                    try (PreparedStatement updateSt = connection.prepareStatement(updateSql)) {
                        updateSt.setInt(1, updatedQuantity);
                        updateSt.setInt(2, customerId);
                        updateSt.setInt(3, productId);
                        updateSt.executeUpdate();
                    }
                } else {
                    // If the cart entry does not exist, insert a new entry
                    try (PreparedStatement insertSt = connection.prepareStatement(insertSql)) {
                        insertSt.setInt(1, customerId);
                        insertSt.setInt(2, productId);
                        insertSt.setInt(3, newQuantity);
                        insertSt.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void deleteCartById(int cartId) {
        String sql = "DELETE FROM Cart WHERE cart_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            // Thiết lập giá trị cho cart ID trong câu lệnh chuẩn bị
            st.setInt(1, cartId);

            // Thực thi câu lệnh DELETE
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void deleteCartByCustomerId(int customerId) {
        String sql = "DELETE FROM Cart WHERE customer_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            // Set the customer ID for the prepared statement
            st.setInt(1, customerId);

            // Execute the DELETE statement
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void updateCartQuantity(int cartId, int newQuantity) {
        String sql = "UPDATE Cart SET quantity = ? WHERE cart_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            // Thiết lập số lượng mới và cart_id trong PreparedStatement
            st.setInt(1, newQuantity);
            st.setInt(2, cartId);

            // Thực thi câu lệnh UPDATE
            st.executeUpdate();

        } catch (SQLException e) {
            // In ra thông báo lỗi nếu có vấn đề về SQL
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        CartDAO cartdao = new CartDAO();
        List<Cart> cart = cartdao.getAllCartByCustomerId(1);
        cartdao.deleteCartById(1);
        cartdao.deleteCartByCustomerId(4);
        for (Cart product : cart) {
            System.out.println("Cart ID: " + product.getCartId());
            System.out.println("Customer Name: " + product.getCustomer().getCustomerName());
            System.out.println("Product: " + product.getProduct().getProductName());
            System.out.println("Quantity: " + product.getQuantity());

            System.out.println("--------------------------------------");
        }
    }

}
