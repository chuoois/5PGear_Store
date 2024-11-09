package controller.home;

import config.Encode;
import dal.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Customer;

public class ProfileController extends HttpServlet {

    private CustomerDAO customerDAO = new CustomerDAO();  // Khởi tạo đối tượng DAO

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String current_password = request.getParameter("current_password");
        String new_password = request.getParameter("new_password");
        String confirm_new_password = request.getParameter("confirm_new_password");

        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");

        if (account != null) {
            // Mã hóa mật khẩu hiện tại mà người dùng nhập
            String encryptedCurrentPassword = Encode.toSHA1(current_password);

            // Kiểm tra xem mật khẩu hiện tại nhập vào có đúng không
            if (encryptedCurrentPassword.equals(account.getPassword())) {
                // Kiểm tra mật khẩu mới và xác nhận mật khẩu có trùng khớp không
                if (new_password.equals(confirm_new_password)) {
                    // Mã hóa mật khẩu mới
                    String encodedNewPassword = Encode.toSHA1(new_password);

                    // Cập nhật mật khẩu trong cơ sở dữ liệu
                    customerDAO.updatePassCustomer(account.getEmail(), encodedNewPassword);

                    // Cập nhật trong session
                    account.setPassword(encodedNewPassword);
                    session.setAttribute("account", account);

                    // Thông báo thành công
                    request.setAttribute("passsuccess", "You have successfully changed your password!");
                } else {
                    // Thông báo lỗi nếu mật khẩu xác nhận không trùng khớp
                    request.setAttribute("passerror", "New password does not match!");
                }
            } else {
                // Thông báo lỗi nếu mật khẩu hiện tại không đúng
                request.setAttribute("passerror", "Old password is incorrect!");
            }
        }

        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
