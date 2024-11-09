package controller.home;

import config.Encode;
import config.GoogleLogin;
import config.Validate;
import dal.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChangePasswordController extends HttpServlet {

    private CustomerDAO customerDAO;
    Validate validate = new Validate();

    @Override
    public void init() throws ServletException {
        // Khởi tạo đối tượng customerDAO
        customerDAO = new CustomerDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("changepass.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pass = request.getParameter("password");
        String repass = request.getParameter("repassword");
        boolean hasErrors = false;
        if (!validate.checkPassword(pass)) {
            request.setAttribute("passwordError", "Invalid password. Password must be at least 8 characters long, contain an uppercase letter, a lowercase letter, a digit, and a special character.");
            hasErrors = true;
        }
        if (!pass.equals(repass)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match.");
            hasErrors = true;
        }
        if (hasErrors) {
            request.getRequestDispatcher("changepass.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();
            String email = (String) session.getAttribute("email");
            String encryptedPassword = Encode.toSHA1(repass);
            if (email != null && pass != null && pass.equals(repass)) {
                // Update the password in the database
                customerDAO.updatePassCustomer(email, encryptedPassword);
                session.setAttribute("successfull", "Update Password successful");
                response.sendRedirect("changepass.jsp"); // Redirect to the changepass page to show the success message
            } else {
                request.setAttribute("error", "Passwords do not match or session is invalid.");
                request.getRequestDispatcher("changepass.jsp").forward(request, response);
            }

        }
    }

    @Override
    public String getServletInfo() {
        return "Change password controller";
    }
}
