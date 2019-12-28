package edu.nuce.giang.ebooks.activities.filter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.activities.detail.EBookFictionActivity;
import edu.nuce.giang.ebooks.adapters.BookFilterAdapter;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.BookView;

public class EBookFilterActivity extends AppCompatActivity implements BookView {

    @BindView(R.id.edittext)
    EditText textFilter;
    @BindView(R.id.spin_filter)
    SpinKitView loadingFilter;
    @BindView(R.id.resultFilter)
    RecyclerView resultFilter;

    InputMethodManager imm;
    BookPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_filter_book);

        ButterKnife.bind(this);

        imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        initFilter();

        presenter = new IBookPresenter(this);

        filter();
    }

    @OnClick(R.id.ic_back)
    public void onBack(View view) {
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        finish();
    }

    private void filter() {

        textFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    presenter.getFilterResultsBooks("$");
                } else {
                    presenter.getFilterResultsBooks(s.toString());
                }
            }
        });
    }

    private void initFilter() {
        textFilter.requestFocus();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public void setFilterResultsBooks(List<BookModel> models) {
        BookFilterAdapter adapter = new BookFilterAdapter(this, models);
        resultFilter.setHasFixedSize(true);
        resultFilter.setLayoutManager(new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false
        ));
        resultFilter.setItemAnimator(new DefaultItemAnimator());
        resultFilter.setNestedScrollingEnabled(true);
        resultFilter.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setClickListener(((view, model) -> {
            Intent intent = new Intent(EBookFilterActivity.this, EBookFictionActivity.class);
            intent.putExtra("book", model);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            startActivity(intent);
        }));
    }

    @Override
    public void loadingData() {
        loadingFilter.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        loadingFilter.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        new CustomSweetAlertDialog(this)
                .alertDialogError("Error!", error);
    }

    @Override
    public void setRelatedBooksCategory(List<BookModel> models) {

    }

    @Override
    public void setRelatedBooksAuthor(List<BookModel> models) {

    }

    @Override
    public void setListData(List<BookModel> models) {

    }

    @Override
    public void setData(BookModel model) {

    }
}
