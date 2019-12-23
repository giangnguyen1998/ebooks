package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.models.GridModel;

public class BookCatgorieAdapter extends RecyclerView.Adapter<BookCatgorieAdapter.MyViewHolder>{

    Context context;


    private List<GridModel> OfferList;


    public class MyViewHolder extends RecyclerView.ViewHolder {



        ImageView fieldImage;
        TextView fieldName;

        public MyViewHolder(View view) {
            super(view);

            fieldName=(TextView)view.findViewById(R.id.fieldName);

            fieldImage=(ImageView)view.findViewById(R.id.fieldImage);


        }

    }


    public BookCatgorieAdapter(Context mainActivityContacts, List<GridModel> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cocorecycler_catagories_recycler1, parent, false);


        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GridModel lists = OfferList.get(position);


        holder.fieldName.setText(lists.getFieldName());
        holder.fieldImage.setImageResource(lists.getFieldImage());


    }

    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}


