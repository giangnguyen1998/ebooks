package edu.nuce.giang.ebooks.activities.home;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import edu.nuce.giang.ebooks.activities.category.EBookCatagoriesActivity;
import edu.nuce.giang.ebooks.adapters.CategoryItemClickListener;
import edu.nuce.giang.ebooks.adapters.CollectionsBookAdapter;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.CollectionModel;
import edu.nuce.giang.ebooks.presenters.CollectionPresenter;
import edu.nuce.giang.ebooks.presenters.impl.ICollectionPresenter;
import edu.nuce.giang.ebooks.views.CollectionView;

public class EBookCollectionFragment extends Fragment implements CollectionView,
        CategoryItemClickListener {

    @BindView(R.id.recyclerViewCollections)
    RecyclerView recyclerViewCollections;
    @BindView(R.id.shimmerCollections)
    ShimmerFrameLayout shimmerCollections;
    @BindView(R.id.swipeRefreshCollection)
    SwipeRefreshLayout swipeRefreshCollection;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_ebooks_collections,
                container,
                false
        );

        ButterKnife.bind(this, view);

        return view;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CollectionPresenter presenter = new ICollectionPresenter(this);
        presenter.getListData();

        //refresh data
        swipeRefreshCollection.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshCollection.setOnRefreshListener(()->{
            presenter.getListData();
            swipeRefreshCollection.setRefreshing(false);
        });
    }

    @Override
    public void onItemClicked(ImageView categoryImage, CollectionModel model) {
        Intent intent = new Intent(getActivity(), EBookCatagoriesActivity.class);
        intent.putExtra("categoryId", model.getIdCategory());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                categoryImage, "categoryImageTransition");
        startActivity(intent, options.toBundle());
    }

    @Override
    public void setListData(List<CollectionModel> models) {
        recyclerViewCollections.setHasFixedSize(true);
        recyclerViewCollections.setLayoutManager(new GridLayoutManager(
                getContext(),
                2,
                GridLayoutManager.VERTICAL,
                false
        ));
        recyclerViewCollections.setItemAnimator(new DefaultItemAnimator());
        recyclerViewCollections.setNestedScrollingEnabled(true);

        CollectionsBookAdapter mAdapter = new CollectionsBookAdapter(getContext(), models, this);
        recyclerViewCollections.setAdapter(mAdapter);
    }

    @Override
    public void setData(CollectionModel model) {

    }

    @Override
    public void loadingData() {
        shimmerCollections.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        shimmerCollections.setVisibility(View.GONE);
    }

    @Override
    public void onError(String error) {
        new CustomSweetAlertDialog(getContext())
                .alertDialogError("Error!", error);
    }
}
