package edu.nuce.giang.ebooks.activities.author;

import java.util.List;

import edu.nuce.giang.ebooks.models.AuthorModel;
import edu.nuce.giang.ebooks.models.BookModel;

public class EBookAuthorMPV {

    public interface CoCoBookAuthorView {
        void setAuthor(AuthorModel author);
        void setBooksAuthor(List<BookModel> models);
        void loadingAuthor();
        void loadedAuthor();
        void loadingBooksAuthor();
        void loadedBooksAuthor();
        void onErrorLoading(String message);
    }

    public interface CoCoBookAuthorPresenter {
        void getAuthorByAuthorId(int authorId);
        void getBooksByAuthorId(int authorId);
    }

}
