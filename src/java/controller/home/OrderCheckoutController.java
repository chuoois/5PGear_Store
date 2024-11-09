package controller.home;

import Email.JavaMail;
import Email.JavaMailImpl;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import config.Config;
import dal.OrderDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import model.Address;
import model.AddressManager;
import model.Cart;
import model.Customer;

public class OrderCheckoutController extends HttpServlet {

    private OrderDAO orderDAO = new OrderDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("account");
        List<Cart> cartItems = (List<Cart>) session.getAttribute("cart1");
        BigDecimal subtotal = (BigDecimal) session.getAttribute("subtotal1");
        String paymentMethod = request.getParameter("payment");
        AddressManager addressManager = (AddressManager) session.getAttribute("addressManager");

        // Get selected address from the form
        String selectedAddress = request.getParameter("selectedAddress");
        Address finalAddress;

        // Determine which address to use
        if ("default".equals(selectedAddress)) {
            // Use the default address of the account
            finalAddress = new Address(customer.getCustomerName(), customer.getPhone(), customer.getAddress(), "");
        } else {
            // Retrieve the selected custom address by index
            int addressIndex = Integer.parseInt(selectedAddress);
            finalAddress = addressManager.getAddresses(customer.getEmail()).get(addressIndex);
        }

        // Handle payment method
        if ("COD".equals(paymentMethod)) {
            handleCODOrder(request, response, customer, cartItems, subtotal, finalAddress);
        } else if ("VNPAY".equals(paymentMethod)) {
            handleVNPayOrder(request, response, customer, cartItems, subtotal, finalAddress);
        } else {
            request.setAttribute("error", "Invalid payment method selected.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }

    private void handleCODOrder(HttpServletRequest request, HttpServletResponse response, Customer customer, List<Cart> cartItems, BigDecimal subtotal, Address finalAddress)
            throws ServletException, IOException {

        int nextSaleId = orderDAO.getNextSaleId();
        if (nextSaleId == -1) {
            request.setAttribute("error", "Unable to allocate sale staff for the order.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Insert the new order with 'Pending' status (ostatus_id = 2)
        int orderId = orderDAO.insertOrder(customer.getCustomerId(), subtotal, nextSaleId, 2, "COD");
        if (orderId != -1) {
            for (Cart cart : cartItems) {
                BigDecimal finalPrice = calculateFinalPrice(cart);
                orderDAO.insertOrderDetail(orderId, cart.getProduct().getProductId(), cart.getQuantity(), finalPrice,
                        finalAddress.getName(), finalAddress.getPhone(), finalAddress.getDetail(), "");
                orderDAO.updateProductHold(cart.getProduct().getProductId(), cart.getQuantity());
            }
            // Save orderId to session
            request.getSession().setAttribute("orderId", orderId);

            // Redirect to thank you page after processing order
            response.sendRedirect("ThanksCustomer.jsp");
        } else {
            request.setAttribute("error", "Unable to process the order.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }

    private void handleVNPayOrder(HttpServletRequest request, HttpServletResponse response, Customer customer, List<Cart> cartItems, BigDecimal subtotal, Address finalAddress)
            throws ServletException, IOException {

        int nextSaleId = orderDAO.getNextSaleId();
        if (nextSaleId == -1) {
            request.setAttribute("error", "Unable to allocate sale staff for the order.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Insert the new order with allocated sale_id
        int orderId = orderDAO.insertOrder(customer.getCustomerId(), subtotal, nextSaleId, 2, "VNPAY");

        if (orderId == -1) {
            request.setAttribute("error", "Unable to process the order.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
            return;
        }

        // Save orderId to session
        request.getSession().setAttribute("orderId", orderId);

        for (Cart cart : cartItems) {
            BigDecimal finalPrice = calculateFinalPrice(cart);
            orderDAO.insertOrderDetail(orderId, cart.getProduct().getProductId(), cart.getQuantity(), finalPrice,
                    finalAddress.getName(), finalAddress.getPhone(), finalAddress.getDetail(), "");
            orderDAO.updateProductHold(cart.getProduct().getProductId(), cart.getQuantity());
        }

        // VNPay payment processing
        String paymentUrl = createVNPayPaymentUrl(subtotal, request);
        response.sendRedirect(paymentUrl);
    }

    private String createVNPayPaymentUrl(BigDecimal subtotal, HttpServletRequest req) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amountInVNDCents = subtotal.multiply(new BigDecimal(2400000)).longValue();
        String bankCode = req.getParameter("bankCode");
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(req);
        String vnp_TmnCode = Config.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountInVNDCents));
        vnp_Params.put("vnp_CurrCode", "VND");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Order Payment: " + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", req.getParameter("language") != null ? req.getParameter("language") : "en");
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        return Config.vnp_PayUrl + "?" + queryUrl;
    }

    private BigDecimal calculateFinalPrice(Cart cart) {
        BigDecimal price = cart.getProduct().getPrice();
        BigDecimal sale = cart.getProduct().getSale().divide(BigDecimal.valueOf(100));
        return price.multiply(BigDecimal.ONE.subtract(sale));
    }

    @Override
    public String getServletInfo() {
        return "Order Checkout Controller";
    }
}
