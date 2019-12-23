package edu.nuce.giang.ebooks.views;

public interface CommentSaveView {
    void processSaveComment();
    void finishedProcessSaveComment();
    void saveCommentSuccess(String status);
    void saveCommentFailure(String error);
    void onErrorSave(String error);
}
