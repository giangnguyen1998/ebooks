package edu.nuce.giang.ebooks.models;

import java.io.Serializable;

public class LibraryModel implements Serializable {
    private Long id;
    private Integer bookId;
    private Integer pageCurrent;
    private Integer finishBook;

    public LibraryModel(Long id, Integer bookId, Integer pageCurrent, Integer finishBook) {
        this.id = id;
        this.bookId = bookId;
        this.pageCurrent = pageCurrent;
        this.finishBook = finishBook;
    }

    public LibraryModel(Integer bookId, Integer pageCurrent, Integer finishBook) {
        this.bookId = bookId;
        this.pageCurrent = pageCurrent;
        this.finishBook = finishBook;
    }

    public LibraryModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(Integer pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public Integer getFinishBook() {
        return finishBook;
    }

    public void setFinishBook(Integer finishBook) {
        this.finishBook = finishBook;
    }
}
