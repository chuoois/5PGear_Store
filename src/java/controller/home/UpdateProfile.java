package controller.home;

import config.Validate;
import dal.CustomerDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Customer;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class UpdateProfile extends HttpServlet {

    private final String UPLOAD_DIRECTORY = "C:\\Users\\acer\\Desktop\\New folder (2)\\web\\img";  // Đường dẫn lưu ảnh

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin người dùng từ form
        String fullName = request.getParameter("full_name");
        String dobStr = request.getParameter("date_of_birth");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String email = request.getParameter("email"); // Email is read-only

        // Lấy thông tin khách hàng bằng email
        CustomerDAO customerDAO = new CustomerDAO();
        Customer customer = customerDAO.getCustomerByEmail(email);

        // Chuyển đổi ngày sinh từ chuỗi sang đối tượng Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dob = null;
        try {

            dob = dateFormat.parse(dobStr);// Chuyển đổi chuỗi ngày tháng
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("profile.jsp?error=true");
            return;  // Stop processing if date parsing fails
        }
        // Kiểm tra số điện thoại
        boolean checkphone = false;
        if (!Validate.checkPhone(phone)) {
            request.setAttribute("ErroPhone", "Invalid format!"); // Hiển thị lỗi nếu không đúng định dạng
            checkphone = true;
        }

         if (checkphone) {
        request.setAttribute("fullName", fullName);
        request.setAttribute("dobStr", dobStr);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
        request.setAttribute("gender", gender);
        request.setAttribute("email", email);
        
        request.getRequestDispatcher("profile.jsp").forward(request, response);
        return;
    }

        // Cập nhật thông tin khách hàng trừ phần ảnh đại diện (handled below)
        customer.setCustomerName(fullName);
        customer.setDob(dob);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setGender(gender);

        // Handle avatar upload logic
        Part filePart = request.getPart("avatar"); // Lấy phần file tải lên
        String fileName = filePart.getSubmittedFileName(); // Lấy tên file tải lên

        // Nếu có file được tải lên (không phải null hoặc rỗng), thì xử lý cập nhật ảnh đại diện
        if (fileName != null && !fileName.isEmpty()) {
            String fileType = filePart.getContentType(); // Lấy MIME type của file
            // Chỉ cho phép file ảnh (MIME type bắt đầu với "image/")
            if (!fileType.startsWith("image/")) {
                response.sendRedirect("profile.jsp?error=invalid_file_type");
                return;  // Stop processing if it's not an image file
            }

            // File handling logic (save file)
            File uploadsDir = new File(UPLOAD_DIRECTORY);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
            }

            try {
                filePart.write(Paths.get(UPLOAD_DIRECTORY, fileName).toString()); // Save the uploaded file
                // Update the avatar URL in the customer object
                customer.setAvatar_url("img/" + fileName);   // Assuming the file path will be handled
            } catch (IOException e) {
                e.printStackTrace();
                response.sendRedirect("profile.jsp?error=file_upload");
                return;  // Dừng xử lý nếu có lỗi
            }
        } else {
            // If no new file is uploaded, keep the existing avatar URL
            customer.setAvatar_url(customer.getAvatar_url());
        }

        // Update customer in the database
        customerDAO.updateCustomer(customer);

        // Update session with new customer data to avoid re-login
        HttpSession session = request.getSession();
        session.setAttribute("account", customer);

        // Redirect to profile page with success message
        response.sendRedirect("home");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for updating user profile";
    }
}
