package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.models.GridModel;


public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder>{

    Context context;
    private List<GridModel> OfferList;

    public class MyViewHolder extends RecyclerView.ViewHolder {



        ImageView fieldImage,tick;
        TextView fieldName;

        public MyViewHolder(View view) {
            super(view);

            fieldName=(TextView)view.findViewById(R.id.fieldName);

            fieldImage=(ImageView)view.findViewById(R.id.fieldImage);
            tick=(ImageView)view.findViewById(R.id.tick);


        }
    }

    public GridAdapter(Context mainActivityContacts, List<GridModel> offerList) {
        this.OfferList = offerList;
        this.context = mainActivityContacts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gerne_recycler, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final GridModel lists = OfferList.get(position);


        holder.fieldName.setText(lists.getFieldName());
        holder.fieldImage.setImageResource(lists.getFieldImage());

//        if (position == 0 || position == 5) {
//
//           holder.tick.setVisibility(View.VISIBLE);
//        }
//        else {
//            holder.tick.setVisibility(View.GONE);
//        }
//
//
//        if (position == position)


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (lists.isSelected() == true){
                    lists.setSelected(false);

                    holder.tick.setVisibility(View.GONE);

                }else {
                    lists.setSelected(true);
                    holder.tick.setVisibility(View.VISIBLE);
                }
            }
        });

    }



    @Override
    public int getItemCount() {
        return OfferList.size();

    }

}

