package controller.marketting;

import dal.BrandDAO;
import dal.CategoryDAO;
import dal.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Marketing;

import dal.StatisticsDAO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Brand;
import model.Category;
import model.Customer;
import model.Product;

@WebServlet(name = "HomeMarketting", urlPatterns = {"/marketting/home"})
public class HomeMarketting extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Marketing marketer = (Marketing) session.getAttribute("marketer");

        // Check if the user is logged in
        if (marketer != null) {
            try {
                StatisticsDAO statisticsDAO = new StatisticsDAO();
                // Xử lý khoảng thời gian
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String startDateParam = request.getParameter("startDate");
                String endDateParam = request.getParameter("endDate");

                Date endDate = (endDateParam != null && !endDateParam.isEmpty())
                        ? dateFormat.parse(endDateParam)
                        : new Date(); // Default to current date if endDate is not provided

                Date startDate;
                if (startDateParam != null && !startDateParam.isEmpty()) {
                    startDate = dateFormat.parse(startDateParam);
                } else {
                    // If startDate is not provided, default to 7 days before the endDate
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(endDate);
                    calendar.add(Calendar.DAY_OF_MONTH, -7); // Subtract 7 days from endDate
                    startDate = calendar.getTime();
                }

                java.sql.Date sqlStartDate = new java.sql.Date(startDate.getTime());
                java.sql.Date sqlEndDate = new java.sql.Date(endDate.getTime());
                request.setAttribute("sqlStartDate", sqlStartDate);
                request.setAttribute("sqlEndDate", sqlEndDate);

                int totalProduct = statisticsDAO.getTotalProductsByDate(sqlStartDate, sqlEndDate);
                int totalCustomers = statisticsDAO.getTotalCustomersByDate(sqlStartDate, sqlEndDate);
                int totalFeedbacks = statisticsDAO.getTotalFeedbacksByDate(sqlStartDate, sqlEndDate);
                int totalBlogs = statisticsDAO.getTotalBlogsByDate(sqlStartDate, sqlEndDate);
                request.setAttribute("totalProducts", totalProduct);
                request.setAttribute("totalCustomers", totalCustomers);
                request.setAttribute("totalFeedbacks", totalFeedbacks);
                request.setAttribute("totalBlogs", totalBlogs);
                request.getRequestDispatcher("/marketting/home.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
               
            }
        } else {
            // If the user is not logged in, redirect to the login page
            response.sendRedirect(request.getContextPath() + "loginstaff.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
