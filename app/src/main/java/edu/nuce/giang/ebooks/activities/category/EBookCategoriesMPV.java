package edu.nuce.giang.ebooks.activities.category;

import java.util.List;

import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.models.CollectionModel;

public class EBookCategoriesMPV {

    public interface CoCoBookCategoriesView {
        void loadingBooks();
        void loadedBooks();
        void onErrorLoading(String message);
        void setBooksByCategory(List<BookModel> models);
        void setCategoryByCategoryId(CollectionModel model);
    }

    public interface CoCoBookCategoriesPresenter {
        void getBooksByCategory(int categoryId);
        void getCategoryByCategoryId(int categoryId);
    }

}
