/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import config.Validate;
import dal.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


/**
 *
 * @author Palala
 */
public class OTPController extends HttpServlet {

    Validate validate = new Validate();
    private CustomerDAO customerDAO; // Đối tượng truy cập cơ sở dữ liệu khách hàng

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("OTPMail.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
//        String pass = request.getParameter("password");
//        String repass = request.getParameter("repassword");
//
//        HttpSession session = request.getSession();
//        String email = (String) session.getAttribute("email");
//
//        customerDAO.updatePassCustomer(email, pass);

        HttpSession session = request.getSession();
        String e = (String) session.getAttribute("email");

        // Debug print to check the OTP values
        System.out.println("session: " + e);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        String otpSever = (String) session.getAttribute("otp");
        String otpClient = request.getParameter("otp");

        String e = (String) session.getAttribute("email");

        // Debug print to check the OTP values
        System.out.println("OTP from session: " + otpSever);
        System.out.println("OTP from client: " + otpClient);
        System.out.println("session: " + e);
        if (otpClient != null && otpClient.equals(otpSever)) {
            // OTP đúng, chuyển hướng đến recover.jsp với type là recover
            response.sendRedirect("changepass");
        } else {
            // OTP sai, gửi lỗi và quay lại trang OTP
            String errOtp = "OTP is incorrect";
            request.setAttribute("errOtp", errOtp);
            request.getRequestDispatcher("OTPMail.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}



