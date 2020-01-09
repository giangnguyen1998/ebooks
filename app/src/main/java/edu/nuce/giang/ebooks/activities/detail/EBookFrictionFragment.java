package edu.nuce.giang.ebooks.activities.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import edu.nuce.giang.ebooks.activities.author.EBookAuthorActivity;
import edu.nuce.giang.ebooks.activities.books.EBookListActivity;
import edu.nuce.giang.ebooks.activities.category.EBookCatagoriesActivity;
import edu.nuce.giang.ebooks.adapters.BookItemClickListener;
import edu.nuce.giang.ebooks.adapters.BookPagerAdapter;
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
    RecyclerView viewPagerBookRelatedCategory;
    @BindView(R.id.viewPagerBookRelatedAuthor)
    RecyclerView viewPagerBookRelatedAuthor;
    @BindView(R.id.relatedBookAuthor)
    MyTextView_Roboto_Medium relatedBookAuthor;
    @BindView(R.id.showRelatedBookAuthor)
    MyTextView_Roboto_Medium showRelatedBookAuthor;
    @BindView(R.id.showRelatedBookCategory)
    MyTextView_Roboto_Medium showRelatedBookCategory;

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

            showRelatedBookAuthor.setOnClickListener(v -> {
                Intent nIntent = new Intent(getContext(), EBookListActivity.class);
                nIntent.putExtra("nameAuthor", authorName);
                nIntent.putExtra("authorId", authorId);
                startActivity(nIntent);
            });
            showRelatedBookCategory.setOnClickListener(v -> {
                Intent nIntent = new Intent(getContext(), EBookCatagoriesActivity.class);
                nIntent.putExtra("categoryId", categoryId);
                startActivity(nIntent);
            });
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
        BookPagerAdapter adapter = new BookPagerAdapter(getContext(), getFiveItems(models),
                this);
        viewPagerBookRelatedCategory.setHasFixedSize(true);
        viewPagerBookRelatedCategory.setItemAnimator(new DefaultItemAnimator());
        viewPagerBookRelatedCategory.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        ));
        viewPagerBookRelatedCategory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setRelatedBooksAuthor(List<BookModel> models) {
        BookPagerAdapter adapter = new BookPagerAdapter(getContext(), getFiveItems(models),
                this);
        viewPagerBookRelatedAuthor.setHasFixedSize(true);
        viewPagerBookRelatedAuthor.setItemAnimator(new DefaultItemAnimator());
        viewPagerBookRelatedAuthor.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        ));
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