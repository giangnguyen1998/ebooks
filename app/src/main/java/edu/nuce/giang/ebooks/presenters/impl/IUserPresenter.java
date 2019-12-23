package edu.nuce.giang.ebooks.presenters.impl;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.models.CheckLoginModel;
import edu.nuce.giang.ebooks.models.StatusModel;
import edu.nuce.giang.ebooks.models.UserModel;
import edu.nuce.giang.ebooks.presenters.UserPresenter;
import edu.nuce.giang.ebooks.views.UserImageView;
import edu.nuce.giang.ebooks.views.UserRegisterView;
import edu.nuce.giang.ebooks.views.UserView;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IUserPresenter implements UserPresenter {

    private UserView view;
    private UserImageView imageView;
    private UserRegisterView registerView;

    public IUserPresenter(UserView view, UserRegisterView registerView) {
        this.view = view;
        this.registerView = registerView;
    }

    public IUserPresenter(UserImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void checkLogin(String username, String password) {
        view.loadingCheck();
        Utils.getEBookApiInstance()
                .checkLogin(username, password)
                .enqueue(new Callback<CheckLoginModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CheckLoginModel> call,
                                           @NonNull Response<CheckLoginModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            view.hideLoading();
                            if (response.body().getError()) {
                                view.loginFailure(response.body().getMessage());
                            } else {
                                view.loginSuccess(response.body());
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CheckLoginModel> call,
                                          @NonNull Throwable t) {
                        view.hideLoading();
                        view.onError(t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void updateAvatarUser(RequestBody userId, MultipartBody.Part image) {

        Utils.getEBookApiInstance()
                .updateImageUser(userId, image)
                .enqueue(new Callback<CheckLoginModel>() {
                    @Override
                    public void onResponse(@NonNull Call<CheckLoginModel> call,
                                           @NonNull Response<CheckLoginModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            imageView.setAvatar(response.body());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CheckLoginModel> call, @NonNull Throwable t) {
                        Log.e("Error", t.getLocalizedMessage());
                    }
                });
    }

    @Override
    public void registerUser(UserModel model) {
        registerView.processRegister();
        Utils.getEBookApiInstance()
                .saveUser(
                        model.getFullname(),
                        model.getUsername(),
                        model.getPassword(),
                        model.getPhone()
                )
                .enqueue(new Callback<StatusModel>() {
                    @Override
                    public void onResponse(@NonNull Call<StatusModel> call,
                                           @NonNull Response<StatusModel> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            registerView.finishedProcessRegister();
                            if (response.body().getStatus() != null) {
                                registerView.registerSuccess(response.body().getStatus());
                            } else
                                registerView.registerFailure("Tên đăng nhập đã tồn tại!");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StatusModel> call,@NonNull Throwable t) {
                        registerView.finishedProcessRegister();
                        registerView.registerFailure("Đăng ký thất bại!");
                    }
                });
    }
}
