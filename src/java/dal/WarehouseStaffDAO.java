package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Role;
import model.WarehouseStaff;

public class WarehouseStaffDAO extends DBContext {

    private final Connection connection;

    public WarehouseStaffDAO() {
        this.connection = DBContext.getConnection();
    }

    public List<WarehouseStaff> getFilteredWaleStaff(String gender, String role, String status, String search, int offset, int limit, String sortField, String sortOrder) {
        List<WarehouseStaff> waleStaffList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT ws.*, r.role_name FROM WaleStaff ws LEFT JOIN Role r ON ws.role_id = r.role_id WHERE 1=1");

        // Filtering conditions
        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND ws.gender = ?");
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND ws.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (ws.walestaff_name LIKE ? OR ws.email LIKE ? OR ws.phone LIKE ?)");
        }

        // Sorting condition
        if (sortField != null && !sortField.isEmpty() && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
            sql.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);
        } else {
            sql.append(" ORDER BY ws.walestaff_id ASC"); // Default sort
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
                WarehouseStaff waleStaff = new WarehouseStaff();
                waleStaff.setWarehousestaffId(rs.getInt("walestaff_id"));
                waleStaff.setWarehousestaffName(rs.getString("walestaff_name"));
                waleStaff.setEmail(rs.getString("email"));
                waleStaff.setPhone(rs.getString("phone"));
                waleStaff.setGender(rs.getString("gender"));
                waleStaff.setStatus(rs.getString("status"));
                waleStaff.setDob(rs.getDate("dob"));
                waleStaff.setAvatar_url(rs.getString("avatar_url"));
                Role roleObj = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                waleStaff.setRole(roleObj);
                waleStaffList.add(waleStaff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waleStaffList;
    }

    public int getWaleStaffCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS waleStaffCount FROM WaleStaff";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("waleStaffCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public WarehouseStaff getWaleStaffById(int waleStaffId) {
        String sql = "SELECT * FROM WaleStaff WHERE walestaff_id = ?";
        WarehouseStaff waleStaff = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, waleStaffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    waleStaff = new WarehouseStaff();
                    waleStaff.setWarehousestaffId(rs.getInt("walestaff_id"));
                    waleStaff.setWarehousestaffName(rs.getString("walestaff_name"));
                    waleStaff.setEmail(rs.getString("email"));
                    waleStaff.setPassword(rs.getString("password"));
                    waleStaff.setPhone(rs.getString("phone"));
                    waleStaff.setGender(rs.getString("gender"));
                    waleStaff.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    waleStaff.setRole(role);
                    waleStaff.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waleStaff;
    }

    public WarehouseStaff getWaleStaffByEmail(String email) {
        String sql = "SELECT * FROM WaleStaff WHERE email = ?";
        WarehouseStaff waleStaff = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email); // Use setString to set the email parameter
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    waleStaff = new WarehouseStaff();
                    waleStaff.setWarehousestaffId(rs.getInt("walestaff_id"));
                    waleStaff.setWarehousestaffName(rs.getString("walestaff_name"));
                    waleStaff.setEmail(rs.getString("email"));
                    waleStaff.setPassword(rs.getString("password"));
                    waleStaff.setPhone(rs.getString("phone"));
                    waleStaff.setGender(rs.getString("gender"));
                    waleStaff.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    waleStaff.setRole(role);
                    waleStaff.setStatus(rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return waleStaff;
    }

    public void addWaleStaff(WarehouseStaff waleStaff) {
        String sql = "INSERT INTO WaleStaff (walestaff_name, email, dob, password, phone, gender, avatar_url, role_id, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, waleStaff.getWarehousestaffName());
            ps.setString(2, waleStaff.getEmail());
            ps.setDate(3, waleStaff.getDob() != null ? new java.sql.Date(waleStaff.getDob().getTime()) : null);
            ps.setString(4, waleStaff.getPassword());
            ps.setString(5, waleStaff.getPhone());
            ps.setString(6, waleStaff.getGender());
            ps.setString(7, waleStaff.getAvatar_url());
            ps.setInt(8, waleStaff.getRole().getRoleId());
            ps.setString(9, waleStaff.getStatus());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateWaleStaffStatus(WarehouseStaff waleStaff) {
        String sql = "UPDATE WaleStaff SET status = ? WHERE walestaff_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, waleStaff.getStatus());
            ps.setInt(2, waleStaff.getWarehousestaffId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
