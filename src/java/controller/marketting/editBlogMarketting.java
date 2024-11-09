/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.marketting;

import dal.BlogDAO;
import dal.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import model.Blog;
import java.util.ArrayList;
import java.util.List;
import model.Brand;
import model.Category;
@WebServlet(name = "editBlogMarketting", urlPatterns = {"/marketting/editBlogMarketting"})
@MultipartConfig // Add this annotation
public class editBlogMarketting extends HttpServlet {

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
            out.println("<title>Servlet editBlogMarketting</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet editBlogMarketting at " + request.getContextPath() + "</h1>");
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

        BlogDAO blogDAO = new BlogDAO();
        Blog blog = blogDAO.getBlogById(Integer.parseInt(id));  // Use the corresponding BlogDAO method

        // Check if the blog exists
        if (blog == null) {
            response.sendRedirect("/SWP391Gr5/marketting/listblogmarketting");  // Redirect if the blog is not found
            return;
        }
        // Set attribute để gửi sản phẩm qua JSP
        request.setAttribute("blog", blog);

        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> listCategory = categoryDAO.getAllCategories();

        request.setAttribute("listCategory", listCategory);

        request.getRequestDispatcher("/marketting/editBlog.jsp").forward(request, response);
    }

 protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    List<String> errors = new ArrayList<>();

    int blogId = Integer.parseInt(request.getParameter("blogId"));
    String title = request.getParameter("title");
    String description = request.getParameter("description");
    String briefInfo = request.getParameter("briefInfo");

    if (title == null || title.trim().isEmpty()) {
        errors.add("Title is required.");
    }
    if (description == null || description.trim().isEmpty()) {
        errors.add("Description is required.");
    }
    if (briefInfo == null || briefInfo.trim().isEmpty()) {
        errors.add("Brief info is required.");
    }

    Part imgPart = request.getPart("imgPath");
    String existingImgPath = request.getParameter("currentImgPath"); // Hidden input for the current image path

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

    int marketerId = Integer.parseInt(request.getParameter("marketerId"));
    String tags = request.getParameter("tags");
    String statusStr = request.getParameter("status");
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

    if (!errors.isEmpty()) {
        request.setAttribute("errors", errors);

        CategoryDAO categoryDAO = new CategoryDAO();
        List<Category> listCategory = categoryDAO.getAllCategories();
        request.setAttribute("listCategory", listCategory);

        request.getRequestDispatcher("/marketting/listblogmarketting").forward(request, response);
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

    java.sql.Date dateCreate = new java.sql.Date(System.currentTimeMillis());
    Blog blog = new Blog(
            blogId,
            title,
            description,
            dateCreate,
            imagePath,
            marketerId,
            status,
            categoryId,
            tags,
            briefInfo
    );

    BlogDAO blogDAO = new BlogDAO();
    blogDAO.updateBlog(blog);

    request.getSession().setAttribute("successMessage", "Blog updated successfully!");
    response.sendRedirect("/SWP391Gr5/marketting/listblogmarketting");
}


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
