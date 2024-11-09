package controller.home;

import dal.BlogDAO;
import dal.CategoryDAO;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Blog;
import model.Category;

public class BlogController extends HttpServlet {

    private static final int BLOGS_PER_PAGE = 2; // Số bài viết mỗi trang

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Nhận từ khóa tìm kiếm từ request (nếu có)
        String query = request.getParameter("query");

        // Nhận categoryId từ request (nếu có)
        String categoryIdParam = request.getParameter("categoryId");
        int categoryId = 0;
        if (categoryIdParam != null && !categoryIdParam.isEmpty()) {
            categoryId = Integer.parseInt(categoryIdParam);
        }

        // Lấy trang hiện tại (nếu không có thì mặc định là trang 1)
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        // Lấy danh sách danh mục để hiển thị bên sidebar
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> categoryList = categoryDAO.getAllCategories();
        request.setAttribute("categoryList", categoryList);

        BlogDAO blogDAO = new BlogDAO();
        List<Blog> blogList;
        int totalBlogs;
        int totalPages;

        if (categoryId != 0) {
            // Nếu có categoryId, lọc blog theo danh mục
            totalBlogs = blogDAO.getBlogsByCategory(categoryId);
            totalPages = (int) Math.ceil((double) totalBlogs / BLOGS_PER_PAGE);

            // Lấy danh sách blog theo trang hiện tại và categoryId
            blogList = blogDAO.getBlogsByCategoryAndPage(categoryId, page, BLOGS_PER_PAGE);
        } else if (query != null && !query.isEmpty()) {
            // Nếu có từ khóa tìm kiếm, tính tổng số blog theo từ khóa
            totalBlogs = blogDAO.getTotalBlogsByTitle(query);
            totalPages = (int) Math.ceil((double) totalBlogs / BLOGS_PER_PAGE);

            // Lấy danh sách blog theo trang hiện tại và từ khóa
            blogList = blogDAO.searchBlogsByTitleAndPage(query, page, BLOGS_PER_PAGE);
        } else {
            // Nếu không có từ khóa tìm kiếm và categoryId, hiển thị tất cả blog
            totalBlogs = blogDAO.getTotalBlogs();
            totalPages = (int) Math.ceil((double) totalBlogs / BLOGS_PER_PAGE);

            // Lấy danh sách blog theo trang hiện tại
            blogList = blogDAO.getBlogsByPage(page, BLOGS_PER_PAGE);
        }

        // Gửi dữ liệu blogList và phân trang sang JSP
        request.setAttribute("blogList", blogList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Gửi categoryId nếu người dùng đang lọc theo danh mục
        if (categoryId != 0) {
            request.setAttribute("categoryId", categoryId);
        }

        // Chuyển tiếp dữ liệu sang blog.jsp
        request.getRequestDispatcher("blog.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý POST request bằng cách chuyển sang GET
        doGet(request, response);
    }
}
