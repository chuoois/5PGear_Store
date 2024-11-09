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
import model.Role;
import model.Marketing;

public class MarketingDAO extends DBContext {

    private final Connection connection;

    public MarketingDAO() {
        this.connection = DBContext.getConnection();
    }

    public List<Marketing> getFilteredMarketing(String gender, String role, String status, String search, int offset, int limit, String sortField, String sortOrder) {
        List<Marketing> marketingList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT m.*, r.role_name FROM Marketing m LEFT JOIN Role r ON m.role_id = r.role_id WHERE 1=1");

        // Filter by gender if provided
        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND m.gender = ?");
        }
        // Filter by role if provided
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
        }
        // Filter by status if provided
        if (status != null && !status.isEmpty()) {
            sql.append(" AND m.status = ?");
        }
        // Filter by search query (name, email, or phone) if provided
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (m.marketer_name LIKE ? OR m.email LIKE ? OR m.phone LIKE ?)");
        }

        // Sorting condition
        if (sortField != null && !sortField.isEmpty() && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
            sql.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);
        } else {
            sql.append(" ORDER BY m.marketer_id ASC"); // Default sort
        }

        // Add pagination
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            // Set gender parameter
            if (gender != null && !gender.isEmpty()) {
                ps.setString(paramIndex++, gender);
            }
            // Set role parameter
            if (role != null && !role.isEmpty()) {
                ps.setString(paramIndex++, role);
            }
            // Set status parameter
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            // Set search parameters
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            // Set offset and limit
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex, limit);

            // Execute query and map results
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Marketing marketing = new Marketing();
                marketing.setMarketingId(rs.getInt("marketer_id"));
                marketing.setMarketingName(rs.getString("marketer_name"));
                marketing.setEmail(rs.getString("email"));
                marketing.setPhone(rs.getString("phone"));
                marketing.setGender(rs.getString("gender"));
                marketing.setStatus(rs.getString("status"));
                marketing.setDob(rs.getDate("dob"));
                Role roleObj = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                marketing.setRole(roleObj);
                marketingList.add(marketing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return marketingList;
    }

    public int getMarketingCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS marketingCount FROM Marketing";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("marketingCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Marketing getMarketerById(int marketerId) {
        String sql = "SELECT * FROM Marketing WHERE marketer_id = ?";
        Marketing marketer = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, marketerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    marketer = new Marketing();
                    marketer.setMarketingId(rs.getInt("marketer_id"));
                    marketer.setMarketingName(rs.getString("marketer_name"));
                    marketer.setEmail(rs.getString("email"));
                    marketer.setPassword(rs.getString("password"));
                    marketer.setPhone(rs.getString("phone"));
                    marketer.setGender(rs.getString("gender"));
                    marketer.setStatus(rs.getString("status"));
                    marketer.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    marketer.setRole(role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marketer;
    }

    public void addMarketing(Marketing marketing) {
        String sql = "INSERT INTO Marketing (marketer_name, email, dob, password, phone, gender, avatar_url, role_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, marketing.getMarketingName());
            ps.setString(2, marketing.getEmail());
            ps.setDate(3, marketing.getDob() != null ? new java.sql.Date(marketing.getDob().getTime()) : null);
            ps.setString(4, marketing.getPassword());
            ps.setString(5, marketing.getPhone());
            ps.setString(6, marketing.getGender());
            ps.setString(7, marketing.getAvatar_url());
            ps.setInt(8, marketing.getRole().getRoleId());
            ps.setString(9, marketing.getStatus());

            ps.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Marketing login(String email, String password) {
        String query = "SELECT * FROM Marketing WHERE email = ? AND password = ?";
        try {

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Marketing marketer = new Marketing();
                marketer.setMarketingId(rs.getInt(1));
                marketer.setMarketingName(rs.getString(2));
                marketer.setEmail(rs.getString("email"));
                marketer.setPassword(rs.getString("password"));
                marketer.setPhone(rs.getString("phone"));
                marketer.setGender(rs.getString("gender"));
                marketer.setStatus(rs.getString("status"));
                marketer.setAvatar_url(rs.getString("avatar_url"));
                // Set other fields if needed
                return marketer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Marketing getMarketingByEmail(String email) {
        String query = "SELECT * FROM Marketing WHERE email = ?";
        try {

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Marketing marketer = new Marketing();
                marketer.setMarketingId(rs.getInt(1));
                marketer.setMarketingName(rs.getString(2));
                marketer.setEmail(rs.getString("email"));
                marketer.setPassword(rs.getString("password"));
                marketer.setPhone(rs.getString("phone"));
                marketer.setGender(rs.getString("gender"));
                marketer.setStatus(rs.getString("status"));
                marketer.setAvatar_url(rs.getString("avatar_url"));
                // Set other fields if needed
                Role role = new Role(rs.getInt("role_id"), null);
                marketer.setRole(role);
                return marketer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
        public void updateMarketingStatus(Marketing marketing) {
        String sql = "UPDATE Marketing SET status = ? WHERE marketer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, marketing.getStatus());
            ps.setInt(2, marketing.getMarketingId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
