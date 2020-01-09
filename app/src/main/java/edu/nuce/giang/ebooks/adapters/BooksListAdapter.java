package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Regular;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.models.LibraryModel;


public class BooksListAdapter extends RecyclerView.Adapter<BooksListAdapter.MyViewHolder> {

    private Context context;

    private List<BookModel> books;

    private OnItemBookListClickListener clickListener;

    public void setClickListener(OnItemBookListClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_bookList_image)
        ImageView itemBookListImage;
        @BindView(R.id.item_bookList_name)
        MyTextView_Roboto_Regular itemBookListName;
        @BindView(R.id.item_bookList_author)
        MyTextView_Roboto_Regular itemBookListAuthor;
        @BindView(R.id.like_book)
        ImageView likeBook;
        @BindView(R.id.item_bookList_rating)
        RatingBar itemBookRating;
        @BindView(R.id.item_bookList_score)
        MyTextView_Roboto_Regular itemScore;

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

    public BooksListAdapter(Context context, List<BookModel> books) {
        this.books = books;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_ebooks_book_list,
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
                .into(holder.itemBookListImage);

        holder.itemBookListName.setText(books.get(i).getName());
        holder.itemBookListAuthor.setText(books.get(i).getAuthorName());
        holder.itemBookRating.setRating(books.get(i).getScore());
        holder.itemScore.setText(String.valueOf(books.get(i).getScore()));
        holder.likeBook.setOnClickListener(v -> {
            try {
                LibraryModel model = Utils.getDataBaseUtilsInstance(context)
                        .getBook(books.get(i).getId());
                if (model == null) {
                    Utils.getDataBaseUtilsInstance(context)
                            .addBook(new LibraryModel(
                                    books.get(i).getId(),
                                    0,
                                    0
                            ));
                    new CustomSweetAlertDialog(context)
                            .alertDialogSuccess(
                                    "Successfully!"
                                    , "Your books has been saved to books mark!"
                            );
                } else {
                    new CustomSweetAlertDialog(context)
                            .alertDialogError(
                                    "Failure!",
                                    "Your book has been had in books mark!");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public interface OnItemBookListClickListener {
        void onItemClicked(View view, BookModel model);
    }

}