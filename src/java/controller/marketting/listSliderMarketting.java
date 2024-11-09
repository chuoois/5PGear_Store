package controller.marketting;

import dal.ProductDAO;
import dal.SliderDAO;  // Assume this DAO exists for retrieving sliders from the database
import model.Slider;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import model.Marketing;
import model.Product;
@WebServlet(name = "listSliderMarketting", urlPatterns = {"/marketting/listSliderMarketting"})
public class listSliderMarketting extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Marketing marketer = (Marketing) session.getAttribute("marketer");

        ProductDAO productDAO = new ProductDAO();
         List<Product> listproduct = productDAO.getAllProducts();
                request.setAttribute("listProduct", listproduct);
        // Check if the user is logged in
        if (marketer != null) {
            try {
                SliderDAO sliderDAO = new SliderDAO();

                // Get filtering parameters
                String search = request.getParameter("search");
                String status = request.getParameter("status");
                String sort = request.getParameter("sort");
                String productIdParam = request.getParameter("product"); 

                // Initialize default values
                search = (search != null) ? search : "";
                status = (status != null) ? status : "";
                productIdParam = (productIdParam != null) ? productIdParam: "";
                sort = (sort != null) ? sort : "slider_title"; // Default sorting by title
                
                int marketingId = marketer.getMarketingId();
                
                // Get the filtered list of sliders
                List<Slider> filteredSliders = sliderDAO.getFilteredSliders(marketingId, search, status, sort,productIdParam);
                request.setAttribute("filteredSliderList", filteredSliders);

                // Pagination
                String pageParam = request.getParameter("page");
                int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
                int pageSize = 3; // Number of sliders per page

                int totalFilteredSliders = filteredSliders.size();
                int totalPages = (int) Math.ceil((double) totalFilteredSliders / pageSize);

                // Calculate start and end index for pagination
                int start = (page - 1) * pageSize;
                int end = Math.min(start + pageSize, totalFilteredSliders);

                // Get sliders for the current page
                List<Slider> slidersForCurrentPage = filteredSliders.subList(start, end);
                request.setAttribute("filteredSliderList", slidersForCurrentPage);
                request.setAttribute("currentPage", page);
                request.setAttribute("totalPages", totalPages);

                // Set parameters back for the UI
                request.setAttribute("paramSearch", search);
                request.setAttribute("paramStatus", status);
                request.setAttribute("paramSort", sort);

                // Forward to the JSP page
                request.getRequestDispatcher("/marketting/listSlider.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Redirect to login if the user is not logged in
            response.sendRedirect(request.getContextPath() + "/loginstaff.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for listing marketing sliders with filtering, sorting, and pagination";
    }
}
