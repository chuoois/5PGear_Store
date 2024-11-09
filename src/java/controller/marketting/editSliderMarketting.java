/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketting;

import dal.ProductDAO;
import dal.SliderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Slider;
import model.Product;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.time.LocalDate;
import model.Marketing;

@WebServlet(name = "editSliderMarketting", urlPatterns = {"/marketting/editSliderMarketting"})
@MultipartConfig // Add this annotation
public class editSliderMarketting extends HttpServlet {

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
            out.println("<title>Servlet editSliderMarketting</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editSliderMarketting at " + request.getContextPath() + "</h1>");
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
        String id = request.getParameter("id");

        SliderDAO slideDAO = new SliderDAO();
        Slider slide = slideDAO.getSliderById(Integer.parseInt(id));  // Use the corresponding BlogDAO method

        // Check if the blog exists
        if (slide == null) {
            response.sendRedirect("/SWP391Gr5/marketting/listSliderMarketting");  // Redirect if the blog is not found
            return;
        }
        // Set attribute để gửi sản phẩm qua JSP
        request.setAttribute("slider", slide);

        ProductDAO productDAO = new ProductDAO();
        List<Product> listProduct = productDAO.getAllProducts();

        request.setAttribute("listProduct", listProduct);

        request.getRequestDispatcher("/marketting/editSlider.jsp").forward(request, response);
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

        HttpSession session = request.getSession();
        Marketing marketer = (Marketing) session.getAttribute("marketer");
        int marketingId = marketer.getMarketingId();

        List<String> errors = new ArrayList<>();

        int sliderId = Integer.parseInt(request.getParameter("sliderId"));
        String sliderTitle = request.getParameter("sliderTitle");

        // Check if required fields are empty
        if (sliderTitle == null || sliderTitle.trim().isEmpty()) {
            errors.add("Slider title is required.");
        }

        Part imgPart = request.getPart("imgPath");
        String existingImgPath = request.getParameter("currentImgPath"); // Hidden input for the current image path

        int productId = 0;
        String productIdStr = request.getParameter("productId");
        try {
            if (productIdStr != null && !productIdStr.trim().isEmpty()) {
                productId = Integer.parseInt(productIdStr);
            } else {
                errors.add("Product is required.");
            }
        } catch (NumberFormatException e) {
            errors.add("Invalid product selection.");
        }

        String statusStr = request.getParameter("status");

        String status = "0";
        if (statusStr != null && !statusStr.trim().isEmpty()) {
            status = statusStr;
        } else {
            errors.add("Status is required.");
        }

        int updateBy = 0;
        try {
            updateBy = Integer.parseInt(request.getParameter("updateBy"));
        } catch (NumberFormatException e) {
            errors.add("Invalid updated by format.");
        }

        // If there are errors, forward back to the form
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);

            ProductDAO productDAO = new ProductDAO();
            List<Product> listProduct = productDAO.getAllProducts();
            request.setAttribute("listProduct", listProduct);

            request.getRequestDispatcher("/marketting/editSliderMarketting.jsp").forward(request, response);
            return;
        }

        // Handling image file upload
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
        String dateCreateStr = request.getParameter("dateCreate");

        LocalDate localDate = LocalDate.parse(dateCreateStr);  // Assuming the date is in "yyyy-MM-dd" format
        java.sql.Date dateCreate = java.sql.Date.valueOf(localDate);
        java.sql.Date dateUpdate = new java.sql.Date(System.currentTimeMillis());
        // Create or update the slider object
        Slider slider = new Slider(sliderId,
                imagePath,
                null,
                status,
                sliderTitle,
                marketingId,
                dateCreate,
                dateUpdate,
                marketingId,
                productId// Update time with current timestamp
        );

        SliderDAO sliderDAO = new SliderDAO();
        sliderDAO.updateSlider(slider);

        // Redirect or forward with success message
        request.getSession().setAttribute("successMessage", "Slider updated successfully!");
        response.sendRedirect("/SWP391Gr5/marketting/listSliderMarketting");
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
