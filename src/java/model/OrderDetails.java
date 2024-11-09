/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author DNC
 */
public class OrderDetails {

    private int orderDetailsId;
    private Order orderId;
    private Product productId;
    private int quantity;
    private String name;
    private String phone;
    private String address;
    private BigDecimal unitPrice;
    private String note;

    // Constructors
    public OrderDetails() {

    }

    public OrderDetails(int orderDetailsId, Order orderId, Product productId, int quantity, String name, String phone, String address, BigDecimal unitPrice, String note) {
        this.orderDetailsId = orderDetailsId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.unitPrice = unitPrice;
        this.note = note;
    }


    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public Order getOrderId() {
        return orderId;
    }

    public void setOrderId(Order orderId) {
        this.orderId = orderId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
