package model;

public class Address {

    private String name;
    private String phone;
    private String detail;
    private String note;

    public Address() {
    }

    public Address(String name, String phone, String detail, String note) {
        this.name = name;
        this.phone = phone;
        this.detail = detail;
        this.note = note;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
