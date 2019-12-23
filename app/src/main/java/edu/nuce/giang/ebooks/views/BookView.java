package edu.nuce.giang.ebooks.views;

import java.util.List;

import edu.nuce.giang.ebooks.base.BaseView;
import edu.nuce.giang.ebooks.models.BookModel;

public interface BookView extends BaseView<BookModel> {
    void setFilterResultsBooks(List<BookModel> models);
    void setRelatedBooksCategory(List<BookModel> models);
    void setRelatedBooksAuthor(List<BookModel> models);
}
