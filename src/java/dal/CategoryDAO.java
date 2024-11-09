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
import model.Category;

public class CategoryDAO extends DBContext {

    private final Connection connection;

    // Constructor nhận đối tượng Connection
    public CategoryDAO() {
        this.connection = DBContext.getConnection();
    }

    public Category getCategoryById(int categoryId) {
        String sql = "SELECT * FROM Category WHERE category_id = ?";
        Category category = null;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, categoryId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description") // Thêm description
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in getCategoryById: " + e.getMessage());
        }

        return category;
    }

    public List<Category> getAllCategories() {
        String sql = "SELECT * FROM Category";
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name"),
                        rs.getString("description")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in getAllCategories: " + e.getMessage());
        }

        return categories;
    }

    public static void main(String[] args) {
        // Create an instance of CategoryDAO
        CategoryDAO categoryDAO = new CategoryDAO();

        // Test getCategoryById method
//        int testCategoryId = 1; // Assuming a category with ID 1 exists
//        Category category = categoryDAO.getCategoryById(testCategoryId);
//        if (category != null) {
//            System.out.println("Category ID: " + category.getCategoryId());
//            System.out.println("Category Name: " + category.getCategoryName());
//            System.out.println("Category Description: " + category.getDescription());
//        } else {
//            System.out.println("Category with ID " + testCategoryId + " not found.");
//        }

        // Test getAllCategories method
        List<Category> categories = categoryDAO.getAllCategories();
        if (!categories.isEmpty()) {
            System.out.println("All Categories:");
            for (Category c : categories) {
                System.out.println("ID: " + c.getCategoryId() + ", Name: " + c.getCategoryName() + ", Description: " + c.getDescription());
            }
        } else {
            System.out.println("No categories found.");
        }
    }
}


