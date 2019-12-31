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
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Medium;
import edu.nuce.giang.ebooks.customfonts.TextViewSFProDisplaySemibold;
import edu.nuce.giang.ebooks.models.BookModel;

public class BookPagerAdapter extends RecyclerView.Adapter<BookPagerAdapter.PagerViewHolder> {

    private Context context;
    private List<BookModel> models;
    private BookItemClickListener clickListener;

    public BookPagerAdapter(Context context, List<BookModel> models, BookItemClickListener clickListener) {
        this.context = context;
        this.models = models;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public PagerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_ebooks_book_view_pager,
                viewGroup,
                false
        );
        return new PagerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PagerViewHolder pagerViewHolder, int i) {
        pagerViewHolder.itemBookAuthor.setText(models.get(i).getAuthorName());
        pagerViewHolder.itemBookName.setText(models.get(i).getName());
        Picasso.get()
                .load(Utils.URL + models.get(i).getUrlImage())
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(pagerViewHolder.itemBookImage);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class PagerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_book_image)
        ImageView itemBookImage;
        @BindView(R.id.item_book_name)
        TextViewSFProDisplaySemibold itemBookName;
        @BindView(R.id.item_book_author)
        MyTextView_Roboto_Medium itemBookAuthor;

        public PagerViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                clickListener.onItemClicked(itemBookImage, models.get(getAdapterPosition()));
            });

        }
    }

}
