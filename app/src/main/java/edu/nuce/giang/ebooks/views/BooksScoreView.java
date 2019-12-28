package edu.nuce.giang.ebooks.views;

import java.util.List;

import edu.nuce.giang.ebooks.models.BookModel;

public interface BooksScoreView {
    void setBooksHighScore(List<BookModel> modelList);
    void loadingBooks();
    void hideLoadingBooks();
    void onErrorLoading(String message);
}
