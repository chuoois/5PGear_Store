package controller.home;

import java.io.IOException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ContactController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("contact.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin từ form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String title = request.getParameter("subject");
        String comment = request.getParameter("comments");

        // Cấu hình email
        String host = "smtp.gmail.com";
        String port = "587";
        final String username = "linhbkhe171723@fpt.edu.vn"; // Thay bằng email của bạn
        final String password = "lxgt ijkj iecw werh";       // Thay bằng mật khẩu ứng dụng của bạn

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tạo phiên làm việc với cấu hình email
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Tạo và cấu hình nội dung email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("buikhanhlinh2003xt@gmail.com"));
            message.setSubject("New Contact Form Submission: " + title);
            message.setText("Name: " + name + "\nEmail: " + email + "\nComment:\n" + comment);

            // Gửi email
            Transport.send(message);

            // Đặt thông báo thành công và chuyển tiếp đến trang contact.jsp
            request.setAttribute("message", "Message sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            // Đặt thông báo lỗi nếu xảy ra lỗi khi gửi email
            request.setAttribute("message", "Error in sending email: " + e.getMessage());
        }

        // Chuyển tiếp đến trang contact.jsp với thông báo
        request.getRequestDispatcher("contact.jsp").forward(request, response);
    }
}
