package controller.marketting;

import dal.BlogDAO;
import dal.CategoryDAO;
import dal.SaleDAO;
import model.Blog;
import model.Category;
import model.Marketing;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import model.Sale;

@WebServlet(name = "listBlogMarketting", urlPatterns = {"/marketting/listblogmarketting"})

public class listBlogMarketting extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Marketing marketer = (Marketing) session.getAttribute("marketer");

        // Check if the user is logged in
        if (marketer != null) {
            try {
                BlogDAO blogDAO = new BlogDAO();
                CategoryDAO categoryDAO = new CategoryDAO();
                SaleDAO saleDAO = new SaleDAO();

                // Get all categories
                List<Category> categories = categoryDAO.getAllCategories();
                request.setAttribute("listCategories", categories);
                
                List<Sale> listSale = saleDAO.getAllSales();
                request.setAttribute("listSale", listSale);

                // Get filtering parameters
                String search = request.getParameter("search");
                String categoryId = request.getParameter("category");
                String status = request.getParameter("status");
                String sort = request.getParameter("sort");

                // Initialize default values
                search = (search != null) ? search : "";
                categoryId = (categoryId != null) ? categoryId : "";
                status = (status != null) ? status : "";
                sort = (sort != null) ? sort : "title"; // Default sorting by title

                int marketingId = marketer.getMarketingId();
                // Get the filtered list of blogs
                List<Blog> filteredBlogs = blogDAO.getFilteredBlogs(marketingId,search, categoryId, sort, status);
                request.setAttribute("filteredBlogList", filteredBlogs);

                // Pagination
                String pageParam = request.getParameter("page");
                int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 3; // Number of blogs per page

                int totalFilteredBlogs = filteredBlogs.size();
                int totalPages = (int) Math.ceil((double) totalFilteredBlogs / pageSize);

                // Calculate start and end index for pagination
                int start = (page - 1) * pageSize;
                int end = Math.min(start + pageSize, totalFilteredBlogs);

                // Get blogs for the current page
                List<Blog> blogsForCurrentPage = filteredBlogs.subList(start, end);
                request.setAttribute("filteredBlogList", blogsForCurrentPage);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                // Set parameters back for the UI
                request.setAttribute("paramSearch", search);
                request.setAttribute("paramCategory", categoryId);
                request.setAttribute("paramStatus", status);
                request.setAttribute("paramSort", sort);

                // Forward to the JSP page
                request.getRequestDispatcher("/marketting/listBlog.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Redirect to login if the user is not logged in
            response.sendRedirect(request.getContextPath() + "/loginstaff.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for listing marketing blog posts with filtering, sorting, and pagination";
    }
}
