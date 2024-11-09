package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Feedback {

    private int feedbackId;
    private Customer customerId;
    private Product productId;
    private String comment;
    private String status;
    private int rating;
    private Date feedbackDate;

    // Constructors
    public Feedback() {
    }

    public Feedback(int feedbackId, Customer customerId, Product productId, String comment, String status, int rating, Date feedbackDate) {
        this.feedbackId = feedbackId;
        this.customerId = customerId;
        this.productId = productId;
        this.comment = comment;
        this.status = status;
        this.rating = rating;
        this.feedbackDate = feedbackDate;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Customer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Customer customerId) {
        this.customerId = customerId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(Date feedbackDate) {
        this.feedbackDate = feedbackDate;
    }
 public String getDobString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(this.feedbackDate);
    }
}
