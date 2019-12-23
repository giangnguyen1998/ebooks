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
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Medium;
import edu.nuce.giang.ebooks.models.AuthorModel;

public class AuthorsBookAdapter extends RecyclerView.Adapter<AuthorsBookAdapter.AuthorViewHolder> {

    private Context context;
    private List<AuthorModel> authors;
    private AuthorItemClickListener clickListener;

    public AuthorsBookAdapter(Context context, List<AuthorModel> authors, AuthorItemClickListener listener) {
        this.context = context;
        this.authors = authors;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(
                        R.layout.item_ebooks_author,
                        viewGroup,
                        false
                );
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder authorViewHolder, int i) {
        Picasso.get()
                .load(Utils.URL + authors.get(i).getAuthorImage())
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(authorViewHolder.authorImage);
        authorViewHolder.authorName.setText(authors.get(i).getAuthorName());
        String totalBooks = authors.get(i).getTotalBook() + " Books Published";
        authorViewHolder.authorTotalBooks.setText(totalBooks);
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public class AuthorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_author_image)
        ImageView authorImage;
        @BindView(R.id.item_author_name)
        MyTextView_Roboto_Bold authorName;
        @BindView(R.id.item_author_totalBooks)
        MyTextView_Roboto_Medium authorTotalBooks;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            authorImage.setOnClickListener(view -> {
                clickListener.onItemClicked(authorImage, authors.get(getAdapterPosition()));
            });
        }
    }
}
