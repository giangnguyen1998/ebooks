package edu.nuce.giang.ebooks.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.adapters.BooksLibraryAdapter;
import edu.nuce.giang.ebooks.models.BookModel;
import edu.nuce.giang.ebooks.models.LibraryModel;
import edu.nuce.giang.ebooks.presenters.BookPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IBookPresenter;
import edu.nuce.giang.ebooks.views.BookLibraryView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibraryFragment extends Fragment implements BookLibraryView {

    @BindView(R.id.recyclerLibrary)
    RecyclerView recyclerLibrary;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.nothingBook)
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ebooks_library, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BookPresenter presenter = new IBookPresenter(this);
        try {
            List<LibraryModel> list = Utils.getDataBaseUtilsInstance(getContext()).getAllBooks();
            if (list.size() > 0) {
                textView.setVisibility(View.GONE);
                List<Integer> ids = new ArrayList<>();
                for (LibraryModel item : list) {
                    ids.add(item.getBookId());
                }
                presenter.getBooksByIds(ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setListData(List<BookModel> models) {
        BooksLibraryAdapter adapter = new BooksLibraryAdapter(getContext(), models);

        recyclerLibrary.setHasFixedSize(true);
        recyclerLibrary.setLayoutManager(new GridLayoutManager(
                getContext(), 2, GridLayoutManager.VERTICAL, false
        ));
        recyclerLibrary.setNestedScrollingEnabled(true);
        recyclerLibrary.setItemAnimator(new DefaultItemAnimator());
        recyclerLibrary.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setData(BookModel model) {

    }

    @Override
    public void loadingData() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        Utils.showAlertDialog(getContext(), "Error", error).show();
    }
}