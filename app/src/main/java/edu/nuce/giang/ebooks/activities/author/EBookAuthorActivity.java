package edu.nuce.giang.ebooks.activities.author;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.adapters.BookItemClickListener;
import edu.nuce.giang.ebooks.adapters.ViewPagerBooksAdapter;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Medium;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Regular;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.AuthorModel;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.activities.detail.EBookFictionActivity;

public class EBookAuthorActivity extends AppCompatActivity implements EBookAuthorMPV.CoCoBookAuthorView,
        BookItemClickListener {

    @BindView(R.id.expanded_author_description)
    ExpandableTextView expandedAuthorDescription;
    @BindView(R.id.author_read_more)
    MyTextView_Roboto_Medium readMore;
    @BindView(R.id.authorImage)
    ImageView authorImage;
    @BindView(R.id.authorName)
    MyTextView_Roboto_Regular authorName;
    @BindView(R.id.authorBooks)
    MyTextView_Roboto_Regular authorBooks;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.shimmerPagerBooksAuthor)
    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.pagerBooksAuthor)
    ViewPager pagerBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_author);

        ButterKnife.bind(this);

        EBookAuthorMPV.CoCoBookAuthorPresenter presenter = new IEBookAuthorPresenter(this);

        Intent intent = getIntent();
        int authorId = intent.getIntExtra("authorId", 0);
        if (authorId != 0) {
            presenter.getAuthorByAuthorId(authorId);
            presenter.getBooksByAuthorId(authorId);
        }
        setUpAuthorDescription();
        setUpActionBar();
    }

    private void setUpAuthorDescription() {
        Typeface typeface = Typeface.createFromAsset(this.getAssets(), "fonts/Roboto-Italic.ttf");
        expandedAuthorDescription.setTypeface(typeface);

        expandedAuthorDescription.setAnimationDuration(750L);
        expandedAuthorDescription.setInterpolator(new OvershootInterpolator());

        readMore.setOnClickListener(view -> {
            readMore.setText(expandedAuthorDescription.isExpanded() ? "SHOW MORE" : "SHOW LESS");
            expandedAuthorDescription.toggle();
        });
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    void setupColorActionBarIcon(MenuItem favoriteItem) {
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (toolbar.getNavigationIcon() != null)
                toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white),
                        PorterDuff.Mode.SRC_ATOP);
            if ((collapsingToolbarLayout.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(collapsingToolbarLayout))) {
                favoriteItem.getIcon().mutate().setColorFilter(getResources().getColor(R.color.white),
                        PorterDuff.Mode.SRC_ATOP);
                collapsingToolbarLayout.setTitle(authorName.getText().toString());
                favoriteItem.setVisible(true);
            } else {
                collapsingToolbarLayout.setTitle(" ");
                favoriteItem.setVisible(false);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem favoriteItem = menu.findItem(R.id.favorite);
        setupColorActionBarIcon(favoriteItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setAuthor(AuthorModel author) {
        Picasso.get()
                .load(Utils.URL + author.getAuthorImage())
                .into(authorImage);
        String bookCount = author.getTotalBook() + " Books";
        authorName.setText(author.getAuthorName());
        authorBooks.setText(bookCount);
        expandedAuthorDescription.setText(author.getAuthorDes());
    }

    @Override
    public void setBooksAuthor(List<BookModel> models) {
        ViewPagerBooksAdapter adapter = new ViewPagerBooksAdapter(getFiveItems(models), this, this);
        pagerBooks.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void loadingAuthor() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadedAuthor() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void loadingBooksAuthor() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadedBooksAuthor() {
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onErrorLoading(String message) {
        new CustomSweetAlertDialog(this)
                .alertDialogError("Error!", message);
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
        Intent intent = new Intent(this, EBookFictionActivity.class);
        intent.putExtra("book", model);
        startActivity(intent);
    }
}
