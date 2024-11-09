package controller.marketting;

import dal.BlogDAO;
import model.Blog;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "createBlogMarketting", urlPatterns = {"/marketting/createBlogMarketting"})
@MultipartConfig // Add this annotation
public class createBlogMarketting extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve form data
        String blogTitle = request.getParameter("blogTitle");
        String categoryIdStr = request.getParameter("category");
        String description = request.getParameter("description");
        String briefInfo = request.getParameter("briefInfo");
//        String imgPath = request.getParameter("imgPath");
        Part imgPart = request.getPart("imgPath");
        String tags = request.getParameter("tags");
        String statusStr = request.getParameter("status");
        String marketerIdStr = request.getParameter("marketerId");

        List<String> errors = new ArrayList<>();

        // Validation
        if (blogTitle == null || blogTitle.trim().isEmpty()) {
            errors.add("Blog title is required.");
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

        if (description == null || description.trim().isEmpty()) {
            errors.add("Description is required.");
        }

        if (briefInfo == null || briefInfo.trim().isEmpty()) {
            errors.add("Brief info is required.");
        }

        int marketerId = 0;
        try {
            if (marketerIdStr != null && !marketerIdStr.trim().isEmpty()) {
                marketerId = Integer.parseInt(marketerIdStr);
            } else {
                errors.add("Marketer ID is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid marketer ID.");
        }

        String status = "0";
        try {
            if (statusStr != null && !statusStr.trim().isEmpty()) {   
                status = statusStr;
            } else {
                errors.add("Status is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid status format.");
        }

        // If there are validation errors, forward back to the form
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/marketting/listblogmarketting").forward(request, response);
            return;
        }
        
        String fileName = imgPart.getSubmittedFileName(); // Get the uploaded file name
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads"; // Define upload directory
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // Create the directory if it doesn't exist
        }
        imgPart.write(uploadPath + File.separator + fileName); // Save the file

        // Get current date for blog creation
        java.sql.Date dateCreate = new java.sql.Date(System.currentTimeMillis());

        // Create Blog object
        Blog blog = new Blog(
                blogTitle,
                description,
                 dateCreate,               
               "uploads/" + fileName,
                marketerId,
                status,               
                categoryId,
                tags,
                briefInfo
               
        );

        // Call DAO to save the new blog in the database
        BlogDAO blogDAO = new BlogDAO();
        blogDAO.createBlog(blog);

        // Set success message in session
        HttpSession session = request.getSession();
        session.setAttribute("successMessage", "Blog created successfully!");

        // Redirect to the blog management page after successful creation
        response.sendRedirect("/SWP391Gr5/marketting/listblogmarketting");
    }
}
