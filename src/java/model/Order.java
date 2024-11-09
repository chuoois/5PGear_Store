package model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

    private int orderId;
    private Customer customerId;
    private BigDecimal totalPrice;
    private Date orderDate;
    private Sale saleId;
    private OStatus ostatusId;
    private String payment;

    // Constructor
    public Order() {
    }

    public Order(int orderId, Customer customerId, BigDecimal totalPrice, Date orderDate, Sale saleId, OStatus ostatusId, String payment) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.saleId = saleId;
        this.ostatusId = ostatusId;
        this.payment = payment;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Sale getSaleId() {
        return saleId;
    }

    public void setSaleId(Sale saleId) {
        this.saleId = saleId;
    }

    public OStatus getOstatusId() {
        return ostatusId;
    }

    public void setOstatusId(OStatus ostatusId) {
        this.ostatusId = ostatusId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", customerId=" + customerId + ", totalPrice=" + totalPrice + ", orderDate=" + orderDate + ", saleId=" + saleId + ", ostatusId=" + ostatusId + ", payment=" + payment + '}';
    }

    
}
