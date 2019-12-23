package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Bold;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Regular;
import edu.nuce.giang.ebooks.models.BookModel;

public class BookFilterAdapter extends RecyclerView.Adapter<BookFilterAdapter.MyViewHolder> {
    private Context context;
    private List<BookModel> books;
    private OnItemBookListClickListener clickListener;

    public void setClickListener(OnItemBookListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_filter_bookImage)
        ImageView itemFilterImageBook;
        @BindView(R.id.item_filter_bookName)
        MyTextView_Roboto_Bold itemFilterBookName;
        @BindView(R.id.item_filter_bookAuthor)
        MyTextView_Roboto_Regular itemFilterBookAuthor;

        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClicked(v, books.get(getAdapterPosition()));
        }
    }

    public BookFilterAdapter(Context context, List<BookModel> books) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_ebooks_book_filter,
                        viewGroup,
                        false
                );
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        Picasso.get()
                .load(Utils.URL + books.get(i).getUrlImage())
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(holder.itemFilterImageBook);
        holder.itemFilterBookName.setText(books.get(i).getName());
        holder.itemFilterBookAuthor.setText(books.get(i).getAuthorName());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public interface OnItemBookListClickListener {
        void onItemClicked(View view, BookModel model);
    }
}
