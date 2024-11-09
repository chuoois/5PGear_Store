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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Role;
import model.Sale;

public class SaleDAO extends DBContext {

    private final Connection connection;

    public SaleDAO() {
        this.connection = DBContext.getConnection();
    }

    public List<Sale> getFilteredSale(String gender, String role, String status, String search, int offset, int limit, String sortField, String sortOrder) {
        List<Sale> saleList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT s.*, r.role_name FROM Sale s LEFT JOIN Role r ON s.role_id = r.role_id WHERE 1=1");

        // Filtering conditions
        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND s.gender = ?");
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND s.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (s.sale_name LIKE ? OR s.email LIKE ? OR s.phone LIKE ?)");
        }

        // Sorting condition
        if (sortField != null && !sortField.isEmpty() && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
            sql.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);
        } else {
            sql.append(" ORDER BY s.sale_id ASC"); // Default sort
        }

        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (gender != null && !gender.isEmpty()) {
                ps.setString(paramIndex++, gender);
            }
            if (role != null && !role.isEmpty()) {
                ps.setString(paramIndex++, role);
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            if (search != null && !search.isEmpty()) {
                String searchPattern = "%" + search + "%";
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
                ps.setString(paramIndex++, searchPattern);
            }
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSaleId(rs.getInt("sale_id"));
                sale.setSaleName(rs.getString("sale_name"));
                sale.setEmail(rs.getString("email"));
                sale.setPhone(rs.getString("phone"));
                sale.setGender(rs.getString("gender"));
                sale.setStatus(rs.getString("status"));
                sale.setDob(rs.getDate("dob"));
                Role roleObj = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                sale.setRole(roleObj);
                saleList.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleList;
    }

    public int getSaleCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS saleCount FROM Sale";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("saleCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Sale getSaleById(int saleId) {
        String sql = "SELECT * FROM Sale WHERE sale_id = ?";
        Sale sale = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, saleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sale = new Sale();
                    sale.setSaleId(rs.getInt("sale_id"));
                    sale.setSaleName(rs.getString("sale_name"));
                    sale.setEmail(rs.getString("email"));
                    sale.setPassword(rs.getString("password"));
                    sale.setPhone(rs.getString("phone"));
                    sale.setGender(rs.getString("gender"));
                    sale.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    sale.setRole(role);
                    sale.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sale;
    }

    public List<Sale> getAllSales() {
        String sql = "SELECT * FROM Sale";
        List<Sale> salesList = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Sale sale = new Sale();
                sale.setSaleId(rs.getInt("sale_id"));
                sale.setSaleName(rs.getString("sale_name"));
                sale.setEmail(rs.getString("email"));
                sale.setPassword(rs.getString("password"));
                sale.setPhone(rs.getString("phone"));
                sale.setGender(rs.getString("gender"));
                sale.setAvatar_url(rs.getString("avatar_url"));

                // Assuming Role has a constructor that takes just the role_id
                Role role = new Role(rs.getInt("role_id"), null);
                sale.setRole(role);

                sale.setStatus(rs.getString("status"));

                // Add the sale object to the list
                salesList.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return salesList;
    }

    public void addSale(Sale sale) {
        String sql = "INSERT INTO Sale (sale_name, email, dob, password, phone, gender, avatar_url, role_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sale.getSaleName());
            ps.setString(2, sale.getEmail());
            ps.setDate(3, sale.getDob() != null ? new java.sql.Date(sale.getDob().getTime()) : null);
            ps.setString(4, sale.getPassword());
            ps.setString(5, sale.getPhone());
            ps.setString(6, sale.getGender());
            ps.setString(7, sale.getAvatar_url());
            ps.setInt(8, sale.getRole().getRoleId());
            ps.setString(9, sale.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Sale getSaleByEmail(String email) {
        String sql = "SELECT * FROM Sale WHERE email = ?";
        Sale sale = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email); // Use setString to set the email parameter
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    sale = new Sale();
                    sale.setSaleId(rs.getInt("sale_id"));
                    sale.setSaleName(rs.getString("sale_name"));
                    sale.setEmail(rs.getString("email"));
                    sale.setPassword(rs.getString("password"));
                    sale.setPhone(rs.getString("phone"));
                    sale.setGender(rs.getString("gender"));
                    sale.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    sale.setRole(role);
                    sale.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sale;
    }

    public void updateSaleStatus(Sale sale) {
        String sql = "UPDATE Sale SET status = ? WHERE sale_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, sale.getStatus());
            ps.setInt(2, sale.getSaleId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, Integer> getPendingOrdersCountForSales() {
        Map<Integer, Integer> pendingOrdersCount = new HashMap<>();

        // Use the correct SQL query
        String query = "SELECT sale_id, COUNT(*) AS pendingCount "
                + "FROM Orders "
                + "WHERE ostatus_id = 2 " // Assuming 2 represents 'Pending' status
                + "GROUP BY sale_id";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            // Loop through the result set and populate the map
            while (rs.next()) {
                int saleId = rs.getInt("sale_id");
                int pendingCount = rs.getInt("pendingCount");
                pendingOrdersCount.put(saleId, pendingCount); // Map sale_id to pendingCount
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pendingOrdersCount;
    }

}
