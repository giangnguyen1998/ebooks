package edu.nuce.giang.ebooks.base;

import java.util.List;

public interface BaseView<T> {
    void setListData(List<T> models);
    void setData(T model);
    void loadingData();
    void hideLoadingData();
    void onError(String error);
}
