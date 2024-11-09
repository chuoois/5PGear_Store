package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Role;
import model.SaleManager;

public class SaleManagerDAO {

    private final Connection connection;

    public SaleManagerDAO() {
        this.connection = DBContext.getConnection();
    }

    public List<SaleManager> getFilteredSaleManager(String gender, String role, String status, String search, int offset, int limit, String sortField, String sortOrder) {
        List<SaleManager> saleManagerList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT sm.*, r.role_name FROM SaleManager sm LEFT JOIN Role r ON sm.role_id = r.role_id WHERE 1=1");

        // Filtering conditions
        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND sm.gender = ?");
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND sm.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (sm.salemanager_name LIKE ? OR sm.email LIKE ? OR sm.phone LIKE ?)");
        }

        // Sorting condition
        if (sortField != null && !sortField.isEmpty() && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
            sql.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);
        } else {
            sql.append(" ORDER BY sm.salemanager_id ASC"); // Default sort
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
                SaleManager saleManager = new SaleManager();
                saleManager.setSalemanagerId(rs.getInt("salemanager_id"));
                saleManager.setSalemanagerName(rs.getString("salemanager_name"));
                saleManager.setEmail(rs.getString("email"));
                saleManager.setPhone(rs.getString("phone"));
                saleManager.setGender(rs.getString("gender"));
                saleManager.setStatus(rs.getString("status"));
                saleManager.setDob(rs.getDate("dob"));
                Role roleObj = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                saleManager.setRole(roleObj);
                saleManagerList.add(saleManager);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleManagerList;
    }

    public int getSaleManagerCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS saleManagerCount FROM SaleManager";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("saleManagerCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public SaleManager getSaleManagerById(int saleManagerId) {
        String sql = "SELECT * FROM SaleManager WHERE salemanager_id = ?";
        SaleManager saleManager = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, saleManagerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    saleManager = new SaleManager();
                    saleManager.setSalemanagerId(rs.getInt("salemanager_id"));
                    saleManager.setSalemanagerName(rs.getString("salemanager_name"));
                    saleManager.setEmail(rs.getString("email"));
                    saleManager.setPassword(rs.getString("password"));
                    saleManager.setPhone(rs.getString("phone"));
                    saleManager.setGender(rs.getString("gender"));
                    saleManager.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    saleManager.setRole(role);
                    saleManager.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saleManager;
    }

    public void addSaleManager(SaleManager saleManager) {
        String sql = "INSERT INTO SaleManager (salemanager_name, email, dob, password, phone, gender, avatar_url, role_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, saleManager.getSalemanagerName());
            ps.setString(2, saleManager.getEmail());
            ps.setDate(3, saleManager.getDob() != null ? new java.sql.Date(saleManager.getDob().getTime()) : null);
            ps.setString(4, saleManager.getPassword());
            ps.setString(5, saleManager.getPhone());
            ps.setString(6, saleManager.getGender());
            ps.setString(7, saleManager.getAvatar_url());
            ps.setInt(8, saleManager.getRole().getRoleId());
            ps.setString(9, saleManager.getStatus());

            ps.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSaleManagerStatus(SaleManager salemnager) {
        String sql = "UPDATE SaleManager SET status = ? WHERE salemanager_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, salemnager.getStatus());
            ps.setInt(2, salemnager.getSalemanagerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SaleManager getSaleManagerByEmail(String email) {
    SaleManager saleManager = null;
    String sql = "SELECT salemanager_id, salemanager_name, email, dob, password, phone, gender, avatar_url, role_id, status FROM SaleManager WHERE email = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, email);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                saleManager = new SaleManager();
                saleManager.setSalemanagerId(rs.getInt("salemanager_id"));
                saleManager.setSalemanagerName(rs.getString("salemanager_name"));
                saleManager.setEmail(rs.getString("email"));
                saleManager.setDob(rs.getDate("dob")); // Assuming dob is stored as Date
                saleManager.setPassword(rs.getString("password"));
                saleManager.setPhone(rs.getString("phone"));
                saleManager.setGender(rs.getString("gender"));
                saleManager.setAvatar_url(rs.getString("avatar_url"));
                
                // Assuming Role is a separate model
                Role role = new Role(rs.getInt("role_id"), null); 
                saleManager.setRole(role);

                saleManager.setStatus(rs.getString("status"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();  // Xử lý lỗi hoặc ghi log nếu cần
    }
    return saleManager;
}


}
