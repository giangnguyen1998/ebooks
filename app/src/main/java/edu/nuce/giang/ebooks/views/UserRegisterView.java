package edu.nuce.giang.ebooks.views;

public interface UserRegisterView {
    void registerSuccess(String message);
    void registerFailure(String error);
    void processRegister();
    void finishedProcessRegister();
}
