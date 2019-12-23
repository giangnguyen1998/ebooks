package edu.nuce.giang.ebooks.dialogs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import edu.nuce.giang.ebooks.R;
import me.shaohui.bottomdialog.BaseBottomDialog;

@SuppressLint("ValidFragment")
public class ShareBottomDialog extends BaseBottomDialog {

    EditText textComment;
    ImageView closeComment;
    private Context context;

    @SuppressLint("ValidFragment")
    public ShareBottomDialog(Context context) {
        this.context = context;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.layout_ebooks_dialog;
    }

    @Override
    public void bindView(View v) {
        textComment = v.findViewById(R.id.user_comment);
        closeComment = v.findViewById(R.id.close_comment);

        textComment.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        closeComment.setOnClickListener(view -> {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            hideDialog();
        });
    }

    private void hideDialog() {
        this.dismiss();
    }

    @Override
    public boolean getCancelOutside() {

        return false;
    }
}
