package edu.nuce.giang.ebooks.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.adapters.HomeRecyclerAdapter;
import edu.nuce.giang.ebooks.models.HomeRecycleModelClass;

public class PopularFragment extends Fragment {
    private ArrayList<HomeRecycleModelClass> homeListModelClassArrayList1;
    private RecyclerView menuRecycler;
    private HomeRecyclerAdapter bAdapter;

    int bookImage[]={R.drawable.lord, R.drawable.the_world,R.drawable.brain,R.drawable.broke,
            R.drawable.fred,R.drawable.online};
    String bookName[]={"Hugo Huesca Dungeon Lord","The World Of Abstra","Mind and  Pieces of Light",
            "Hugo Huesca Dungeon Lord","The World Of Abstra","Mind and  Pieces of Light"};
    String authorName[]={"M Methew","Create Media","Charles","M Methew","Create Media","Charles"};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_ebooks_popular, container, false);

        menuRecycler = view.findViewById(R.id.menuRecycler);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        menuRecycler.setLayoutManager(layoutManager);
        menuRecycler.setItemAnimator(new DefaultItemAnimator());

        homeListModelClassArrayList1 = new ArrayList<>();

        for (int i = 0; i < bookImage.length; i++) {
            HomeRecycleModelClass beanClassForRecyclerView_contacts = new HomeRecycleModelClass(bookImage[i],bookName[i],authorName[i]);
            homeListModelClassArrayList1.add(beanClassForRecyclerView_contacts);
        }
        bAdapter = new HomeRecyclerAdapter(getContext(),homeListModelClassArrayList1);
        menuRecycler.setAdapter(bAdapter);

        return view;

    }

}

