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
import edu.nuce.giang.ebooks.models.HomeRecycleModelClass;

public class BookCatgorieAdapter2 extends RecyclerView.Adapter<BookCatgorieAdapter2.MyViewHolder>{

    Context context;


    private List<HomeRecycleModelClass> OfferList;


    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView bookName,authorName;
        ImageView bookImage;

        public MyViewHolder(View view) {
            super(view);

            bookName=(TextView)view.findViewById(R.id.bookType);
            authorName=(TextView)view.findViewById(R.id.NoOfBooks);
            bookImage=(ImageView)view.findViewById(R.id.iconImage);


        }

    }


    public BookCatgorieAdapter2(Context mainActivityContacts, List<HomeRecycleModelClass> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cocorecycler_catagories_recycler2, parent, false);


        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HomeRecycleModelClass lists = OfferList.get(position);

        holder.bookName.setText(lists.getBookName());
        holder.authorName.setText(lists.getAuthorName());
        holder.bookImage.setImageResource(lists.getBookImage());

    }





    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}
