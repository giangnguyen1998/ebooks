package edu.nuce.giang.ebooks.adapters;

import android.widget.ImageView;

import edu.nuce.giang.ebooks.models.CollectionModel;

public interface CategoryItemClickListener {
    void onItemClicked(ImageView categoryImage, CollectionModel model);
}
