package edu.nuce.giang.ebooks.activities.home;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.activities.author.EBookAuthorActivity;
import edu.nuce.giang.ebooks.activities.books.EBookListActivity;
import edu.nuce.giang.ebooks.adapters.AuthorItemClickListener;
import edu.nuce.giang.ebooks.adapters.AuthorsBookAdapter;
import edu.nuce.giang.ebooks.adapters.BookItemClickListener;
import edu.nuce.giang.ebooks.adapters.BookPagerAdapter;
import edu.nuce.giang.ebooks.adapters.SliderBannerAdapter;
import edu.nuce.giang.ebooks.adapters.ViewPagerBooksAdapter;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.AuthorModel;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.activities.detail.EBookFictionActivity;
import edu.nuce.giang.ebooks.presenters.AuthorPresenter;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IAuthorPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.AuthorTopView;
import edu.nuce.giang.ebooks.views.BookView;
import edu.nuce.giang.ebooks.views.BooksScoreView;


public class EBookFragment extends Fragment implements BookView, BookItemClickListener,
        BooksScoreView, AuthorTopView, AuthorItemClickListener {

    @BindView(R.id.imageSlider)
    SliderView sliderBanner;
    @BindView(R.id.viewPagerBook)
    RecyclerView viewPagerBook;
    @BindView(R.id.shimmerPagerBooks)
    ShimmerFrameLayout shimmerPagerBooks;
    @BindView(R.id.viewPagerScore)
    RecyclerView viewPagerScore;
    @BindView(R.id.shimmerPagerScore)
    ShimmerFrameLayout shimmerPagerScore;
    @BindView(R.id.recycler_authors)
    RecyclerView recyclerAuthors;
    @BindView(R.id.shimmerAuthors)
    ShimmerFrameLayout shimmerAuthors;
    @BindView(R.id.swipeRefreshHome)
    SwipeRefreshLayout swipeRefreshHome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ebooks_books, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpSliderBannerImage();

        BookPresenter presenterBook = new IBookPresenter((BookView) this);
        presenterBook.getListData();
        BookPresenter presenterScore = new IBookPresenter((BooksScoreView) this);
        presenterScore.getBooksHighScore();
        AuthorPresenter presenterAuthor = new IAuthorPresenter(this);
        presenterAuthor.getTopAuthors();

        //refresh data
        swipeRefreshHome.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshHome.setOnRefreshListener(() -> {
            presenterBook.getListData();
            presenterScore.getBooksHighScore();
            presenterAuthor.getTopAuthors();
            swipeRefreshHome.setRefreshing(false);
        });
    }

    private void setUpSliderBannerImage() {
        SliderBannerAdapter adapter = new SliderBannerAdapter(getContext());
        sliderBanner.setSliderAdapter(adapter);
    }

    @Override
    public void setListData(List<BookModel> models) {
        BookPagerAdapter adapter = new BookPagerAdapter(getContext(), getFiveItems(models),
                this);
        viewPagerBook.setHasFixedSize(true);
        viewPagerBook.setItemAnimator(new DefaultItemAnimator());
        viewPagerBook.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        ));
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
        new CustomSweetAlertDialog(getContext())
                .alertDialogError("Error!", error);
    }

    private List<BookModel> getFiveItems(List<BookModel> books) {
        List<BookModel> items = new ArrayList<>();
        if (books.size() >= 5) {
            for (int i = 0; i < 5; i++) {
                Random rand = new Random();
                int id = rand.nextInt(books.size());
                if (!items.contains(books.get(id))) {
                    items.add(books.get(id));
                }
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

//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
//                bookImage, "sharedBookModelTran");
        startActivity(intent);
    }

    @Override
    public void setBooksHighScore(List<BookModel> modelList) {
        BookPagerAdapter adapter = new BookPagerAdapter(getContext(), modelList, this);
        viewPagerScore.setHasFixedSize(true);
        viewPagerScore.setItemAnimator(new DefaultItemAnimator());
        viewPagerScore.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false
        ));
        viewPagerScore.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadingBooks() {
        shimmerPagerScore.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingBooks() {
        shimmerPagerScore.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoading(String message) {
        new CustomSweetAlertDialog(getContext())
                .alertDialogError("Error!", message);
    }

    @Override
    public void setAuthorsTop(List<AuthorModel> models) {
        recyclerAuthors.setHasFixedSize(true);
        recyclerAuthors.setLayoutManager(new GridLayoutManager(
                getContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
        ));
        recyclerAuthors.setItemAnimator(new DefaultItemAnimator());
        recyclerAuthors.setNestedScrollingEnabled(true);
        //adapter
        AuthorsBookAdapter adapter = new AuthorsBookAdapter(getContext(), models, this);

        recyclerAuthors.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadingAuthors() {
        shimmerAuthors.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingAuthors() {
        shimmerAuthors.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoadingAuthors(String message) {
        new CustomSweetAlertDialog(getContext())
                .alertDialogError("Error!", message);
    }

    @Override
    public void onItemClicked(ImageView authorImage, AuthorModel model) {
        Intent intent = new Intent(getActivity(), EBookAuthorActivity.class);
        intent.putExtra("authorId", model.getAuthorId());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                authorImage, "authorImageTransition");
        startActivity(intent, options.toBundle());
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

