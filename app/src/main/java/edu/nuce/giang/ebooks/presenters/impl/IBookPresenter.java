package edu.nuce.giang.ebooks.presenters.impl;

import android.support.annotation.NonNull;

import java.util.List;

import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.views.BookLibraryView;
import edu.nuce.giang.ebooks.views.BookView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IBookPresenter implements BookPresenter {

    private BookView view;
    private BookLibraryView libraryView;

    public IBookPresenter(BookLibraryView libraryView) {
        this.libraryView = libraryView;
    }

    public IBookPresenter(BookView view) {
        this.view = view;
    }

    @Override
    public void getListData() {
        view.loadingData();

        Utils.getEBookApiInstance()
                .findAllBooks()
                .enqueue(new Callback<List<BookModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookModel>> call,
                                           @NonNull Response<List<BookModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoadingData();
                            view.setListData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookModel>> call, @NonNull Throwable t) {
                        view.hideLoadingData();
                        view.onError(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getData(Integer integer) {
        view.loadingData();

        Utils.getEBookApiInstance()
                .findOneBookByBookId(integer)
                .enqueue(new Callback<List<BookModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookModel>> call,
                                           @NonNull Response<List<BookModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoadingData();
                            view.setData(response.body().get(0));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookModel>> call, @NonNull Throwable t) {
                        view.hideLoadingData();
                        view.onError(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getFilterResultsBooks(String value) {
        view.loadingData();

        Utils.getEBookApiInstance()
                .resultsFilterBooks(value)
                .enqueue(new Callback<List<BookModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookModel>> call,
                                           @NonNull Response<List<BookModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoadingData();
                            view.setFilterResultsBooks(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookModel>> call,
                                          @NonNull Throwable t) {
                        view.hideLoadingData();
                        view.onError(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getBooksRelatedCategory(int categoryId) {
        view.loadingData();
        Utils.getEBookApiInstance()
                .findAllBooksByCategoryId(categoryId)
                .enqueue(new Callback<List<BookModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookModel>> call,
                                           @NonNull Response<List<BookModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoadingData();
                            view.setRelatedBooksCategory(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookModel>> call, @NonNull Throwable t) {
                        view.hideLoadingData();
                        view.onError(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getBooksRelatedAuthor(int authorId) {
        view.loadingData();
        Utils.getEBookApiInstance()
                .findAllBooksByAuthorId(authorId)
                .enqueue(new Callback<List<BookModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookModel>> call,
                                           @NonNull Response<List<BookModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoadingData();
                            view.setRelatedBooksAuthor(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookModel>> call, @NonNull Throwable t) {
                        view.hideLoadingData();
                        view.onError(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getBooksByIds(List<Integer> ids) {
        libraryView.loadingData();
        Utils.getEBookApiInstance()
                .findAllBookByIds(ids)
                .enqueue(new Callback<List<BookModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<BookModel>> call,
                                           @NonNull Response<List<BookModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            libraryView.hideLoadingData();
                            libraryView.setListData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<BookModel>> call,@NonNull Throwable t) {
                        libraryView.hideLoadingData();
                        libraryView.onError(t.getLocalizedMessage());
                    }
                });
    }
}
