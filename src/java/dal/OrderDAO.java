package dal;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Category;
import model.Customer;
import model.OStatus;

import model.Order;
import model.Sale;

public class OrderDAO extends DBContext {

    private final Connection connection;
    private SaleDAO saleDao;
    private CustomerDAO customerDao;

    public OrderDAO() {
        this.connection = DBContext.getConnection();
        this.saleDao = new SaleDAO();
        this.customerDao = new CustomerDAO();
    }

    public Order getOrderByID(int order_id) {
        String sql = "SELECT o.*, os.ostatus_name "
                + "FROM [Orders] o "
                + "JOIN [OStatus] os ON o.ostatus_id = os.ostatus_id "
                + "WHERE o.order_id = ?";
        Order order = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, order_id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

                    int sale_id = rs.getInt("sale_id");
                    int customer_id = rs.getInt("customer_id");

                    Sale sale = saleDao.getSaleById(sale_id);
                    Customer customer = customerDao.getCustomerById(customer_id);

                    order = new Order();
                    // Map the result set to the Order object
                    order.setOrderId(rs.getInt("order_id"));
                    order.setCustomerId(customer);
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setOrderDate(rs.getDate("order_date"));
                    order.setSaleId(sale);
                    // Lấy cả ostatus_id và ostatus_name
                    OStatus oStatus = new OStatus(rs.getInt("ostatus_id"), rs.getString("ostatus_name"));
                    order.setOstatusId(oStatus);
                    order.setPayment(rs.getString("payment"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();

        String sql = "SELECT o.*, os.ostatus_name "
                + "FROM [Orders] o "
                + "JOIN [OStatus] os ON o.ostatus_id = os.ostatus_id";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Tạo đối tượng Order
                Order order = new Order();

                // Lấy sale_id và customer_id
                int sale_id = rs.getInt("sale_id");
                int customer_id = rs.getInt("customer_id");

                // Lấy thông tin Sale và Customer từ DAO
                Sale sale = saleDao.getSaleById(sale_id);
                Customer customer = customerDao.getCustomerById(customer_id);

                // Thiết lập các thuộc tính cho đối tượng Order
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(customer);
                order.setTotalPrice(rs.getBigDecimal("total_price"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setSaleId(sale);

                // Lấy ostatus_id và ostatus_name
                OStatus oStatus = new OStatus(rs.getInt("ostatus_id"), rs.getString("ostatus_name"));
                order.setOstatusId(oStatus);
                order.setPayment(rs.getString("payment"));

                // Thêm đối tượng Order vào danh sách
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

    public int getOrderCountByStatus(String startDate, String endDate, String status) {
        // SQL query to count orders based on status and date range
        String sql = "SELECT COUNT(*) AS order_count\n"
                + "FROM Orders o\n"
                + "JOIN OStatus os ON o.ostatus_id = os.ostatus_id\n"
                + "WHERE o.order_date BETWEEN ? AND ?\n"
                + "AND os.ostatus_name = ?;";

        int orderCount = 0; // Variable to store the order count

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the date and status parameters
            ps.setString(1, startDate);
            ps.setString(2, endDate);
            ps.setString(3, status);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    orderCount = rs.getInt("order_count");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderCount;
    }

    public Map<Category, Float> getRevenueByCategory(LocalDate startDate, LocalDate endDate) {
        // SQL query to fetch total revenue per category within the date range
        String sql = "SELECT c.category_id, c.category_name, SUM(od.unit_price * od.quantity) AS revenue "
                + "FROM Orders o "
                + "JOIN OrderDetails od ON o.order_id = od.order_id "
                + "JOIN Product p ON od.product_id = p.product_id "
                + "JOIN Category c ON p.category_id = c.category_id "
                + "WHERE o.order_date BETWEEN ? AND ? "
                + "GROUP BY c.category_id, c.category_name";

        Map<Category, Float> revenueMap = new HashMap<>();

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the date parameters
            ps.setDate(1, Date.valueOf(startDate));
            ps.setDate(2, Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                // Process the result set and map categories to their revenue
                while (rs.next()) {
                    int categoryId = rs.getInt("category_id");
                    String categoryName = rs.getString("category_name");
                    float revenue = rs.getFloat("revenue");

                    // Create a new Category object
                    Category category = new Category(categoryId, categoryName, null);

                    // Add to the map
                    revenueMap.put(category, revenue);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return revenueMap;
    }

    public float getTotalRevenue(String startDate, String endDate) {
        float totalRevenue = 0;
        String sql = "SELECT SUM(total_price) AS total_revenue FROM Orders "
                + "WHERE order_date BETWEEN ? AND ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the date and status parameters
            ps.setString(1, startDate);
            ps.setString(2, endDate);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    totalRevenue = rs.getInt("total_revenue");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalRevenue;
    }

    public int getNewlyPurchasedCustomerCount(LocalDate startDate, LocalDate endDate) {
        int count = 0;
        String sql = "SELECT COUNT(DISTINCT customer_id) AS customerCount "
                + "FROM Orders "
                + "WHERE order_date BETWEEN ? AND ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Set the date and status parameters
            ps.setDate(1, java.sql.Date.valueOf(startDate));
            ps.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("customerCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public Map<LocalDate, Integer> getOrderCountByDate(LocalDate startDate, LocalDate endDate, String status) {
        Map<LocalDate, Integer> orderCountByDate = new HashMap<>();

        String sql = "SELECT CAST(order_date AS DATE) AS orderDay, COUNT(*) AS orderCount "
                + "FROM Orders "
                + "WHERE order_date BETWEEN ? AND ?";

        if (status != null && !status.isEmpty()) {
            sql += " AND status = ?";
        }

        sql += " GROUP BY CAST(order_date AS DATE)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            int parameterIndex = 1;
            ps.setDate(parameterIndex++, java.sql.Date.valueOf(startDate));
            ps.setDate(parameterIndex++, java.sql.Date.valueOf(endDate));

            if (status != null && !status.isEmpty()) {
                ps.setString(parameterIndex++, status);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    LocalDate orderDay = rs.getDate("orderDay").toLocalDate();
                    int orderCount = rs.getInt("orderCount");
                    orderCountByDate.put(orderDay, orderCount);
                }
            }
        } catch (SQLException ex) {
        }

        return orderCountByDate;
    }

    public List<Order> getAllBySaleID(int saleId, int pageNumber, int pageSize, String sortColumn, String sortOrder, Date fromDate, Date toDate, String status, String searchQuery) {
        List<Order> orders = new ArrayList<>();

        // Calculate offset for pagination
        int offset = (pageNumber - 1) * pageSize;

        // Build SQL query
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.order_id, c.customer_id, c.customer_name, s.sale_id, s.sale_name, o.total_price, o.order_date, os.ostatus_id, os.ostatus_name, o.payment ")
                .append("FROM Orders o ")
                .append("JOIN Customer c ON c.customer_id = o.customer_id ")
                .append("JOIN Sale s ON s.sale_id = o.sale_id ")
                .append("JOIN Ostatus os ON os.ostatus_id = o.ostatus_id ")
                .append("WHERE 1=1 ");

        // Add filters
        if (saleId > 0) {
            sql.append("AND s.sale_id = ? ");
        }
        if (fromDate != null) {
            sql.append("AND o.order_date >= ? ");
        }
        if (toDate != null) {
            sql.append("AND o.order_date <= ? ");
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND os.ostatus_name = ? ");
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql.append("AND (c.customer_name LIKE ? OR s.sale_name LIKE ?) ");
        }

        // Add sorting
        sql.append("ORDER BY ").append(sortColumn).append(" ").append(sortOrder).append(" ")
                .append("OFFSET ? ROWS ")
                .append("FETCH NEXT ? ROWS ONLY;");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Set filter parameters
            if (saleId > 0) {
                ps.setInt(paramIndex++, saleId);
            }
            if (fromDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(fromDate.getTime()));
            }
            if (toDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(toDate.getTime()));
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status); // Now compare the string status with `ostatus_name`
            }
            if (searchQuery != null && !searchQuery.isEmpty()) {
                ps.setString(paramIndex++, "%" + searchQuery + "%"); // for customer_name
                ps.setString(paramIndex++, "%" + searchQuery + "%"); // for sale_name
            }

            // Set pagination parameters
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    Customer cu = new Customer();
                    cu.setCustomerId(rs.getInt("customer_id"));
                    cu.setCustomerName(rs.getString("customer_name"));
                    order.setCustomerId(cu);
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setOrderDate(rs.getDate("order_date"));
                    Sale sale = new Sale();
                    sale.setSaleId(rs.getInt("sale_id"));
                    sale.setSaleName(rs.getString("sale_name"));
                    order.setSaleId(sale);
                    OStatus st = new OStatus(rs.getInt("ostatus_id"), rs.getString("ostatus_name"));
                    order.setOstatusId(st);
                    order.setPayment(rs.getString("payment"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }

    public int getTotalOrder() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS totalOrder FROM Orders"; // Đảm bảo tên bảng là đúng
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                count = rs.getInt("totalOrder");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public boolean updateStatus(int orderId, int status) {
        String sql = "UPDATE [dbo].[Orders] SET ostatus_id = ? WHERE order_id = ?"; // Câu lệnh SQL để cập nhật trạng thái đơn hàng

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // Thiết lập các tham số cho PreparedStatement
            ps.setInt(1, status);      // Trạng thái mới
            ps.setInt(2, orderId);     // ID đơn hàng cần cập nhật

            // Thực hiện cập nhật và kiểm tra số lượng dòng bị ảnh hưởng
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu cập nhật thành công
        } catch (SQLException e) {
            // In ra thông báo lỗi chi tiết
            System.err.println("SQL error while updating order status: " + e.getMessage());
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

    public int insertOrder(int customerId, BigDecimal totalPrice, int saleId, int ostatusId, String payment) {
        String sql = "INSERT INTO Orders (customer_id, total_price, order_date, sale_id, ostatus_id, payment) VALUES (?, ?, GETDATE(), ?, ?, ?)";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, customerId);
            ps.setBigDecimal(2, totalPrice);
            ps.setInt(3, saleId);
            ps.setInt(4, ostatusId); // Chỉnh sửa tên tham số
            ps.setString(5, payment);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về order_id
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Trả về -1 nếu có lỗi xảy ra
    }

    public void insertOrderDetail(int orderId, int productId, int quantity, BigDecimal unitPrice,
            String name, String phone, String address, String note) {
        String sql = "INSERT INTO OrderDetails (order_id, product_id, quantity, unit_price, name, phone, address, note) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBContext.getConnection(); // Lấy kết nối từ lớp DBConnection
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Thiết lập các giá trị cho PreparedStatement
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, quantity);
            pstmt.setBigDecimal(4, unitPrice);
            pstmt.setString(5, name);
            pstmt.setString(6, phone);
            pstmt.setString(7, address);
            pstmt.setString(8, note);

            // Thực thi câu lệnh
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public int getNextSaleId() {
        String sql = "WITH SaleOrderCounts AS ("
                + "    SELECT s.sale_id, COALESCE(COUNT(o.order_id), 0) AS PendingCount "
                + "    FROM Sale s "
                + "    LEFT JOIN Orders o ON s.sale_id = o.sale_id AND o.ostatus_id = 2 "
                + "    GROUP BY s.sale_id "
                + "), MinPendingCount AS ("
                + "    SELECT MIN(PendingCount) AS MinCount "
                + "    FROM SaleOrderCounts "
                + ") "
                + "SELECT TOP 1 sale_id "
                + // Lấy sale_id đầu tiên có số lượng đơn hàng ít nhất
                "FROM SaleOrderCounts "
                + "WHERE PendingCount = (SELECT MinCount FROM MinPendingCount);";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("sale_id"); // Trả về sale_id đầu tiên
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1; // Trả về -1 nếu không tìm thấy sale_id nào
    }

    public void updateProductHold(int productId, int orderedQuantity) {
        String updateQuery = "UPDATE Product SET hold = hold + ? WHERE product_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            // Set parameters
            preparedStatement.setInt(1, orderedQuantity);
            preparedStatement.setInt(2, productId);

            // Execute the update
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Product hold updated successfully for product ID: " + productId);
            } else {
                System.out.println("No product found with ID: " + productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<int[]> getSalesOrderSummary() throws SQLException {
        String sql = "SELECT sale_id, \n"
                + "       SUM(CASE WHEN status = 'pending' THEN 1 ELSE 0 END) AS totalPending, \n"
                + "       SUM(CASE WHEN status = 'submitted' THEN 1 ELSE 0 END) AS totalSubmitted, \n"
                + "       SUM(CASE WHEN status = 'success' THEN 1 ELSE 0 END) AS totalSuccess\n"
                + "FROM [Orders]  \n"
                + "GROUP BY sale_id";

        List<int[]> salesSummary = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int saleId = rs.getInt("sale_id");
                int totalPending = rs.getInt("totalPending");
                int totalSubmitted = rs.getInt("totalSubmitted");
                int totalSuccess = rs.getInt("totalSuccess");

                // Lưu trữ các giá trị này vào một mảng int
                salesSummary.add(new int[]{saleId, totalPending, totalSubmitted, totalSuccess});
            }
        }
        return salesSummary;
    }

    public List<Order> getAllByCustomerId(int customerId, int pageNumber, int pageSize, String sortColumn, String sortOrder, Date fromDate, Date toDate, String status, String searchQuery) {
        List<Order> orders = new ArrayList<>();

        // Calculate offset for pagination
        int offset = (pageNumber - 1) * pageSize;

        // Build SQL query
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.order_id, c.customer_id, c.customer_name, s.sale_id, s.sale_name, o.total_price, o.order_date, os.ostatus_id, os.ostatus_name, o.payment ")
                .append("FROM Orders o ")
                .append("JOIN Customer c ON c.customer_id = o.customer_id ")
                .append("JOIN Sale s ON s.sale_id = o.sale_id ")
                .append("JOIN Ostatus os ON os.ostatus_id = o.ostatus_id ")
                .append("WHERE 1=1 ");

        // Add filters
        if (customerId > 0) {
            sql.append("AND c.customer_id = ? ");
        }
        if (fromDate != null) {
            sql.append("AND o.order_date >= ? ");
        }
        if (toDate != null) {
            sql.append("AND o.order_date <= ? ");
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND os.ostatus_name = ? ");
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql.append("AND o.payment = ? ");
        }

        // Add sorting
        sql.append("ORDER BY ").append(sortColumn).append(" ").append(sortOrder).append(" ")
                .append("OFFSET ? ROWS ")
                .append("FETCH NEXT ? ROWS ONLY;");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Set filter parameters
            if (customerId > 0) {
                ps.setInt(paramIndex++, customerId);
            }
            if (fromDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(fromDate.getTime()));
            }
            if (toDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(toDate.getTime()));
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            if (searchQuery != null && !searchQuery.isEmpty()) {
                ps.setString(paramIndex++, searchQuery);
            }

            // Set pagination parameters
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    Customer cu = new Customer();
                    cu.setCustomerId(rs.getInt("customer_id"));
                    cu.setCustomerName(rs.getString("customer_name"));
                    order.setCustomerId(cu);
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setOrderDate(rs.getDate("order_date"));
                    Sale sale = new Sale();
                    sale.setSaleId(rs.getInt("sale_id"));
                    sale.setSaleName(rs.getString("sale_name"));
                    order.setSaleId(sale);
                    OStatus st = new OStatus(rs.getInt("ostatus_id"), rs.getString("ostatus_name"));
                    order.setOstatusId(st);
                    order.setPayment(rs.getString("payment"));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching orders: " + e.getMessage());
        }

        return orders;
    }

    public int getTotalOrderByCustomerId(int customerId, Date fromDate, Date toDate, String status, String searchQuery) {
        int count = 0;

        // Bắt đầu xây dựng câu SQL
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) AS totalOrder FROM Orders o ")
                .append("JOIN Customer c ON c.customer_id = o.customer_id ")
                .append("JOIN Ostatus os ON os.ostatus_id = o.ostatus_id ")
                .append("WHERE c.customer_id = ? "); // Điều kiện bắt buộc cho customer_id

        // Thêm điều kiện ngày tháng (nếu có)
        if (fromDate != null) {
            sql.append("AND o.order_date >= ? ");
        }
        if (toDate != null) {
            sql.append("AND o.order_date <= ? ");
        }

        // Thêm điều kiện trạng thái (nếu có)
        if (status != null && !status.isEmpty()) {
            sql.append("AND os.ostatus_name = ? ");
        }

        // Thêm điều kiện tìm kiếm (nếu có)
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql.append("AND (c.customer_name LIKE ? OR o.payment LIKE ?) ");
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Đặt giá trị cho customer_id
            ps.setInt(paramIndex++, customerId);

            // Đặt giá trị cho ngày tháng (nếu có)
            if (fromDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(fromDate.getTime()));
            }
            if (toDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(toDate.getTime()));
            }

            // Đặt giá trị cho trạng thái (nếu có)
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }

