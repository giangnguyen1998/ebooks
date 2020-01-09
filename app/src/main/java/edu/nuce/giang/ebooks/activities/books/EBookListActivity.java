package edu.nuce.giang.ebooks.activities.books;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.adapters.BooksListAdapter;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Medium;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.activities.detail.EBookFictionActivity;
import edu.nuce.giang.ebooks.activities.filter.EBookFilterActivity;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.BookView;


public class EBookListActivity extends AppCompatActivity implements BookView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.shimmer_books_list)
    ShimmerFrameLayout shimmerFrameLayout;
    @BindView(R.id.edittext)
    EditText textFilter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ic_back)
    ImageView onBack;
    @BindView(R.id.toolbar_title)
    MyTextView_Roboto_Medium toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_list);

        ButterKnife.bind(this);

        BookPresenter presenter = new IBookPresenter(this);
        onBack.setVisibility(View.VISIBLE);
        Intent nIntent = getIntent();
        if (nIntent != null && nIntent.getStringExtra("nameAuthor") != null
                && nIntent.getIntExtra("authorId",0) != 0) {
            toolbarTitle.setText("BOOKS OF " + nIntent.getStringExtra("nameAuthor").toUpperCase());
            presenter.getBooksRelatedAuthor(nIntent.getIntExtra("authorId",0));
        } else {
            toolbarTitle.setText("ALL BOOKS");
            presenter.getListData();
        }

        textFilter.setOnClickListener(v -> {
            Intent intent = new Intent(EBookListActivity.this, EBookFilterActivity.class);
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(EBookListActivity.this,
                    toolbar, "filterBook");
            startActivity(intent, options.toBundle());
        });
    }

    @OnClick(R.id.ic_back)
    public void onBack() {
        finish();
    }

    @Override
    public void setListData(List<BookModel> models) {
        BooksListAdapter adapter = new BooksListAdapter(this, models);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setClickListener((view, model) -> {
            Intent intent = new Intent(EBookListActivity.this, EBookFictionActivity.class);
            intent.putExtra("book", model);
            startActivity(intent);
        });
    }

    @Override
    public void loadingData() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        new CustomSweetAlertDialog(this)
                .alertDialogError("Error!", error);
    }

    @Override
    public void setData(BookModel model) {

    }

    @Override
    public void setFilterResultsBooks(List<BookModel> models) {

    }

    @Override
    public void setRelatedBooksCategory(List<BookModel> models) {

    }

    @Override
    public void setRelatedBooksAuthor(List<BookModel> models) {
        BooksListAdapter adapter = new BooksListAdapter(this, models);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setClickListener((view, model) -> {
            Intent intent = new Intent(EBookListActivity.this, EBookFictionActivity.class);
            intent.putExtra("book", model);
            startActivity(intent);
        });
    }
}
