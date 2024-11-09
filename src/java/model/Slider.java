/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Bùi Khánh Linh
 */
public class Slider {
    private int sliderId;
    private String imgPath;
    private String backlink;
    private String status;
    private String title;
    private int updateBy;
    private Date createdAt;
    private Date updateAt;
    private int createdBy;
    private int productId;

    public Slider() {
    }

    public Slider(int sliderId, String imgPath, String backlink, String status, String title, int updateBy, Date createdAt, Date updateAt, int createdBy, int productId) {
        this.sliderId = sliderId;
        this.imgPath = imgPath;
        this.backlink = backlink;
        this.status = status;
        this.title = title;
        this.updateBy = updateBy;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.createdBy = createdBy;
        this.productId = productId;
    }
    public Slider( String imgPath, String backlink, String status, String title, int updateBy, Date createdAt, Date updateAt, int createdBy, int productId) {
        this.imgPath = imgPath;
        this.backlink = backlink;
        this.status = status;
        this.title = title;
        this.updateBy = updateBy;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.createdBy = createdBy;
        this.productId = productId;
    }

    public int getSliderId() {
        return sliderId;
    }

    public void setSliderId(int sliderId) {
        this.sliderId = sliderId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getBacklink() {
        return backlink;
    }

    public void setBacklink(String backlink) {
        this.backlink = backlink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(int updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Slider{" + "sliderId=" + sliderId + ", imgPath=" + imgPath + ", backlink=" + backlink + ", status=" + status + ", title=" + title + ", updateBy=" + updateBy + ", createdAt=" + createdAt + ", updateAt=" + updateAt + ", createdBy=" + createdBy + ", productId=" + productId + '}';
    }

    
    
    
}


