package controller.admin;

import Email.JavaMailImpl;
import config.Encode;
import dal.AdminDAO;
import dal.CustomerDAO;
import dal.MarketingDAO;
import dal.RoleDAO;
import dal.SaleDAO;
import dal.SaleManagerDAO;
import dal.WarehouseStaffDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import model.Admin;
import model.Customer;
import model.Marketing;
import model.Role;
import model.Sale;
import model.SaleManager;
import model.WarehouseStaff;

public class AddUserController extends HttpServlet {

    private CustomerDAO customerDao = new CustomerDAO();
    private SaleDAO saleDao = new SaleDAO();
    private SaleManagerDAO saleManagerDao = new SaleManagerDAO();
    private MarketingDAO marketingDao = new MarketingDAO();
    private RoleDAO roleDao = new RoleDAO();
    private AdminDAO adminDao = new AdminDAO();
    private WarehouseStaffDAO warehouseDao = new WarehouseStaffDAO();
    private JavaMailImpl mailSender = new JavaMailImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số từ form
        String fullName = request.getParameter("fullName");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String password = "123456789A@a";

        try {
            int roleId = roleDao.getRoleId(role);

            if (roleId == -1) {
                throw new IllegalArgumentException("Role không tồn tại: " + role);
            }

            String encryptedPassword = Encode.toSHA1(password);

            // Xử lý cho từng role cụ thể
            switch (roleId) {
                case 1:
                    Admin newAdmin = new Admin();
                    newAdmin.setAdminName(fullName);
                    newAdmin.setGender(gender);
                    newAdmin.setEmail(email);
                    newAdmin.setPhone(phone);
                    newAdmin.setStatus(status);
                    newAdmin.setDob(java.sql.Date.valueOf("2003-01-01"));
                    newAdmin.setPassword(encryptedPassword);

                    // Tạo đối tượng Role và gán cho Customer
                    Role roleObj1 = new Role();
                    roleObj1.setRoleId(roleId);
                    newAdmin.setRole(roleObj1);

                    adminDao.addAdmin(newAdmin);
                    break;
                case 2:
                    Customer newCustomer = new Customer();
                    newCustomer.setCustomerName(fullName);
                    newCustomer.setGender(gender);
                    newCustomer.setEmail(email);
                    newCustomer.setPhone(phone);
                    newCustomer.setStatus(status);
                    newCustomer.setDob(java.sql.Date.valueOf("2003-01-01"));
                    newCustomer.setPassword(encryptedPassword);

                    // Tạo đối tượng Role và gán cho Customer
                    Role roleObj2 = new Role();
                    roleObj2.setRoleId(roleId);
                    newCustomer.setRole(roleObj2);
                    newCustomer.setDateCreate(new Date());

                    customerDao.addCustomer(newCustomer);
                    break;
                case 3:
                    Sale newSale = new Sale();
                    newSale.setSaleName(fullName);
                    newSale.setGender(gender);
                    newSale.setEmail(email);
                    newSale.setPhone(phone);
                    newSale.setStatus(status);
                    newSale.setDob(java.sql.Date.valueOf("2003-01-01"));
                    newSale.setPassword(encryptedPassword);

                    Role roleObj3 = new Role();
                    roleObj3.setRoleId(roleId);
                    newSale.setRole(roleObj3);

                    saleDao.addSale(newSale);
                    break;
                case 5:
                    SaleManager newsalemanager = new SaleManager();
                    newsalemanager.setSalemanagerName(fullName);
                    newsalemanager.setGender(gender);
                    newsalemanager.setEmail(email);
                    newsalemanager.setPhone(phone);
                    newsalemanager.setStatus(status);
                    newsalemanager.setDob(java.sql.Date.valueOf("2003-01-01"));
                    newsalemanager.setPassword(encryptedPassword);

                    Role roleObj5 = new Role();
                    roleObj5.setRoleId(roleId);
                    newsalemanager.setRole(roleObj5);

                    saleManagerDao.addSaleManager(newsalemanager);
                    break;
                case 4:
                    Marketing newmareting = new Marketing();
                    newmareting.setMarketingName(fullName);
                    newmareting.setGender(gender);
                    newmareting.setEmail(email);
                    newmareting.setPhone(phone);
                    newmareting.setStatus(status);
                    newmareting.setDob(java.sql.Date.valueOf("2003-01-01"));
                    newmareting.setPassword(encryptedPassword);

                    Role roleObj4 = new Role();
                    roleObj4.setRoleId(roleId);
                    newmareting.setRole(roleObj4);

                    marketingDao.addMarketing(newmareting);
                    break;
                case 6:
                    WarehouseStaff warehouse = new WarehouseStaff();
                    warehouse.setWarehousestaffName(fullName);
                    warehouse.setGender(gender);
                    warehouse.setEmail(email);
                    warehouse.setPhone(phone);
                    warehouse.setStatus(status);
                    warehouse.setDob(java.sql.Date.valueOf("2003-01-01"));
                    warehouse.setPassword(encryptedPassword);
                    
                    Role roleObj6 = new Role();
                    roleObj6.setRoleId(roleId);
                    warehouse.setRole(roleObj6);

                    warehouseDao.addWaleStaff(warehouse);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown role: " + role);
            }

            // Send email with the password
            String subject = "Your Account Password";
            String messageText = "Hello " + fullName + ",<br/>"
                    + "Your account has been created successfully!<br/>"
                    + "Your password is: " + password + "<br/>"
                    + "Please change it upon your first login.";
            mailSender.send(email, subject, messageText);

            // Redirect to user list page upon success
            response.sendRedirect("userlist");
        } catch (Exception e) {
            e.printStackTrace();
            // In ra lỗi chi tiết hơn
            request.setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình thêm người dùng: " + e.getMessage());
            request.getRequestDispatcher("userlist").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Add User Servlet";
    }
}
