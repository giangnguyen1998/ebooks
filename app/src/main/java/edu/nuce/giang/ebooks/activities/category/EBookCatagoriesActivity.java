package edu.nuce.giang.ebooks.activities.category;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.activities.detail.EBookFictionActivity;
import edu.nuce.giang.ebooks.activities.filter.EBookFilterActivity;
import edu.nuce.giang.ebooks.adapters.BooksListAdapter;
import edu.nuce.giang.ebooks.customfonts.EditText_Roboto_Medium;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.models.CollectionModel;

public class EBookCatagoriesActivity extends AppCompatActivity implements
        EBookCategoriesMPV.CoCoBookCategoriesView {

    @BindView(R.id.htab_header)
    ImageView imageView;
    @BindView(R.id.ic_back)
    ImageView onBack;
    @BindView(R.id.htab_appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.htab_collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.linearSearch)
    LinearLayout layout;
    @BindView(R.id.shimmer_books_categories)
    ShimmerFrameLayout shimmerBooksCategories;
    @BindView(R.id.recyclerBookCategories)
    RecyclerView recyclerBookCategories;
    @BindView(R.id.backPre)
    ImageView backPre;
    @BindView(R.id.edittext)
    EditText_Roboto_Medium textFilter;
    @BindView(R.id.swipeRefreshCategory)
    SwipeRefreshLayout swipeRefreshCategory;

    private boolean isScroll = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_catagories);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        int categoryId = intent.getIntExtra("categoryId", 0);

        EBookCategoriesMPV.CoCoBookCategoriesPresenter presenter
                = new IEBookCategoriesPresenter(this);

        if (categoryId != 0) {
            presenter.getBooksByCategory(categoryId);
            presenter.getCategoryByCategoryId(categoryId);
        }

        //refresh data
        swipeRefreshCategory.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshCategory.setOnRefreshListener(() -> {
            if (categoryId != 0) {
                presenter.getBooksByCategory(categoryId);
                presenter.getCategoryByCategoryId(categoryId);
            }
            swipeRefreshCategory.setRefreshing(false);
        });

        setupActionBarIcon();

        textFilter.setOnClickListener(v -> {
            Intent intent1 = new Intent(EBookCatagoriesActivity.this, EBookFilterActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(EBookCatagoriesActivity.this,
                    layout, "filterBook");
            startActivity(intent1, options.toBundle());
        });
    }

    void setupActionBarIcon() {
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {

            if ((collapsingToolbarLayout.getHeight() + verticalOffset) <
                    (2 * ViewCompat.getMinimumHeight(collapsingToolbarLayout))) {
                backPre.setVisibility(View.GONE);
                onBack.animate()
                        .alpha(1.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                onBack.setVisibility(View.VISIBLE);
                                isScroll = true;
                            }
                        });
                layout.animate()
                        .translationX(onBack.getHeight() + 13.5f)
                        .setDuration(300);
            } else {
                backPre.setVisibility(View.VISIBLE);
                onBack.animate()
                        .alpha(0.0f)
                        .setDuration(300)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                onBack.setVisibility(View.GONE);
                            }
                        });
                if (isScroll) {
                    layout.animate()
                            .translationX(0)
                            .setDuration(300);
                }
            }
        });

        backPre.setOnClickListener(v -> {
            onBackPressed();
        });

        onBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    @Override
    public void loadingBooks() {
        shimmerBooksCategories.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadedBooks() {
        shimmerBooksCategories.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoading(String message) {
        new CustomSweetAlertDialog(this)
                .alertDialogError("Error!", message);
    }

    @Override
    public void setBooksByCategory(List<BookModel> models) {
        BooksListAdapter adapter = new BooksListAdapter(this, models);
        recyclerBookCategories.setHasFixedSize(true);
        recyclerBookCategories.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));
        recyclerBookCategories.setItemAnimator(new DefaultItemAnimator());
        recyclerBookCategories.setNestedScrollingEnabled(true);
        recyclerBookCategories.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setClickListener((view, model) -> {
            Intent intent = new Intent(EBookCatagoriesActivity.this, EBookFictionActivity.class);
            intent.putExtra("book", model);
            startActivity(intent);
        });
    }

    @Override
    public void setCategoryByCategoryId(CollectionModel model) {
        Picasso.get()
                .load(Utils.URL + model.getImageUrl())
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(imageView);
    }
}
