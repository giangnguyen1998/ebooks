package edu.nuce.giang.ebooks.views;

import edu.nuce.giang.ebooks.models.CheckLoginModel;

public interface UserView {
    void loginSuccess(CheckLoginModel model);
    void loginFailure(String message);
    void loadingCheck();
    void hideLoading();
    void onError(String error);
}
