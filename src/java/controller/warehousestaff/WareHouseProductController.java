/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.warehousestaff;

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
import model.Product;
import model.WarehouseStaff;
import java.math.BigDecimal;

/**
 *
 * @author acer
 */
public class WareHouseProductController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet WareHouseProduct</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet WareHouseProduct at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        WarehouseStaff wareStaff = (WarehouseStaff) session.getAttribute("waleStaff");

        // Check if the user is logged in
        if (wareStaff != null) {
            try {

                ProductDAO productDAO = new ProductDAO();
                BrandDAO brandDAO = new BrandDAO();
                List<Brand> listBrand = brandDAO.getAllBrands();
                CategoryDAO categoryDAO = new CategoryDAO();

                // Lấy các giá trị lọc
                String search = request.getParameter("search");
                String categoryId = request.getParameter("category");
                String brandId = request.getParameter("brand");
                String sort = request.getParameter("sort");

                // Khởi tạo các giá trị mặc định
                search = (search != null) ? search : "";
                categoryId = (categoryId != null) ? categoryId : "";
                brandId = (brandId != null) ? brandId : "";
                sort = (sort != null) ? sort : "";
                // Lấy danh sách sản phẩm đã lọc
                List<Product> filteredProducts = productDAO.getFilteredProduct(search, categoryId, sort, brandId);
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
                List<Product> productsForCurrentPage = filteredProducts.subList(start, end);
                request.setAttribute("filteredProductList", productsForCurrentPage);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                // Lấy danh sách danh mục và thương hiêu
                List<Category> categories = categoryDAO.getAllCategories();
                request.setAttribute("listCategories", categories);
                request.setAttribute("listBrand", listBrand);
                request.setAttribute("paramSearch", search);
                request.setAttribute("paramCategory", categoryId);
                request.setAttribute("paramBrand", brandId);
                request.setAttribute("paramSort", sort);

                request.getRequestDispatcher("warehouseproductlist.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();

            }
        } else {
            // If the user is not logged in, redirect to the login page
            response.sendRedirect("loginstaff.jsp");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Lấy tham số từ request
            String productid = request.getParameter("productId");
            String quantity = request.getParameter("quantity");
            String importPrice = request.getParameter("importPrice");

            ProductDAO productDAO = new ProductDAO();

            int parsedProductId = Integer.parseInt(productid);
            int parsedQuantity = Integer.parseInt(quantity);

            if (importPrice != null && !importPrice.trim().isEmpty()) {
                BigDecimal price = new BigDecimal(importPrice);
                productDAO.importProduct(parsedProductId, parsedQuantity, price);
            } else {
                productDAO.increaseProductQuantity(parsedProductId, parsedQuantity);
            }

            // Chuyển hướng về trang warehouseproduct
            response.sendRedirect("warehouseproduct");
        } catch (NumberFormatException e) {
            // Log lỗi chuyển đổi số và thông báo lỗi cho người dùng
            System.out.println("Invalid number format: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input format.");
        } 
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
