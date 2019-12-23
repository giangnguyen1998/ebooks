package edu.nuce.giang.ebooks.models;

public class FictionModel {
    Integer image;
    String bookName,author,price;

    public FictionModel(Integer image, String bookName, String author, String price) {
        this.image = image;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
