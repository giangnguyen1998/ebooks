package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

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
    private int value;
    private OnBookLibraryClickListener listener;

    public BooksLibraryAdapter(Context context, List<BookModel> models, int value) {
        this.context = context;
        this.models = models;
        this.value = value;
    }

    public void setListener(OnBookLibraryClickListener listener) {
        this.listener = listener;
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
        if (value == 3) {
            libraryViewHolder.frameLayout.setVisibility(View.GONE);
        } else {
            LibraryModel model = null;
            try {
                model = Utils.getDataBaseUtilsInstance(context).getBook(models.get(i).getId());
                assert model != null;
                libraryViewHolder.pageToPages.setText(model.getPageCurrent() + "/" + model.getFinishBook());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        libraryViewHolder.bookDelete.setOnClickListener(v -> {
            try {
                LibraryModel model = Utils.getDataBaseUtilsInstance(context)
                        .getBook(models.get(i).getId());
                listener.onDeleteBookClick(v, model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class LibraryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
        @BindView(R.id.book_delete)
        ImageView bookDelete;

        public LibraryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClickItem(v, models.get(getAdapterPosition()));
        }
    }

    public interface OnBookLibraryClickListener {
        void onClickItem(View v, BookModel model);
        void onDeleteBookClick(View v, LibraryModel model);
    }
}
