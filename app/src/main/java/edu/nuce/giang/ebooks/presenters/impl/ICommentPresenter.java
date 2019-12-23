package edu.nuce.giang.ebooks.presenters.impl;

import android.support.annotation.NonNull;

import java.util.List;

import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.models.CommentModel;
import edu.nuce.giang.ebooks.models.StatusModel;
import edu.nuce.giang.ebooks.presenters.CommentPresenter;
import edu.nuce.giang.ebooks.views.CommentSaveView;
import edu.nuce.giang.ebooks.views.CommentView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ICommentPresenter implements CommentPresenter {

    private CommentView view;

    private CommentSaveView saveView;

    public ICommentPresenter(CommentView view, CommentSaveView saveView) {
        this.saveView = saveView;
        this.view = view;
    }

    @Override
    public void getCommentsOfBook(int bookId) {
        view.loadingData();
        Utils.getEBookApiInstance()
                .findCommentsOfBook(bookId)
                .enqueue(new Callback<List<CommentModel>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<CommentModel>> call,
                                           @NonNull Response<List<CommentModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoadingData();
                            view.setListComments(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<CommentModel>> call, @NonNull Throwable t) {
                        view.hideLoadingData();
                        view.onErrorLoading(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void saveComment(CommentModel model) {
        saveView.processSaveComment();
        Utils.getEBookApiInstance()
                .saveComment(
                        model.getUserId(),
                        model.getBookId(),
                        model.getContent(),
                        model.getScore()
                ).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(@NonNull Call<StatusModel> call, @NonNull Response<StatusModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    saveView.finishedProcessSaveComment();
                    if (response.body().getStatus() != null) {
                        saveView.saveCommentSuccess(response.body().getStatus());
                    } else {
                        saveView.saveCommentFailure("Bình luận thất bại!");
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<StatusModel> call, @NonNull Throwable t) {
                saveView.finishedProcessSaveComment();
                saveView.onErrorSave(t.getLocalizedMessage());
            }
        });
    }
}
