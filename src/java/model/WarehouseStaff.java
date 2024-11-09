/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Palala
 */
public class WarehouseStaff {
    
    private int warehousestaffId;
    private String warehousestaffName;
    private String email;
    private Date dob;
    private String password;
    private String phone;
    private String gender;
    public String avatar_url;
    private Role role;
    private String status;

    public WarehouseStaff() {
    }

    public WarehouseStaff(int warehousestaffId, String warehousestaffName, String email, Date dob, String password, String phone, String gender, String avatar_url, Role role, String status) {
        this.warehousestaffId = warehousestaffId;
        this.warehousestaffName = warehousestaffName;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.role = role;
        this.status = status;
    }

    public int getWarehousestaffId() {
        return warehousestaffId;
    }

    public void setWarehousestaffId(int warehousestaffId) {
        this.warehousestaffId = warehousestaffId;
    }

    public String getWarehousestaffName() {
        return warehousestaffName;
    }

    public void setWarehousestaffName(String warehousestaffName) {
        this.warehousestaffName = warehousestaffName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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
    
    
    
}
