package edu.nuce.giang.ebooks.presenters;

import edu.nuce.giang.ebooks.models.UserModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface UserPresenter {
    void checkLogin(String username, String password);
    void updateAvatarUser(RequestBody userId, MultipartBody.Part image);
    void registerUser(UserModel model);
}
