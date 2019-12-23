package edu.nuce.giang.ebooks.activities.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.activities.home.EBookHome2Activity;
import edu.nuce.giang.ebooks.base.SharedPrefs;
import edu.nuce.giang.ebooks.models.CheckLoginModel;
import edu.nuce.giang.ebooks.presenters.UserPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IUserPresenter;
import edu.nuce.giang.ebooks.views.UserImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AvatarActivity extends AppCompatActivity implements UserImageView {

    @BindView(R.id.imageUser)
    CircularImageView imageUser;
    @BindView(R.id.avatar_back)
    ImageView onBack;
    @BindView(R.id.btnSaveAvatar)
    TextView saveAvatar;
    @BindView(R.id.choseImage)
    RelativeLayout relativeLayout;

    String filePath = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_change_image);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        int int_position = intent.getIntExtra("int_position", -1);
        int pos = intent.getIntExtra("position", -1);

        if (int_position != -1 && pos != -1) {
            filePath = StoreImageActivity.al_images.get(int_position).getAllImagePath().get(pos);
            Picasso.get()
                    .load(
                            "file://" + filePath
                    )
                    .into(imageUser);
        }

        saveAvatar.setOnClickListener(v -> {
            File file = new File(filePath);
            SharedPrefs prefs = new SharedPrefs(getApplicationContext());
            String stringId = prefs.getModel().getUser().getUserId().toString();
            String userImage = prefs.getModel().getUser().getImage();
            userImage = userImage.split("\\\\")[2];
            if (!userImage.equals(file.getName())) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("userImage",
                        filePath, requestBody);
                RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), stringId);
                UserPresenter presenter = new IUserPresenter(this);
                presenter.updateAvatarUser(userId, body);
            } else
                changeActivity("Ảnh không thay đổi");
        });

        onBack.setOnClickListener(v -> {
            finish();
        });

        relativeLayout.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), StoreImageActivity.class));
        });
    }

    private void changeActivity(String res) {
        Intent intent = new Intent(getApplicationContext(), EBookHome2Activity.class);
        intent.putExtra("status", res);
        startActivity(intent);
    }

    @Override
    public void setAvatar(CheckLoginModel model) {
        if (model != null) {
            Intent intent = new Intent(getApplicationContext(), EBookHome2Activity.class);
            intent.putExtra("status","Thành công!");
            intent.putExtra("user", model);
            startActivity(intent);
        }
    }
}
