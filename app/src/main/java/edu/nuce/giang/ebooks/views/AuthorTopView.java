package edu.nuce.giang.ebooks.views;

import java.util.List;

import edu.nuce.giang.ebooks.models.AuthorModel;

public interface AuthorTopView {
    void setAuthorsTop(List<AuthorModel> models);
    void loadingAuthors();
    void hideLoadingAuthors();
    void onErrorLoadingAuthors(String message);
}
