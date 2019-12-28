package edu.nuce.giang.ebooks.presenters.impl;

import android.support.annotation.NonNull;

import java.util.List;

import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.models.AuthorModel;
import edu.nuce.giang.ebooks.presenters.AuthorPresenter;
import edu.nuce.giang.ebooks.views.AuthorTopView;
import edu.nuce.giang.ebooks.views.AuthorView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IAuthorPresenter implements AuthorPresenter {

    private AuthorView view;

    private AuthorTopView authorTopView;

    public IAuthorPresenter(AuthorTopView authorTopView) {
        this.authorTopView = authorTopView;
    }

    public IAuthorPresenter(AuthorView view) {
        this.view = view;
    }

    @Override
    public void getListData() {
        view.loadingData();
        Utils.getEBookApiInstance()
                .findAllAuthors()
                .enqueue(new Callback<List<AuthorModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<AuthorModel>> call,
                                           @NonNull Response<List<AuthorModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoadingData();
                            view.setListData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<AuthorModel>> call, @NonNull Throwable t) {
                        view.hideLoadingData();
                        view.onError(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getData(Integer integer) {

    }

    @Override
    public void getTopAuthors() {
        authorTopView.loadingAuthors();
        Utils.getEBookApiInstance()
                .findAllTopAuthors()
                .enqueue(new Callback<List<AuthorModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<AuthorModel>> call,
                                           @NonNull Response<List<AuthorModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            authorTopView.hideLoadingAuthors();
                            authorTopView.setAuthorsTop(response.body());
                        }
                     }

                    @Override
                    public void onFailure(@NonNull Call<List<AuthorModel>> call, @NonNull Throwable t) {
                        authorTopView.hideLoadingAuthors();
                        authorTopView.onErrorLoadingAuthors(t.getLocalizedMessage());
                    }
                });
    }
}
