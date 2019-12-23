package edu.nuce.giang.ebooks.adapters;

import android.widget.ImageView;

import edu.nuce.giang.ebooks.models.AuthorModel;

public interface AuthorItemClickListener {
    void onItemClicked(ImageView authorImage, AuthorModel model);
}
