package controller.marketting;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import model.Product;
import model.Brand;
import model.Category;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "editProductMarketting", urlPatterns = {"/marketting/editProductMarketting"})
@MultipartConfig
public class editProductMarketting extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        // Lấy thông tin sản phẩm dựa vào productId
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductById(Integer.parseInt(id));

        // Set attribute để gửi sản phẩm qua JSP
        request.setAttribute("product", product);
        
        // Lấy danh sách các thương hiệu và danh mục
        BrandDAO brandDAO = new BrandDAO();
        List<Brand> listBrand = brandDAO.getAllBrands();

        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> listCategory = categoryDAO.getAllCategories();

        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listCategory", listCategory);

        request.getRequestDispatcher("/marketting/editProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<>();

        // Retrieve form data
        int productId = Integer.parseInt(request.getParameter("productId"));
        String productName = request.getParameter("productName");

        // Price validation
        BigDecimal price = null;
        String priceStr = request.getParameter("price");
        try {
            if (priceStr != null && !priceStr.trim().isEmpty()) {
                price = new BigDecimal(priceStr);
                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    errors.add("Price must be greater than 0.");
                }
            } else {
                errors.add("Price is required and must be a valid number.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid price format.");
        }

        // Lấy thông tin sản phẩm hiện tại để lấy giá nhập khẩu (importPrice)
        ProductDAO productDAO = new ProductDAO();
        Product oldproduct = productDAO.getProductById(productId);
        BigDecimal importPrice = oldproduct.getImportPrice();

        // So sánh Price với importPrice
        if (price != null && price.compareTo(importPrice) < 0) {
            errors.add("Price must be greater than or equal to the import price.");
        }

        // Description validation
        String description = request.getParameter("description");
        if (description == null || description.trim().isEmpty()) {
            errors.add("Description is required.");
        }

        // Image upload
        Part imgPart = request.getPart("imgPath");
        String existingImgPath = request.getParameter("currentImgPath");

        // Brand validation
        int brandId = 0;
        String brandIdStr = request.getParameter("brandId");
        try {
            if (brandIdStr != null && !brandIdStr.trim().isEmpty()) {
                brandId = Integer.parseInt(brandIdStr);
            } else {
                errors.add("Brand is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid brand selection.");
        }

        // Category validation
        int categoryId = 0;
        String categoryIdStr = request.getParameter("categoryId");
        try {
            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                categoryId = Integer.parseInt(categoryIdStr);
            } else {
                errors.add("Category is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid category selection.");
        }

        // Release year validation
        int releaseYear = 0;
        String releaseYearStr = request.getParameter("releaseYear");
        try {
            if (releaseYearStr != null && !releaseYearStr.trim().isEmpty()) {
                releaseYear = Integer.parseInt(releaseYearStr);
            } else {
                errors.add("Release year is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid release year format.");
        }

        // Sale price validation
        BigDecimal sale = BigDecimal.ZERO;
        String saleStr = request.getParameter("sale");
        try {
            if (saleStr != null && !saleStr.trim().isEmpty()) {
                sale = new BigDecimal(saleStr);
                if (sale.compareTo(BigDecimal.ZERO) < 0) {
                    errors.add("Sale price cannot be negative.");
                }
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid sale price format.");
        }

        // Rate and status validation
        int rate = Integer.parseInt(request.getParameter("rate"));
        int status = Integer.parseInt(request.getParameter("status"));

        // Lấy danh sách thương hiệu và danh mục để hiển thị lại khi có lỗi
        BrandDAO brandDAO = new BrandDAO();
        List<Brand> listBrand = brandDAO.getAllBrands();
        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> listCategory = categoryDAO.getAllCategories();

        request.setAttribute("listBrand", listBrand);
        request.setAttribute("listCategory", listCategory);

        // Kiểm tra nếu có lỗi
        if (!errors.isEmpty()) {
            // Set attributes to keep the form data
            request.setAttribute("errors", errors);
            request.setAttribute("productName", productName);
            request.setAttribute("price", price);
            request.setAttribute("importPrice", importPrice);
            request.setAttribute("description", description);
            request.setAttribute("brandId", brandId);
            request.setAttribute("categoryId", categoryId);
            request.setAttribute("releaseYear", releaseYear);
            request.setAttribute("sale", sale);
            request.setAttribute("rate", rate);
            request.setAttribute("status", status);

            request.getRequestDispatcher("/marketting/editProduct.jsp").forward(request, response);
            return;
        }

        String fileName = imgPart != null && imgPart.getSize() > 0 ? imgPart.getSubmittedFileName() : null;
        String imagePath;

        if (fileName != null) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            imgPart.write(uploadPath + File.separator + fileName);
            imagePath = "uploads/" + fileName;
        } else {
            imagePath = existingImgPath; // Use existing image path if no new image is uploaded
        }

        // Tạo đối tượng Product và cập nhật sản phẩm
        Product product = new Product(
                productId,
                productName,
                price,
                oldproduct.getQuantity(),
                oldproduct.getHold(),
                description,
                imagePath,
                brandDAO.getBrandById(brandId),
                categoryDAO.getCategoryById(categoryId),
                releaseYear,
                sale,
                rate,
                status,
                new java.sql.Date(System.currentTimeMillis()),
                oldproduct.getImportPrice()
        );

        // Cập nhật sản phẩm vào cơ sở dữ liệu
        productDAO.updateProduct(product);

        // Set success message and redirect
        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Product updated successfully!");
        response.sendRedirect("/SWP391Gr5/marketting/listproductmarketting");
    }
}
