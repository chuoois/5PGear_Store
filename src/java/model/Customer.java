package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Customer {

    private int customerId;
    private String customerName;
    private Date dob;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String gender;
    public String avatar_url;
    private Role role;
    private String status;
    private String token;
    private Date dateCreate;

    public Customer() {
    }

    public Customer(int customerId, String customerName, Date dob, String email, String password, String phone, String address, String gender, String avatar_url, Role role, String status, String token, Date dateCreate) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.dob = dob;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.role = role;
        this.status = status;
        this.token = token;
        this.dateCreate = dateCreate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public String getDobString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.dob);
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", customerName=" + customerName + ", dob=" + dob + ", email=" + email + ", password=" + password + ", phone=" + phone + ", address=" + address + ", gender=" + gender + ", avatar_url=" + avatar_url + ", role=" + role + ", status=" + status + ", token=" + token + ", dateCreate=" + dateCreate + '}';
    }
    
}
