package edu.nuce.giang.ebooks.activities.author;

import android.support.annotation.NonNull;

import java.util.List;

import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.models.AuthorModel;
import edu.nuce.giang.ebooks.models.BookModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IEBookAuthorPresenter implements EBookAuthorMPV.CoCoBookAuthorPresenter {

    private EBookAuthorMPV.CoCoBookAuthorView view;

    public IEBookAuthorPresenter(EBookAuthorMPV.CoCoBookAuthorView view) {
        this.view = view;
    }

    @Override
    public void getAuthorByAuthorId(int authorId) {
        view.loadingAuthor();

        Utils.getEBookApiInstance()
                .findOneAuthor(authorId)
                .enqueue(new Callback<List<AuthorModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<AuthorModel>> call,
                                           @NonNull Response<List<AuthorModel>> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            view.loadedAuthor();
                            view.setAuthor(response.body().get(0));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<AuthorModel>> call, @NonNull Throwable t) {
                        view.loadedAuthor();
                        view.onErrorLoading(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getBooksByAuthorId(int authorId) {
        view.loadingBooksAuthor();

        Utils.getEBookApiInstance()
                .findAllBooksByAuthorId(authorId)
                .enqueue(new Callback<List<BookModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookModel>> call,
                                           @NonNull Response<List<BookModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.loadedBooksAuthor();
                            view.setBooksAuthor(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookModel>> call, @NonNull Throwable t) {
                        view.loadedBooksAuthor();
                        view.onErrorLoading(t.getLocalizedMessage());
                    }
                });
    }
}
