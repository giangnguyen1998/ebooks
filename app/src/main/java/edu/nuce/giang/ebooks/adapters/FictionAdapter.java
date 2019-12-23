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
import edu.nuce.giang.ebooks.models.FictionModel;


public class FictionAdapter extends RecyclerView.Adapter<FictionAdapter.MyViewHolder>{

    Context context;


    private List<FictionModel> OfferList;


    public class MyViewHolder extends RecyclerView.ViewHolder {



        ImageView image;
        TextView bookName,author,price;

        public MyViewHolder(View view) {
            super(view);

            bookName=(TextView)view.findViewById(R.id.bookName);
            author=(TextView)view.findViewById(R.id.author);
            price=(TextView)view.findViewById(R.id.price);

            image=(ImageView)view.findViewById(R.id.image);


        }

    }


    public FictionAdapter(Context mainActivityContacts, List<FictionModel> offerList) {
        this.context = mainActivityContacts;
        this.OfferList = offerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ebooks_friction, parent, false);


        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FictionModel lists = OfferList.get(position);


        holder.bookName.setText(lists.getBookName());
        holder.image.setImageResource(lists.getImage());
        holder.author.setText(lists.getAuthor());
        holder.price.setText(lists.getPrice());


    }

    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}


