package edu.nuce.giang.ebooks.base;

import java.io.Serializable;

public interface BasePresenter<ID extends Serializable> {
    void getListData();
    void getData(ID id);
}
