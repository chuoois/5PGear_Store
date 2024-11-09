package model;

import java.util.Date;

public class Marketing {

    private int marketingId;
    private String marketingName;
    private String email;
    private Date dob;
    private String password;
    private String phone;
    private String gender;
    public String avatar_url;
    private Role role;
    private String status;

    public Marketing() {
    }

    public Marketing(int marketingId, String marketingName, String email, Date dob, String password, String phone, String gender, String avatar_url, Role role, String status) {
        this.marketingId = marketingId;
        this.marketingName = marketingName;
        this.email = email;
        this.dob = dob;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.avatar_url = avatar_url;
        this.role = role;
        this.status = status;
    }

    public int getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(int marketingId) {
        this.marketingId = marketingId;
    }

    public String getMarketingName() {
        return marketingName;
    }

    public void setMarketingName(String marketingName) {
        this.marketingName = marketingName;
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

    @Override
    public String toString() {
        return "Marketing{" + "marketingId=" + marketingId + ", marketingName=" + marketingName + ", email=" + email + ", dob=" + dob + ", password=" + password + ", phone=" + phone + ", gender=" + gender + ", avatar_url=" + avatar_url + ", role=" + role + ", status=" + status + '}';
    }

    

}
