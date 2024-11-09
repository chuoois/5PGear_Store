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
import model.Brand;

public class BrandDAO extends DBContext {

    private final Connection connection;

    // Constructor nhận đối tượng Connection
    public BrandDAO() {
        this.connection = DBContext.getConnection();
    }

    public Brand getBrandById(int brandId) {
        String sql = "SELECT * FROM Brand WHERE brand_id = ?";
        Brand brand = null;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, brandId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                brand = new Brand(
                        rs.getInt("brand_id"),
                        rs.getString("brand_name"),
                        rs.getString("description") // Thêm description
                );
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in getBrandById: " + e.getMessage());
        }

        return brand;
    }

    public List<Brand> getAllBrands() {
        String sql = "SELECT * FROM Brand";
        List<Brand> brands = new ArrayList<>();

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Brand brand = new Brand(
                        rs.getInt("brand_id"),
                        rs.getString("brand_name"),
                        rs.getString("description") // Thêm description
                );
                brands.add(brand);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in getAllBrands: " + e.getMessage());
        }

        return brands;
    }

}


