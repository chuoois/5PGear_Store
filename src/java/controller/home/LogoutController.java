package controller.home;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 *
 * @author Palala
 */
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy đối tượng HttpSession từ request
        HttpSession session = request.getSession(false);
        
        // Nếu session tồn tại
        if (session != null) {
            // Lấy tất cả các attribute trong session
            Enumeration<String> attributeNames = session.getAttributeNames();
            
            // Lặp qua tất cả các attribute và xóa ngoại trừ 'newAddresses'
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                // Giữ lại 'newAddresses' và xóa các attribute khác
                if (!"userAddresses".equals(attributeName)) {
                    session.removeAttribute(attributeName);
                }
            }
        }
        
        // Chuyển hướng người dùng đến trang đăng nhập hoặc trang chính
        response.sendRedirect(request.getContextPath() + "/home");
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Logout Servlet";
    }
}
