package controller.admin;

import config.Encode;
import dal.AdminDAO;
import dal.MarketingDAO;
import dal.SaleDAO;
import dal.WarehouseStaffDAO;
import dal.SaleManagerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;
import model.Marketing;
import model.Sale;
import model.WarehouseStaff;
import model.SaleManager;

public class LoginStaffController extends HttpServlet {

    private AdminDAO adminDAO;
    private MarketingDAO marketingDAO;
    private SaleDAO saleDAO;
    private WarehouseStaffDAO waleStaffDAO;
    private SaleManagerDAO saleManagerDAO; // Thêm SaleManagerDAO

    @Override
    public void init() throws ServletException {
        adminDAO = new AdminDAO();
        marketingDAO = new MarketingDAO();
        saleDAO = new SaleDAO();
        waleStaffDAO = new WarehouseStaffDAO();
        saleManagerDAO = new SaleManagerDAO(); // Khởi tạo SaleManagerDAO
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("loginstaff.jsp").forward(request, response);
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

        // Tạo cookies cho email, password, remember
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
            // Kiểm tra Admin
            Admin admin = adminDAO.getAdminByEmail(email);
            if (admin != null) {
                String encryptedPassword = Encode.toSHA1(password);
                if (!encryptedPassword.equals(admin.getPassword())) {
                    request.setAttribute("passwordError", "Incorrect password.");
                    hasErrors = true;
                } else {
                    HttpSession session = request.getSession();
                    session.setAttribute("account_a", admin);
                    session.setAttribute("successMessage", "Login successful.");
                    response.sendRedirect("userlist");
                    return;
                }
            } else {
                // Kiểm tra Marketing
                Marketing marketing = marketingDAO.getMarketingByEmail(email);
                if (marketing != null) {
                    String encryptedPassword = Encode.toSHA1(password);
                    if (!encryptedPassword.equals(marketing.getPassword())) {
                        request.setAttribute("passwordError", "Incorrect password.");
                        hasErrors = true;
                    }
                    if (!"Active".equals(marketing.getStatus())) {
                        request.setAttribute("error", "Your account has not been activated.");
                        hasErrors = true;
                    } else {
                        HttpSession session = request.getSession();
                        session.setAttribute("marketer", marketing);
                        response.sendRedirect("/SWP391Gr5/marketting/home");
                        return;
                    }
                } else {
                    // Kiểm tra Sale
                    Sale sale = saleDAO.getSaleByEmail(email);
                    if (sale != null) {
                        String encryptedPassword = Encode.toSHA1(password);
                        if (!encryptedPassword.equals(sale.getPassword())) {
                            request.setAttribute("passwordError", "Incorrect password.");
                            hasErrors = true;
                        }
                        if (!"Active".equals(sale.getStatus())) {
                            request.setAttribute("error", "Your account has not been activated.");
                            hasErrors = true;
                        } else {
                            HttpSession session = request.getSession();
                            session.setAttribute("sales", sale);
                            response.sendRedirect("orderlist");
                            return;
                        }
                    } else {
                        // Kiểm tra WarehouseStaff
                        WarehouseStaff waleStaff = waleStaffDAO.getWaleStaffByEmail(email);
                        if (waleStaff != null) {
                            String encryptedPassword = Encode.toSHA1(password);
                            if (!encryptedPassword.equals(waleStaff.getPassword())) {
                                request.setAttribute("passwordError", "Incorrect password.");
                                hasErrors = true;
                            }
                            if (!"Active".equals(waleStaff.getStatus())) {
                                request.setAttribute("error", "Your account has not been activated.");
                                hasErrors = true;
                            } else {
                                HttpSession session = request.getSession();
                                session.setAttribute("waleStaff", waleStaff);
                                session.setAttribute("successMessage", "Login successful.");
                                response.sendRedirect("warehouse");
                                return;
                            }
                        } else {
                            // Kiểm tra SaleManager
                            SaleManager saleManager = saleManagerDAO.getSaleManagerByEmail(email);
                            if (saleManager != null) {
                                String encryptedPassword = Encode.toSHA1(password);
                                if (!encryptedPassword.equals(saleManager.getPassword())) {
                                    request.setAttribute("passwordError", "Incorrect password.");
                                    hasErrors = true;
                                }
                                if (!"Active".equals(saleManager.getStatus())) {
                                    request.setAttribute("error", "Your account has not been activated.");
                                    hasErrors = true;
                                } else {
                                    HttpSession session = request.getSession();
                                    session.setAttribute("saleManager", saleManager);
                                    response.sendRedirect("salemanagerlist");
                                    return;
                                }
                            } else {
                                request.setAttribute("emailError", "Email is not registered.");
                                hasErrors = true;
                            }
                        }
                    }
                }
            }

            if (hasErrors) {
                request.setAttribute("email", email);
                request.setAttribute("password", password);
                request.getRequestDispatcher("loginstaff.jsp").forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Controller for multiple roles including Admin, Marketing, Sale, Warehouse Staff, and Sale Manager";
    }
}
