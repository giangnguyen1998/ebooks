package edu.nuce.giang.ebooks.activities.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.adapters.BookItemClickListener;
import edu.nuce.giang.ebooks.adapters.ViewPagerBooksAdapter;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Medium;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.BookView;


public class EBookFrictionFragment extends Fragment implements BookView, BookItemClickListener {

    @BindView(R.id.shimmerPagerBooksAuthor)
    ShimmerFrameLayout shimmerBooksAuthor;
    @BindView(R.id.shimmerPagerBooksCategory)
    ShimmerFrameLayout shimmerBooksCategory;
    @BindView(R.id.viewPagerBookRelatedCategory)
    ViewPager viewPagerBookRelatedCategory;
    @BindView(R.id.viewPagerBookRelatedAuthor)
    ViewPager viewPagerBookRelatedAuthor;
    @BindView(R.id.relatedBookAuthor)
    MyTextView_Roboto_Medium relatedBookAuthor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ebooks_friction, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BookPresenter presenter = new IBookPresenter(this);

        if (getArguments() != null) {
            int authorId = getArguments().getInt("authorId");
            int categoryId = getArguments().getInt("categoryId");
            String authorName = getArguments().getString("authorName");
            relatedBookAuthor.setText("Books By " + authorName);
            presenter.getBooksRelatedCategory(categoryId);
            presenter.getBooksRelatedAuthor(authorId);
        }
    }

    private List<BookModel> getFiveItems(List<BookModel> books) {
        List<BookModel> items = new ArrayList<>();
        if (books.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                items.add(books.get(i));
            }
            return items;
        }
        return books;
    }

    @Override
    public void onItemClicked(ImageView bookImage, BookModel model) {
        Intent intent = new Intent(getContext(), EBookFictionActivity.class);
        intent.putExtra("book", model);
        startActivity(intent);
    }

    @Override
    public void setFilterResultsBooks(List<BookModel> models) {

    }

    @Override
    public void setRelatedBooksCategory(List<BookModel> models) {
        ViewPagerBooksAdapter adapter = new ViewPagerBooksAdapter(
                getFiveItems(models),
                getContext(),
                this
        );
        viewPagerBookRelatedCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setRelatedBooksAuthor(List<BookModel> models) {
        ViewPagerBooksAdapter adapter = new ViewPagerBooksAdapter(
                getFiveItems(models),
                getContext(),
                this);
        viewPagerBookRelatedAuthor.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadingData() {
        shimmerBooksAuthor.setVisibility(View.VISIBLE);
        shimmerBooksCategory.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        shimmerBooksCategory.setVisibility(View.GONE);
        shimmerBooksAuthor.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        new CustomSweetAlertDialog(getContext())
                .alertDialogError("Error!", error);
    }

    @Override
    public void setListData(List<BookModel> models) {

    }

    @Override
    public void setData(BookModel model) {

    }
}