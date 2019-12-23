package edu.nuce.giang.ebooks.presenters;

import edu.nuce.giang.ebooks.models.CommentModel;

public interface CommentPresenter {
    void getCommentsOfBook(int bookId);
    void saveComment(CommentModel model);
}
