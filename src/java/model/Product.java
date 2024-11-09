
package model;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private int productId;
    private String productName;
    private BigDecimal price;
    private int quantity;
    private int hold;
    private String description;
    private String imgPath;
    private Brand brand;
    private Category category;
    private int releaseYear;
    private BigDecimal sale; 
    private int rate; 
    private int status;
    private Date dateCreate;
    private BigDecimal importPrice;

    public Product() {
    }

    public Product(int productId, String productName, BigDecimal price, int quantity, int hold, String description, String imgPath, Brand brand, Category category, int releaseYear, BigDecimal sale, int rate, int status, Date dateCreate, BigDecimal importPrice) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.hold = hold;
        this.description = description;
        this.imgPath = imgPath;
        this.brand = brand;
        this.category = category;
        this.releaseYear = releaseYear;
        this.sale = sale;
        this.rate = rate;
        this.status = status;
        this.dateCreate = dateCreate;
        this.importPrice = importPrice;
    }
    public Product( String productName, BigDecimal price, int quantity, int hold, String description, String imgPath, Brand brand, Category category, int releaseYear, BigDecimal sale, int rate, int status, Date dateCreate, BigDecimal importPrice) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.hold = hold;
        this.description = description;
        this.imgPath = imgPath;
        this.brand = brand;
        this.category = category;
        this.releaseYear = releaseYear;
        this.sale = sale;
        this.rate = rate;
        this.status = status;
        this.dateCreate = dateCreate;
        this.importPrice = importPrice;
    }
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getHold() {
        return hold;
    }

    public void setHold(int hold) {
        this.hold = hold;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BigDecimal getSale() {
        return sale;
    }

    public void setSale(BigDecimal sale) {
        this.sale = sale;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public BigDecimal getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(BigDecimal importPrice) {
        this.importPrice = importPrice;
    }

    @Override
    public String toString() {
        return "Product{" + "productId=" + productId + ", productName=" + productName + ", price=" + price + ", quantity=" + quantity + ", hold=" + hold + ", description=" + description + ", imgPath=" + imgPath + ", brand=" + brand + ", category=" + category + ", releaseYear=" + releaseYear + ", sale=" + sale + ", rate=" + rate + ", status=" + status + ", dateCreate=" + dateCreate + ", importPrice=" + importPrice + '}';
    }
    

    
}

