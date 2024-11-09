/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.home;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Cart;
import jakarta.servlet.http.HttpSession;
import model.Customer;
import dal.CartDAO;
import java.math.BigDecimal;

/**
 *
 * @author acer
 */
public class CartController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");
        CartDAO cartdao = new CartDAO();
        if (account != null) {
            String cartIdString = request.getParameter("cartId");
            String cartDeleteAll = request.getParameter("cartdeleteall");
            String productQuantity = request.getParameter("quantitypro");
            String cartquantityId = request.getParameter("cartquantityId");
            if (cartquantityId != null && productQuantity != null && !cartquantityId.trim().isEmpty() && !productQuantity.trim().isEmpty()) {
                int proQuantity = Integer.parseInt(productQuantity);
                int cardIdQuantity = Integer.parseInt(cartquantityId);
                cartdao.updateCartQuantity(cardIdQuantity, proQuantity);
            }
            if (cartIdString != null && !cartIdString.trim().isEmpty()) {
                int cartId = Integer.parseInt(cartIdString);
                cartdao.deleteCartById(cartId);
            }

            if (cartDeleteAll != null && !cartDeleteAll.trim().isEmpty()) {
                int customerid = account.getCustomerId();
                cartdao.deleteCartByCustomerId(customerid);
            }
            List<Cart> cart = cartdao.getAllCartByCustomerId(account.getCustomerId());
            BigDecimal subtotal = BigDecimal.ZERO;
            BigDecimal finalprice = BigDecimal.ZERO;
            for (Cart cart1 : cart) {
                // Lấy giá và khuyến mãi dưới dạng BigDecimal
                BigDecimal price = cart1.getProduct().getPrice();
                BigDecimal sale = cart1.getProduct().getSale().divide(BigDecimal.valueOf(100));
                // Tính giá cuối cùng sau khi áp dụng khuyến mãi
                finalprice = price.multiply(BigDecimal.ONE.subtract(sale));
                // Cộng tổng vào subtotal
                subtotal = subtotal.add(finalprice.multiply(BigDecimal.valueOf(cart1.getQuantity())));
            }
               
            request.setAttribute("cart", cart);
            session.setAttribute("cart1", cart);
            session.setAttribute("subtotal1", subtotal);
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } else {
            response.sendRedirect("login.jsp");
        }
        // Tạo đối tượng Cart

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String proId = request.getParameter("productId");
        String proQuantity = request.getParameter("productQuantity");
        int productQuantity = 1;

        if (proQuantity != null && !proQuantity.isEmpty()) {
            productQuantity = Integer.parseInt(proQuantity);
        }

        int productId = Integer.parseInt(proId);
        HttpSession session = request.getSession();
        Customer account = (Customer) session.getAttribute("account");

        CartDAO cartdao = new CartDAO();
        int customerid = account.getCustomerId();
        cartdao.updateCart(customerid, productId, productQuantity);

        response.sendRedirect("cart");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    

}
