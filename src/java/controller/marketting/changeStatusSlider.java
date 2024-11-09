/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.marketting;

import dal.SliderDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Slider;

@WebServlet(name="changeStatusSlider", urlPatterns={"/marketting/changestatusslider"})
public class changeStatusSlider extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
            out.println("<title>Servlet changeStatusSlider</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet changeStatusSlider at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String sliderIdParam = request.getParameter("id");
    SliderDAO sliderDAO = new SliderDAO();

    // Capture the original query string
    String queryString = request.getQueryString();

    if (sliderIdParam != null) {
        int sliderId = Integer.parseInt(sliderIdParam);
        Slider slider = sliderDAO.getSliderById(sliderId); // Fetch the slider by ID

        if (slider != null) {
            String currentStatus = slider.getStatus();
            String newStatus = currentStatus.equals("1") ? "0" : "1"; // Toggle status
            slider.setStatus(newStatus);

            boolean success = sliderDAO.updateSliderStatus(sliderId, newStatus); // Update slider status

            if (success) {
                // Redirect back to the slider list with the original query string
                if (queryString != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("successMessage", "Slider status changed successfully!"); // Success message
                    response.sendRedirect("listSliderMarketting?" + queryString);
                } else {
                    response.sendRedirect("listSliderMarketting"); // Redirect without query string
                }
            } else {
                response.getWriter().write("Failed to update slider status."); // Error message
            }
        } else {
            response.getWriter().write("Slider not found."); // Slider not found
        }
    } else {
        response.getWriter().write("Slider ID is missing."); // Missing ID
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
        processRequest(request, response);
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
