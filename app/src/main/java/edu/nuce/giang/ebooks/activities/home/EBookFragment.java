package edu.nuce.giang.ebooks.activities.home;

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
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.activities.books.EBookListActivity;
import edu.nuce.giang.ebooks.adapters.BookItemClickListener;
import edu.nuce.giang.ebooks.adapters.SliderBannerAdapter;
import edu.nuce.giang.ebooks.adapters.ViewPagerBooksAdapter;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.activities.detail.EBookFictionActivity;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.BookView;


public class EBookFragment extends Fragment implements BookView, BookItemClickListener {

    @BindView(R.id.imageSlider)
    SliderView sliderBanner;
    @BindView(R.id.viewPagerBook)
    ViewPager viewPagerBook;
    @BindView(R.id.shimmerPagerBooks)
    ShimmerFrameLayout shimmerPagerBooks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ebooks_books, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpSliderBannerImage();

        BookPresenter presenter = new IBookPresenter(this);
        presenter.getListData();
    }

    private void setUpSliderBannerImage() {
        SliderBannerAdapter adapter = new SliderBannerAdapter(getContext());
        sliderBanner.setSliderAdapter(adapter);
    }

    @Override
    public void setListData(List<BookModel> models) {
        ViewPagerBooksAdapter adapter = new ViewPagerBooksAdapter(
                getFiveItems(models),
                getContext(),
                this
        );
        viewPagerBook.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadingData() {
        shimmerPagerBooks.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        shimmerPagerBooks.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        Utils.showAlertDialog(getContext(), "Error", error).show();
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

    @OnClick(R.id.viewAllBooks)
    public void onViewAllBooksClicked() {
        startActivity(new Intent(getActivity(), EBookListActivity.class));
    }

    @Override
    public void onItemClicked(ImageView bookImage, BookModel model) {
        Intent intent = new Intent(getActivity(), EBookFictionActivity.class);
        intent.putExtra("book", model);
        startActivity(intent);
    }

    @Override
    public void setFilterResultsBooks(List<BookModel> models) {

    }

    @Override
    public void setRelatedBooksCategory(List<BookModel> models) {

    }

    @Override
    public void setRelatedBooksAuthor(List<BookModel> models) {

    }

    @Override
    public void setData(BookModel model) {

    }
}

