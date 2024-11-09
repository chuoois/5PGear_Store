package controller.marketting;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import model.Product;
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
import model.Brand;
import model.Category;

@WebServlet(name = "createProductMarketting", urlPatterns = {"/marketting/createProductMarketting"})
@MultipartConfig // Add this annotation
public class createProductMarketting extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Fetch all brands and categories to display in the form
//        BrandDAO brandDAO = new BrandDAO();
//        List<Brand> listBrand = brandDAO.getAllBrands();
//
//        CategoryDAO categoryDAO = new CategoryDAO();
//        List<Category> listCategory = categoryDAO.getAllCategories();
//
//        request.setAttribute("listBrand", listBrand);
//        request.setAttribute("listCategory", listCategory);
//
//        // Forward to the create product JSP page
//        request.getRequestDispatcher("/marketting/createProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve data from the form
        String productName = request.getParameter("productName");
        String priceStr = request.getParameter("price");
        String description = request.getParameter("description");
        Part imgPart = request.getPart("imgPath");
        String brandIdStr = request.getParameter("brand");
        String categoryIdStr = request.getParameter("category");
        String releaseYearStr = request.getParameter("releaseYear");
        String saleStr = request.getParameter("salePrice");
        String statusStr = request.getParameter("status");
        List<String> errors = new ArrayList<>();

        // Validation: Check if required fields are empty or not valid
        if (productName == null || productName.trim().isEmpty()) {
            errors.add("Product name is required.");
        }
       

        if (description == null || description.trim().isEmpty()) {
            errors.add("Description is required.");
        }

        int brandId = 0;
        try {
            if (brandIdStr != null && !brandIdStr.trim().isEmpty()) {
                brandId = Integer.parseInt(brandIdStr);
            } else {
                errors.add("Brand is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid brand selection.");
        }

        int categoryId = 0;
        try {
            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                categoryId = Integer.parseInt(categoryIdStr);
            } else {
                errors.add("Category is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid category selection.");
        }

        int releaseYear = 0;
        try {
            if (releaseYearStr != null && !releaseYearStr.trim().isEmpty()) {
                releaseYear = Integer.parseInt(releaseYearStr);
            } else {
                errors.add("Release year is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid release year format.");
        }

        int status = 0;
        try {
            if (statusStr != null && !statusStr.trim().isEmpty()) {
                status = Integer.parseInt(statusStr);
            } else {
                errors.add("Status is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid status format.");
        }

        // If there are validation errors, forward back to the form with errors
        // If there are validation errors
        if (!errors.isEmpty()) {
            // Set the errors attribute and forward back to the JSP
            request.setAttribute("errors", errors);

            // Fetch categories and brands again to populate the form
            BrandDAO brandDAO = new BrandDAO();
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Brand> listBrand = brandDAO.getAllBrands();
            List<Category> listCategory = categoryDAO.getAllCategories();
            request.setAttribute("listBrand", listBrand);
            request.setAttribute("listCategory", listCategory);

            // Forward back to the JSP (this will trigger the modal to reopen with errors)
            request.getRequestDispatcher("/marketting/listproductmarketting").forward(request, response);
            return;
        }

        String fileName = imgPart.getSubmittedFileName(); // Get the uploaded file name
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads"; // Define upload directory
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // Create the directory if it doesn't exist
        }
        imgPart.write(uploadPath + File.separator + fileName); // Save the file
        // Retrieve Brand and Category from DAO
        BrandDAO brandDAO = new BrandDAO();
        CategoryDAO categoryDAO = new CategoryDAO();
        Brand brand = brandDAO.getBrandById(brandId);
        Category category = categoryDAO.getCategoryById(categoryId);

        // Get current date for dateCreate
        java.sql.Date dateCreate = new java.sql.Date(System.currentTimeMillis());

        // Create Product object with provided data
        Product product = new Product(
                productName,
                BigDecimal.valueOf(0),
                0,
                0,
                description,
                "uploads/" + fileName,
                brand,
                category,
                releaseYear,
                BigDecimal.valueOf(0),
                5,
                status,
                dateCreate,
                BigDecimal.valueOf(0)
                
        );

        // Call DAO to save the new product in the database
        ProductDAO productDAO = new ProductDAO();
        productDAO.createProduct(product);

        // Set success message in session
        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Product created successfully!");

        // Redirect to the product management page after successful creation
        response.sendRedirect("/SWP391Gr5/marketting/listproductmarketting");
    }

}
