package dal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import static dal.DBContext.getConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Product;
import model.Category;
import model.Brand;

public class ProductDAO extends DBContext {

    private final Connection connection;
    private final BrandDAO brandDao;
    private final CategoryDAO categoryDao;

    public ProductDAO() {
        this.connection = DBContext.getConnection();
        this.brandDao = new BrandDAO();
        this.categoryDao = new CategoryDAO();
    }

    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product where status = 1 ORDER BY product_id";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int brandId = rs.getInt("brand_id");
                int categoryId = rs.getInt("category_id");

                Brand brand = brandDao.getBrandById(brandId);
                Category category = categoryDao.getCategoryById(categoryId);

                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getInt("hold"),
                        rs.getString("description"),
                        rs.getString("img_path"),
                        brand,
                        category,
                        rs.getInt("release_year"),
                        rs.getBigDecimal("sale"),
                        rs.getInt("rate"),
                        rs.getInt("status"),
                        rs.getDate("date_create"),
                        rs.getBigDecimal("import_price")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Product> getAllProductsByPage(int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product where status = 1 ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int offset = (page - 1) * pageSize; // Tính toán OFFSET
            st.setInt(1, offset);
            st.setInt(2, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int brandId = rs.getInt("brand_id");
                    int categoryId = rs.getInt("category_id");

                    Brand brand = brandDao.getBrandById(brandId);
                    Category category = categoryDao.getCategoryById(categoryId);

                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getInt("hold"),
                            rs.getString("description"),
                            rs.getString("img_path"),
                            brand,
                            category,
                            rs.getInt("release_year"),
                            rs.getBigDecimal("sale"),
                            rs.getInt("rate"),
                            rs.getInt("status"),
                            rs.getDate("date_create"),
                            rs.getBigDecimal("import_price")
                    );
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Product> getLatestProducts() {
        // Danh sách chứa 4 sản phẩm mới nhất
        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 4 * FROM Product where status = 1 ORDER BY release_year DESC";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu từ bảng Brand và Category dựa trên brand_id và category_id
                int brandId = rs.getInt("brand_id");
                int categoryId = rs.getInt("category_id");

                // Truy vấn thông tin Brand
                Brand brand = brandDao.getBrandById(brandId);
                // Truy vấn thông tin Category
                Category category = categoryDao.getCategoryById(categoryId);

                // Tạo đối tượng Product với các thông tin đã lấy
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getInt("hold"),
                        rs.getString("description"),
                        rs.getString("img_path"),
                        brand,
                        category,
                        rs.getInt("release_year"),
                        rs.getBigDecimal("sale"),
                        rs.getInt("rate"),
                        rs.getInt("status"),
                        rs.getDate("date_create"),
                        rs.getBigDecimal("import_price")
                );
                list.add(product); // Thêm product vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Product> getProductsSortedCreateDateByPage(String order, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql;

        if ("oldest".equalsIgnoreCase(order)) {
            sql = "SELECT * FROM Product where status = 1 ORDER BY date_create ASC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        } else {
            sql = "SELECT * FROM Product where status = 1 ORDER BY date_create DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        }

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            int offset = (page - 1) * pageSize;
            st.setInt(1, offset);
            st.setInt(2, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int brandId = rs.getInt("brand_id");
                    int categoryId = rs.getInt("category_id");

                    Brand brand = brandDao.getBrandById(brandId);
                    Category category = categoryDao.getCategoryById(categoryId);

                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getInt("hold"),
                            rs.getString("description"),
                            rs.getString("img_path"),
                            brand,
                            category,
                            rs.getInt("release_year"),
                            rs.getBigDecimal("sale"),
                            rs.getInt("rate"),
                            rs.getInt("status"),
                            rs.getDate("date_create"),
                            rs.getBigDecimal("import_price")
                    );
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Product> getProductsByCategoryByPage(int categoryId, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE category_id = ? and status = 1 ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, categoryId);
            int offset = (page - 1) * pageSize;
            st.setInt(2, offset);
            st.setInt(3, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int brandId = rs.getInt("brand_id");

                    Brand brand = brandDao.getBrandById(brandId);
                    Category category = categoryDao.getCategoryById(categoryId);

                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getInt("hold"),
                            rs.getString("description"),
                            rs.getString("img_path"),
                            brand,
                            category,
                            rs.getInt("release_year"),
                            rs.getBigDecimal("sale"),
                            rs.getInt("rate"),
                            rs.getInt("status"),
                            rs.getDate("date_create"),
                            rs.getBigDecimal("import_price")
                    );
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Product> getProductsByBrandByPage(int brandId, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE brand_id = ? and status = 1 ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, brandId);
            int offset = (page - 1) * pageSize;
            st.setInt(2, offset);
            st.setInt(3, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int categoryId = rs.getInt("category_id");

                    Brand brand = brandDao.getBrandById(brandId);
                    Category category = categoryDao.getCategoryById(categoryId);

                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getInt("hold"),
                            rs.getString("description"),
                            rs.getString("img_path"),
                            brand,
                            category,
                            rs.getInt("release_year"),
                            rs.getBigDecimal("sale"),
                            rs.getInt("rate"),
                            rs.getInt("status"),
                            rs.getDate("date_create"),
                            rs.getBigDecimal("import_price")
                    );
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Product> searchProductsByNameByPage(String keyword, int page, int pageSize) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product WHERE product_name LIKE ? AND status = 1 ORDER BY product_id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, "%" + keyword + "%");
            int offset = (page - 1) * pageSize;
            st.setInt(2, offset);
            st.setInt(3, pageSize);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int brandId = rs.getInt("brand_id");
                    int categoryId = rs.getInt("category_id");

                    Brand brand = brandDao.getBrandById(brandId);
                    Category category = categoryDao.getCategoryById(categoryId);

                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getInt("hold"),
                            rs.getString("description"),
                            rs.getString("img_path"),
                            brand,
                            category,
                            rs.getInt("release_year"),
                            rs.getBigDecimal("sale"),
                            rs.getInt("rate"),
                            rs.getInt("status"),
                            rs.getDate("date_create"),
                            rs.getBigDecimal("import_price")
                    );
                    list.add(product);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Product> getProductLastest() {

        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 6 * \n"
                + "FROM Product \n"
                + "ORDER BY date_create DESC;";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu từ bảng Brand và Category dựa trên brand_id và category_id
                int brandId = rs.getInt("brand_id");
                int categoryId = rs.getInt("category_id");

                // Truy vấn thông tin Brand
                Brand brand = brandDao.getBrandById(brandId);
                // Truy vấn thông tin Category
                Category category = categoryDao.getCategoryById(categoryId);

                // Tạo đối tượng Product với các thông tin đã lấy
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getInt("hold"),
                        rs.getString("description"),
                        rs.getString("img_path"),
                        brand,
                        category,
                        rs.getInt("release_year"),
                        rs.getBigDecimal("sale"),
                        rs.getInt("rate"),
                        rs.getInt("status"),
                        rs.getDate("date_create"),
                        rs.getBigDecimal("import_price")
                );
                list.add(product); // Thêm product vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }

