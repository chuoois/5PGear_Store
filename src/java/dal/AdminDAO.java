package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Admin;
import model.Role;

public class AdminDAO extends DBContext {

    private final Connection connection;

    public AdminDAO() {
        this.connection = DBContext.getConnection();
    }

    public Admin getAdminByEmail(String email) {
        Admin admin = null;
        String sql = "SELECT admin_id, admin_name, email, password, avatar_url, role_id FROM Admin WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    admin = new Admin();
                    admin.setAdminId(rs.getInt("admin_id"));
                    admin.setAdminName(rs.getString("admin_name"));
                    admin.setEmail(rs.getString("email"));
                    admin.setPassword(rs.getString("password"));
                    admin.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    admin.setRole(role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Log lỗi tại đây hoặc xử lý theo nhu cầu
        }
        return admin;
    }

    public Role getRoleById(int roleId) {
        String query = "SELECT * FROM Role WHERE role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, roleId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Role(rs.getInt("role_id"), rs.getString("role_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Admin> getAllAdmin(int offset, int limit) {
        List<Admin> adminList = new ArrayList<>();
        String sql = "SELECT * FROM Admin ORDER BY admin_id ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setAdminName(rs.getString("admin_name"));
                admin.setEmail(rs.getString("email"));
                admin.setPhone(rs.getString("phone"));
                admin.setStatus(rs.getString("status"));
                Role role = new Role(rs.getInt("role_id"), rs.getString(null));
                admin.setRole(role);
                adminList.add(admin);
            }
        } catch (SQLException e) {
        }
        return adminList;
    }

    public void addAdmin(Admin admin) {
        String sql = "INSERT INTO Admin (admin_name, email, dob, password, phone, gender, avatar_url, role_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, admin.getAdminName());
            ps.setString(2, admin.getEmail());
            ps.setDate(3, admin.getDob() != null ? new java.sql.Date(admin.getDob().getTime()) : null);
            ps.setString(4, admin.getPassword());
            ps.setString(5, admin.getPhone());
            ps.setString(6, admin.getGender());
            ps.setString(7, admin.getAvatar_url());
            ps.setInt(8, admin.getRole().getRoleId());
            ps.setString(9, admin.getStatus());

            ps.executeUpdate(); // Execute the insert statement
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}   
