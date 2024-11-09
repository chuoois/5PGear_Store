package controller.home;

import Email.JavaMail;
import Email.JavaMailImpl;
import config.Encode;
import dal.CustomerDAO;
import model.Customer;
import model.Role;
import config.Validate;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterController extends HttpServlet {

    Validate validate = new Validate();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters
        String Fullname = request.getParameter("fullname");
        String Email = request.getParameter("email");
        String Address = request.getParameter("address");
        String Dateofbirth = request.getParameter("dob");
        String Gender = request.getParameter("gender");
        String Password = request.getParameter("password");
        String rePassword = request.getParameter("confirmPassword");
        String phone = request.getParameter("phone");
        Role role = new Role(2, "Customer");

        // Keep data
        request.setAttribute("fullname", Fullname);
        request.setAttribute("email", Email);
        request.setAttribute("address", Address);
        request.setAttribute("dob", Dateofbirth);
        request.setAttribute("gender", Gender);
        request.setAttribute("password", Password);
        request.setAttribute("phone", phone);
        request.setAttribute("confirmPassword", rePassword);

        // Validate input
        boolean hasErrors = false;
        if (!validate.checkFullName(Fullname)) {
            request.setAttribute("fullnameError", "Please enter a valid name.");
            hasErrors = true;
        }
        if (!validate.checkEmail(Email)) {
            request.setAttribute("emailError", "Please enter a valid email.");
            hasErrors = true;
        }
        if (!validate.checkPhone(phone)) {
            request.setAttribute("phoneError", "Please enter a valid phone.");
            hasErrors = true;
        }

        if (!validate.checkPassword(Password)) {
            request.setAttribute("passwordError", "Password must be at least 8 characters long, including one uppercase letter and one special character.");
            hasErrors = true;
        }
        if (!Password.equals(rePassword)) {
            request.setAttribute("confirmPasswordError", "Passwords do not match.");
            hasErrors = true;
        }

        if (hasErrors) {
            request.getRequestDispatcher("register.jsp").forward(request, response);
        } else {
            String encryptedPassword = Encode.toSHA1(Password);
            CustomerDAO adb = new CustomerDAO();
            Customer accountByEmail = adb.getCustomerByEmail(Email);

            if (accountByEmail == null) {
                Customer newCustomer = new Customer();
                newCustomer.setCustomerName(Fullname);
                newCustomer.setEmail(Email);
                newCustomer.setAddress(Address);
                newCustomer.setPhone(phone);
                newCustomer.setDob(java.sql.Date.valueOf(Dateofbirth));
                newCustomer.setGender(Gender);
                newCustomer.setPassword(encryptedPassword);
                newCustomer.setRole(role);
                newCustomer.setStatus("Inactive");
                newCustomer.setDateCreate(new java.util.Date());
                String activationToken = adb.generateActivationToken();
                adb.addCustomerWithToken(newCustomer, activationToken);

                // Send activation email
                String activationLink = "http://localhost:8080/SWP391Gr5/home?token=" + activationToken;
                sendActivationEmail(Email, activationLink);

                request.setAttribute("successMessage", "Please check your email for activation link.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
            } else {
                if (accountByEmail != null) {
                    request.setAttribute("emailError", "Email already exists!");
                }
                request.getRequestDispatcher("register.jsp").forward(request, response);
            }
        }
    }

    private void sendActivationEmail(String to, String activationLink) {
        String subject = "Activate Your Account";
        String messageText = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Account Activation</title>"
                + "</head>"
                + "<body>"
                + "<h2>Welcome to Our Website!</h2>"
                + "<p>Thank you for registering. Please click the link below to activate your account:</p>"
                + "<a href=\"" + activationLink + "\">Activate Your Account</a>"
                + "<p>If you didn't register on our website, please ignore this email.</p>"
                + "</body>"
                + "</html>";

        JavaMail mailer = new JavaMailImpl();
        mailer.send(to, subject, messageText);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
