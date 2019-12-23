package edu.nuce.giang.ebooks.activities.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.nuce.giang.ebooks.R;
import edu.nuce.giang.ebooks.Utils;
import edu.nuce.giang.ebooks.activities.home.EBookHome2Activity;
import edu.nuce.giang.ebooks.base.SharedPrefs;
import edu.nuce.giang.ebooks.models.CheckLoginModel;
import edu.nuce.giang.ebooks.models.UserModel;
import edu.nuce.giang.ebooks.presenters.UserPresenter;
import edu.nuce.giang.ebooks.presenters.impl.IUserPresenter;
import edu.nuce.giang.ebooks.views.UserRegisterView;
import edu.nuce.giang.ebooks.views.UserView;

public class EBookLoginActivity extends AppCompatActivity implements UserView, UserRegisterView {
    @BindView(R.id.tv_login)
    TextView textLogin;
    @BindView(R.id.tv_register)
    TextView textRegister;
    @BindView(R.id.viewFliper)
    ViewFlipper viewFlipper;
    @BindView(R.id.editTextLoginEmail)
    EditText textEmail;
    @BindView(R.id.editTextLoginPassword)
    EditText textPassword;
    @BindView(R.id.cirLoginButton)
    Button cirLoginButton;
    @BindView(R.id.editFullName)
    EditText editFullName;
    @BindView(R.id.editPassword)
    EditText editPassword;
    @BindView(R.id.editUsername)
    EditText editUsername;
    @BindView(R.id.editPhoneNumber)
    EditText editPhoneNumber;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebooks_login);

        ButterKnife.bind(this);

        SharedPrefs prefs = new SharedPrefs(this);
        CheckLoginModel model = prefs.getModel();
        if (model != null) {
            changeActivity();
        }

        hideShowLoginRegister();

        UserPresenter presenter = new IUserPresenter(this, this);

        dialog = new ProgressDialog(this);

        cirLoginButton.setOnClickListener(v -> {
            if (textEmail.getText().length() >= 15 && textPassword.getText().length() >= 6) {
                presenter.checkLogin(textEmail.getText().toString(),
                        textPassword.getText().toString());
            } else {
                if (textEmail.getText().length() < 15) {
                    textEmail.setError("Tên đăng nhập phải nhiều hơn hoặc bằng 15 ký tự!");
                } else if (textPassword.getText().length() < 6) {
                    textPassword.setError("Mật khẩu phải nhiều hơn hoặc bằng 6 ký tự!");
                }
            }
        });

        btnRegister.setOnClickListener(v -> {
            if (editFullName.getText().length() >= 10 && editUsername.getText().length() >= 15
                    && editPassword.getText().length() >= 6 && editPhoneNumber.getText().length() == 10) {
                UserModel userModel = new UserModel();
                userModel.setFullname(editFullName.getText().toString());
                userModel.setUsername(editUsername.getText().toString());
                userModel.setPassword(editPassword.getText().toString());
                userModel.setPhone(editPhoneNumber.getText().toString());
                presenter.registerUser(userModel);
            } else {
                if (editFullName.getText().length() < 10) {
                    editFullName.setError("Họ tên phải nhiều hơn hoặc bằng 20 ký tự!");
                } else if (editUsername.getText().length() < 15) {
                    editUsername.setError("Tên đăng nhập phải nhiều hơn hoặc bằng 15 ký tự!");
                } else if (editPassword.getText().length() < 6) {
                    editPassword.setError("Mật khẩu phải nhiều hơn hoặc bằng 6 ký tự!");
                } else if (editPhoneNumber.getText().length() < 10) {
                    editPhoneNumber.setError("Số điện thoại phải là 10 ký tự!");
                }
            }
        });
    }

    private void hideShowLoginRegister() {
        textLogin.setOnClickListener(v -> {
            if (viewFlipper.getDisplayedChild() != 0) {
                viewFlipper.setInAnimation(EBookLoginActivity.this, R.anim.view_transition_in_right);
                viewFlipper.setOutAnimation(EBookLoginActivity.this, R.anim.view_transition_out_right);
                viewFlipper.setDisplayedChild(0);
            }
        });

        textRegister.setOnClickListener(v -> {
            if (viewFlipper.getDisplayedChild() != 1) {
                viewFlipper.setInAnimation(EBookLoginActivity.this, R.anim.view_transition_in_left);
                viewFlipper.setOutAnimation(EBookLoginActivity.this, R.anim.view_transition_out_left);
                viewFlipper.setDisplayedChild(1);
            }
        });
    }

    private void changeActivity() {
        startActivity(new Intent(EBookLoginActivity.this, EBookHome2Activity.class));
        finish();
    }

    private void clearTextRegister() {
        editFullName.setText("");
        editPhoneNumber.setText("");
        editPassword.setText("");
        editUsername.setText("");
    }

    @Override
    public void loginSuccess(CheckLoginModel model) {
        SharedPrefs prefs = new SharedPrefs(this);
        prefs.setModel(model);
        changeActivity();
    }

    @Override
    public void loginFailure(String message) {
        Utils.showAlertDialog(this, "Notification!", message).show();
    }

    @Override
    public void loadingCheck() {
        dialog.setMessage("Doing check account, please wait.");
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void registerSuccess(String message) {
        clearTextRegister();
        Toast.makeText(this, "Đăng ký " + message + "!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void registerFailure(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void processRegister() {
        dialog.setMessage("Doing register account, please wait.");
        dialog.show();
    }

    @Override
    public void finishedProcessRegister() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String error) {
        Utils.showAlertDialog(this, "Error", error).show();
    }
}
