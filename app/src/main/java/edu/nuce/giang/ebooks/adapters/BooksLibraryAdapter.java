package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import edu.nuce.giang.ebooks.models.LibraryModel;
import edu.nuce.giang.ebooks.repositories.DataBaseUtils;

public class BooksLibraryAdapter extends RecyclerView.Adapter<BooksLibraryAdapter.LibraryViewHolder> {

    private Context context;
    private List<BookModel> models;
    private List<LibraryModel> libraryModels;

    public BooksLibraryAdapter(Context context, List<BookModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public LibraryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
                R.layout.item_ebooks_library,
                viewGroup,
                false
        );
        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryViewHolder libraryViewHolder, int i) {
        Picasso.get()
                .load(Utils.URL + models.get(i).getUrlImage())
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(libraryViewHolder.bookImage1);
        libraryViewHolder.bookName1.setText(models.get(i).getName());
        libraryViewHolder.authorName1.setText(models.get(i).getAuthorName());
        LibraryModel model = null;
        try {
            model = Utils.getDataBaseUtilsInstance(context).getBook(models.get(i).getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert model != null;
        if (model.getPageCurrent() != 0 && model.getFinishBook() != 0) {
            libraryViewHolder.pageToPages.setText(model.getPageCurrent() + "/" + model.getFinishBook());
        } else
            libraryViewHolder.frameLayout.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class LibraryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.bookImage1)
        ImageView bookImage1;
        @BindView(R.id.pageToPages)
        TextViewSFProDisplaySemibold pageToPages;
        @BindView(R.id.bookName1)
        TextViewSFProDisplaySemibold bookName1;
        @BindView(R.id.authorName1)
        MyTextView_Roboto_Medium authorName1;
        @BindView(R.id.backPageToPages)
        FrameLayout frameLayout;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
