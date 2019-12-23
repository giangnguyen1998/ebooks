package edu.nuce.giang.ebooks.activities.detail;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import java.util.List;
import java.util.Objects;

import at.blogc.android.views.ExpandableTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Bold;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Medium;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.BookView;

public class EBookFragment extends Fragment implements BookView {

    @BindView(R.id.book_description)
    ExpandableTextView bookDescription;
    @BindView(R.id.book_name)
    MyTextView_Roboto_Bold bookName;
    @BindView(R.id.book_author)
    MyTextView_Roboto_Bold bookAuthor;
    @BindView(R.id.book_publisher)
    MyTextView_Roboto_Bold bookPublisher;
    @BindView(R.id.book_category)
    MyTextView_Roboto_Bold bookCategory;
    @BindView(R.id.book_read_more)
    MyTextView_Roboto_Medium readMore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_ebooks_detail,
                container,
                false
        );

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpBookDescription();

        if (getArguments() != null) {
            int id = getArguments().getInt("id");
            BookPresenter presenter = new IBookPresenter(this);
            presenter.getData(id);
        }
    }

    private void setUpBookDescription() {
        Typeface typeface = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(),
                "fonts/Roboto-Italic.ttf");
        bookDescription.setTypeface(typeface);

        bookDescription.setAnimationDuration(750L);
        bookDescription.setInterpolator(new OvershootInterpolator());

        readMore.setOnClickListener(view -> {
            readMore.setText(bookDescription.isExpanded() ? "SHOW MORE" : "SHOW LESS");
            bookDescription.toggle();
        });
    }

    @Override
    public void setData(BookModel model) {
        bookName.setText(model.getName());
        bookAuthor.setText(model.getAuthorName());
        bookCategory.setText(model.getCategoryName());
        bookPublisher.setText(model.getPublisher());
        bookDescription.setText(model.getDescription());
    }

    @Override
    public void onError(String error) {
        Utils.showAlertDialog(getContext(), "Error", error).show();
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
    public void setListData(List<BookModel> models) {

    }

    @Override
    public void loadingData() {

    }

    @Override
    public void hideLoadingData() {

    }
}
