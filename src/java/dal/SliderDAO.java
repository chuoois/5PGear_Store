/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.getConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Slider;

/**
 *
 * @author Bùi Khánh Linh
 */
public class SliderDAO extends DBContext {

    private final Connection connection;

    public SliderDAO() {
        this.connection = DBContext.getConnection();

    }

    public List getSliderHomepage() {
        List<Slider> list = new ArrayList<>();
        String sql = "Select * from Slider where status = 1";
        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Slider slider = new Slider(rs.getInt("slider_id"),
                        rs.getString("slider_img"),
                        rs.getString("backLink"),
                        rs.getString("status"),
                        rs.getString("slider_title"),
                        rs.getInt("update_by"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at"),
                        rs.getInt("created_by"),
                        rs.getInt("product_id")
                );
                list.add(slider);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Slider> getFilteredSliders(int marketingId, String search, String status, String sort, String productIdParam) {
        System.out.println(productIdParam);
        List<Slider> sliderList = new ArrayList<>();
        String sql = "SELECT * FROM Slider WHERE [created_by] = ? and 1=1 ";

        // Check if filters are provided
        if (search != null && !search.isEmpty()) {
            sql += " AND [slider_title] LIKE N'%" + search + "%'";
        }

        if (productIdParam != null && !productIdParam.isEmpty()) {
            sql += " AND [product_id] = '" + productIdParam + "'";
        }

        if (status != null && !status.isEmpty()) {
            sql += " AND [status] = '" + status + "'";
        }

        // Sorting logic
        switch (sort) {           
            case "titleAZ":
                sql += " ORDER BY [slider_title]";
                break;
            case "titleZA":
                sql += " ORDER BY [slider_title] DESC";
                break;
            case "dateCreated":
                sql += " ORDER BY [created_at]";
                break;
            default:
                sql += " ORDER BY [slider_id]";
                break;
        }
        System.out.println(sql);

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, marketingId);  // Set marketing ID for filtering

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Slider slider = new Slider(
                            rs.getInt("slider_id"),
                            rs.getString("slider_img"),
                            rs.getString("backlink"),
                            rs.getString("status"),
                            rs.getString("slider_title"),
                            rs.getInt("update_by"),
                            rs.getDate("created_at"),
                            rs.getDate("updated_at"),
                            rs.getInt("created_by"),
                            rs.getInt("product_id")
                    );
                    sliderList.add(slider);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sliderList;
    }

    public Slider getSliderById(int sliderId) {
        Slider slider = null;
        String query = "SELECT * FROM [Slider] WHERE [slider_id] = ?"; // Replace with your actual table name

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, sliderId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                slider = new Slider(
                        rs.getInt("slider_id"),
                        rs.getString("slider_img"),
                        rs.getString("backlink"),
                        rs.getString("status"),
                        rs.getString("slider_title"),
                        rs.getInt("update_by"),
                        rs.getDate("created_at"),
                        rs.getDate("updated_at"),
                        rs.getInt("created_by"),
                        rs.getInt("product_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return slider;
    }

    // Add other methods like updateSliderStatus, etc.
    public boolean updateSliderStatus(int sliderId, String newStatus) {
        String query = "UPDATE [Slider] SET status = ? WHERE slider_id = ?";
        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, newStatus);
            pstmt.setInt(2, sliderId);
            return pstmt.executeUpdate() > 0; // Return true if update is successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if an error occurs
        }
    }

    public boolean addSlider(Slider slider) {
        String sql = "INSERT INTO Slider (slider_title, slider_img, backlink, status, created_at,[updated_at], product_id,[update_by],[created_by]) VALUES (?, ?, ?, ?, ?, ?, ?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, slider.getTitle());
            pstmt.setString(2, slider.getImgPath());
            pstmt.setString(3, slider.getBacklink());
            pstmt.setString(4, slider.getStatus());
            pstmt.setDate(5, (Date) slider.getCreatedAt());
            pstmt.setDate(6, (Date) slider.getUpdateAt());
            pstmt.setInt(7, slider.getProductId()); // Assuming productId is an int
            pstmt.setInt(8, slider.getUpdateBy());
            pstmt.setInt(9, slider.getCreatedBy());
            return pstmt.executeUpdate() > 0; // Return true if the insert is successful
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            return false; // Return false if there is an error
        }
    }

    public boolean updateSlider(Slider slider) {
        String sql = "UPDATE Slider SET slider_title = ?, slider_img = ?, backlink = ?, status = ?, updated_at = ?,[created_at]=?, product_id = ?, update_by = ?,[created_by]=? WHERE slider_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, slider.getTitle());
            pstmt.setString(2, slider.getImgPath());
            pstmt.setString(3, slider.getBacklink());
            pstmt.setString(4, slider.getStatus());
            pstmt.setDate(5, (Date) slider.getUpdateAt());
            pstmt.setDate(6, (Date) slider.getCreatedAt());
            pstmt.setInt(7, slider.getProductId()); // Assuming productId is an int
            pstmt.setInt(8, slider.getUpdateBy());
            pstmt.setInt(9, slider.getCreatedBy());
            pstmt.setInt(10, slider.getSliderId()); // Assuming slider_id is an int
            return pstmt.executeUpdate() > 0; // Return true if the update is successful
        } catch (SQLException e) {
            e.printStackTrace(); // Log the exception
            return false; // Return false if there is an error
        }
    }

    public static void main(String[] args) {
        SliderDAO sd = new SliderDAO();
//        List<Slider> slider = sd.getSliderHomepage();
//        for (Slider sli : slider) {
//            System.out.println(sli.toString());
//        }

        // Sample test case with parameters
        int marketingId = 1; // Example marketing ID
        String search = ""; // Example search string
        String status = "1"; // Example status (1: active, 0: inactive)
        String sort = ""; // Example sort by title in ascending order

        List<Slider> filteredSliders = sd.getFilteredSliders(marketingId, search, status, sort, "10");
        for (Slider sli : filteredSliders) {
            System.out.println(sli.toString());
        }

//        Slider x = sd.getSliderById(1);
//        System.out.println(x);
//        boolean check = sd.updateSliderStatus(1, "0");
//        System.out.println(check);

    }

}
