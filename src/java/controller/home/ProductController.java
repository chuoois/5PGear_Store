/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import dal.BrandDAO;
import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dal.ProductDAO;

import model.Product;
import model.Brand;
import model.Category;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số phân trang
        String pageStr = request.getParameter("page");   
        int page = 1; // mặc định trang 1
        int pageSize = 9; // mặc định mỗi trang có 10 sản phẩm

        if (pageStr != null) {
            page = Integer.parseInt(pageStr);
        }      
        ProductDAO productDAO = new ProductDAO();

        BrandDAO brand = new BrandDAO();
        CategoryDAO category = new CategoryDAO();
        List<Product> productlist = productDAO.getAllProducts(); // Danh sách tất cả sản phẩm
        List<Product> productlatest = productDAO.getLatestProducts(); // Danh sách sản phẩm mới nhất
        List<Brand> brandlist = brand.getAllBrands(); // Danh sách tất cả các thương hiệu
        List<Category> categorylist = category.getAllCategories(); // Danh sách tất cả các loại sản phẩm
        Map<Integer, Integer> brandProductCount = new HashMap<>();
        Map<Integer, Integer> categoryProductCount = new HashMap<>();

        // Tính số lượng sản phẩm cho mỗi thương hiệu
        for (Product product1 : productlist) {
            int brandId = product1.getBrand().getBrandId();
            int categoryId = product1.getCategory().getCategoryId();
            brandProductCount.put(brandId, brandProductCount.getOrDefault(brandId, 0) + 1);
            categoryProductCount.put(categoryId, categoryProductCount.getOrDefault(categoryId, 0) + 1);
        }
        // Lấy các tham số khác (sort, categoryId, brandId, search)
        String sort = request.getParameter("sort");
        String cateIdStr = request.getParameter("categoryId");
        String brandIdStr = request.getParameter("brandId");
        String search = request.getParameter("search");

        int totalProducts = 0;
        List<Product> products;
        // Gọi phương thức với tham số tương ứng
        if (sort != null && !sort.isEmpty()) {
            if ("oldest".equalsIgnoreCase(sort)) {
                products = productDAO.getProductsSortedCreateDateByPage("oldest", page, pageSize);
            } else {
                products = productDAO.getProductsSortedCreateDateByPage("latest", page, pageSize);
            }
            totalProducts = productlist.size();
        } else if (cateIdStr != null && !cateIdStr.isEmpty()) {
            int cateId = Integer.parseInt(cateIdStr);
            products = productDAO.getProductsByCategoryByPage(cateId, page, pageSize);
            totalProducts = categoryProductCount.get(cateId);
        } else if (brandIdStr != null && !brandIdStr.isEmpty()) {
            int brandId = Integer.parseInt(brandIdStr);
            products = productDAO.getProductsByBrandByPage(brandId, page, pageSize);
            totalProducts = brandProductCount.get(brandId);
        } else if (search != null && !search.isEmpty()) {
            products = productDAO.searchProductsByNameByPage(search, page, pageSize);
            totalProducts = products.size();
        } else {
            products = productDAO.getAllProductsByPage(page, pageSize);
            totalProducts = productlist.size();
        }
        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) totalProducts / pageSize);

        // Đặt thuộc tính lên request
        request.setAttribute("plist", products);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("pageSize", pageSize);

        // Các phần khác vẫn giữ nguyên
        request.setAttribute("pllist", productlatest);
        request.setAttribute("brandProductCount", brandProductCount);
        request.setAttribute("blist", brandlist);
        request.setAttribute("categoryProductCount", categoryProductCount);
        request.setAttribute("clist", categorylist);

        // Chuyển tiếp sang JSP
        request.getRequestDispatcher("productlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


