package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Role;  // Assuming you have a Role model class

public class RoleDAO extends DBContext {

    private final Connection connection;

    public RoleDAO() {
        this.connection = DBContext.getConnection();
    }

    // Method to get all roles
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT role_id, role_name FROM Role";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role_name");

                Role role = new Role(roleId, roleName);
                roles.add(role);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    public int getRoleId(String roleName) {
        String sql = "SELECT role_id FROM Role WHERE role_name = ?";
        int roleId = -1;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, roleName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    roleId = rs.getInt("role_id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roleId;
    }

}
