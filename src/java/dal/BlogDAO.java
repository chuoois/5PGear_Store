/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import static dal.DBContext.getConnection;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import model.Blog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Category;

/**
 *
 * @author Bùi Khánh Linh
 */
public class BlogDAO extends DBContext {

    private final Connection connection;
    private final CategoryDAO categoryDAO;

    public BlogDAO() {
        this.connection = DBContext.getConnection();
        this.categoryDAO = new CategoryDAO();
    }

    public List getBlogHomepage() {
        List<Blog> list = new ArrayList<>();
        String sql = "SELECT TOP 3 * \n"
                + "FROM Blog \n"
                + "ORDER BY date_created DESC;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                int categoryId = rs.getInt("category_id");

                Category cate = categoryDAO.getCategoryById(categoryId);

                Blog blog = new Blog(rs.getInt("blog_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getDate("date_created"),
                        rs.getString("img_path"),
                        rs.getInt("marketer_id"),
                        rs.getString("status"),
                        rs.getInt("category_id"),
                        rs.getString("tags"),
                        rs.getString("brief_info")
                );
                list.add(blog);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public List getAllBlogs() {
        List<Blog> list = new ArrayList<>();
        String sql = "Select * from Blog where status =1 ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                int categoryId = rs.getInt("category_id");

                Category cate = categoryDAO.getCategoryById(categoryId);

                Blog blog = new Blog(rs.getInt("blog_id"),
                        rs.getString("title"),
                        rs.getString("descrption"),
                        rs.getDate("date_created"),
                        rs.getString("img_path"),
                        rs.getInt("marketer_id"),
                        rs.getString("status"),
                        rs.getInt("category_id"),
                        rs.getString("tags"),
                        rs.getString("brief_info"));
                list.add(blog);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public List getAllBlogs1() {
        List<Blog> list = new ArrayList<>();
        String sql = "Select * from Blog where status =1 ";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {

                int categoryId = rs.getInt("category_id");

                Category cate = categoryDAO.getCategoryById(categoryId);

                Blog blog = new Blog(rs.getInt("blog_id"),
                        rs.getString("title"),
                        rs.getString("descrption"),
                        rs.getDate("date_created"),
                        rs.getString("img_path"),
                        rs.getInt("marketer_id"),
                        rs.getString("status"),
                        rs.getInt("category_id"),
                        rs.getString("tags"),
                        rs.getString("brief_info"));
                list.add(blog);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return list;
    }

    public List<Blog> getBlogsByPage(int page, int blogsPerPage) {
        List<Blog> blogList = new ArrayList<>();

        // Câu truy vấn với OFFSET và FETCH NEXT
        String sql = "SELECT * FROM Blog where status =1 ORDER BY date_created DESC, blog_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Tính toán giá trị OFFSET dựa trên trang hiện tại
            int offset = (page - 1) * blogsPerPage;
            ps.setInt(1, offset);        // Đặt giá trị OFFSET
            ps.setInt(2, blogsPerPage);  // Đặt số lượng blog cần lấy (FETCH NEXT)

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setTitle(rs.getString("title"));
                blog.setDescription(rs.getString("description"));
                blog.setDateCreated(rs.getDate("date_created"));
                blog.setImgPath(rs.getString("img_path"));
                blog.setMarketerId(rs.getInt("marketer_id"));
                blog.setStatus(rs.getString("status"));
                blog.setCategoryId(rs.getInt("category_id"));
                blog.setTags(rs.getString("tags"));
                blog.setBrief_info(rs.getString("brief_info"));
                blogList.add(blog);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blogList;
    }

    public int getTotalBlogs() {
        String sql = "SELECT COUNT(*) FROM Blog";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<Blog> searchBlogsByTitle(String title) {
        List<Blog> blogList = new ArrayList<>();
        String sql = "SELECT * FROM Blog WHERE title LIKE ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + title + "%"); // Tìm kiếm với từ khóa chứa trong title
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setTitle(rs.getString("title"));
                blog.setDescription(rs.getString("description"));
                blog.setDateCreated(rs.getDate("date_created"));
                blog.setImgPath(rs.getString("img_path"));
                blog.setMarketerId(rs.getInt("marketer_id"));
                blog.setStatus(rs.getString("status"));
                blog.setCategoryId(rs.getInt("category_id"));
                blog.setTags(rs.getString("tags"));
                blog.setBrief_info(rs.getString("brief_info"));
                blogList.add(blog);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blogList;
    }

    public int getTotalBlogsByTitle(String title) {
        String sql = "SELECT COUNT(*) FROM Blog WHERE title LIKE ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<Blog> searchBlogsByTitleAndPage(String title, int page, int blogsPerPage) {
        List<Blog> blogList = new ArrayList<>();
        String sql = "SELECT * FROM Blog WHERE title LIKE ? ORDER BY date_created DESC, blog_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            int offset = (page - 1) * blogsPerPage;
            ps.setString(1, "%" + title + "%");
            ps.setInt(2, offset);
            ps.setInt(3, blogsPerPage);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setTitle(rs.getString("title"));
                blog.setBrief_info(rs.getString("brief_info"));
                blog.setImgPath(rs.getString("img_path"));
                blog.setDateCreated(rs.getDate("date_created"));
                blogList.add(blog);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blogList;
    }

    public boolean updateBlogStatus(int blogId, String newStatus) {
        String sql = "UPDATE [Blog] SET status = ? WHERE [blog_id] = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newStatus);
            st.setInt(2, blogId);

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public int getBlogsByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Blog WHERE category_id = ?";
        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public List<Blog> getBlogsByCategoryAndPage(int categoryId, int page, int blogsPerPage) {
        List<Blog> blogList = new ArrayList<>();
        String sql = "SELECT * FROM Blog WHERE category_id = ? ORDER BY date_created DESC, blog_id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            int offset = (page - 1) * blogsPerPage;
            ps.setInt(1, categoryId);
            ps.setInt(2, offset);
            ps.setInt(3, blogsPerPage);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setTitle(rs.getString("title"));
                blog.setBrief_info(rs.getString("brief_info"));
                blog.setImgPath(rs.getString("img_path"));
                blog.setDateCreated(rs.getDate("date_created"));
                blogList.add(blog);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blogList;
    }

    public Blog getBlogById(int blogId) {
        Blog blog = null;
        String sql = "SELECT * FROM Blog WHERE blog_id = ?";

        try (Connection conn = DBContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, blogId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                blog = new Blog();
                blog.setBlogId(rs.getInt("blog_id"));
                blog.setTitle(rs.getString("title"));
                blog.setDescription(rs.getString("description"));
                blog.setDateCreated(rs.getDate("date_created"));
                blog.setImgPath(rs.getString("img_path"));
                blog.setMarketerId(rs.getInt("marketer_id"));
                blog.setStatus(rs.getString("status"));
                blog.setCategoryId(rs.getInt("category_id"));
                blog.setTags(rs.getString("tags"));
                blog.setBrief_info(rs.getString("brief_info"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return blog;
    }

    public List<Blog> getFilteredBlogs(int marketingId, String search, String categoryId, String sort, String status) {
        List<Blog> blogList = new ArrayList<>();
        String sql = "SELECT * FROM Blog WHERE [marketer_id]=? and 1=1 ";

        boolean hasCategoryId = !categoryId.isEmpty();

        if (hasCategoryId) {
            sql += " AND category_id = " + categoryId;
        }
        if (search != null && !search.equals("")) {
            sql += " AND [title] like N'%" + search + "%'";
        }
        if (status != null && !status.isEmpty()) {
            sql += " AND status = " + status;
        }

        switch (sort) {
            case "blogId":
                sql += " ORDER BY [blog_id]";
                break;
            case "titleAZ":
                sql += " ORDER BY [title]";
                break;
            case "titleZA":
                sql += " ORDER BY [title] DESC";
                break;
            case "dateCreated":
                sql += " ORDER BY [date_created]";
                break;
            default:
                sql += " ORDER BY [blog_id]";
        }

        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, marketingId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category category = categoryDAO.getCategoryById(rs.getInt("category_id"));

                    Blog blog = new Blog(
                            rs.getInt("blog_id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("date_created"),
                            rs.getString("img_path"),
                            rs.getInt("marketer_id"),
                            rs.getString("status"),
                            rs.getInt("category_id"),
                            rs.getString("tags"),
                            rs.getString("brief_info")
                    );
                    blogList.add(blog);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return blogList;
    }

    public void createBlog(Blog blog) {
        String sql = "INSERT INTO Blog (title, description, date_created, img_path, marketer_id, status, category_id, tags, brief_info) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, blog.getTitle());
            st.setString(2, blog.getDescription());
            st.setDate(3, (Date) blog.getDateCreated());  // Assuming dateCreated is of type java.sql.Date
            st.setString(4, blog.getImgPath());
            st.setInt(5, blog.getMarketerId());
            st.setString(6, blog.getStatus());
            st.setInt(7, blog.getCategoryId());
            st.setString(8, blog.getTags());
            st.setString(9, blog.getBrief_info());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Creating blog failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBlog(Blog blog) {
        String sql = "UPDATE Blog SET "
                + "title = ?, "
                + "description = ?, "
                + "date_created = ?, "
                + "img_path = ?, "
                + "marketer_id = ?, "
                + "status = ?, "
                + "category_id = ?, "
                + "tags = ?, "
                + "brief_info = ? "
                + "WHERE blog_id = ?";  // Make sure to update the blog identified by its ID

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, blog.getTitle());
            st.setString(2, blog.getDescription());
            st.setDate(3, (Date) blog.getDateCreated()); // Assuming dateCreate is of type java.sql.Date
            st.setString(4, blog.getImgPath());
            st.setInt(5, blog.getMarketerId());
            st.setString(6, blog.getStatus());
            st.setInt(7, blog.getCategoryId());
            st.setString(8, blog.getTags());
            st.setString(9, blog.getBrief_info());
            st.setInt(10, blog.getBlogId()); // Update the blog based on its ID

            int rowsAffected = st.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Updating blog failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        BlogDAO blogDAO = new BlogDAO();
//
//        int page = 1; // Trang đầu tiên
//        int blogsPerPage = 2; // Ví dụ, hiển thị 2 bài viết trên mỗi trang
//
//        // Kiểm tra tổng số blog trước
//        int totalBlogs = blogDAO.getTotalBlogs();
//        int totalPages = (int) Math.ceil((double) totalBlogs / blogsPerPage);
//        System.out.println("Total blogs: " + totalBlogs);
//        System.out.println("Total pages: " + totalPages);
//
//        if (page <= totalPages) {
//            // Lấy blog cho trang hiện tại
//            List<Blog> list = blogDAO.getBlogsByPage(page, blogsPerPage);
//            System.out.println("Number of blogs returned: " + list.size());
//
//            // Duyệt qua danh sách blog
//            for (Blog blog : list) {
//                System.out.println(blog.toString());
//            }
//        } else {
//            System.out.println("Page number out of range.");
//        }

        // Các giá trị đầu vào để lọc sản phẩm
        String search = "";     // Ví dụ tìm kiếm theo từ khóa "laptop"
        String categoryId = "";      // Ví dụ lọc theo danh mục có ID là 2
        String sort = "";        // Ví dụ sắp xếp theo giá niêm yết

        // Gọi hàm getFilteredProducts và lấy danh sách sản phẩm
        List<Blog> filteredProducts = blogDAO.getFilteredBlogs(1, search, categoryId, sort, "1");

        // Kiểm tra và in ra danh sách sản phẩm kết quả
        if (filteredProducts.isEmpty()) {
            System.out.println("No products found for the given criteria.");
        } else {
            for (Blog product : filteredProducts) {
                System.out.println(product);
            }
        }

//        // Test the updateBlog method
        Blog blogToUpdate = new Blog();
        blogToUpdate.setBlogId(1); // Set the ID of the blog you want to update
        blogToUpdate.setTitle("Updated Title");
        blogToUpdate.setDescription("Updated Blog descriptions");
        blogToUpdate.setDateCreated(new java.sql.Date(System.currentTimeMillis())); // Set the current date
        blogToUpdate.setImgPath("assets/images/a1.jpg"); // Update the image path
        blogToUpdate.setMarketerId(1); // Set the marketer ID (make sure it exists in the corresponding table)
        blogToUpdate.setStatus("0"); // Set the status
        blogToUpdate.setCategoryId(1); // Make sure this ID exists in the Category table
        blogToUpdate.setTags("Updated, Example, Tags"); // Update tags
        blogToUpdate.setBrief_info("Powerful gaming laptop with high refresh rate screen."); // Update brief information

        // Call the updateBlog method
        try {
            blogDAO.updateBlog(blogToUpdate);
            System.out.println("Blog updated successfully.");
        } catch (Exception e) {
            System.out.println("Failed to update blog: " + e.getMessage());
        }
    }

}
