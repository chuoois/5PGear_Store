package controller.home;

import Email.JavaMail;
import Email.JavaMailImpl;
import dal.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Random;
import model.Customer;
public class ForgotPasswordController extends HttpServlet {

    private JavaMail emailService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo emailService (có thể dùng Dependency Injection hoặc New Object)
        emailService = new JavaMailImpl(); // Thay thế bằng cài đặt thực tế của bạn
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("recover.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
       CustomerDAO customerDAO= new CustomerDAO();
       Customer acc= customerDAO.getCustomerByEmail(email);
      

        if (email == null || email.isEmpty()||acc==null|| acc.getCustomerName().isEmpty()) {
            request.setAttribute("emailError", "Email not found");
            request.getRequestDispatcher("recover.jsp").forward(request, response);
        }

        String otp = generateOTP();

        try {
            boolean isSent = emailService.send(email, "Your OTP Code", "Your OTP code is: " + otp);
            if (isSent) {
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(1 * 60); // Set session timeout to 2 minutes
                session.setAttribute("otp", otp);
                session.setAttribute("email", email);
                response.sendRedirect("OTPMail.jsp?type=recover");
            } else {
                request.setAttribute("emailError", "Failed to send OTP. Please try again.");
                request.getRequestDispatcher("recover.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("emailError", "An unexpected error occurred. Please try again later.");
            request.getRequestDispatcher("recover.jsp").forward(request, response);
        }
    }

    private String generateOTP() {
        Random random = new Random();
        int otp = random.nextInt(999999);
        return String.format("%06d", otp);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}



