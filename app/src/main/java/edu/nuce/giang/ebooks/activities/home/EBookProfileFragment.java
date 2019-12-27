package edu.nuce.giang.ebooks.activities.home;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.activities.library.EBookLibraryActivity;
import edu.nuce.giang.ebooks.activities.photo.StoreImageActivity;
import edu.nuce.giang.ebooks.activities.login.EBookLoginActivity;
import edu.nuce.giang.ebooks.base.SharedPrefs;
import edu.nuce.giang.ebooks.models.CheckLoginModel;

public class EBookProfileFragment extends Fragment {

    @BindView(R.id.imageUser)
    CircularImageView imageUser;
    @BindView(R.id.camera)
    ImageView camera;
    @BindView(R.id.fullNameUser)
    TextView fullNameUser;
    @BindView(R.id.usernameUser)
    TextView usernameUser;
    @BindView(R.id.fullName)
    TextView fullName;
    @BindView(R.id.phoneNumber)
    TextView phoneNumber;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.logout)
    RelativeLayout logout;
    @BindView(R.id.library_books)
    RelativeLayout library;
    @BindView(R.id.sizeAllBooks)
    TextView sizeAllBooks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ebooks_profile, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPrefs prefs = new SharedPrefs(getContext());
        CheckLoginModel model = prefs.getModel();

        if (model != null) {
            Picasso.get()
                    .load(Utils.URL + model.getUser().getImage())
                    .placeholder(R.drawable.shadow_bottom_to_top)
                    .into(imageUser);
            fullNameUser.setText(model.getUser().getFullname());
            fullName.setText(model.getUser().getFullname());
            usernameUser.setText(model.getUser().getUsername());
            email.setText(model.getUser().getUsername());
            phoneNumber.setText(model.getUser().getPhone());
            try {
                long sizeBooksLibrary = Utils.getDataBaseUtilsInstance(getContext()).countBooks();
                sizeAllBooks.setText(sizeBooksLibrary + " Books");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        logout.setOnClickListener(v -> {
            showAlertLogout(getContext(), getActivity());
        });

        camera.setOnClickListener(v -> {
            startActivity(new Intent(
                    getContext(), StoreImageActivity.class
            ));
        });
        library.setOnClickListener(v -> {
            startActivity(new Intent(
                    getContext(), EBookLibraryActivity.class
            ));
        });
    }

    private void showAlertLogout(Context context, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Log out");
        builder.setMessage("Are you sure to log out?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPrefs prefs = new SharedPrefs(context);
                        prefs.clearAccount();
                        startActivity(
                                new Intent(
                                        context,
                                        EBookLoginActivity.class
                                )
                        );
                        activity.finish();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }
}
