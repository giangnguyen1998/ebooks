package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.icu.text.UnicodeSetSpanner;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.models.ImageModel;

public class GridViewAdapter extends ArrayAdapter<ImageModel> {

    Context context;
    ViewHolder viewHolder;
    ArrayList<ImageModel> al_menu;
    int int_position;


    public GridViewAdapter(Context context, ArrayList<ImageModel> al_menu, int int_position) {
        super(context, R.layout.item_photo_folder, al_menu);
        this.al_menu = al_menu;
        this.context = context;
        this.int_position = int_position;
    }

    @Override
    public int getCount() {

        Log.e("ADAPTER LIST SIZE", al_menu.get(int_position).getAllImagePath().size() + "");
        return al_menu.get(int_position).getAllImagePath().size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if (al_menu.get(int_position).getAllImagePath().size() > 0) {
            return al_menu.get(int_position).getAllImagePath().size();
        } else {
            return 1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_photo_folder, parent,
                    false);
            viewHolder.tv_foldern = convertView.findViewById(R.id.tv_folder);
            viewHolder.tv_foldersize = convertView.findViewById(R.id.tv_folder2);
            viewHolder.iv_image = convertView.findViewById(R.id.iv_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_foldern.setVisibility(View.GONE);
        viewHolder.tv_foldersize.setVisibility(View.GONE);

        Glide.with(context)
                .load("file://" + al_menu.get(int_position).getAllImagePath().get(position))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(viewHolder.iv_image);

        return convertView;

    }

    private static class ViewHolder {
        TextView tv_foldern, tv_foldersize;
        ImageView iv_image;
    }
}