package edu.nuce.giang.ebooks.activities.home;

import android.app.ActivityOptions;
import android.content.Intent;
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
import android.widget.ImageView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.adapters.AuthorItemClickListener;
import edu.nuce.giang.ebooks.adapters.AuthorsBookAdapter;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.AuthorModel;
import edu.nuce.giang.ebooks.activities.author.EBookAuthorActivity;
import edu.nuce.giang.ebooks.presenters.AuthorPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IAuthorPresenter;
import edu.nuce.giang.ebooks.views.AuthorView;

public class EBookAuthorFragment extends Fragment implements AuthorView,
        AuthorItemClickListener {

    @BindView(R.id.recycler_authors)
    RecyclerView recyclerAuthors;
    @BindView(R.id.shimmerAuthors)
    ShimmerFrameLayout shimmerAuthors;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_ebooks_authors,
                container,
                false
        );

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AuthorPresenter presenter = new IAuthorPresenter(this);
        presenter.getListData();
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
    public void setListData(List<AuthorModel> models) {
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
    public void setData(AuthorModel model) {

    }

    @Override
    public void loadingData() {
        shimmerAuthors.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        shimmerAuthors.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        new CustomSweetAlertDialog(getContext())
                .alertDialogError("Error!", error);
    }
}
