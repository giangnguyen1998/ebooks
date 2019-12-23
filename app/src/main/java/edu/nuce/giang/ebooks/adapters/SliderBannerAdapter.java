package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;

public class SliderBannerAdapter extends SliderViewAdapter<SliderBannerAdapter.SliderAdapterVH> {

    private Context context;

    public SliderBannerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(
                R.layout.item_slider_banner,
                parent,
                false
        );
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
        String imageUrl = Utils.URL + "uploads/images/";

        switch (position) {
            case 0:
                Picasso.get()
                        .load(imageUrl + "Slider-1-BOOKS.jpg")
                        .placeholder(R.drawable.shadow_bottom_to_top)
                        .into(viewHolder.itemImageBanner);
                break;
            case 1:
                Picasso.get()
                        .load(imageUrl + "Slider5.png")
                        .placeholder(R.drawable.shadow_bottom_to_top)
                        .into(viewHolder.itemImageBanner);
                break;
            case 2:
                Picasso.get()
                        .load(imageUrl + "slider24.jpg")
                        .placeholder(R.drawable.shadow_bottom_to_top)
                        .into(viewHolder.itemImageBanner);
                break;
            default:
                Picasso.get()
                        .load(imageUrl + "Pencils-books-slider-1.jpg")
                        .placeholder(R.drawable.shadow_bottom_to_top)
                        .into(viewHolder.itemImageBanner);
                break;

        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        @BindView(R.id.item_image_banner)
        ImageView itemImageBanner;

        public SliderAdapterVH(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
