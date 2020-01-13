package edu.nuce.giang.ebooks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentModel implements Serializable {

    @SerializedName("book_id")
    @Expose
    private Integer bookId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("score")
    @Expose
    private Float score;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("createddate")
    @Expose
    private String createddate;

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
