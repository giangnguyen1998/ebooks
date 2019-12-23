package edu.nuce.giang.ebooks.views;

import java.util.List;

import edu.nuce.giang.ebooks.models.CommentModel;

public interface CommentView {
    void loadingData();

    void hideLoadingData();

    void setListComments(List<CommentModel> models);

    void onErrorLoading(String error);
}
