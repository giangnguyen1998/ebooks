package edu.nuce.giang.ebooks.models;

public class HomeRecycleModelClass {
    Integer bookImage;
    String bookName,authorName;

    public HomeRecycleModelClass(Integer bookImage, String bookName, String authorName) {
        this.bookImage = bookImage;
        this.bookName = bookName;
        this.authorName = authorName;
    }

    public Integer getBookImage() {
        return bookImage;
    }

    public void setBookImage(Integer bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
