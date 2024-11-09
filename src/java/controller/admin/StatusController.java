package controller.admin;

import dal.CustomerDAO;
import dal.MarketingDAO;
import dal.SaleDAO;
import dal.SaleManagerDAO;
import dal.WarehouseStaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Customer;
import model.Marketing;
import model.Sale;
import model.SaleManager;
import model.WarehouseStaff;

public class StatusController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("id"));
        String type = request.getParameter("type");
        
        // Xác định type parameter cho URL chuyển hướng
        String redirectType = "";
        if ("Customer".equals(type)) {
            redirectType = "2";
        } else if ("Sale".equals(type)) {
            redirectType = "3";
        } else if ("Marketing".equals(type)) {
            redirectType = "4";
        } else if ("SaleManager".equals(type)) {
            redirectType = "5";
        } else if ("Warehouse".equals(type)) {
            redirectType = "6";
        }

        if ("updateStatus".equals(action)) {
            switch (type) {
                case "Customer":
                    CustomerDAO customerDAO = new CustomerDAO();
                    Customer customer = customerDAO.getCustomerById(userId);
                    customer.setStatus("Active".equals(customer.getStatus()) ? "Inactive" : "Active");
                    customerDAO.updateCustomerStatus(customer);
                    break;
                    
                case "Sale":
                    SaleDAO saleDAO = new SaleDAO();
                    Sale sale = saleDAO.getSaleById(userId);
                    sale.setStatus("Active".equals(sale.getStatus()) ? "Inactive" : "Active");
                    saleDAO.updateSaleStatus(sale);
                    break;
                    
                case "SaleManager":
                    SaleManagerDAO salemanagerDAO = new SaleManagerDAO();
                    SaleManager salemanager = salemanagerDAO.getSaleManagerById(userId);
                    salemanager.setStatus("Active".equals(salemanager.getStatus()) ? "Inactive" : "Active");
                    salemanagerDAO.updateSaleManagerStatus(salemanager);
                    break;
                    
                case "Marketing":
                    MarketingDAO marketingDAO = new MarketingDAO();
                    Marketing marketing = marketingDAO.getMarketerById(userId);
                    marketing.setStatus("Active".equals(marketing.getStatus()) ? "Inactive" : "Active");
                    marketingDAO.updateMarketingStatus(marketing);
                    break;
                    
                case "Warehouse":
                    WarehouseStaffDAO warehouseStaffDao = new WarehouseStaffDAO();
                    WarehouseStaff warehouse = warehouseStaffDao.getWaleStaffById(userId);
                    warehouse.setStatus("Active".equals(warehouse.getStatus()) ? "Inactive" : "Active");
                    warehouseStaffDao.updateWaleStaffStatus(warehouse);
                    break;
            }
            
            // Chuyển hướng về trang userlist với type tương ứng
            response.sendRedirect("userlist?type=" + redirectType);
        }
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