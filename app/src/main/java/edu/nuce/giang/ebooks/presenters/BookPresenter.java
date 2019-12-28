package edu.nuce.giang.ebooks.presenters;

import java.util.List;

import edu.nuce.giang.ebooks.base.BasePresenter;

public interface BookPresenter extends BasePresenter<Integer> {
    void getFilterResultsBooks(String value);
    void getBooksRelatedCategory(int categoryId);
    void getBooksRelatedAuthor(int authorId);
    void getBooksByIds(List<Integer> ids);
    void getBooksHighScore();
}
