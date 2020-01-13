package edu.nuce.giang.ebooks.activities.detail;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.adapters.CommentsBookAdapter;
import edu.nuce.giang.ebooks.base.SharedPrefs;
import edu.nuce.giang.ebooks.customfonts.MyTextView_Roboto_Regular;
import edu.nuce.giang.ebooks.dialogs.CustomSweetAlertDialog;
import edu.nuce.giang.ebooks.models.CommentModel;
import edu.nuce.giang.ebooks.presenters.CommentPresenter;
import edu.nuce.giang.ebooks.presenters.impl.ICommentPresenter;
import edu.nuce.giang.ebooks.views.CommentSaveView;
import edu.nuce.giang.ebooks.views.CommentView;

public class EBookReviewFragment extends Fragment implements CommentView, CommentSaveView {

    @BindView(R.id.recyclerComments)
    RecyclerView recyclerComments;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.count_reading_book)
    MyTextView_Roboto_Regular countReadingBook;
    @BindView(R.id.book_rating_text)
    MyTextView_Roboto_Regular textBookRating;
    @BindView(R.id.book_rating)
    RatingBar bookRating;
    @BindView(R.id.seekBarScore)
    SeekBar seekBar;
    @BindView(R.id.tv_score)
    TextView textViewScore;
    @BindView(R.id.user_comment)
    EditText userComment;
    @BindView(R.id.send_comment)
    ImageView sendComment;

    private ProgressDialog dialog;
    private CommentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ebooks_reviews, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new ProgressDialog(getContext());

        presenter = new ICommentPresenter(this, this);
        if (getArguments() != null) {
            int bookId = getArguments().getInt("id");
            presenter.getCommentsOfBook(bookId);
            sendComment.setOnClickListener(v -> {
                if (userComment.getText().length() < 10) {
                    userComment.setError("Bình luận tối thiểu phải có 10 ký tự!");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            userComment.setError(null);
                        }
                    }, 2000);
                } else {
                    CommentModel model = new CommentModel();
                    model.setBookId(bookId);
                    model.setUserId((new SharedPrefs(getContext()).getModel().getUser().getUserId()));
                    model.setContent(userComment.getText().toString());
                    model.setScore(Float.valueOf((float) seekBar.getProgress() / 10));
                    presenter.saveComment(model);
                }
            });
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textViewScore.setText(String.valueOf((float) this.progress / 10));
            }
        });
    }

    @Override
    public void loadingData() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingData() {
        progressBar.setVisibility(View.GONE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void setListComments(List<CommentModel> models) {
        CommentsBookAdapter adapter = new CommentsBookAdapter(getContext(),
                convertCreatedDateOfComments(models));
        recyclerComments.setHasFixedSize(true);
        recyclerComments.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false
        ));
        recyclerComments.setItemAnimator(new DefaultItemAnimator());
        recyclerComments.setNestedScrollingEnabled(true);
        recyclerComments.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        countReadingBook.setText(String.valueOf(models.size()));
        textBookRating.setText(String.valueOf(computeRating(models)));
        bookRating.setRating(computeRating(models));
    }

    @Override
    public void onErrorLoading(String error) {
        new CustomSweetAlertDialog(getContext())
                .alertDialogError("Error!", error);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<CommentModel> convertCreatedDateOfComments(List<CommentModel> models) {
        for (CommentModel model : models) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    Locale.ENGLISH);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(
                    "dd-MM-yyyy",
                    Locale.ENGLISH);
            LocalDate date = LocalDate.parse(model.getCreateddate(), inputFormatter);
            String formattedDate = outputFormatter.format(date);
            model.setCreateddate(formattedDate);
        }
        return models;
    }

    private float computeRating(List<CommentModel> models) {
        float sum = 0.0f;
        float result = 0.0f;
        if (models.size() > 0) {
            for (CommentModel model : models) {
                sum += model.getScore();
            }
            result = sum / models.size();
        }
        return result;
    }

    private void clearComment() {
        seekBar.setProgress(0);
        userComment.setText("");
        textViewScore.setText("0.0");
    }

    @Override
    public void processSaveComment() {
        dialog.setMessage("Doing save comment, please wait.");
        dialog.show();
    }

    @Override
    public void finishedProcessSaveComment() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void saveCommentSuccess(String status) {
        clearComment();
        Toast.makeText(getContext(), "Thêm bình luận " + status + "!", Toast.LENGTH_SHORT).show();
        assert getArguments() != null;
        presenter.getCommentsOfBook(getArguments().getInt("id"));
    }

    @Override
    public void saveCommentFailure(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorSave(String error) {
        new CustomSweetAlertDialog(getContext())
                .alertDialogError("Error!", error);
    }
}