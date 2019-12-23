package edu.nuce.giang.ebooks.activities.category;

import android.support.annotation.NonNull;

import java.util.List;

import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.models.CollectionModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IEBookCategoriesPresenter implements EBookCategoriesMPV.CoCoBookCategoriesPresenter {

    private EBookCategoriesMPV.CoCoBookCategoriesView view;

    public IEBookCategoriesPresenter(EBookCategoriesMPV.CoCoBookCategoriesView view) {
        this.view = view;
    }

    @Override
    public void getBooksByCategory(int categoryId) {
        view.loadingBooks();
        Utils.getEBookApiInstance()
                .findAllBooksByCategoryId(categoryId)
                .enqueue(new Callback<List<BookModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookModel>> call,
                                           @NonNull Response<List<BookModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.loadedBooks();
                            view.setBooksByCategory(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookModel>> call, @NonNull Throwable t) {
                        view.loadedBooks();
                        view.onErrorLoading(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getCategoryByCategoryId(int categoryId) {
        Utils.getEBookApiInstance()
                .findOneCategory(categoryId)
                .enqueue(new Callback<List<CollectionModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CollectionModel>> call,
                                           @NonNull Response<List<CollectionModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.setCategoryByCategoryId(response.body().get(0));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CollectionModel>> call, @NonNull Throwable t) {
                        view.onErrorLoading(t.getLocalizedMessage());
                    }
                });
    }
}
