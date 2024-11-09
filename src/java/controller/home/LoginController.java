package controller.home;

import config.Encode;
import config.GoogleLogin;
import dal.CustomerDAO;
import dal.MarketingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.Customer;
import model.GoogleAccount;
import model.Marketing;
import model.Role;

public class LoginController extends HttpServlet {
    
    private CustomerDAO customerDAO; // Đối tượng truy cập cơ sở dữ liệu khách hàng
    private MarketingDAO markettingDAO;

    @Override
    public void init() throws ServletException {
        // Khởi tạo đối tượng CustomerDAO
        customerDAO = new CustomerDAO();
        markettingDAO = new MarketingDAO();
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // Lấy mã code từ Google 
        String code = request.getParameter("code");
        if (code != null) {
            // Đăng nhập với Google
            GoogleLogin gg = new GoogleLogin();
            String accessToken = gg.getToken(code);
            GoogleAccount googleAccount = gg.getUserInfo(accessToken);

            // Kiểm tra tài khoản Google đã tồn tại trong hệ thống chưa
            Customer existingCustomer = customerDAO.getCustomerByEmail(googleAccount.getEmail());
            
            if (existingCustomer == null) {
                // Nếu không tồn tại, tạo tài khoản mới từ thông tin Google
                Customer newCustomer = createNewCustomerFromGoogleAccount(googleAccount);
                try {
                    // Lưu thông tin khách hàng mới vào cơ sở dữ liệu
                    customerDAO.addCustomer(newCustomer);
                    System.out.println("Customer added: " + newCustomer.getEmail());

                    // Lưu thông tin tài khoản vào session và chuyển hướng đến trang chủ
                    HttpSession session = request.getSession();
                    session.setAttribute("account", newCustomer);
                    session.setAttribute("successMessage", "Google account has been created and logged in successfully.");
                    response.sendRedirect("home");
                } catch (Exception e) {
                    // Xử lý khi có lỗi xảy ra trong quá trình thêm khách hàng
                    e.printStackTrace();
                    request.setAttribute("error", "Error creating Google account.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                // Nếu tài khoản đã tồn tại, lưu vào session và chuyển hướng đến trang chủ
                HttpSession session = request.getSession();
                session.setAttribute("account", existingCustomer);
                session.setAttribute("successMessage", "Login successful.");
                response.sendRedirect("home");
            }
        } else {
            // Chuyển hướng đến trang login nếu không có mã Google
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Tạo một đối tượng khách hàng mới từ tài khoản Google
    private Customer createNewCustomerFromGoogleAccount(GoogleAccount googleAccount) {
        Customer newCustomer = new Customer();
        newCustomer.setCustomerName(googleAccount.getName());
        newCustomer.setEmail(googleAccount.getEmail());
        newCustomer.setGender("Other");
        newCustomer.setAvatar_url(googleAccount.getPicture());
        newCustomer.setStatus("Active");
        newCustomer.setPhone("0986152053");
        newCustomer.setDob(java.sql.Date.valueOf("2003-01-01"));
        newCustomer.setAddress("Hà Nội");
        // Đặt mật khẩu mặc định và mã hóa mật khẩu
        newCustomer.setPassword(Encode.toSHA1("123456789A@a"));
        // Đặt vai trò mặc định là "Customer"
        Role defaultRole = new Role(2, "Customer");
        newCustomer.setRole(defaultRole);
        newCustomer.setDateCreate(new java.util.Date());
        return newCustomer;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy dữ liệu người dùng nhập
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");

        // Tạo 3 cookies: email, password, remember
        Cookie ce = new Cookie("cemail", email);
        Cookie cp = new Cookie("cpass", password);
        Cookie cr = new Cookie("crem", remember);
        if (remember != null) {
            ce.setMaxAge(60 * 60 * 24 * 1); // 1 ngày
            cp.setMaxAge(60 * 60 * 24 * 1);
            cr.setMaxAge(60 * 60 * 24 * 1);
        } else {
            ce.setMaxAge(0); // xóa
            cp.setMaxAge(0);
            cr.setMaxAge(0);
        }
        response.addCookie(ce);
        response.addCookie(cp);
        response.addCookie(cr);
        
        boolean hasErrors = false;
        
        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("emailError", "Email is required.");
            hasErrors = true;
        } else {
            // Kiểm tra xem email có tồn tại trong bảng Customer hay không
            Customer customer = customerDAO.getCustomerByEmail(email);
            
            if (customer == null) {
                request.setAttribute("emailError", "Email is not registered.");
                hasErrors = true;
            } else {
                if (password == null || password.trim().isEmpty()) {
                    request.setAttribute("passwordError", "Password is required.");
                    hasErrors = true;
                } else {
                    String encryptedPassword = Encode.toSHA1(password);
                    if (!encryptedPassword.equals(customer.getPassword())) {
                        request.setAttribute("passwordError", "Incorrect password.");
                        hasErrors = true;
                    } else if (!"Active".equals(customer.getStatus())) {
                        request.setAttribute("error", "Your account has not been activated.");
                        hasErrors = true;
                    } else {
                        // Đăng nhập thành công cho customer
                        HttpSession session = request.getSession();
                        session.setAttribute("account", customer);
                        // Chuyển hướng đến trang home
                        response.sendRedirect("home");
                        return;
                    }
                }
            }
            
        }
        
        if (hasErrors) {
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
    
    @Override
    public String getServletInfo() {
        return "Login Controller Servlet";
    }
}
