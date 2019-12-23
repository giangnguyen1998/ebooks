package edu.nuce.giang.ebooks.presenters.impl;

import android.support.annotation.NonNull;
import java.util.List;

import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.models.CollectionModel;
import edu.nuce.giang.ebooks.presenters.CollectionPresenter;
import edu.nuce.giang.ebooks.views.CollectionView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ICollectionPresenter implements CollectionPresenter {

    private CollectionView view;

    public ICollectionPresenter(CollectionView view) {
        this.view = view;
    }

    @Override
    public void getListData() {
        view.loadingData();

        Utils.getEBookApiInstance()
                .findAllCollections()
                .enqueue(new Callback<List<CollectionModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CollectionModel>> call,
                                           @NonNull Response<List<CollectionModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoadingData();
                            view.setListData(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CollectionModel>> call,
                                          @NonNull Throwable t) {
                        view.hideLoadingData();
                        view.onError(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void getData(Integer integer) {

    }
}
