package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Medium;
import edu.nuce.giang.ebooks.customfonts.TextViewSFProDisplaySemibold;
import edu.nuce.giang.ebooks.models.BookModel;

public class ViewPagerBooksAdapter extends PagerAdapter {

    private List<BookModel> books;
    private Context context;
    private BookItemClickListener clickListener;

    public ViewPagerBooksAdapter(List<BookModel> books, Context context, BookItemClickListener clickListener) {
        this.books = books;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view.equals(o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_ebooks_book_view_pager,
                container,
                false
        );

        //init data
        ImageView itemBookImage = view.findViewById(R.id.item_book_image);
        TextViewSFProDisplaySemibold itemBookName = view.findViewById(R.id.item_book_name);
        MyTextView_Roboto_Medium itemBookAuthor = view.findViewById(R.id.item_book_author);

        String bookName = books.get(position).getName();
        String image = books.get(position).getUrlImage();
        String authorName = books.get(position).getAuthorName();

        Picasso.get()
                .load(Utils.URL + image)
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(itemBookImage);
        itemBookName.setText(bookName);
        itemBookAuthor.setText(authorName);

        itemBookImage.setOnClickListener(v -> {
            clickListener.onItemClicked(itemBookImage, books.get(position));
        });

        container.addView(view, 0);

        return view;
    }

    @Override
    public float getPageWidth(int position) {
        return (0.45f);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
