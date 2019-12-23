package edu.nuce.giang.ebooks.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CollectionModel implements Serializable {

    @SerializedName("id_category")
    @Expose
    private Integer idCategory;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("total_book")
    @Expose
    private Integer totalBook;

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(Integer totalBook) {
        this.totalBook = totalBook;
    }
}
