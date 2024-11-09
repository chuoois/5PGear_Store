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
public class Blog {
    private int blogId;
    private String title;
    private String description;
    private Date dateCreated;
    private String imgPath;
    private int marketerId;   
    private String status;
    private int categoryId;
    private String tags;
    private String brief_info;

    public Blog() {
    }

    public Blog(int blogId, String title, String description, Date dateCreated, String imgPath, int marketerId, String status, int categoryId, String tags, String brief_info) {
        this.blogId = blogId;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.imgPath = imgPath;
        this.marketerId = marketerId; 
        this.status = status;
        this.categoryId = categoryId;
        this.tags = tags;
        this.brief_info = brief_info;
    }
    public Blog(String title, String description, Date dateCreated, String imgPath, int marketerId, String status, int categoryId, String tags, String brief_info) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
        this.imgPath = imgPath;
        this.marketerId = marketerId;  // Change here
        this.status = status;
        this.categoryId = categoryId;
        this.tags = tags;
        this.brief_info = brief_info;
    }

    public int getBlogId() {
        return blogId;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

     public int getMarketerId() {  // Change here: Rename to getMarketerId
        return marketerId;  // Change here
    }

    public void setMarketerId(int marketerId) {  // Change here: Rename to setMarketerId
        this.marketerId = marketerId;  // Change here
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getBrief_info() {
        return brief_info;
    }

    public void setBrief_info(String brief_info) {
        this.brief_info = brief_info;
    }

    @Override
    public String toString() {
        return "Blog{" + "blogId=" + blogId + ", title=" + title + ", description=" + description + ", dateCreated=" + dateCreated + ", imgPath=" + imgPath + ", marketerId=" + marketerId + ", status=" + status + ", categoryId=" + categoryId + ", tags=" + tags + ", brief_info=" + brief_info + '}';
    }
    

    

    
   

    
    
}
