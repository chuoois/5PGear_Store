package controller.home;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Address;
import model.Customer;
import model.AddressManager;

public class OrderInfor extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("orderInformation.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            deleteAddress(request, response);
        } else {
            processRequest(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addNewAddress(request, response);
        } else {
            processRequest(request, response);
        }
    }

    // Helper method to delete an address
    private void deleteAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");

        AddressManager addressManager = (AddressManager) session.getAttribute("addressManager");
        if (addressManager == null) {
            addressManager = new AddressManager();
            session.setAttribute("addressManager", addressManager);
        }

        int index = Integer.parseInt(request.getParameter("index"));
        addressManager.deleteAddress(account.getEmail(), index);

        // Redirect back to the order information page after deleting
        response.sendRedirect("orderinfor?updateaddress");
    }

    // Helper method to add a new address
    private void addNewAddress(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");

        AddressManager addressManager = (AddressManager) session.getAttribute("addressManager");
        if (addressManager == null) {
            addressManager = new AddressManager();
            session.setAttribute("addressManager", addressManager);
        }

        // Get the new address details from the request
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String detail = request.getParameter("detail");
        String note = request.getParameter("productMessage");

        // Create a new Address object
        Address newAddress = new Address(name, phone, detail, note);

        // Add the new address to the address manager
        addressManager.addAddress(account.getEmail(), newAddress);

        // Store the new address in the session
        session.setAttribute("userAddresses", newAddress);

        // Redirect back to the order information page after adding the address
        response.sendRedirect("orderinfor?updateaddress");
    }

    @Override
    public String getServletInfo() {
        return "Order Information Servlet";
    }
}
