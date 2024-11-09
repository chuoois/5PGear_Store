/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.marketting;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import dal.StatisticsDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Brand;
import model.Category;
import model.Marketing;
import model.Product;

@WebServlet(name="listProductMarketting", urlPatterns={"/marketting/listproductmarketting"})
public class listProductMarketting extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Marketing marketer = (Marketing) session.getAttribute("marketer");

        // Check if the user is logged in
        if (marketer != null) {
            try {
//                List<Customer> newCustomersTrend = statisticsDAO.getNewCustomersByDay(sqlStartDate, sqlEndDate);
//                request.setAttribute("newCustomersTrend", newCustomersTrend.size());
                // Lấy danh sách sản phẩm
                ProductDAO productDAO = new ProductDAO();
                BrandDAO brandDAO = new BrandDAO();
                List<Brand> listBrand = brandDAO.getAllBrands();
                CategoryDAO categoryDAO = new CategoryDAO();
                List<Product> allProducts = productDAO.getAllProducts(); // Lấy tất cả sản phẩm

                // Lấy các giá trị lọc
                String search = request.getParameter("search");
                String categoryId = request.getParameter("category");
                String status = request.getParameter("status");
                String sort = request.getParameter("sort");

                // Khởi tạo các giá trị mặc định
                search = (search != null) ? search : "";
                categoryId = (categoryId != null) ? categoryId : "";
                status = (status != null) ? status : "";
                sort = (sort != null) ? sort : "title"; // Mặc định sắp xếp theo tiêu đề
                // Lấy danh sách sản phẩm đã lọc
                List<Product> filteredProducts = productDAO.getFilteredProducts(search, categoryId, sort, status);
                request.setAttribute("filteredProductList", filteredProducts);

                // Lấy danh sách danh mục và thương hiêu
                List<Category> categories = categoryDAO.getAllCategories();
                request.setAttribute("listCategories", categories);
                 request.setAttribute("listBrand", listBrand);
                request.setAttribute("paramSearch", search);
                request.setAttribute("paramCategory", categoryId);
                request.setAttribute("paramStatus", status);
                request.setAttribute("paramSort", sort);

                // Phân trang
                String pageParam = request.getParameter("page");
                int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 5; // Số sản phẩm mỗi trang

                // Tính toán số lượng sản phẩm và số trang
                int totalFilteredProducts = filteredProducts.size();
                int totalPages = (int) Math.ceil((double) totalFilteredProducts / pageSize);

                // Tính toán chỉ số bắt đầu và kết thúc cho sản phẩm hiện tại
                int start = (page - 1) * pageSize;
                int end = Math.min(start + pageSize, totalFilteredProducts);

                // Lấy danh sách sản phẩm cho trang hiện tại
                List<Product> productsForCurrentPage = filteredProducts.subList(start, end);
                request.setAttribute("filteredProductList", productsForCurrentPage);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                // Chuyển hướng đến JSP
                request.getRequestDispatcher("/marketting/listProduct.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
               
            }
        } else {
            // If the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "loginstaff.jsp");
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