            // Đặt giá trị cho tìm kiếm (nếu có)
            if (searchQuery != null && !searchQuery.isEmpty()) {
                ps.setString(paramIndex++, "%" + searchQuery + "%"); // customer_name
                ps.setString(paramIndex++, "%" + searchQuery + "%"); // payment
            }

            // Thực thi truy vấn và lấy kết quả
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt("totalOrder");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public boolean decreaseProductHold(int orderId) {
        String sql = "UPDATE Product "
                + "SET Product.hold = Product.hold - od.quantity "
                + "FROM OrderDetails od "
                + "WHERE od.product_id = Product.product_id "
                + "AND od.order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true; // Successful update
            } else {
                return false; // No rows affected
            }
        } catch (SQLException e) {
            System.err.println("SQL error while updating product hold: " + e.getMessage());
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    public boolean updateProductOnDelivery(int orderId) {
        String sql = "UPDATE Product "
                + "SET Product.quantity = Product.quantity - od.quantity, "
                + "Product.hold = Product.hold - od.quantity "
                + "FROM OrderDetails od "
                + "WHERE od.product_id = Product.product_id "
                + "AND od.order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true; // Successful update
            } else {
                return false; // No rows affected
            }
        } catch (SQLException e) {
            System.err.println("SQL error while updating product on delivery: " + e.getMessage());
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    public boolean returnProductToStore(int orderId) {
        String sql = "UPDATE Product "
                + "SET Product.quantity = Product.quantity + od.quantity "
                + "FROM OrderDetails od "
                + "WHERE od.product_id = Product.product_id "
                + "AND od.order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return true; // Successful update
            } else {
                return false; // No rows affected
            }
        } catch (SQLException e) {
            System.err.println("SQL error while updating product quantity for returned order: " + e.getMessage());
            e.printStackTrace();
            return false; // Indicate failure
        }
    }

    // Ware house Start
    public List<Order> getAllOrdersWarehouse(int offset, int limit) {
        List<Order> orders = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.*, os.ostatus_name ")
                .append("FROM [Orders] o ")
                .append("JOIN [OStatus] os ON o.ostatus_id = os.ostatus_id ")
                .append("ORDER BY o.order_date DESC ")
                .append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));

                int sale_id = rs.getInt("sale_id");
                int customer_id = rs.getInt("customer_id");

                Sale sale = saleDao.getSaleById(sale_id);
                Customer customer = customerDao.getCustomerById(customer_id);
                order.setCustomerId(customer);
                order.setTotalPrice(rs.getBigDecimal("total_price"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setSaleId(sale);
                OStatus oStatus = new OStatus(rs.getInt("ostatus_id"), rs.getString("ostatus_name"));
                order.setOstatusId(oStatus);
                order.setPayment(rs.getString("payment"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public int getOrderCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS ordersCount FROM Orders";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("ordersCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getOrderCount1() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS ordersCount1 FROM Orders WHERE ostatus_id >= 3 OR ostatus_id = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("ordersCount1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getOrderCount4() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS ordersCount4 FROM Orders WHERE ostatus_id = 4";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("ordersCount4");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getOrderCount5() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS ordersCount5 FROM Orders WHERE ostatus_id = 5";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("ordersCount5");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getOrderCount6() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS ordersCount6 FROM Orders WHERE ostatus_id = 6";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("ordersCount6");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getOrderCount7() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS ordersCount7 FROM Orders WHERE ostatus_id = 7";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("ordersCount7");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getOrderCount8() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS ordersCount8 FROM Orders WHERE ostatus_id = 8";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("ordersCount8");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public int getOrderCount2() {
        int count = 0;
        String sql = "SELECT COUNT(*) AS ordersCount2 FROM Orders WHERE ostatus_id = 1";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt("ordersCount2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //Warehouse End
    public boolean assignSaleToOrder(int orderId, int saleId) {
        String sql = "UPDATE Orders SET sale_id = ? WHERE order_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, saleId);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getFilteredOrders(Date fromDate, Date toDate, String status, String searchQuery, int offset, int limit, String sortField, String sortOrder) {
        List<Order> orderList = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT o.*, os.ostatus_name, c.customer_name, s.sale_name ")
                .append("FROM [Orders] o ")
                .append("JOIN [OStatus] os ON o.ostatus_id = os.ostatus_id ")
                .append("JOIN [Customer] c ON o.customer_id = c.customer_id ")
                .append("JOIN [Sale] s ON o.sale_id = s.sale_id ")
                .append("WHERE 1=1 "); // Always true, used to append conditions

        // Add filter conditions dynamically
        if (fromDate != null) {
            sql.append("AND o.order_date >= ? ");
        }
        if (toDate != null) {
            sql.append("AND o.order_date <= ? ");
        }
        if (status != null && !status.isEmpty()) {
            sql.append("AND os.ostatus_name = ? ");
        }
        if (searchQuery != null && !searchQuery.isEmpty()) {
            sql.append("AND (c.customer_name LIKE ? OR s.sale_name LIKE ?) ");
        }

        // Add sorting
        sql.append("ORDER BY ").append(sortField != null ? sortField : "order_date").append(" ").append(sortOrder)
                .append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            // Set filter parameters dynamically
            if (fromDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(fromDate.getTime()));
            }
            if (toDate != null) {
                ps.setDate(paramIndex++, new java.sql.Date(toDate.getTime()));
            }
            if (status != null && !status.isEmpty()) {
                ps.setString(paramIndex++, status);
            }
            if (searchQuery != null && !searchQuery.isEmpty()) {
                ps.setString(paramIndex++, "%" + searchQuery + "%");
                ps.setString(paramIndex++, "%" + searchQuery + "%");
            }

            // Set pagination parameters
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, limit);

            // Execute query and process result set
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order order = new Order();
                    Sale sale = saleDao.getSaleById(rs.getInt("sale_id"));
                    Customer customer = customerDao.getCustomerById(rs.getInt("customer_id"));
                    OStatus ostatus = new OStatus(rs.getInt("ostatus_id"), rs.getString("ostatus_name"));

                    order.setOrderId(rs.getInt("order_id"));
                    order.setCustomerId(customer);
                    order.setSaleId(sale);
                    order.setOstatusId(ostatus);
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                    order.setOrderDate(rs.getDate("order_date"));
                    order.setPayment(rs.getString("payment"));

                    orderList.add(order);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderList;
    }

}
