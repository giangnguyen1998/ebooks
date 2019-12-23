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
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Regular;
import edu.nuce.giang.ebooks.models.CollectionModel;


public class CollectionsBookAdapter extends RecyclerView.Adapter<CollectionsBookAdapter.MyViewHolder> {

    private Context context;

    private List<CollectionModel> collections;

    private CategoryItemClickListener clickListener;

    public CollectionsBookAdapter(Context context, List<CollectionModel> collections,
                                  CategoryItemClickListener clickListener) {
        this.collections = collections;
        this.context = context;
        this.clickListener = clickListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bookCategory)
        MyTextView_Roboto_Regular bookCategory;
        @BindView(R.id.totalBook)
        MyTextView_Roboto_Regular totalBook;
        @BindView(R.id.iconImage)
        ImageView categoryImage;

        public MyViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            categoryImage.setOnClickListener(v -> {
                clickListener.onItemClicked(categoryImage, collections.get(getAdapterPosition()));
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_ebooks_book_collection,
                        parent,
                        false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CollectionModel model = collections.get(position);
        //set value item
        holder.bookCategory.setText(model.getCategoryName());
        Picasso.get()
                .load(Utils.URL + model.getImageUrl())
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(holder.categoryImage);
        String totalBooks = model.getTotalBook() + " Books";
        holder.totalBook.setText(totalBooks);
    }

    @Override
    public int getItemCount() {
        return collections.size();
    }

}
