package controller.marketting;

import dal.SliderDAO;
import model.Slider;
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
import model.Marketing;

@WebServlet(name = "createSliderMarketting", urlPatterns = {"/marketting/createSliderMarketting"})
@MultipartConfig // Add this annotation to handle file uploads
public class createSliderMarketting extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Marketing marketer = (Marketing) session.getAttribute("marketer");
        int marketingId = marketer.getMarketingId();
        // Retrieve form data
        String sliderTitle = request.getParameter("sliderTitle");
        String productIdStr = request.getParameter("product");
        String statusStr = request.getParameter("status");

        Part imgPart = request.getPart("sliderImage"); // Handle the uploaded file
        List<String> errors = new ArrayList<>();

        // Validation
        if (sliderTitle == null || sliderTitle.trim().isEmpty()) {
            errors.add("Slider title is required.");
        }

        int productId = 0;
        try {
            if (productIdStr != null && !productIdStr.trim().isEmpty()) {
                productId = Integer.parseInt(productIdStr);
            } else {
                errors.add("Product is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid product selection.");
        }


        String status = "0"; // Default status
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
            request.getRequestDispatcher("/marketting/createSliderMarketting").forward(request, response);
            return;
        }

        // Handle file upload
        String fileName = imgPart.getSubmittedFileName(); // Get the uploaded file name
        String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads"; // Define upload directory
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir(); // Create the directory if it doesn't exist
        }
        imgPart.write(uploadPath + File.separator + fileName); // Save the file

        java.sql.Date dateCreate = new java.sql.Date(System.currentTimeMillis());
        java.sql.Date dateUpdate = null;
        // Create Slider object
        Slider slider = new Slider( "uploads/" + fileName,
                null, 
                status,
                sliderTitle,
                marketingId,
                dateCreate,
                dateUpdate,
                marketingId,
                productId);

        // Call DAO to save the new slider in the database
        SliderDAO sliderDAO = new SliderDAO();
        sliderDAO.addSlider(slider); // Implement this method in your SliderDAO

        // Set success message in session
        session.setAttribute("successMessage", "Slider created successfully!");

        // Redirect to the slider management page after successful creation
        response.sendRedirect("/SWP391Gr5/marketting/listSliderMarketting");
    }
}
