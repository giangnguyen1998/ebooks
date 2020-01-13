package edu.nuce.giang.ebooks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Regular;
import edu.nuce.giang.ebooks.models.CommentModel;

public class CommentsBookAdapter extends RecyclerView.Adapter<CommentsBookAdapter.CommentViewHolder> {

    private Context context;
    private List<CommentModel> models;

    public CommentsBookAdapter(Context context, List<CommentModel> models) {
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(
            R.layout.item_user_review,
            viewGroup,
            false
        );
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int i) {
        Picasso.get()
                .load(Utils.URL + models.get(i).getImage())
                .placeholder(R.drawable.shadow_bottom_to_top)
                .into(commentViewHolder.userImage);
        commentViewHolder.textScore.setText(models.get(i).getScore() + "/5");
        commentViewHolder.userScore.setRating(models.get(i).getScore());
        commentViewHolder.userName.setText(models.get(i).getFullname());
        commentViewHolder.contentReview.setText(models.get(i).getContent());
        commentViewHolder.dateTime.setText(models.get(i).getCreateddate());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_image)
        CircularImageView userImage;
        @BindView(R.id.user_name)
        MyTextView_Roboto_Regular userName;
        @BindView(R.id.user_score)
        RatingBar userScore;
        @BindView(R.id.score_char)
        TextView textScore;
        @BindView(R.id.content_review)
        MyTextView_Roboto_Regular contentReview;
        @BindView(R.id.datetime)
        TextView dateTime;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
