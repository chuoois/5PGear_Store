package dal;

import model.Customer;
import model.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomerDAO extends DBContext {

    private final Connection connection;

    public CustomerDAO() {
        this.connection = DBContext.getConnection();
    }

    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO Customer (customer_name, dob, email, password, phone, address, gender, role_id, status, avatar_url, date_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerName());
            ps.setDate(2, customer.getDob() != null ? new java.sql.Date(customer.getDob().getTime()) : null);
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getPhone() != null ? customer.getPhone() : null);
            ps.setString(6, customer.getAddress() != null ? customer.getAddress() : null);
            ps.setString(7, customer.getGender());
            ps.setInt(8, customer.getRole().getRoleId());
            ps.setString(9, customer.getStatus());
            ps.setString(10, customer.getAvatar_url());
            ps.setDate(11, new java.sql.Date(customer.getDateCreate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerByEmail(String email) {
        String sql = "SELECT * FROM Customer WHERE email = ?";
        Customer customer = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setCustomerName(rs.getString("customer_name"));
                    customer.setDob(rs.getDate("dob"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPassword(rs.getString("password"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("address"));
                    customer.setGender(rs.getString("gender"));
                    customer.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    customer.setRole(role);
                    customer.setStatus(rs.getString("status"));
                    customer.setToken(rs.getString("token")); // Include token
                    customer.setDateCreate(rs.getDate("date_create")); // Include date_create
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public Customer getCustomerByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM Customer WHERE phone = ?";
        Customer customer = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setCustomerName(rs.getString("customer_name"));
                    customer.setDob(rs.getDate("dob"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPassword(rs.getString("password"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("address"));
                    customer.setGender(rs.getString("gender"));
                    customer.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    customer.setRole(role);
                    customer.setStatus(rs.getString("status"));
                    customer.setToken(rs.getString("token")); // Include token
                    customer.setDateCreate(rs.getDate("date_create")); // Include date_create
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public String generateActivationToken() {
        return UUID.randomUUID().toString();
    }

    public void addCustomerWithToken(Customer customer, String token) {
        String sql = "INSERT INTO Customer (customer_name, email, password, phone, address, gender, dob, role_id, status, token, date_create) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());
            ps.setString(6, customer.getGender());
            ps.setDate(7, new java.sql.Date(customer.getDob().getTime()));
            ps.setInt(8, customer.getRole().getRoleId());
            ps.setString(9, customer.getStatus());
            ps.setString(10, token);
            ps.setDate(11, new java.sql.Date(customer.getDateCreate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomerByToken(String token) {
        String sql = "SELECT * FROM Customer WHERE token = ?";
        Customer customer = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, token);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setCustomerName(rs.getString("customer_name"));
                    customer.setDob(rs.getDate("dob"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPassword(rs.getString("password"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("address"));
                    customer.setGender(rs.getString("gender"));
                    customer.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    customer.setRole(role);
                    customer.setStatus(rs.getString("status"));
                    customer.setToken(rs.getString("token")); // Include token
                    customer.setDateCreate(rs.getDate("date_create")); // Include date_create
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public void activateCustomer(int customerId) {
        String sql = "UPDATE Customer SET status = 'Active', token = NULL WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassCustomer(String email, String newPass) {
        String sql = "UPDATE Customer SET password = ? WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPass);
            ps.setString(2, email);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomerStatus(Customer customer) {
        String sql = "UPDATE Customer SET status = ? WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getStatus());
            ps.setInt(2, customer.getCustomerId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        String sql = "UPDATE Customer SET customer_name = ?, dob = ?, email = ?, password = ?, phone = ?, address = ?, gender = ?, role_id = ?, status = ?, avatar_url = ?, date_create = ? WHERE customer_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, customer.getCustomerName());
            ps.setDate(2, customer.getDob() != null ? new java.sql.Date(customer.getDob().getTime()) : null); // Null check for dob
            ps.setString(3, customer.getEmail());
            ps.setString(4, customer.getPassword());
            ps.setString(5, customer.getPhone());
            ps.setString(6, customer.getAddress());
            ps.setString(7, customer.getGender());
            ps.setInt(8, customer.getRole().getRoleId());
            ps.setString(9, customer.getStatus());
            ps.setString(10, customer.getAvatar_url());
            ps.setDate(11, new java.sql.Date(customer.getDateCreate().getTime())); // Setting dateCreate
            ps.setInt(12, customer.getCustomerId()); // WHERE condition to specify which customer to update

            // Execute the update
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getNewlyRegisteredCustomerCount(LocalDate startDate, LocalDate endDate, int roleId) {
        String sql = "SELECT COUNT(*) FROM Customer "
                + "WHERE date_create BETWEEN ? AND ? "
                + "AND role_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            // Set the parameters for the query
            pstmt.setDate(1, java.sql.Date.valueOf(startDate));
            pstmt.setDate(2, java.sql.Date.valueOf(endDate));
            pstmt.setInt(3, roleId);

            // Execute the query
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Return the count of customers
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
        return 0; // Return 0 if no customers are found or on error
    }

    public List<Customer> getFilteredCustomer(String gender, String role, String status, String search, int offset, int limit, String sortField, String sortOrder) {
        List<Customer> customers = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT c.*, r.role_name FROM Customer c LEFT JOIN Role r ON c.role_id = r.role_id WHERE 1=1");

        // Filtering conditions
        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND c.gender = ?");
        }
        if (role != null && !role.isEmpty()) {
            sql.append(" AND r.role_name = ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND c.status = ?");
        }
        if (search != null && !search.isEmpty()) {
            sql.append(" AND (c.customer_name LIKE ? OR c.email LIKE ? OR c.phone LIKE ?)");
        }

        // Sorting condition
        if (sortField != null && !sortField.isEmpty() && (sortOrder.equalsIgnoreCase("ASC") || sortOrder.equalsIgnoreCase("DESC"))) {
            sql.append(" ORDER BY ").append(sortField).append(" ").append(sortOrder);
        } else {
            sql.append(" ORDER BY c.customer_id ASC"); // Default sort
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
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setCustomerName(rs.getString("customer_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setGender(rs.getString("gender"));
                Role roleObj = new Role(rs.getInt("role_id"), rs.getString("role_name"));
                customer.setRole(roleObj);
                customer.setAvatar_url(rs.getString("avatar_url"));
                customer.setToken(rs.getString("token"));
                customer.setStatus(rs.getString("status"));
                customer.setDob(rs.getDate("dob"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public int getCustomerCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS customerCount FROM Customer";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("customerCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM Customer WHERE customer_id = ?";
        Customer customer = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, customerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customer = new Customer();
                    customer.setCustomerId(rs.getInt("customer_id"));
                    customer.setCustomerName(rs.getString("customer_name"));
                    customer.setDob(rs.getDate("dob"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPassword(rs.getString("password"));
                    customer.setPhone(rs.getString("phone"));
                    customer.setAddress(rs.getString("address"));
                    customer.setGender(rs.getString("gender"));
                    customer.setAvatar_url(rs.getString("avatar_url"));
                    Role role = new Role(rs.getInt("role_id"), null);
                    customer.setRole(role);
                    customer.setStatus(rs.getString("status"));
                    customer.setToken(rs.getString("token")); // Include token if necessary
                    customer.setDateCreate(rs.getDate("date_create"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
