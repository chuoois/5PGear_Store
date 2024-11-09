package controller.admin;

import dal.CustomerDAO;
import dal.SaleDAO;
import dal.SaleManagerDAO;
import dal.MarketingDAO;
import dal.RoleDAO;
import dal.WarehouseStaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.Sale;
import model.SaleManager;
import model.Marketing;
import model.WarehouseStaff;

@WebServlet(name = "UserListController", urlPatterns = {"/userlist"})
public class UserListController extends HttpServlet {

    private static final int RECORDS_PER_PAGE = 5;
    private CustomerDAO customerDao = new CustomerDAO();
    private SaleDAO saleDao = new SaleDAO();
    private SaleManagerDAO saleManagerDao = new SaleManagerDAO();
    private MarketingDAO marketingDao = new MarketingDAO();
    private RoleDAO roleDao = new RoleDAO();
    private WarehouseStaffDAO warehouseStaffDao = new WarehouseStaffDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }
        int offset = (page - 1) * RECORDS_PER_PAGE;

        // Retrieve filter parameters
        String gender = request.getParameter("gender");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        String type = request.getParameter("type");

        // Retrieve sort parameters
        String sortField = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        if (sortOrder == null) {
            sortOrder = "ASC"; // Default sort order
        }

        // Get total counts for all user types
        int userCount2 = customerDao.getCustomerCount();
        int userCount3 = saleDao.getSaleCount();
        int userCount4 = saleManagerDao.getSaleManagerCount();
        int userCount5 = marketingDao.getMarketingCount();
        int userCount6 = warehouseStaffDao.getWaleStaffCount();
        int totalUsers = userCount2 + userCount3 + userCount4 + userCount5 + userCount6;

        // Initialize lists as empty
        List<Customer> customerList = new ArrayList<>();
        List<Sale> saleList = new ArrayList<>();
        List<SaleManager> saleManagerList = new ArrayList<>();
        List<Marketing> marketingList = new ArrayList<>();
        List<WarehouseStaff> warehousestaffList = new ArrayList<>();

        // Total records for current type (for pagination)
        int currentTypeCount = totalUsers;

        // Filter based on type
        if (type == null || type.isEmpty()) {
            // Get all types
            customerList = customerDao.getFilteredCustomer(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
            saleList = saleDao.getFilteredSale(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
            saleManagerList = saleManagerDao.getFilteredSaleManager(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
            marketingList = marketingDao.getFilteredMarketing(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
            warehousestaffList = warehouseStaffDao.getFilteredWaleStaff(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
        } else {
            switch (type) {
                case "2":
                    customerList = customerDao.getFilteredCustomer(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
                    currentTypeCount = userCount2;
                    break;
                case "3":
                    saleList = saleDao.getFilteredSale(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
                    currentTypeCount = userCount3;
                    break;
                case "4":
                    marketingList = marketingDao.getFilteredMarketing(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
                    currentTypeCount = userCount5;
                    break;
                case "5":
                    saleManagerList = saleManagerDao.getFilteredSaleManager(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
                    currentTypeCount = userCount4;
                    break;
                case "6":
                    warehousestaffList = warehouseStaffDao.getFilteredWaleStaff(gender, role, status, search, offset, RECORDS_PER_PAGE, sortField, sortOrder);
                    currentTypeCount = userCount6;
                    break;
            }
        }

        // Calculate total pages based on current type count
        int totalPages = (int) Math.ceil((double) currentTypeCount / RECORDS_PER_PAGE);

        // Set counts in request
        request.setAttribute("userCount2", userCount2);
        request.setAttribute("userCount3", userCount3);
        request.setAttribute("userCount4", userCount4);
        request.setAttribute("userCount5", userCount5);
        request.setAttribute("userCount6", userCount6);
        request.setAttribute("totalUsers", totalUsers);

        // Set lists in request
        request.setAttribute("customerList", customerList);
        request.setAttribute("saleList", saleList);
        request.setAttribute("saleManagerList", saleManagerList);
        request.setAttribute("marketingList", marketingList);
        request.setAttribute("warehousestaffList", warehousestaffList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Create search params string
        StringBuilder searchParamsBuilder = new StringBuilder();
        if (gender != null && !gender.isEmpty()) {
            searchParamsBuilder.append("&gender=").append(gender);
        }
        if (role != null && !role.isEmpty()) {
            searchParamsBuilder.append("&role=").append(role);
        }
        if (status != null && !status.isEmpty()) {
            searchParamsBuilder.append("&status=").append(status);
        }
        if (search != null && !search.isEmpty()) {
            searchParamsBuilder.append("&search=").append(search);
        }
        if (type != null && !type.isEmpty()) {
            searchParamsBuilder.append("&type=").append(type);
        }
        request.setAttribute("searchParams", searchParamsBuilder.toString());

        // Forward to JSP
        request.getRequestDispatcher("userlist.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}