    public List<Product> getProductTopSale() {

        List<Product> list = new ArrayList<>();
        String sql = "SELECT TOP 6 * \n"
                + "FROM Product \n"
                + "ORDER BY sale DESC;";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu từ bảng Brand và Category dựa trên brand_id và category_id
                int brandId = rs.getInt("brand_id");
                int categoryId = rs.getInt("category_id");

                // Truy vấn thông tin Brand
                Brand brand = brandDao.getBrandById(brandId);
                // Truy vấn thông tin Category
                Category category = categoryDao.getCategoryById(categoryId);

                // Tạo đối tượng Product với các thông tin đã lấy
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity"),
                        rs.getInt("hold"),
                        rs.getString("description"),
                        rs.getString("img_path"),
                        brand,
                        category,
                        rs.getInt("release_year"),
                        rs.getBigDecimal("sale"),
                        rs.getInt("rate"),
                        rs.getInt("status"),
                        rs.getDate("date_create"),
                        rs.getBigDecimal("import_price")
                );
                list.add(product); // Thêm product vào danh sách
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return list;
    }
//    --------------------------------Mớiiiiiiiiiiii bên MARKETTING--------------------------------------------

    public List<Product> getFilteredProducts(String search, String categoryId, String sort, String status) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE 1=1 ";

        boolean hasCategoryId = !categoryId.isEmpty();

        if (hasCategoryId) {
            sql += " AND category_id = " + categoryId;
        }
        if (search != null && !search.equals("")) {
            sql += " AND [product_name] like N'%" + search + "%'";
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND status = " + status;
        }

        switch (sort) {
            case "productId":
                sql += " ORDER BY [product_id]";
                break;
            case "title":
                sql += " ORDER BY [product_name]";
                break;
            case "listPrice":
                sql += " ORDER BY (price-(price * sale /100))";
                break;
            case "salePrice":
                sql += " ORDER BY sale";
                break;

            default:
                sql += " ORDER BY [product_id]";
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Brand brand = new Brand(rs.getInt("brand_id"));

                    int catId = rs.getInt("category_id");
                    Category category = categoryDao.getCategoryById(catId);

                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getInt("hold"),
                            rs.getString("description"),
                            rs.getString("img_path"),
                            brand,
                            category,
                            rs.getInt("release_year"),
                            rs.getBigDecimal("sale"),
                            rs.getInt("rate"),
                            rs.getInt("status"),
                            rs.getDate("date_create"),
                            rs.getBigDecimal("import_price")
                    );
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public Product getProductById(int productId) {
        Product product = null;
        String sql = "SELECT * FROM Product WHERE product_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, productId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    int brandId = rs.getInt("brand_id");
                    int categoryId = rs.getInt("category_id");

                    Brand brand = brandDao.getBrandById(brandId);
                    Category category = categoryDao.getCategoryById(categoryId);

                    product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getInt("hold"),
                            rs.getString("description"),
                            rs.getString("img_path"),
                            brand,
                            category,
                            rs.getInt("release_year"),
                            rs.getBigDecimal("sale"),
                            rs.getInt("rate"),
                            rs.getInt("status"),
                            rs.getDate("date_create"),
                            rs.getBigDecimal("import_price")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }

        return product;
    }

    public boolean updateProduct(Product product) {
        String sql = "UPDATE Product SET product_name = ?, price = ?, quantity = ?,hold=?, description = ?, img_path = ?, brand_id = ?,"
                + " category_id = ?, release_year = ?, sale = ?, rate = ?, status = ?, date_create = ?, import_price = ? WHERE product_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, product.getProductName());
            st.setBigDecimal(2, product.getPrice());
            st.setInt(3, product.getQuantity());
            st.setInt(4, product.getHold());
            st.setString(5, product.getDescription());
            st.setString(6, product.getImgPath());
            st.setInt(7, product.getBrand().getBrandId()); // Assuming you have a Brand object in the Product
            st.setInt(8, product.getCategory().getCategoryId()); // Assuming you have a Category object in the Product
            st.setInt(9, product.getReleaseYear());
            st.setBigDecimal(10, product.getSale());
            st.setInt(11, product.getRate());
            st.setInt(12, product.getStatus());
            st.setDate(13, (Date) product.getDateCreate()); // Assuming dateCreate is of type java.sql.Date
            st.setBigDecimal(14, product.getImportPrice());
            st.setInt(15, product.getProductId());

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("SQL Exception in updateProduct: " + e.getMessage());
        }

        return false;
    }

    public boolean updateProductStatus(int productId, int newStatus) {
        String sql = "UPDATE product SET status = ? WHERE product_id = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, newStatus);
            st.setInt(2, productId);

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void createProduct(Product product) {
        String sql = "INSERT INTO product (product_name, price, quantity,hold, description, img_path, brand_id, category_id, release_year, sale, rate, status, date_create,import_price)"
                + " VALUES (?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, product.getProductName());
            st.setBigDecimal(2, product.getPrice());
            st.setInt(3, product.getQuantity());
            st.setInt(4, product.getHold());
            st.setString(5, product.getDescription());
            st.setString(6, product.getImgPath());
            st.setInt(7, product.getBrand().getBrandId()); // Assuming you have a Brand object in the Product
            st.setInt(8, product.getCategory().getCategoryId()); // Assuming you have a Category object in the Product
            st.setInt(9, product.getReleaseYear());
            st.setBigDecimal(10, product.getSale());
            st.setInt(11, product.getRate());
            st.setInt(12, product.getStatus());
            st.setDate(13, (Date) product.getDateCreate()); // Assuming dateCreate is of type java.sql.Date
            st.setBigDecimal(14, product.getImportPrice());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating product failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//-------------------------------ENDDDDDDDDDDDDDDDDD---------------------------------------------
    public List<Product> getFilteredProduct(String search, String categoryId, String sort, String brandId) {
        List<Product> productList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Product WHERE 1=1");

        // Sử dụng PreparedStatement để tránh SQL injection
        List<Object> params = new ArrayList<>();

        // Kiểm tra categoryId (String) và thêm vào điều kiện nếu hợp lệ
        if (categoryId != null && !categoryId.isEmpty()) {
            sql.append(" AND category_id = ?");
            params.add(categoryId);  // Truyền categoryId dưới dạng String
        }

        // Kiểm tra search (String) và thêm vào điều kiện nếu hợp lệ
        if (search != null && !search.isEmpty()) {
            sql.append(" AND product_name LIKE ?");
            params.add("%" + search + "%");
        }

        // Kiểm tra brand và thêm vào điều kiện nếu hợp lệ
        if (brandId != null && !brandId.isEmpty()) {
            sql.append(" AND brand_id = ?");
            params.add(brandId);  // Truyền status dưới dạng String
        }

        // Logic sắp xếp
        if (sort != null && !sort.isEmpty()) {
            switch (sort) {
                case "id" ->
                    sql.append(" ORDER BY product_id ASC");
                case "quantity" ->
                    sql.append(" ORDER BY quantity ASC");
                case "hold" ->
                    sql.append(" ORDER BY hold ASC");
                case "available" ->
                    sql.append(" ORDER BY (quantity - hold) ASC");
            }
        } else {
            sql.append(" ORDER BY product_id ASC");
        }

        try (PreparedStatement st = connection.prepareStatement(sql.toString())) {
            // Gắn tham số
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    int braId = rs.getInt("brand_id");
                    Brand brand = brandDao.getBrandById(braId);
                    int catId = rs.getInt("category_id");
                    Category category = categoryDao.getCategoryById(catId);

                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity"),
                            rs.getInt("hold"),
                            rs.getString("description"),
                            rs.getString("img_path"),
                            brand,
                            category,
                            rs.getInt("release_year"),
                            rs.getBigDecimal("sale"),
                            rs.getInt("rate"),
                            rs.getInt("status"),
                            rs.getDate("date_create"),
                            rs.getBigDecimal("import_price")
                    );
                    productList.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productList;
    }

    public void increaseProductQuantity(int productId, int increaseAmount) {
        // Câu lệnh SQL để cập nhật số lượng
        String updateSql = "UPDATE Product SET quantity = quantity + ? WHERE product_id = ?";

        try (PreparedStatement st = connection.prepareStatement(updateSql)) {
            // Gắn tham số
            st.setInt(1, increaseAmount);
            st.setInt(2, productId);

            // Thực thi câu lệnh UPDATE
            int rowsAffected = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public void importProduct(int productId, int increaseAmount, BigDecimal importPrice) {
        // Câu lệnh SQL để cập nhật số lượng và import price
        String updateSql = "UPDATE Product SET quantity = quantity + ?, import_price = ? WHERE product_id = ?";

        try (PreparedStatement st = connection.prepareStatement(updateSql)) {
            // Gắn tham số
            st.setInt(1, increaseAmount);
            st.setBigDecimal(2, importPrice);
            st.setInt(3, productId);

            // Thực thi câu lệnh UPDATE
            int rowsAffected = st.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Create an instance of ProductDAO
        ProductDAO productDAO = new ProductDAO();
        List<Product> product1 = productDAO.getAllProducts();
        for (Product product : product1) {
            System.out.println(product);
        }
//        System.out.println(product1);

        // Fetch all products from the database
//        List<Product> products = productDAO.getAllProducts();
//        for (Product product : products) {
//            System.out.println(product);
//        }
        // Check if the list is not empty
//        if (products.isEmpty()) {
//            System.out.println("No products found.");
//        } else {
//            // Iterate through the products and print their details
//            for (Product product : products) {
//                System.out.println("Product ID: " + product.getProductId());
//                System.out.println("Product Name: " + product.getProductName());
//                System.out.println("Price: " + product.getPrice());
//                System.out.println("Quantity: " + product.getQuantity());
//                System.out.println("Description: " + product.getDescription());
//                System.out.println("Image Path: " + product.getImgPath());
//                System.out.println("Brand: " + product.getBrand().getBrandName());
//                System.out.println("Category: " + product.getCategory().getCategoryName());
//                System.out.println("Release Year: " + product.getReleaseYear());
//                System.out.println("Rate: " + product.getRate());
//                System.out.println("Sale: " + product.getSale());
//                System.out.println("Date Created: " + product.getDateCreate());
//                System.out.println("--------------------------------------");
//            }
//        }
        // Các giá trị đầu vào để lọc sản phẩm
        String search = "";     // Ví dụ tìm kiếm theo từ khóa "laptop"
        String categoryId = "";      // Ví dụ lọc theo danh mục có ID là 2
        String sort = "";        // Ví dụ sắp xếp theo giá niêm yết

        // Gọi hàm getFilteredProducts và lấy danh sách sản phẩm
        List<Product> filteredProducts = productDAO.getFilteredProducts(search, categoryId, sort, "1");

        // Kiểm tra và in ra danh sách sản phẩm kết quả
        if (filteredProducts.isEmpty()) {
            System.out.println("No products found for the given criteria.");
        } else {
            for (Product product : filteredProducts) {
                System.out.println(product);
            }
        }
    }

}
