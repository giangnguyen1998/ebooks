package edu.nuce.giang.ebooks.adapters;

import android.widget.ImageView;

import edu.nuce.giang.ebooks.models.BookModel;

public interface BookItemClickListener {
    void onItemClicked(ImageView bookImage, BookModel model);
}
